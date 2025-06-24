package ru.innopolis.integration.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
/**
 * Сервис для отправки уведомлений об ошибках в Kafka.
 * Формирует и отправляет сообщения об ошибках в заданный топик.
 */
@Service
@RequiredArgsConstructor
public class ErrorNotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${app.kafka.error-topic}")
    private String errorTopic;

    /**
     * Отправляет уведомление об ошибке в Kafka.
     *
     * @param errorMessage описание ошибки
     * @param path путь, по которому произошла ошибка
     * @param stackTrace трассировка стека ошибки
     */
    public void sendErrorNotification(String errorMessage, String path, String stackTrace) {
        String payload = String.format("""
                {
                    "error": "%s",
                    "path": "%s",
                    "stackTrace": "%s"
                }
                """, errorMessage, path, stackTrace);

        kafkaTemplate.send(errorTopic, "error-key", payload);
    }
}
