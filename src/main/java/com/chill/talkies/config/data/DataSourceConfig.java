package com.chill.talkies.config.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("postgres-local")
public class DataSourceConfig extends AbstractDataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return createDataSource("jdbc:postgresql://localhost/movie",
                "org.postgresql.Driver", "postgres", "postgres");
    }

}
