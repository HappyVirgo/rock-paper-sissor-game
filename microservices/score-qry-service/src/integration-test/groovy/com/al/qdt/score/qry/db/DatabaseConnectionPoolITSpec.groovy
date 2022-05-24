package com.al.qdt.score.qry.db

import com.al.qdt.score.qry.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

import javax.sql.DataSource

@Title("Integration testing of the datasource")
class DatabaseConnectionPoolITSpec extends AbstractIntegration {
    static final DATASOURCE_NAME = "com.zaxxer.hikari.HikariDataSource"

    @Subject
    @Autowired
    DataSource dataSource

    def 'Testing injections'() {
        expect:
        assert dataSource
    }

    def 'Testing datasource type'() {
        expect:
        dataSource.class.name == DATASOURCE_NAME
    }
}
