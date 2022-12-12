package com.jostea.zomboid.whitelist.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = ExtensionDatabaseConfiguration.REPOSITORY_PACKAGE,
        entityManagerFactoryRef = "extensionEntityManagerFactory",
        transactionManagerRef = "extensionTransactionManager"
)
public class ExtensionDatabaseConfiguration {

    static final String REPOSITORY_PACKAGE = "com.jostea.zomboid.whitelist.repository.extension";

    private static final String ENTITY_PACKAGE = "com.jostea.zomboid.whitelist.repository.domain.model";

    @Bean
    @ConfigurationProperties("spring.datasource.extension")
    public DataSourceProperties extensionDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource extensionDataSource() {
        return extensionDatasourceProperties().initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean extensionEntityManagerFactory(
            @Qualifier("extensionDataSource") final DataSource extensionDataSource,
            final EntityManagerFactoryBuilder builder
    ) {
        return builder.dataSource(extensionDataSource)
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Bean
    public PlatformTransactionManager extensionTransactionManager(
            @Qualifier("extensionEntityManagerFactory") final LocalContainerEntityManagerFactoryBean extensionEntityManagerFactory
    ) {
        return new JpaTransactionManager(Objects.requireNonNull(extensionEntityManagerFactory.getObject()));
    }
}
