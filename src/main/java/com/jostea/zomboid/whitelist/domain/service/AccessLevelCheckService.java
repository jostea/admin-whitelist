package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.ScheduleConfig;
import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.domain.repository.extension.PlayerAccessLevelRepository;
import com.jostea.zomboid.whitelist.domain.repository.game.WhitelistRepository;
import com.jostea.zomboid.whitelist.domain.model.PlayerAccessLevel;
import com.jostea.zomboid.whitelist.domain.model.Whitelist;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Async(ScheduleConfig.ASYNC_TASK_EXECUTOR)
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

        final List<Whitelist> whitelistUsers = whitelistRepository.findByAccessLevelIn(searchedAccessLevelTypes);
        final Map<String, PlayerAccessLevel> playerAccessLevelMap = playerAccessLevelRepository.findAllMap();

        final Set<String> steamIdSet = new HashSet<>();
        final Set<String> nicknameSet = new HashSet<>();

        for (final Whitelist user : whitelistUsers) {
            // get real admin player
            final PlayerAccessLevel playerAccessLevel = playerAccessLevelMap.get(user.getUsername());

            // map returns null when get method cannot find legal admin in database, then ban him
            if (isNull(playerAccessLevel)) {
                steamIdSet.add(user.getSteamId());
                nicknameSet.add(user.getUsername());

                log.info("Banned user with {} Steam ID", user.getSteamId());
            }
        }

        final CompositeBanSet playersToBan = new CompositeBanSet();
        playersToBan.setSteamIdSet(steamIdSet);
        playersToBan.setNicknameSet(nicknameSet);

        return playersToBan;
    }

    private void banPlayers(final CompositeBanSet playersToBan) {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        executeRconPlayerCommand(rcon, RconCommandType.BAN_BY_STEAM_ID, playersToBan.getSteamIdSet(), Collections.emptyList());
        executeRconPlayerCommand(rcon, RconCommandType.SET_ACCESS_LEVEL, playersToBan.getNicknameSet(), Collections.emptyList());
    }

    @Getter
    @RequiredArgsConstructor
    private enum AccessLevelType {

        ADMIN("admin"),
        MODERATOR("moderator");

        private final String typeName;
    }

    @Data
    private static class CompositeBanSet {

        private Set<String> steamIdSet;

        private Set<String> nicknameSet;
    }
}
