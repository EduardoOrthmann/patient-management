package com.pm.patientservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopologyConfig {

    public static final String EXCHANGE_PATIENT_EVENTS = "patient.events";
    public static final String QUEUE_ANALYTICS = "analytics.queue";
    public static final String QUEUE_AUDIT = "audit.queue";

    public static final String ROUTING_KEY_PATIENT_CREATED = "patient.created";
    public static final String ROUTING_KEY_PATIENT_WILDCARD = "patient.*";

    public static final String DLX = "patient.dlx";
    public static final String DLQ = "patient.dlq";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX);
    }

    @Bean
    public TopicExchange patientEventsExchange() {
        // Topic exchange enables pattern matching (e.g., patient.*)
        return new TopicExchange(EXCHANGE_PATIENT_EVENTS);
    }

    @Bean
    public Queue analyticsQueue() {
        // (name, durable) - Survives broker restarts
        return QueueBuilder.durable(QUEUE_ANALYTICS)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", "failed.analytics")
                .build();
    }

    @Bean
    public Queue auditQueue() {
        return QueueBuilder.durable(QUEUE_AUDIT)
                .withArgument("x-dead-letter-exchange", DLX)
                .withArgument("x-dead-letter-routing-key", "failed.audit")
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ, true);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("#");
    }

    @Bean
    public Binding analyticsBinding(Queue analyticsQueue, TopicExchange patientEventsExchange) {
        // Analytics only cares about creation events
        return BindingBuilder.bind(analyticsQueue)
                .to(patientEventsExchange)
                .with(ROUTING_KEY_PATIENT_CREATED);
    }

    @Bean
    public Binding auditBinding(Queue auditQueue, TopicExchange patientEventsExchange) {
        // Audit logs all patient-related events
        return BindingBuilder.bind(auditQueue)
                .to(patientEventsExchange)
                .with(ROUTING_KEY_PATIENT_WILDCARD);
    }
}
