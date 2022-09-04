package com.al.qdt.rps.cmd.api.commands;

import com.al.qdt.common.enums.Player;
import com.al.qdt.rps.cmd.base.CommandTests;
import com.al.qdt.rps.cmd.base.ValidationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.cqrs.messages.Message.ID_MUST_NOT_BE_NULL;
import static com.al.qdt.rps.cmd.api.commands.AddScoreCommand.WINNER_MUST_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing AddScoreCommand class")
class AddScoreValidationTest extends ValidationBaseTest implements CommandTests {
    AddScoreCommand expectedAddScoreCommand;

    @BeforeEach
    void setUp() {
        this.expectedAddScoreCommand = createAddScoreCommand(TEST_UUID, USER);
    }

    @AfterEach
    void tearDown() {
        this.expectedAddScoreCommand = null;
    }

    @Test
    @DisplayName("Testing AddScoreCommand properties")
    void addScoreCommandPropertiesTest() {
        assertAll("Testing AddScoreCommand",
                () -> assertAll("BaseCommand properties",
                        () -> assertEquals(TEST_UUID, this.expectedAddScoreCommand.getId(), "Id didn't match!")
                ),
                () -> assertAll("AddScoreCommand properties",
                        () -> assertEquals(USER, this.expectedAddScoreCommand.getWinner(), "Winner didn't match!")
                )
        );
    }

    @Test
    @DisplayName("Testing AddScoreCommand equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualAddScoreCommand = createAddScoreCommand(TEST_UUID, USER);

        assertTrue(this.expectedAddScoreCommand.equals(actualAddScoreCommand) &&
                actualAddScoreCommand.equals(this.expectedAddScoreCommand));
        assertEquals(this.expectedAddScoreCommand.hashCode(), actualAddScoreCommand.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedAddScoreCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var addScoreCommand = createAddScoreCommand(id, USER);
        final var constraintViolations = validator.validate(addScoreCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @EnumSource(Player.class)
    @DisplayName("Testing winner validating constrains with right parameters")
    void winnerIsValid(Player winner) {
        final var addScoreCommand = createAddScoreCommand(TEST_UUID, winner);
        final var constraintViolations = validator.validate(addScoreCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing winner validating constrains with wrong parameters")
    void winnerIsNull(Player winner) {
        final var addScoreCommand = createAddScoreCommand(TEST_UUID, winner);
        final var constraintViolations = validator.validate(addScoreCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(WINNER_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
