package com.al.qdt.score.qry

import com.al.qdt.score.qry.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import spock.lang.Subject
import spock.lang.Title

@Title("Integration testing of the application context")
class ScoreQryServiceAppITSpec extends AbstractIntegration {

    @Subject
    @Autowired
    ApplicationContext context

    def 'Test context loads'() {
        expect:
        context != null
    }
}
