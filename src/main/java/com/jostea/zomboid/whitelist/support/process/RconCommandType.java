package com.jostea.zomboid.whitelist.support.process;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RconCommandType {

    BAN_BY_STEAM_ID("banid %s", 1),
    SET_ACCESS_LEVEL("setaccesslevel %s none", 1),
    SAVE_SERVER("save", 0),
    GET_LIST_OF_PLAYERS("players",0),
    KICK_PLAYER("kickuser \\\"%s\\\" -r \\\"%s\\\"",2);

    private final String command;

    private final int parameterNumber;

    public String resolveParameters(final Object... parameters) {
        if (parameters.length != parameterNumber) {
            throw new IllegalArgumentException("Cannot correlate parameters for command: " + name());
        }

        return String.format(command, parameters);
    }
}
