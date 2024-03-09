package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;


@Slf4j
@Service
@RequiredArgsConstructor
public class PlayersAmountService {

    private final WhitelistProperties properties;

    public String getAmountOfPlayers() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        try {
            log.info("About to get amount of players on the server...");
            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.PLAYERS.getCommand());

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Output: {}", output.replace("-", "\n"));
            return output;
        } catch (IOException e) {
            log.error("Unable to get amount of players on the server: ", e);
        }
        return "";
    }
}
