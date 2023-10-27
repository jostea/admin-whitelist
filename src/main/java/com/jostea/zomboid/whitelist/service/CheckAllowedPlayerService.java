package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.repository.extension.AllowedPlayerRepository;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;
import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconPlayerCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckAllowedPlayerService {

    private static final String KICK_REASON = "Proydi sobesedovanie v Discorde";

    private final WhitelistProperties properties;

    private final AllowedPlayerRepository allowedPlayerRepository;

//    @Scheduled(fixedDelayString = "${whitelist.player-access-to-server-delay-seconds}", timeUnit = TimeUnit.SECONDS)
    public void check() {
        log.info("Allowed player heartbeat");

        final WhitelistProperties.Rcon rcon = properties.getRcon();
        try {
            final List<String> whitelistsOfPlayers = allowedPlayerRepository.getAllUsernames();

            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.GET_LIST_OF_PLAYERS.getCommand());
            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            final List<String> currentPlayersList = new ArrayList<>(List.of(output.replace("\n", "").split("-")));
            currentPlayersList.remove(0);

            final Set<String> playersToKick = currentPlayersList.stream()
                    .filter(player -> !whitelistsOfPlayers.contains(player))
                    .collect(Collectors.toSet());

            executeRconPlayerCommand(rcon, RconCommandType.KICK_PLAYER, playersToKick, Collections.singletonList(KICK_REASON));
        } catch (IOException e) {
            log.error("Unable to kick list of players: ", e);
        }
    }
}
