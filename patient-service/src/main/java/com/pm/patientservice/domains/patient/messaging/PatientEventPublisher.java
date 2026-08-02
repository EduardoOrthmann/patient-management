package com.pm.patientservice.domains.patient.messaging;

import com.pm.patientservice.config.RabbitMQTopologyConfig;
import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishPatientCreatedEvent(PatientResponseDTO patient) {
        rabbitTemplate.convertAndSend(
                RabbitMQTopologyConfig.EXCHANGE_PATIENT_EVENTS,
                RabbitMQTopologyConfig.ROUTING_KEY_PATIENT_CREATED,
                patient
        );
    }
}
