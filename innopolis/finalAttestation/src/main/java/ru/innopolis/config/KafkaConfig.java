package ru.innopolis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
/**
 * Конфигурационный класс для настройки Kafka.
 * Создает топик для обработки ошибок с заданными параметрами.
 */
@Configuration
public class KafkaConfig {
    @Value("${app.kafka.error-topic}")
    private String errorTopic;

    /**
     * Создает топик Kafka для обработки ошибок.
     *
     * @return новый топик с заданными параметрами: 1 партиция, 1 реплика, политика удаления сообщений
     */
    @Bean
    public KafkaAdmin.NewTopics errorTopic() {
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name(errorTopic)
                        .partitions(1)
                        .replicas(1)
                        .config("cleanup.policy", "delete")
                        .build()
        );
    }
}
