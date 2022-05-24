package com.al.qdt.score.cmd

import com.al.qdt.score.cmd.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import spock.lang.Subject
import spock.lang.Title

@Title("Integration testing of the application context")
class ScoreCmdServiceAppITSpec extends AbstractIntegration {

    @Subject
    @Autowired
    ApplicationContext context

    def 'Test context loads'() {
        expect:
        context != null
    }
}
