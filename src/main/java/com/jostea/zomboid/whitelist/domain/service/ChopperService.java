package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.ScheduleConfig;
import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
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
public class ChopperService {

    private final WhitelistProperties properties;

    @Async(ScheduleConfig.ASYNC_TASK_EXECUTOR)
    @Scheduled(fixedDelayString = "${whitelist.chopper-server}", timeUnit = TimeUnit.HOURS)
    public void chopper() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        try {
            log.info("About to call chopper on the server...");
            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.CHOPPER.getCommand());

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Output: {}", output);
        } catch (IOException e) {
            log.error("Unable to call chopper on the server: ", e);
        }
    }
}
