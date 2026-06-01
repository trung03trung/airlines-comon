package com.skyviet.common.config;

import com.skyviet.common.event.BaseEvent;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(name = "spring.kafka.bootstrap-servers")
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, BaseEvent<?>> producerFactory(
            org.springframework.boot.autoconfigure.kafka.KafkaProperties props) throws Exception {
        Map<String, Object> config = new HashMap<>(props.buildProducerProperties(null));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        applySslConfig(config);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, BaseEvent<?>> kafkaTemplate(
            ProducerFactory<String, BaseEvent<?>> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    public static void applySslConfig(Map<String, Object> config) throws Exception {
        ClassPathResource keystore = new ClassPathResource("client.keystore.p12");
        if (!keystore.exists()) return;

        config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
        config.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, extractToFile("client.keystore.p12"));
        config.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "changeit");
        config.put(SslConfigs.SSL_KEYSTORE_TYPE_CONFIG, "PKCS12");
        config.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, extractToFile("client.truststore.jks"));
        config.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, "changeit");
    }

    public static String extractToFile(String classpathResource) throws Exception {
        ClassPathResource resource = new ClassPathResource(classpathResource);
        File tempFile = File.createTempFile("kafka-", "-" + classpathResource);
        tempFile.deleteOnExit();
        try (InputStream is = resource.getInputStream()) {
            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile.getAbsolutePath();
    }
}
