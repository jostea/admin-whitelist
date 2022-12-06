package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.repository.PlayerAccessLevelRepository;
import com.jostea.zomboid.whitelist.repository.WhitelistRepository;
import com.jostea.zomboid.whitelist.repository.model.PlayerAccessLevel;
import com.jostea.zomboid.whitelist.repository.model.Whitelist;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconPlayerCommand;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessLevelCheckService {

    private final WhitelistProperties properties;

    private final WhitelistRepository whitelistRepository;

    private final PlayerAccessLevelRepository playerAccessLevelRepository;

    @Scheduled(fixedDelayString = "${whitelist.player-access-level-check-delay-seconds}", timeUnit = TimeUnit.SECONDS)
    public void check() {
        log.info("Access level heartbeat");

        final CompositeBanSet playersToBan = prepareBannedPlayers();
        banPlayers(playersToBan);
    }

    private List<String> getSearchedAccessLevelTypes() {
        return List.of(
                AccessLevelType.ADMIN.getTypeName(),
                AccessLevelType.MODERATOR.getTypeName()
        );
    }

    private CompositeBanSet prepareBannedPlayers() {
        final List<String> searchedAccessLevelTypes = getSearchedAccessLevelTypes();

        final List<Whitelist> whitelistUsers = whitelistRepository.findByAccesslevelIn(searchedAccessLevelTypes);
        final Map<String, PlayerAccessLevel> playerAccessLevelMap = playerAccessLevelRepository.findAllMap();

        final Set<String> steamIdSet = new HashSet<>();
        final Set<String> nicknameSet = new HashSet<>();

        for (final Whitelist user : whitelistUsers) {
            // get real admin player
            final PlayerAccessLevel playerAccessLevel = playerAccessLevelMap.get(user.getUsername());

            // map returns null when get method cannot find legal admin in database, then ban him
            if (isNull(playerAccessLevel)) {
                steamIdSet.add(user.getSteamid());
                nicknameSet.add(user.getUsername());

                log.info("Banned user with {} Steam ID", user.getSteamid());
            }
        }

        final CompositeBanSet playersToBan = new CompositeBanSet();
        playersToBan.setSteamIdSet(steamIdSet);
        playersToBan.setNicknameSet(nicknameSet);

        return playersToBan;
    }

    private void banPlayers(final CompositeBanSet playersToBan) {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        executeRconPlayerCommand(RconCommandType.BAN_BY_STEAM_ID, playersToBan.getSteamIdSet(), rcon);
        executeRconPlayerCommand(RconCommandType.SET_ACCESS_LEVEL, playersToBan.getNicknameSet(), rcon);
    }
}
