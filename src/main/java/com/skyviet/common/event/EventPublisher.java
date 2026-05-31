package com.skyviet.common.event;

import com.skyviet.common.config.CorrelationIdFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, BaseEvent<?>> kafkaTemplate;

    /**
     * Publish event with bookingId as partition key (ensures ordering per booking)
     */
    public <T> void publish(String topic, String partitionKey, String type, String source, T data) {
        String correlationId = MDC.get(CorrelationIdFilter.MDC_KEY);
        BaseEvent<T> event = BaseEvent.of(type, source, correlationId, data);
        kafkaTemplate.send(topic, partitionKey, event);
        log.info("Published event [{}] to topic [{}] key=[{}]", type, topic, partitionKey);
    }
}
