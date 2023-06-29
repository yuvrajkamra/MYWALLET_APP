package com.example.ewallet.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Properties;

@Configuration
public class WalletConfig {

    @Bean
    ObjectMapper getObjectMapper()
    {
        return new ObjectMapper();
    }

    // producerFactory creates a producer that publishes our messages
    // instance of kafkaproducer factory with customized properties.
    ProducerFactory getProducerFactory(){
        return new DefaultKafkaProducerFactory(getPProperties());
    }


    // pass the customized instance of defaultkafka factory to kafka template bean initialization.
    @Bean
    KafkaTemplate<String,String> getKafkaTemplate(){
        return new KafkaTemplate<>(getProducerFactory());
    }
    Properties getCProperties(){
        Properties properties=new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");//kafka server will run on 9092
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return properties;
    }
    Properties getPProperties(){
        Properties properties=new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");//kafka server will run on 9092
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return properties;
    }

    // producerFactory creates a Cconsumer that listens our messages
    ConsumerFactory getConsumerFactory(){
        return new DefaultKafkaConsumerFactory(getCProperties());
    }


}