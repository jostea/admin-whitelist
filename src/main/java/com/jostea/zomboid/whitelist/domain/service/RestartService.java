package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.ScheduleConfig;
import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.CommandUtils;
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
public class RestartService {

    private final WhitelistProperties properties;

    private final ServerStatusService serverStatusService;

    public String restartServer() {
        try {
            if (serverStatusService.getCurrentStartState()) {
                serverStatusService.disallowServerToStart();
                return startServer();
            }

            if (serverStatusService.getCurrentRestartState()) {
                serverStatusService.disallowServerToRestart();
                return restart();
            }
        } catch (IOException | InterruptedException e) {
            log.error("Unable to restart server state: ", e);
            return "Service Error";
        }
        return "Forbidden to Start/Restart Server";
    }

    private String startServer() {
        CommandUtils.executeCommand("cmd /c start \"\" \"" + properties.getServerStartPath() + "\"");
        log.info("Server is starting ...");
        return "Server Started";
    }

    private void stopServer() throws IOException {
        final WhitelistProperties.Rcon rcon = properties.getRcon();
        log.info("About to stop server...");
        final InputStream inputStream = executeRconCommand(rcon, RconCommandType.QUIT.getCommand());
        final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        log.info("Server stopped: {}", output);
    }

    private String restart() throws IOException, InterruptedException {
        stopServer();
        TimeUnit.SECONDS.sleep(10);
        startServer();
        return "Server Restarted";
    }

    @Async(ScheduleConfig.ASYNC_TASK_EXECUTOR)
    @Scheduled(fixedDelayString = "${whitelist.check-server-state-seconds}", timeUnit = TimeUnit.SECONDS)
    public void queryServerStatus() {
        final boolean isServerWorking = serverStatusService.getCurrentWorkingState();
        if (isServerWorking) {
            serverStatusService.allowServerToRestart();
            serverStatusService.disallowServerToStart();
        } else {
            serverStatusService.disallowServerToRestart();
            serverStatusService.allowServerToStart();
        }
    }
}
