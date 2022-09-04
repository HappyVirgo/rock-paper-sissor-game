package com.al.qdt.rps.cmd.base;

import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.cqrs.events.BaseEvent;
import com.al.qdt.cqrs.infrastructure.EventStore;
import com.al.qdt.rps.cmd.RpsCmdServiceApp;
import com.al.qdt.rps.cmd.api.commands.PlayGameCommand;
import com.al.qdt.rps.cmd.infrastructure.config.GrpcInProcessConfig;
import com.al.qdt.rps.cmd.infrastructure.config.TestConfig;
import com.al.qdt.rps.grpc.v1.services.GameRequest;
import io.grpc.Channel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Abstract class for integration tests, allows to reduce the amount of test context restarts.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RpsCmdServiceApp.class,
        properties = {
                "grpc.server.inProcessName=test", // Enable inProcess server
                "grpc.server.port=-1", // Disable external server
                "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the inProcess server
        }
)
// Ensures that the grpc-server is properly shutdown after each test, avoids "port already in use" during tests
@DirtiesContext
@AutoConfigureMockMvc
@Import({TestConfig.class, GrpcInProcessConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("it")
public abstract class AbstractIntegrationTest {

    @Autowired
    protected ApplicationContext applicationContext;

    // a gRPC channel that provides a connection to a gRPC server on a specified host and port
    @GrpcClient("${grpc.server.inProcessName}")
    protected Channel channel;

    @PostConstruct
    protected void init() {
        // Test injection
        assertNotNull(this.channel);
    }

    protected void setupEnvironment(GameRequest gameRequest) {
        final var playGameCommand = PlayGameCommand.builder()
                .id(TEST_UUID)
                .username(gameRequest.getGame().getUsername())
                .hand(com.al.qdt.common.enums.Hand.valueOf(gameRequest.getGame().getHand().name()))
                .build();
        final var gamePlayedEvent = GamePlayedEvent.builder()
                .id(playGameCommand.getId())
                .username(playGameCommand.getUsername())
                .hand(playGameCommand.getHand())
                .build();

        setupEventStore(List.of(gamePlayedEvent));
    }

    protected void setupEventStore(List<BaseEvent> events) {
        final var eventStore = applicationContext.getBean(EventStore.class);

        assertNotNull(eventStore);
        when(eventStore.findByAggregateId(any(UUID.class))).thenReturn(events);
    }
}
