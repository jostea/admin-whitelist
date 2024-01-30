package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class GunshotService {

    private final WhitelistProperties properties;

    @Scheduled(fixedDelayString = "${whitelist.gunshot-server}", timeUnit = TimeUnit.HOURS)
    public void gunshot() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        try {
            log.info("About to make gunshot on the server...");
            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.GUNSHOT.getCommand());

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info(output);
        } catch (IOException e) {
            log.error("Unable to do shot on the server: ", e);
        }
    }
}
