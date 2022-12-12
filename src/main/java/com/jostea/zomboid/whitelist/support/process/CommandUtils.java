package com.jostea.zomboid.whitelist.support.process;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandUtils {

    private static final String NOT_DEFINED_COMMAND = "not-defined";

    private static final String SPACE = " ";

    public static InputStream executeRconCommand(final WhitelistProperties.Rcon rcon,
                                                 final String command) throws IOException {

        final String formedAddress = String.join(SPACE, "-a", rcon.getAddress());
        final String formedPassword = String.join(SPACE, "-p", rcon.getPassword());

        final String escapedCommand = String.format("\"%s\"", command);
        final String formedCommand = String.join(SPACE, rcon.getAppName(), formedAddress, formedPassword, escapedCommand);

        return Runtime.getRuntime().exec(formedCommand).getInputStream();
    }

    public static void executeRconPlayerCommand(final WhitelistProperties.Rcon rcon,
                                                final RconCommandType rconCommandType,
                                                final Set<String> playerSet,
                                                final List<String> parameters) {

        final AtomicReference<String> command = new AtomicReference<>(NOT_DEFINED_COMMAND);
        playerSet.forEach(player -> {
            try {
                if (parameters.size() > 0) {
                    command.set(rconCommandType.resolveParameters(player, parameters));
                } else {
                    command.set(rconCommandType.resolveParameters(player));
                }

                final InputStream inputStream = CommandUtils.executeRconCommand(rcon, command.get());
                final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                log.info("Executed command {}, with output: {}", command, output);
            } catch (IOException e) {
                log.error("Cannot execute {} command with for address: {}", command, rcon.getAddress());
            }
        });
    }
}
