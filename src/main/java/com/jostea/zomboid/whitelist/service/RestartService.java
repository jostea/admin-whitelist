package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.CommandUtils;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestartService {

    private final WhitelistProperties properties;

    public void restartServer() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        try {

            log.info("About to restart server state...");
            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.QUIT.getCommand());

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Server stopped: {}", output);
            TimeUnit.SECONDS.sleep(10);
            CommandUtils.executeCommand("cmd /c start \"\" \"" + properties.getServerStartPath() + "\"");
            log.info("Server is starting ...");

        } catch (IOException | InterruptedException e) {
            log.error("Unable to restart server state: ", e);
        }
    }
}
