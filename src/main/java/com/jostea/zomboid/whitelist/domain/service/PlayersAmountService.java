package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    /**
     * Parses the "players" RCON output, where every online player is listed on its own line prefixed with "-".
     */
    public List<String> getOnlinePlayers() {
        return getAmountOfPlayers().lines()
                .map(String::trim)
                .filter(line -> line.startsWith("-"))
                .map(line -> line.substring(1).trim())
                .filter(name -> !name.isEmpty())
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }
}
