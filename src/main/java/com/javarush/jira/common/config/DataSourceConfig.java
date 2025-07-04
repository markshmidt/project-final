package com.javarush.jira.common.config;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

    @Profile("prod")
    @Bean
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/jira")
                .username(System.getenv("DB_USERNAME"))
                .password(System.getenv("DB_PASSWORD"))
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Profile("test")
    @Bean
    public DataSource h2DataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

}
