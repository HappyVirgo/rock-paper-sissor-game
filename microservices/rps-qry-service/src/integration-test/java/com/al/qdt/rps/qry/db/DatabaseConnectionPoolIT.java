package com.al.qdt.rps.qry.db;

import com.al.qdt.rps.qry.base.AbstractIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("Integration testing of the datasource")
@Tag(value = "database")
class DatabaseConnectionPoolIT extends AbstractIntegrationTest {
    static final String DATASOURCE_NAME = "com.zaxxer.hikari.HikariDataSource";

    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("Testing injections")
    void injectionTest() {
        assertNotNull(this.dataSource);
    }

    @Test
    @DisplayName("Testing datasource type")
    void dataSourceTest() {
        log.info("DataSource: {}", dataSource.getClass().getName());
        assertEquals(DATASOURCE_NAME, dataSource.getClass().getName());
    }
}
