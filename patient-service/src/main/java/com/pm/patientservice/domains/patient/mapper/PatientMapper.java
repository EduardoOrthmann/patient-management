package com.pm.patientservice.domains.patient.mapper;

import com.pm.patientservice.config.CentralMapperConfig;
import com.pm.patientservice.domains.patient.dto.PatientCreateDTO;
import com.pm.patientservice.domains.patient.dto.PatientReplaceDTO;
import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import com.pm.patientservice.domains.patient.dto.PatientUpdateDTO;
import com.pm.patientservice.domains.patient.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = CentralMapperConfig.class)
public interface PatientMapper {
    PatientResponseDTO toDTO(Patient patient);

    @Mapping(target = "registeredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Patient toEntity(PatientCreateDTO dto);

    @Mapping(target = "registeredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    void replaceEntityFromDTO(PatientReplaceDTO dto, @MappingTarget Patient patient);

    @Mapping(target = "registeredDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PatientUpdateDTO dto, @MappingTarget Patient patient);
}