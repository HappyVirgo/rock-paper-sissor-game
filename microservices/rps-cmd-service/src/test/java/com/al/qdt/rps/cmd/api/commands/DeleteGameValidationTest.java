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

@DisplayName("Testing DeleteGameCommand class")
class DeleteGameValidationTest extends ValidationBaseTest implements CommandTests {
    DeleteGameCommand expectedDeleteGameCommand;

    @BeforeEach
    void setUp() {
        this.expectedDeleteGameCommand = createDeleteGameCommand(TEST_UUID);
    }

    @AfterEach
    void tearDown() {
        this.expectedDeleteGameCommand = null;
    }

    @Test
    @DisplayName("Testing DeleteGameCommand properties")
    void deleteGameCommandPropertiesTest() {
        assertAll("Testing DeleteGameCommand",
                () -> assertAll("BaseCommand properties",
                        () -> assertEquals(TEST_UUID, this.expectedDeleteGameCommand.getId(), "Id didn't match!")
                )
        );
    }

    @Test
    @DisplayName("Testing DeleteGameCommand equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualDeleteGameCommand = createDeleteGameCommand(TEST_UUID);

        assertTrue(this.expectedDeleteGameCommand.equals(actualDeleteGameCommand) &&
                actualDeleteGameCommand.equals(this.expectedDeleteGameCommand));
        assertEquals(this.expectedDeleteGameCommand.hashCode(), actualDeleteGameCommand.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedDeleteGameCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var deleteGameCommand = createDeleteGameCommand(id);
        final var constraintViolations = validator.validate(deleteGameCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
