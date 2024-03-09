package com.jostea.zomboid.whitelist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class ServerStatusService {

    private final AtomicBoolean isAllowedToStart = new AtomicBoolean(true);

    private final AtomicBoolean isAllowedToRestart = new AtomicBoolean(false);

    private final PlayersAmountService playersAmountService;

    /**
     * Start server
     */
    public void allowServerToStart() {
        isAllowedToStart.set(true);
    }

    public void disallowServerToStart() {
        isAllowedToStart.set(false);
    }

    public boolean getCurrentStartState() {
        return isAllowedToStart.get();
    }

    /**
     * Restart server
     */
    public void allowServerToRestart() {
        isAllowedToRestart.set(true);
    }

    public void disallowServerToRestart() {
        isAllowedToRestart.set(false);
    }

    public boolean getCurrentRestartState() {
        return isAllowedToRestart.get();
    }

    /**
     * Is working server
     */
    public boolean getCurrentWorkingState() {
        return !Objects.equals(playersAmountService.getAmountOfPlayers(), "");
    }
}
