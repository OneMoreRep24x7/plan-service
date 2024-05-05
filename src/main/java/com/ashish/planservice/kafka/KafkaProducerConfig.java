package com.ashish.planservice.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    // Define the Kafka server address and port
    private static final String KAFKA_SERVER = "15.206.80.123";

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("OneMoreRep", 3, (short) 1);
    }

    @Bean
    public Map<String,Object> producerConfig(){
        Map<String,Object> props=new HashMap<>();
        // Update the BOOTSTRAP_SERVERS_CONFIG with the correct Kafka server address
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        // Add other Kafka producer properties as needed (e.g., security protocol, SSL/TLS configuration)
        // props.put("security.protocol", "SSL");
        // props.put("ssl.truststore.location", "/path/to/truststore.jks");
        // props.put("ssl.truststore.password", "truststorePassword");
        // props.put("ssl.keystore.location", "/path/to/keystore.jks");
        // props.put("ssl.keystore.password", "keystorePassword");
        return props;
    }

    @Bean
    public ProducerFactory<String,Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
