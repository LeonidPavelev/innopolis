package config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class JDBCTemplateLink {

    public static JdbcTemplate jdbcTemplate() {
        DriverManagerDataSource driver = new DriverManagerDataSource("jdbc:postgresql://localhost:5432/online_electronics_store", "postgres", "5432");
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setSchema("online_electronics_store");
        return new JdbcTemplate(driver);
    }
}
