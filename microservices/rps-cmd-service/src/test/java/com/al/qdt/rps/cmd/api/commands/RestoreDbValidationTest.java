package com.al.qdt.rps.cmd.api.commands;

import com.al.qdt.rps.cmd.base.ValidationBaseTest;
import com.al.qdt.rps.cmd.base.CommandTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.cqrs.messages.Message.ID_MUST_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing RestoreDbCommand class")
class RestoreDbValidationTest extends ValidationBaseTest implements CommandTests {
    RestoreDbCommand expectedRestoreDbCommand;

    @BeforeEach
    void setUp() {
        this.expectedRestoreDbCommand = createRestoreDbCommand(TEST_UUID);
    }

    @AfterEach
    void tearDown() {
        this.expectedRestoreDbCommand = null;
    }

    @Test
    @DisplayName("Testing RestoreDbCommand properties")
    void restoreDbCommandPropertiesTest() {
        assertAll("Testing RestoreDbCommand",
                () -> assertAll("BaseCommand properties",
                        () -> assertEquals(TEST_UUID, this.expectedRestoreDbCommand.getId(), "Id didn't match!")
                )
        );
    }

    @Test
    @DisplayName("Testing RestoreDbCommand equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualRestoreDbCommand = createRestoreDbCommand(TEST_UUID);

        assertTrue(this.expectedRestoreDbCommand.equals(actualRestoreDbCommand) &&
                actualRestoreDbCommand.equals(this.expectedRestoreDbCommand));
        assertEquals(this.expectedRestoreDbCommand.hashCode(), actualRestoreDbCommand.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedRestoreDbCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var restoreDbCommand = createRestoreDbCommand(id);
        final var constraintViolations = validator.validate(restoreDbCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
