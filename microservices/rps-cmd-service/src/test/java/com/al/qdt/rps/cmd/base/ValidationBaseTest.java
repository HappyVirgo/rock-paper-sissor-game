package com.al.qdt.rps.cmd.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

import javax.validation.Validation;
import javax.validation.Validator;

@Tag(value = "command")
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
