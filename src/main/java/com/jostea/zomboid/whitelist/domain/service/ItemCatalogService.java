package com.jostea.zomboid.whitelist.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Keeps the list of game items ("Module.ItemName") loaded once from items/items.txt.
 * Regenerate the file with scripts/generate-items.sh after game updates.
 */
@Slf4j
@Service
public class ItemCatalogService {

    private static final String ITEMS_FILE = "items/items.txt";

    private static final int SEARCH_LIMIT = 50;

    private final List<String> items;

    public ItemCatalogService() {
        this.items = loadItems();
        log.info("Loaded {} game items", items.size());
    }

    public List<String> findByNamePrefix(final String prefix) {
        final String normalizedPrefix = prefix == null ? "" : prefix.trim().toLowerCase(Locale.ROOT);
        if (normalizedPrefix.isEmpty()) {
            return List.of();
        }

        return items.stream()
                .filter(item -> itemName(item).toLowerCase(Locale.ROOT).startsWith(normalizedPrefix))
                .limit(SEARCH_LIMIT)
                .toList();
    }

    public boolean exists(final String item) {
        return items.contains(item);
    }

    private static String itemName(final String item) {
        return item.substring(item.indexOf('.') + 1);
    }

    private static List<String> loadItems() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new ClassPathResource(ITEMS_FILE).getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to load " + ITEMS_FILE, e);
        }
    }
}
