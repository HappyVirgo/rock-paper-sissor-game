package com.al.qdt.score.cmd.services.grpc;

import com.al.qdt.rps.grpc.v1.services.DeleteScoreByIdRequest;
import com.al.qdt.rps.grpc.v1.services.DeleteScoreByIdResponse;
import com.al.qdt.rps.grpc.v1.services.ScoreCmdServiceGrpc;
import com.al.qdt.score.cmd.services.ScoreServiceV2;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

/**
 * Score grpc service implementation class.
 */
@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ScoreGrpcServiceV1 extends ScoreCmdServiceGrpc.ScoreCmdServiceImplBase {
    private final ScoreServiceV2 scoreService;

    /**
     * Deletes score by id unary rpc service.
     *
     * @param request          delete score by id request
     * @param responseObserver operation response
     */
    @Override
    public void deleteById(DeleteScoreByIdRequest request, StreamObserver<DeleteScoreByIdResponse> responseObserver) {
        final var scoreId = UUID.fromString(request.getId());
        log.info("UNARY GRPC SERVICE: Deleting score by id: {}.", scoreId.toString());
        this.scoreService.deleteById(scoreId);
        // we use the response observer’s onNext() method to return the result
        responseObserver.onNext(DeleteScoreByIdResponse.newBuilder().build());
        // we use the response observer’s onCompleted() method to specify that we’ve finished dealing with the RPC
        responseObserver.onCompleted();
    }
}
