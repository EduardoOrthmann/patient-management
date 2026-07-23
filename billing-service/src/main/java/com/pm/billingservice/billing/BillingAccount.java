package com.pm.billingservice.billing;

public record BillingAccount(
        String accountId,
        String patientId,
        String name,
        String email
) {
}
