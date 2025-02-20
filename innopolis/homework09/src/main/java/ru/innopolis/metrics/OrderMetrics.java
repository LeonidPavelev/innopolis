package ru.innopolis.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.innopolis.service.OrderService;

@Component
@RequiredArgsConstructor
public class OrderMetrics {

    private final OrderService orderService;

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> customizeMetrics() {
        return registry -> {
            Gauge.builder("order.count", orderService, OrderService::getOrderCount)
                    .description("Количество заказов")
                    .register(registry);
            Gauge.builder("order.averageAmount", orderService, OrderService::getAverageOrderAmount)
                    .description("Средний чек заказа")
                    .register(registry);
        };
    }
}
