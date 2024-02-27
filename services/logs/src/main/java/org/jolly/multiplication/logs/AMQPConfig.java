package org.jolly.multiplication.logs;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jolly
 */
@Configuration
public class AMQPConfig {
    @Bean
    public TopicExchange logsExchange() {
        return ExchangeBuilder
                .topicExchange("logs.topic")
                .durable(true)
                .build();
    }

    @Bean
    public Queue logsQueue() {
        return QueueBuilder
                .durable("logs.queue")
                .build();
    }

    @Bean
    public Binding logsBinding(final TopicExchange logsExchange, final Queue logsQueue) {
        return BindingBuilder
                .bind(logsQueue)
                .to(logsExchange)
                .with("#");
    }
}
