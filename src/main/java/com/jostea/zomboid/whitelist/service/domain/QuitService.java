package com.jostea.zomboid.whitelist.service.domain;

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
import java.util.concurrent.TimeUnit;

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
