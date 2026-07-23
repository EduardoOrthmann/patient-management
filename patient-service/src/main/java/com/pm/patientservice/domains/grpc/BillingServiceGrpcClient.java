package com.pm.patientservice.domains.grpc;

import com.pm.billing.v1.BillingServiceGrpc;
import com.pm.billing.v1.CreateBillingAccountRequest;
import com.pm.billing.v1.CreateBillingAccountResponse;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {

    private final String host;
    private final int port;
    private final BillingServiceGrpc.BillingServiceBlockingStub stub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String host,
                                    @Value("${billing.service.grpc.port:9001}") int port) {
        this.host = host;
        this.port = port;
        this.stub = BillingServiceGrpc.newBlockingStub(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build());
    }

    @PostConstruct
    public void init() {
        log.info("Initializing BillingServiceGrpcClient with host: {} and port: {}", host, port);
    }

    public CreateBillingAccountResponse createBillingAccount(String patientId, String name, String email) {
        var request = CreateBillingAccountRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name)
                .setEmail(email)
                .build();

        return stub.createBillingAccount(request);
    }
}
