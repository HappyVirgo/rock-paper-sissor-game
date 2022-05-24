package com.al.qdt.score.qry.base

import org.springframework.test.web.servlet.ResultActions

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

trait MvcHelper {

    /**
     * Test status and content type validation helper method.
     *
     * @param results result of the api call
     */
    def testStatusAndContentType(ResultActions result) {
        result?.andExpect status().isOk()
        result?.andExpect content().contentType(APPLICATION_JSON)
    }
}