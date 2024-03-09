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
public class SaveServerStateService {

    private final WhitelistProperties properties;

    @Async(ScheduleConfig.ASYNC_TASK_EXECUTOR)
    @Scheduled(fixedDelayString = "${whitelist.save-server-delay-hours}", timeUnit = TimeUnit.HOURS)
    public void save() {
        final WhitelistProperties.Rcon rcon = properties.getRcon();

        try {
            log.info("About to save server state...");
            final InputStream inputStream = executeRconCommand(rcon, RconCommandType.SAVE_SERVER.getCommand());

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Server saved: {}", output);
        } catch (IOException e) {
            log.error("Unable to save server state: ", e);
        }
    }
}
