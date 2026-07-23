package com.pm.patientservice.domains.grpc;

import com.pm.billing.v1.BillingServiceGrpc;
import com.pm.billing.v1.CreateBillingAccountRequest;
import com.pm.billing.v1.CreateBillingAccountResponse;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub stub;

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String host,
            @Value("${billing.service.grpc.port:9001}") int port
    ) {
        log.info("Initializing BillingServiceGrpcClient with host: {} and port: {}", host, port);

        var channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        this.stub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public CreateBillingAccountResponse createBillingAccount(String patientId, String name, String email) {
        var request = CreateBillingAccountRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        var response = stub.createBillingAccount(request);

        log.info("Create BillingAccount Response: {}", response);

        return response;
    }
}
