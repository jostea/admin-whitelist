package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuitService {

    private final WhitelistProperties properties;

    public void stopServer() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();
        try {
            log.info("About to stop server state...");
            executeRconCommand(rcon, RconCommandType.QUIT.getCommand());

        } catch (IOException  e) {
            log.error("Unable to stop server state: ", e);
        }
    }
}
