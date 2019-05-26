package com.chill.talkies.config.data;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class AbstractDataSourceConfig {

    protected DataSource createDataSource(String jdbcUrl, String driverClass, String userName, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }
}
