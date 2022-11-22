package com.jostea.zomboid.whitelist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.jostea.zomboid.whitelist.config.WhitelistProperties.PLACEHOLDER_NAME;

@Data
@Configuration
@ConfigurationProperties(prefix = PLACEHOLDER_NAME)
public class WhitelistProperties {

    static final String PLACEHOLDER_NAME = "whitelist";

    private String playerAccessLevelCheckDelay;

    private Rcon rcon;

    @Data
    public static class Rcon {

        private String appName;

        private String address;

        private String password;
    }
}
