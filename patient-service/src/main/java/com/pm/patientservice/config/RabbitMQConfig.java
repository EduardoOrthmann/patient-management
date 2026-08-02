package com.pm.patientservice.config;

import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public JsonMapper jsonMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                // Resilient Contract: consumer ignores new properties sent by producer
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
                .build();
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();

        Map<String, Class<?>> idClassMapping = new HashMap<>();

        // key = type sent in message header
        // value = class to deserialize into
        idClassMapping.put("patientCreated", PatientResponseDTO.class);

        classMapper.setIdClassMapping(idClassMapping);

        // fallback (optional)
        classMapper.setDefaultType(PatientResponseDTO.class);

        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(JsonMapper jsonMapper, DefaultClassMapper classMapper) {
        var converter = new JacksonJsonMessageConverter(jsonMapper);
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
