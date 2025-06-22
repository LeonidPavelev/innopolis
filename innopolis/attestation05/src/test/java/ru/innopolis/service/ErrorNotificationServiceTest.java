package ru.innopolis.service;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.innopolis.integration.kafka.ErrorNotificationService;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(
        topics = "${app.kafka.error-topic}",
        partitions = 1,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9095",
                "port=9095"
        }
)
public class ErrorNotificationServiceTest {

    @Autowired
    private ErrorNotificationService errorNotificationService;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testSendErrorNotification() throws Exception {
        String errorMessage = "Test error";
        String path = "/api/test";
        String stackTrace = "Test stack trace";

        errorNotificationService.sendErrorNotification(errorMessage, path, stackTrace);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "test-group", "true", embeddedKafkaBroker);
        consumerProps.put("auto.offset.reset", "earliest");

        DefaultKafkaConsumerFactory<String, String> consumerFactory =
                new DefaultKafkaConsumerFactory<>(
                        consumerProps,
                        new StringDeserializer(),
                        new StringDeserializer());

        try (Consumer<String, String> consumer = consumerFactory.createConsumer()) {
            consumer.subscribe(Collections.singleton("error-events"));

            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofSeconds(10));

            assertFalse(records.isEmpty(), "Сообщение не было получено из Kafka");

            ConsumerRecord<String, String> record = records.iterator().next();
            assertNotNull(record.value(), "Тело сообщения не должно быть null");

            assertTrue(record.value().contains(errorMessage),
                    "Сообщение должно содержать текст ошибки");
            assertTrue(record.value().contains(path),
                    "Сообщение должно содержать путь");
            assertTrue(record.value().contains(stackTrace),
                    "Сообщение должно содержать stack trace");

            System.out.println("Получено сообщение об ошибке: " + record.value());
        }
    }
}