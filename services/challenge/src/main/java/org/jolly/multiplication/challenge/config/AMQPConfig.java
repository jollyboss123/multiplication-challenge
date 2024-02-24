package org.jolly.multiplication.challenge.config;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jolly
 */
@Configuration
public class AMQPConfig {
    @Bean
    public TopicExchange challengesTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder
                .topicExchange(exchangeName)
                .durable(true)
                .build();
    }

    /**
     * Override Java default JSON object serializer so that:
     * 1. if consumer is written in languages other than Java, would have to look for specific library to
     * perform cross-language deserialization;
     * 2. default deserializer expects a hardcoded, fully qualified type name in the header of
     * the message. The deserializer also expects the Java bean to be located in the same package and
     * to have the same name and fields. This is not flexible since we may want to deserialize only
     * some properties and keep our own version of the event data, following good domain design practices.
     */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
