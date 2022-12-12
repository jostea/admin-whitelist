package com.jostea.zomboid.whitelist.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = GameDatabaseConfiguration.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = "gameEntityManagerFactory",
        considerNestedRepositories = true
)
public class GameDatabaseConfiguration {

    static final String REPOSITORY_PACKAGE = "com.jostea.zomboid.whitelist.repository.game";

    private static final String ENTITY_PACKAGE = "com.jostea.zomboid.whitelist.repository.domain.model";

    @Bean
    @ConfigurationProperties("spring.datasource.game")
    public DataSourceProperties gameDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource gameDataSource() {
        return gameDatasourceProperties().initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean gameEntityManagerFactory(
            @Qualifier("gameDataSource") final DataSource gameDataSource,
            final EntityManagerFactoryBuilder builder
    ) {
        return builder.dataSource(gameDataSource)
                .packages(ENTITY_PACKAGE)
                .build();
    }
}
