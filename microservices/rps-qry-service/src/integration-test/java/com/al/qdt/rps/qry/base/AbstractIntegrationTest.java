package com.al.qdt.rps.qry.base;

import com.al.qdt.rps.qry.RpsQryServiceApp;
import com.al.qdt.rps.qry.config.GrpcInProcessConfig;
import com.al.qdt.rps.qry.config.TestConfig;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

/**
 * Abstract class for integration tests, allows to reduce the amount of test context restarts.
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = RpsQryServiceApp.class,
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
}
