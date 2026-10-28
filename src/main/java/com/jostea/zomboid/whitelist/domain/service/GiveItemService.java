package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import com.jostea.zomboid.whitelist.support.process.RconCommandType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

import static com.jostea.zomboid.whitelist.support.process.CommandUtils.executeRconCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class GiveItemService {

    private static final Set<Integer> ALLOWED_COUNTS = Set.of(1, 5, 10);

    private static final int MAX_USERNAME_LENGTH = 50;

    private final WhitelistProperties properties;

    private final ItemCatalogService itemCatalogService;

    public String giveItem(final String username, final String item, final int count) {
        validate(username, item, count);

        final String command = RconCommandType.ADD_ITEM.resolveParameters(username, item, count);
        try {
            log.info("About to give {} x{} to player {}", item, count, username);
            final InputStream inputStream = executeRconCommand(properties.getRcon(), command);

            final String output = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            log.info("Output: {}", output);
            return output;
        } catch (IOException e) {
            log.error("Unable to give {} to player {}: ", item, username, e);
            return "Unable to give item";
        }
    }

    private void validate(final String username, final String item, final int count) {
        if (username == null || username.isBlank() || username.length() > MAX_USERNAME_LENGTH
                || username.contains("\"") || username.contains("\\")) {
            throw new IllegalArgumentException("Player is not valid");
        }
        if (!itemCatalogService.exists(item)) {
            throw new IllegalArgumentException("Unknown item: " + item);
        }
        if (!ALLOWED_COUNTS.contains(count)) {
            throw new IllegalArgumentException("Amount must be one of " + ALLOWED_COUNTS);
        }
    }
}
