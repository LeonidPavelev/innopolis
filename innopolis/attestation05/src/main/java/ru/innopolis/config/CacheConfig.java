package ru.innopolis.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Конфигурационный класс для настройки кэширования в приложении.
 * Включает кэширование и создает менеджер кэша для хранения задач и пользователей.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Создает и настраивает менеджер кэша для хранения данных.
     *
     * @return менеджер кэша с настроенными кэшами "tasks" и "users"
     */
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("tasks", "users");
    }
}
