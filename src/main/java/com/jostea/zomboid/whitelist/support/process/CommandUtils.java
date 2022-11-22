package com.jostea.zomboid.whitelist.support.process;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommandUtils {

    private static final String NOT_DEFINED_COMMAND = "not-defined";

    private static final String SPACE = " ";

    public static InputStream executeCommand(final String appName,
                                             final String address,
                                             final String password,
                                             final String command) throws IOException {

        final String formedAddress = String.join(SPACE, "-a", address);
        final String formedPassword = String.join(SPACE, "-p", password);

        final String escapedCommand = String.format("\"%s\"", command);
        final String formedCommand = String.join(SPACE, appName, formedAddress, formedPassword, escapedCommand);

        return Runtime.getRuntime().exec(formedCommand).getInputStream();
    }

    public static void executeRconCommand(final RconCommandType rconCommandType,
                                          final Set<String> playersToBan,
                                          final WhitelistProperties.Rcon rcon) {

        final AtomicReference<String> command = new AtomicReference<>(NOT_DEFINED_COMMAND);
        playersToBan.forEach(player -> {
            try {
                command.set(rconCommandType.resolveParameters(player));

                final InputStream inputStream =
                        CommandUtils.executeCommand(rcon.getAppName(), rcon.getAddress(), rcon.getPassword(), command.get());

                final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                log.info("Executed command {}, with output: {}", command, output);
            } catch (IOException e) {
                log.error("Cannot execute {} command with for address: {}", command, rcon.getAddress());
            }
        });
    }
}
