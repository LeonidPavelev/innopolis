package ru.innopolis.config;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DataSourceConfig {
    public static DataSourceConfig DEFAULT = DataSourceConfig.builder()
            .url("jdbc:postgresql://localhost:5432/online_electronics_store")
            .user("postgres")
            .password("5432")
            .driverName("org.postgresql.Driver")
            .schema("online_electronics_store")
            .build();

    String url;
    String user;
    String password;
    String driverName;
    String schema;

}
