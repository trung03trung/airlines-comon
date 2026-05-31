package com.skyviet.common.config;

import com.skyviet.common.event.BaseEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, BaseEvent<?>> producerFactory(
            org.springframework.boot.autoconfigure.kafka.KafkaProperties props) {
        Map<String, Object> config = new HashMap<>(props.buildProducerProperties(null));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, BaseEvent<?>> kafkaTemplate(
            ProducerFactory<String, BaseEvent<?>> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
