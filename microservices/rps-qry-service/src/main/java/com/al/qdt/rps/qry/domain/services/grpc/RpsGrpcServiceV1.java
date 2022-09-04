package com.al.qdt.rps.qry.domain.services.grpc;

import com.al.qdt.rps.grpc.v1.services.ListOfGamesRequest;
import com.al.qdt.rps.grpc.v1.services.ListOfGamesResponse;
import com.al.qdt.rps.grpc.v1.services.RpsQryServiceGrpc;
import com.al.qdt.rps.qry.domain.services.RpsServiceV2;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class RpsGrpcServiceV1 extends RpsQryServiceGrpc.RpsQryServiceImplBase {
    private final RpsServiceV2 rpsService;

    /**
     * Returns all games unary rpc service.
     *
     * @param request          request
     * @param responseObserver game collection of all games
     */
    @Override
    public void listOfGames(ListOfGamesRequest request, StreamObserver<ListOfGamesResponse> responseObserver) {
        log.info("UNARY GRPC SERVICE: Getting all games...");
        responseObserver.onNext(this.rpsService.all());
        responseObserver.onCompleted();
    }
}
