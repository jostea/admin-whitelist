package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.ScheduleConfig;
import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
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

    public String save() {
        return EventService.callEvent("SAVE_SERVER", properties.getRcon());
    }
    @Async(ScheduleConfig.ASYNC_TASK_EXECUTOR)
    @Scheduled(fixedDelayString = "${whitelist.save-server-delay-hours}", timeUnit = TimeUnit.HOURS)
    public void autoSave() {
        save();
    }
}
