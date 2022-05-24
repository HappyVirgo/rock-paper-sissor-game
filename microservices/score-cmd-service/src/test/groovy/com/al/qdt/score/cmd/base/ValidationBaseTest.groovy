package com.al.qdt.score.cmd.base

import spock.lang.Specification

import javax.validation.Validation

abstract class ValidationBaseTest extends Specification {
    static ZERO_VIOLATIONS = 0
    static SINGLE_VIOLATION = 1
    static validator

    // Run before the first feature method
    def setupSpec() {
        def factory = Validation.buildDefaultValidatorFactory()
        validator = factory.getValidator()
    }
}
