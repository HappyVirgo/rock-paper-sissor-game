package com.al.qdt.score.qry.infrastructure.propertirs

import com.al.qdt.common.infrastructure.properties.RpsApiProperties
import com.al.qdt.score.qry.base.AbstractIntegration
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject
import spock.lang.Title

@Title("Integration testing of the RpsApiPropertiesIT class")
class RpsApiPropertiesITSpec extends AbstractIntegration {
    static final LICENSE_NAME = 'The GNU General Public License, Version 3'
    static final LICENSE_URL = 'https://www.gnu.org/licenses/gpl-3.0.txt'
    static final DEV_SERVER_BASE_URL = 'http://localhost:8084/score-qry-api'
    static final DEV_SERVER_DESCRIPTION = 'Dev server'
    static final PROD_SERVER_BASE_URL = 'http://localhost:8080/score-qry-api'
    static final PROD_SERVER_DESCRIPTION = 'Prod server'

    @Subject
    @Autowired
    RpsApiProperties rpsApiProperties

    def 'Testing property injections'() {
        expect:
        assert rpsApiProperties
    }

    def 'Testing injected properties'() {
        expect:
        LICENSE_NAME == rpsApiProperties.licenseName
        LICENSE_URL == rpsApiProperties.licenseUrl
        DEV_SERVER_BASE_URL == rpsApiProperties.dev.server.baseUrl
        DEV_SERVER_DESCRIPTION == rpsApiProperties.dev.server.description
        PROD_SERVER_BASE_URL == rpsApiProperties.prod.server.baseUrl
        PROD_SERVER_DESCRIPTION == rpsApiProperties.prod.server.description
    }
}
