package com.al.qdt.rps.cmd.services.grpc;

import com.al.qdt.rps.cmd.base.AbstractIntegrationTest;
import com.al.qdt.rps.cmd.base.ProtoTests;
import com.al.qdt.rps.grpc.v1.common.Hand;
import com.al.qdt.rps.grpc.v1.services.GameResponse;
import com.al.qdt.rps.grpc.v1.services.RpsCmdServiceGrpc;
import io.grpc.internal.testing.StreamRecorder;
import lombok.SneakyThrows;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Integration testing of the RpsGrpcServiceV1T class")
@Tag(value = "grpc")
class RpsGrpcServiceV1IT extends AbstractIntegrationTest implements ProtoTests {

    // a blocking/synchronous stub blocks the currently running thread and ensures that the
    // rpc call invoked on it doesn't return until it returns a response or raises an exception
    @GrpcClient("${grpc.server.inProcessName}")
    protected RpsCmdServiceGrpc.RpsCmdServiceBlockingStub rpsCmdServiceBlockingStub;

    // a non-blocking/asynchronous stub makes non-blocking rpc calls where the response is returned asynchronously via a StreamObserver callback object
    @GrpcClient("${grpc.server.inProcessName}")
    protected RpsCmdServiceGrpc.RpsCmdServiceStub rpsCmdServiceStub;

    // a non-blocking/asynchronous future stub makes non-blocking rpc calls where the response is returned asynchronously via a StreamObserver callback object
    @GrpcClient("${grpc.server.inProcessName}")
    protected RpsCmdServiceGrpc.RpsCmdServiceFutureStub rpsCmdServiceFutureStub;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.rpsCmdServiceBlockingStub);
        assertNotNull(this.rpsCmdServiceStub);
        assertNotNull(this.rpsCmdServiceFutureStub);
    }

    @Nested
    @DisplayName("Tests for the method play()")
    class Play {

        @ParameterizedTest
        @EnumSource(value = Hand.class,
                names = {"UNRECOGNIZED", "EMPTY"},
                mode = EnumSource.Mode.EXCLUDE
        )
        @DisplayName("Testing unary play() method with blocking stub")
        void playWithBlockingStubTest(Hand hand) {
            final var gameRequest = createGameRequest(hand);
            setupEnvironment(gameRequest);

            final var gameResponse = rpsCmdServiceBlockingStub.play(gameRequest);

            assertNotNull(gameResponse);
            assertEquals(gameRequest.getGame().getHand().name(), gameResponse.getResult().getUserChoice());
        }

        @ParameterizedTest
        @EnumSource(value = Hand.class,
                names = {"UNRECOGNIZED", "EMPTY"},
                mode = EnumSource.Mode.EXCLUDE
        )
        @DisplayName("Testing server-stream playServerStreaming() method with blocking stub")
        void playServerStreamingWithBlockingStubTest(Hand hand) {
            final var gameRequest = createGameRequest(hand);
            setupEnvironment(gameRequest);

            // this enables compression for requests. Independent of this setting, servers choose whether
            // to compress responses.
            final var gameResponseServerStreaming = rpsCmdServiceBlockingStub
                    .withCompression("gzip")
                    .playServerStreaming(gameRequest);

            assertNotNull(gameResponseServerStreaming);
            gameResponseServerStreaming.forEachRemaining(item ->
                    assertEquals(gameRequest.getGame().getHand().name(), item.getResult().getUserChoice())
            );
        }

        @SneakyThrows({InterruptedException.class, ExecutionException.class})
        @ParameterizedTest
        @EnumSource(value = Hand.class,
                names = {"UNRECOGNIZED", "EMPTY"},
                mode = EnumSource.Mode.EXCLUDE
        )
        @DisplayName("Testing unary play() method with async stub")
        void playWithAsyncStubTest(Hand hand) {
            final var gameRequest = createGameRequest(hand);
            setupEnvironment(gameRequest);
            final StreamRecorder<GameResponse> streamRecorder = StreamRecorder.create();

            rpsCmdServiceStub.play(gameRequest, streamRecorder);

            assertNull(streamRecorder.getError());

            final GameResponse gameResponse = streamRecorder.firstValue().get();

            assertNotNull(gameResponse);
            assertEquals(gameRequest.getGame().getHand().name(), gameResponse.getResult().getUserChoice());
        }

        @SneakyThrows({InterruptedException.class, ExecutionException.class})
        @ParameterizedTest
        @EnumSource(value = Hand.class,
                names = {"UNRECOGNIZED", "EMPTY"},
                mode = EnumSource.Mode.EXCLUDE
        )
        @DisplayName("Testing server-stream playServerStreaming() method with async stub")
        void playServerStreamingWithAsyncStubTest(Hand hand) {
            final var gameRequest = createGameRequest(hand);
            setupEnvironment(gameRequest);
            final StreamRecorder<GameResponse> streamRecorder = StreamRecorder.create();

            rpsCmdServiceStub.withCompression("gzip").playServerStreaming(gameRequest, streamRecorder);

            assertNull(streamRecorder.getError());

            final GameResponse gameResponse = streamRecorder.firstValue().get();

            assertNotNull(gameResponse);
            assertEquals(gameRequest.getGame().getHand().name(), gameResponse.getResult().getUserChoice());
        }

        @SneakyThrows({InterruptedException.class, ExecutionException.class})
        @ParameterizedTest
        @EnumSource(value = Hand.class,
                names = {"UNRECOGNIZED", "EMPTY"},
                mode = EnumSource.Mode.EXCLUDE
        )
        @DisplayName("Testing unary play() method with future stub")
        void playWithFutureStubTest(Hand hand) {
            final var gameRequest = createGameRequest(hand);
            final var gameResponseListenableFuture =
                    rpsCmdServiceFutureStub.play(gameRequest);
            setupEnvironment(gameRequest);

            final var gameResponse = gameResponseListenableFuture.get();

            assertNotNull(gameResponse);
            assertEquals(gameRequest.getGame().getHand().name(), gameResponse.getResult().getUserChoice());
        }
    }
}
