package com.jostea.zomboid.whitelist.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.jostea.zomboid.whitelist.repository",
        considerNestedRepositories = true
)
public class DataSourceConfig {

}
