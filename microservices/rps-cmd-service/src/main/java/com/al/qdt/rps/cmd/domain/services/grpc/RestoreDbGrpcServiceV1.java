package com.al.qdt.rps.cmd.domain.services.grpc;

import com.al.qdt.rps.cmd.domain.services.AdminServiceV2;
import com.al.qdt.rps.grpc.v1.services.AdminCmdServiceGrpc;
import com.al.qdt.rps.grpc.v1.services.RestoreDbRequest;
import com.al.qdt.rps.grpc.v1.services.RestoreDbResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * Admin grpc server service implementation class.
 */
@Slf4j
@GrpcService
@RequiredArgsConstructor
public class RestoreDbGrpcServiceV1 extends AdminCmdServiceGrpc.AdminCmdServiceImplBase {
    private final AdminServiceV2 adminService;

    /**
     * Restores database unary rpc service..
     *
     * @param request          request
     * @param responseObserver result
     */
    @Override
    public void restoreDb(RestoreDbRequest request, StreamObserver<RestoreDbResponse> responseObserver) {
        log.info("UNARY GRPC SERVICE: Restoring database...");
        this.adminService.restoreDb();
        // we use the response observer’s onNext() method to return the result
        responseObserver.onNext(RestoreDbResponse.newBuilder().build());
        // we use the response observer’s onCompleted() method to specify that we’ve finished dealing with the RPC
        responseObserver.onCompleted();
    }
}
