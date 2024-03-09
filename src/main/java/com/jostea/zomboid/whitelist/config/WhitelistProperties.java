package com.jostea.zomboid.whitelist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.jostea.zomboid.whitelist.config.WhitelistProperties.PLACEHOLDER_NAME;

@Data
@Configuration
@ConfigurationProperties(prefix = PLACEHOLDER_NAME)
public class WhitelistProperties {

    static final String PLACEHOLDER_NAME = "whitelist";

    private Rcon rcon;

    private String serverStartPath;

    private List<String> superAdminIps;

    private String logPath;

    private ThreadPool asyncWorker;

    @Data
    public static class Rcon {

        private String appName;

        private String address;

        private String password;
    }

    @Data
    public static class ThreadPool {

        private int minPoolSize;

        private int maxPoolSize;

        private int queueLength;

        private String threadPoolPrefixName;
    }
}
