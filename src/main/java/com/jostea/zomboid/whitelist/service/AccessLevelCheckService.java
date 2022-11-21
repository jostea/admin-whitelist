package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.repository.BannedIdRepository;
import com.jostea.zomboid.whitelist.repository.PlayerAccessLevelRepository;
import com.jostea.zomboid.whitelist.repository.WhitelistRepository;
import com.jostea.zomboid.whitelist.repository.model.BannedId;
import com.jostea.zomboid.whitelist.repository.model.PlayerAccessLevel;
import com.jostea.zomboid.whitelist.repository.model.AccessLevelType;
import com.jostea.zomboid.whitelist.repository.model.Whitelist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessLevelCheckService {

    private static final String BAN_REASON_MESSAGE = "Automatically banned";

    private final WhitelistRepository whitelistRepository;

    private final PlayerAccessLevelRepository playerAccessLevelRepository;

    private final BannedIdRepository bannedIdRepository;

    @Scheduled(fixedDelayString = "${whitelist.player-access-level-check-delay}")
    public void check() {
        log.info("Access level heartbeat");

        final List<String> searchedAccessLevelTypes = getSearchedAccessLevelTypes();

        final List<Whitelist> whitelistUsers = whitelistRepository.findByAccesslevelIn(searchedAccessLevelTypes);
        final Map<String, PlayerAccessLevel> playerAccessLevelMap = playerAccessLevelRepository.findAllMap();

        final Set<BannedId> steamIdSet = new HashSet<>();

        for (final Whitelist user : whitelistUsers) {
            // get real admin player
            final PlayerAccessLevel playerAccessLevel = playerAccessLevelMap.get(user.getUsername());

            // map returns null when get method cannot find legal admin in database, then ban him
            if (isNull(playerAccessLevel)) {
                user.setBanned(1);
                user.setAccesslevel(null);

                steamIdSet.add(createBannedId(user.getSteamid()));

                log.info("Banned user with {} username", user.getUsername());
            }
        }

        // save the changes in appropriate tables(in database)
        whitelistRepository.saveAll(whitelistUsers);
        bannedIdRepository.saveAll(steamIdSet.stream().toList());
    }

    private List<String> getSearchedAccessLevelTypes() {
        return List.of(
                AccessLevelType.ADMIN.getTypeName(),
                AccessLevelType.MODERATOR.getTypeName()
        );
    }

    private BannedId createBannedId(final String steamId) {
        final BannedId bannedId = new BannedId();
        bannedId.setSteamid(steamId);
        bannedId.setReason(BAN_REASON_MESSAGE);

        return bannedId;
    }
}
