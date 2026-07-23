package com.pm.billingservice.billing;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MockBillingAccountRepository implements BillingAccountRepository {
    private final Map<String, BillingAccount> store = new ConcurrentHashMap<>();

    @Override
    public BillingAccount save(BillingAccount account) {
        store.put(account.accountId(), account);
        return account;
    }
}
