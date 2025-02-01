package ru.innopolis.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JDBCTemplateFactory {

    public static JdbcTemplate jdbcTemplate() {
        return jdbcTemplate(DataSourceConfig.DEFAULT);
    }

    public static JdbcTemplate jdbcTemplate(DataSourceConfig dataSourceConfig) {
        DriverManagerDataSource driver = new DriverManagerDataSource(dataSourceConfig.getUrl(), dataSourceConfig.getUser() ,dataSourceConfig.getPassword());
        driver.setDriverClassName(dataSourceConfig.getDriverName());
        driver.setSchema(dataSourceConfig.getSchema());
        return new JdbcTemplate(driver);
    }
}
