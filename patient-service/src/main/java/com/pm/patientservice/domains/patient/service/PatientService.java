package com.pm.patientservice.domains.patient.service;

import com.pm.patientservice.domains.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.domains.patient.dto.PatientCreateDTO;
import com.pm.patientservice.domains.patient.dto.PatientReplaceDTO;
import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import com.pm.patientservice.domains.patient.dto.PatientUpdateDTO;
import com.pm.patientservice.domains.patient.model.Patient;
import com.pm.patientservice.domains.patient.repository.PatientRepository;
import com.pm.patientservice.exception.custom.EmailAlreadyExistsException;
import com.pm.patientservice.exception.custom.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import com.pm.patientservice.domains.patient.mapper.PatientMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public List<PatientResponseDTO> findAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDTO).toList();
    }

    public PatientResponseDTO findById(UUID id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Transactional
    public PatientResponseDTO save(PatientCreateDTO patientCreateDTO) {
        if (patientRepository.existsByEmail(patientCreateDTO.getEmail())) {
            throw new EmailAlreadyExistsException(patientCreateDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(patientMapper.toEntity(patientCreateDTO));
        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(), newPatient.getName(), newPatient.getEmail());

        return patientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO replace(UUID id, PatientReplaceDTO patientReplaceDTO) {
        Patient patient = getPatientOrThrow(id);
        validateEmailChange(patient.getEmail(), patientReplaceDTO.getEmail());

        patientMapper.replaceEntityFromDTO(patientReplaceDTO, patient);

        return patientMapper.toDTO(patientRepository.save(patient));
    }

    public PatientResponseDTO update(UUID id, PatientUpdateDTO patientUpdateDTO) {
        Patient patient = getPatientOrThrow(id);
        validateEmailChange(patient.getEmail(), patientUpdateDTO.getEmail());

        patientMapper.updateEntityFromDTO(patientUpdateDTO, patient);

        return patientMapper.toDTO(patientRepository.save(patient));
    }

    public void deleteById(UUID id) {
        Patient patient = getPatientOrThrow(id);
        patientRepository.deleteById(patient.getId());
    }

    private Patient getPatientOrThrow(UUID id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    private void validateEmailChange(String email, String newEmail) {
        if (newEmail != null && !email.equals(newEmail) && patientRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException(newEmail);
        }
    }
}
