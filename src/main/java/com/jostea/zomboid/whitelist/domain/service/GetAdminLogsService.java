package com.jostea.zomboid.whitelist.domain.service;

import com.jostea.zomboid.whitelist.config.properties.WhitelistProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAdminLogsService {

    private final WhitelistProperties whitelistProperties;

    public String getAdminLogs() {
        String adminLogs = "";

        final String directoryPath = whitelistProperties.getLogPath();
        final File directory = new File(directoryPath);

        final String regex = "\\d{1,2}-\\d{1,2}-\\d{1,2}_\\d{1,2}-\\d{1,2}-\\d{1,2}_admin\\.txt";

        if (directory.exists() && directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files != null) {
                final Pattern pattern = Pattern.compile(regex);

                for (File file : files) {
                    final String fileName = file.getName();
                    final Matcher matcher = pattern.matcher(fileName);

                    if (matcher.matches()) {
                        try {
                            adminLogs = Files.readString(Path.of(file.getPath()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            log.info("Log wasn't found");
        }

        return adminLogs.replace(".", ".\n");
    }

    public InputStream getDebugLogServer() {
        final String directoryPath = whitelistProperties.getLogPath();
        final File directory = new File(directoryPath);

        final String regex = "\\d{1,2}-\\d{1,2}-\\d{1,2}_\\d{1,2}-\\d{1,2}-\\d{1,2}_DebugLog-server\\.txt";

        if (directory.exists() && directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files != null) {
                final Pattern pattern = Pattern.compile(regex);

                for (File file : files) {
                    final String fileName = file.getName();
                    final Matcher matcher = pattern.matcher(fileName);

                    if (matcher.matches()) {
                        try {
                            return new FileInputStream(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            log.info("Log wasn't found");
        }
        return new ByteArrayInputStream("Files not found".getBytes());
    }
}
