package com.pm.billingservice.billing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillingAccountService {
    private static final Logger log = LoggerFactory.getLogger(BillingAccountService.class);
    private final BillingAccountRepository billingAccountRepository;

    public BillingAccountService(BillingAccountRepository billingAccountRepository) {
        this.billingAccountRepository = billingAccountRepository;
    }

    public BillingAccount createAccount(String patientId, String name, String email) {
        validateInput(patientId, name, email);

        String id = UUID.randomUUID().toString();
        BillingAccount account = new BillingAccount(id, patientId, name, email);

        log.info("Created billing account with ID: {}", id);

        return billingAccountRepository.save(account);
    }

    private void validateInput(String patientId, String name, String email) {
        if (patientId == null || patientId.isBlank()) {
            throw new IllegalArgumentException("Patient ID is required");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
    }
}
