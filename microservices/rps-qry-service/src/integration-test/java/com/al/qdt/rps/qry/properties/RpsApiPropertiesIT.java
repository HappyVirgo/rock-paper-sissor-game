package com.al.qdt.rps.qry.properties;

import com.al.qdt.common.properties.RpsApiProperties;
import com.al.qdt.rps.qry.base.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration testing of the RpsApiProperties class")
@Tag(value = "common")
class RpsApiPropertiesIT extends AbstractIntegrationTest {
    private static final String LICENSE_NAME = "The GNU General Public License, Version 3";
    private static final String LICENSE_URL = "https://www.gnu.org/licenses/gpl-3.0.txt";
    private static final String DEV_SERVER_BASE_URL = "http://localhost:8081/api";
    private static final String DEV_SERVER_DESCRIPTION = "Dev server";
    private static final String PROD_SERVER_BASE_URL = "http://localhost:8080/api";
    private static final String PROD_SERVER_DESCRIPTION = "Prod server";

    @Autowired
    RpsApiProperties rpsApiProperties;

    @Order(1)
    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.rpsApiProperties);
    }

    @Test
    @DisplayName("Testing injected properties")
    void propertiesTest() {
        assertEquals(LICENSE_NAME, this.rpsApiProperties.getLicenseName());
        assertEquals(LICENSE_URL, this.rpsApiProperties.getLicenseUrl());
        assertEquals(DEV_SERVER_BASE_URL, this.rpsApiProperties.getDev().getServer().getBaseUrl());
        assertEquals(DEV_SERVER_DESCRIPTION, this.rpsApiProperties.getDev().getServer().getDescription());
        assertEquals(PROD_SERVER_BASE_URL, this.rpsApiProperties.getProd().getServer().getBaseUrl());
        assertEquals(PROD_SERVER_DESCRIPTION, this.rpsApiProperties.getProd().getServer().getDescription());
    }
}
