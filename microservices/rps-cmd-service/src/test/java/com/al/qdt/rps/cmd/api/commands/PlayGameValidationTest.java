package com.al.qdt.rps.cmd.api.commands;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.rps.cmd.base.CommandTests;
import com.al.qdt.rps.cmd.base.ValidationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.cqrs.messages.Message.ID_MUST_NOT_BE_NULL;
import static com.al.qdt.rps.cmd.api.commands.PlayGameCommand.HAND_MUST_NOT_BE_NULL;
import static com.al.qdt.rps.cmd.api.commands.PlayGameCommand.USERNAME_MUST_NOT_BE_BLANK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing PlayGameCommand class")
class PlayGameValidationTest extends ValidationBaseTest implements CommandTests {
    PlayGameCommand expectedPlayGameCommand;

    @BeforeEach
    void setUp() {
        this.expectedPlayGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, ROCK);
    }

    @AfterEach
    void tearDown() {
        this.expectedPlayGameCommand = null;
    }

    @Test
    @DisplayName("Testing PlayGameCommand properties")
    void playGameCommandPropertiesTest() {
        assertAll("Testing PlayGameCommand",
                () -> assertAll("BaseCommand properties",
                        () -> assertEquals(TEST_UUID, this.expectedPlayGameCommand.getId(), "Id didn't match!")
                ),
                () -> assertAll("PlayGameCommand properties",
                        () -> assertEquals(USERNAME_ONE, this.expectedPlayGameCommand.getUsername(), "Username didn't match!"),
                        () -> assertEquals(ROCK, this.expectedPlayGameCommand.getHand(), "Hand didn't match!")
                )
        );
    }

    @Test
    @DisplayName("Testing PlayGameCommand equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualPlayGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, ROCK);

        assertTrue(this.expectedPlayGameCommand.equals(actualPlayGameCommand) &&
                actualPlayGameCommand.equals(this.expectedPlayGameCommand));
        assertEquals(this.expectedPlayGameCommand.hashCode(), actualPlayGameCommand.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedPlayGameCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var playGameCommand = createPlayGameCommand(id, USERNAME_ONE, ROCK);
        final var constraintViolations = validator.validate(playGameCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Testing username validating constrains with right parameters")
    void usernameIsValid() {
        final var playGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, ROCK);
        final var constraintViolations = validator.validate(playGameCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Testing username validating constrains with wrong parameters")
    void usernameIsBlank(String username) {
        final var playGameCommand = createPlayGameCommand(TEST_UUID, username, ROCK);
        final var constraintViolations = validator.validate(playGameCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(USERNAME_MUST_NOT_BE_BLANK, constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @EnumSource(Hand.class)
    @DisplayName("Testing hand validating constrains with right parameters")
    void handIsValid(Hand hand) {
        final var playGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, hand);
        final var constraintViolations = validator.validate(playGameCommand);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing hand validating constrains with wrong parameters")
    void handIsNull(Hand hand) {
        final var playGameCommand = createPlayGameCommand(TEST_UUID, USERNAME_ONE, hand);
        final var constraintViolations = validator.validate(playGameCommand);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(HAND_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
