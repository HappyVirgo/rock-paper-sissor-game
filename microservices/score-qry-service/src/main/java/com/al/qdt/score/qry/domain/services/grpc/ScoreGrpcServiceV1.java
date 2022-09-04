package com.al.qdt.score.qry.domain.services.grpc;

import com.al.qdt.rps.grpc.v1.services.FindScoreByIdRequest;
import com.al.qdt.rps.grpc.v1.services.FindScoreByWinnerRequest;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresRequest;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;
import com.al.qdt.rps.grpc.v1.services.ScoreQryServiceGrpc;
import com.al.qdt.rps.grpc.v1.services.ScoreResponse;
import com.al.qdt.score.qry.domain.services.ScoreServiceV2;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.UUID;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class ScoreGrpcServiceV1 extends ScoreQryServiceGrpc.ScoreQryServiceImplBase {
    private final ScoreServiceV2 scoreService;

    @Override
    public void listOfScores(ListOfScoresRequest request, StreamObserver<ListOfScoresResponse> responseObserver) {
        log.info("UNARY GRPC SERVICE: Getting all scores.");
        this.scoreService.all();
        responseObserver.onNext(ListOfScoresResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findById(FindScoreByIdRequest request, StreamObserver<ScoreResponse> responseObserver) {
        final var scoreId = UUID.fromString(request.getId());
        log.info("UNARY GRPC SERVICE: Finding scores by id: {}.", scoreId.toString());
        final var scoreDto = this.scoreService.findById(scoreId);
        final ScoreResponse scoreResponse = ScoreResponse.newBuilder()
                .setScore(scoreDto)
                .build();
        responseObserver.onNext(scoreResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void findByWinner(FindScoreByWinnerRequest request, StreamObserver<ListOfScoresResponse> responseObserver) {
        log.info("SERVICE: Finding scores by winner: {}.", request.getWinner());
        responseObserver.onNext(this.scoreService.findByWinner(request.getWinner()));
        responseObserver.onCompleted();
    }
}
