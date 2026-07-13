package com.pm.patientservice.domains.patient.controller;

import com.pm.patientservice.domains.patient.dto.PatientCreateDTO;
import com.pm.patientservice.domains.patient.dto.PatientReplaceDTO;
import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import com.pm.patientservice.domains.patient.dto.PatientUpdateDTO;
import com.pm.patientservice.domains.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "APIs for managing patients")
public class PatientController {
    private final PatientService patientService;

    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @Operation(summary = "Get patient by ID", description = "Retrieve a patient by their unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @Operation(summary = "Create a new patient", description = "Create a new patient with the provided details")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> save(@RequestBody @Valid PatientCreateDTO patientCreateDTO) {
        return ResponseEntity.ok(patientService.save(patientCreateDTO));
    }

    @Operation(summary = "Update a patient", description = "Update an existing patient with the provided details")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> replace(@PathVariable UUID id, @RequestBody @Valid PatientReplaceDTO patientReplaceDTO) {
        return ResponseEntity.ok(patientService.replace(id, patientReplaceDTO));
    }

    @Operation(summary = "Partially update a patient", description = "Partially update an existing patient with the provided details")
    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.ok(patientService.update(id, patientUpdateDTO));
    }

    @Operation(summary = "Delete a patient", description = "Delete a patient by their unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
