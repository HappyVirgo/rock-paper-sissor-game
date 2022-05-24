package com.al.qdt.rps.qry.base;

import org.junit.jupiter.api.BeforeAll;

import javax.validation.Validation;
import javax.validation.Validator;

public abstract class ValidationBaseTest {
    protected static final int ZERO_VIOLATIONS = 0;
    protected static final int SINGLE_VIOLATION = 1;
    protected static Validator validator;

    @BeforeAll
    static void beforeAll() {
        final var factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
