package com.pm.billingservice.grpc;

import com.pm.billing.v1.BillingServiceGrpc;
import com.pm.billing.v1.BillingStatus;
import com.pm.billing.v1.CreateBillingAccountRequest;
import com.pm.billing.v1.CreateBillingAccountResponse;
import com.pm.billingservice.billing.BillingAccount;
import com.pm.billingservice.billing.BillingAccountService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

    private final BillingAccountService billingAccountService;

    public BillingGrpcService(BillingAccountService billingAccountService) {
        this.billingAccountService = billingAccountService;
    }

    @Override
    public void createBillingAccount(CreateBillingAccountRequest request, StreamObserver<CreateBillingAccountResponse> responseObserver) {
        try {
            BillingAccount account = billingAccountService.createAccount(request.getPatientId(), request.getName(), request.getEmail());

            CreateBillingAccountResponse response = CreateBillingAccountResponse.newBuilder()
                    .setAccountId(account.accountId())
                    .setStatus(BillingStatus.BILLING_STATUS_SUCCESS)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription("Internal server error").asRuntimeException());
        }
    }
}
