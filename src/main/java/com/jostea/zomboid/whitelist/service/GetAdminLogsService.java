package com.jostea.zomboid.whitelist.service;

import com.jostea.zomboid.whitelist.config.WhitelistProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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

        // Замените "путь_к_каталогу" на путь к вашему каталогу
        String directoryPath = whitelistProperties.getLogPath();
        File directory = new File(directoryPath);

        // Замените "ваше_регулярное_выражение" на ваше регулярное выражение
        String regex = "\\d{1,2}-\\d{1,2}-\\d{1,2}_\\d{1,2}-\\d{1,2}-\\d{1,2}_admin\\.txt";

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                Pattern pattern = Pattern.compile(regex);

                for (File file : files) {
                    String fileName = file.getName();
                    Matcher matcher = pattern.matcher(fileName);

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
            log.info("Log doesn't found");
        }

        return adminLogs.replace(".", ".\n");
    }
}
