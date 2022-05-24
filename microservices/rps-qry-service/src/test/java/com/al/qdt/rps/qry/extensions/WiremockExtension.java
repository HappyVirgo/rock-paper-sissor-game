package com.al.qdt.rps.qry.extensions;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WiremockExtension implements AfterEachCallback, BeforeEachCallback {

//    private WireMockServer wireMockServer;

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
//        WireMock.reset();
//
//        wireMockServer.stop();
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
//        wireMockServer = new WireMockServer(wireMockConfig().port(8089)); //No-args constructor will start on port 8080, no HTTPS
//        wireMockServer.start();
    }
}
