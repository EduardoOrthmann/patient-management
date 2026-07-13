package com.pm.patientservice.domains.patient.controller;

import com.pm.patientservice.domains.patient.dto.PatientCreateDTO;
import com.pm.patientservice.domains.patient.dto.PatientReplaceDTO;
import com.pm.patientservice.domains.patient.dto.PatientResponseDTO;
import com.pm.patientservice.domains.patient.dto.PatientUpdateDTO;
import com.pm.patientservice.domains.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> save(@RequestBody @Valid PatientCreateDTO patientCreateDTO) {
        return ResponseEntity.ok(patientService.save(patientCreateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> replace(@PathVariable UUID id, @RequestBody @Valid PatientReplaceDTO patientReplaceDTO) {
        return ResponseEntity.ok(patientService.replace(id, patientReplaceDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable UUID id, @RequestBody @Valid PatientUpdateDTO patientUpdateDTO) {
        return ResponseEntity.ok(patientService.update(id, patientUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        patientService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
