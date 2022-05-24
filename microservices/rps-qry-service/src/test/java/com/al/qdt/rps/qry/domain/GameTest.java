package com.al.qdt.rps.qry.domain;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.rps.qry.base.EntityTests;
import com.al.qdt.rps.qry.base.ValidationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
import static com.al.qdt.rps.qry.domain.Game.HAND_MUST_NOT_BE_NULL;
import static com.al.qdt.rps.qry.domain.Game.USERNAME_MUST_NOT_BE_BLANK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing Game entity class")
@Tag(value = "domain")
class GameTest extends ValidationBaseTest implements EntityTests {
    Game expectedGame;

    @BeforeEach
    void setUp() {
        this.expectedGame = createGame(TEST_UUID, USERNAME_ONE, ROCK);
    }

    @AfterEach
    void tearDown() {
        this.expectedGame = null;
    }

    @Test
    @DisplayName("Testing Game properties")
    void gamePropertiesTest() {
        assertAll("Testing Game entity",
                () -> assertAll("BaseEntity properties",
                        () -> assertEquals(TEST_UUID, this.expectedGame.getId(), "Id didn't match!")
                ),
                () -> assertAll("Game properties",
                        () -> assertEquals(USERNAME_ONE, this.expectedGame.getUsername(), "Username didn't match!"),
                        () -> assertEquals(ROCK, this.expectedGame.getHand(), "Hand didn't match!")
                )
        );
    }

    @Test
    @DisplayName("Testing equals() and hash() methods of identical objects")
    void equalsIdenticalObjectsTest() {
        final var actualGame = this.expectedGame;

        assertEquals(actualGame, this.expectedGame);
        assertEquals(this.expectedGame.hashCode(), actualGame.hashCode());
    }

    @Test
    @DisplayName("Testing equals() and hash() methods with identical UUIDs")
    void equalsIdenticalUUIDTest() {
        final var actualGame = createGame(TEST_UUID, USERNAME_ONE, ROCK);

        assertEquals(actualGame, this.expectedGame);
        assertEquals(this.expectedGame.hashCode(), actualGame.hashCode());
    }

    @Test
    @DisplayName("Testing equals() and hash() methods with objects with different UUIDs")
    void equalsDifferentUUIDTest() {
        final var actualGame = createSecondGame();

        assertNotEquals(actualGame, this.expectedGame);
        assertNotEquals(this.expectedGame.hashCode(), actualGame.hashCode());
    }

    @Test
    @DisplayName("Testing equals() and hash() methods with objects with nullable UUIDs")
    void equalsNullUUIDTest() {
        final var expectedGame = createGameWithNulUUID();
        final var actualGame = createGameWithNulUUID();

        assertNotEquals(actualGame, expectedGame);
        assertEquals(expectedGame.hashCode(), actualGame.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedGame);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var actualGame = createGame(id, USERNAME_ONE, ROCK);
        final var constraintViolations = validator.validate(actualGame);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Testing username validating constrains with right parameters")
    void usernameIsValid() {
        final var constraintViolations = validator.validate(this.expectedGame);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Testing username validating constrains with wrong parameters")
    void usernameIsBlank(String username) {
        final var actualGame = createGame(TEST_UUID, username, ROCK);
        final var constraintViolations = validator.validate(actualGame);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(USERNAME_MUST_NOT_BE_BLANK, constraintViolations.iterator().next().getMessage());
    }

    @ParameterizedTest
    @EnumSource(Hand.class)
    @DisplayName("Testing hand validating constrains with right parameters")
    void handIsValid(Hand hand) {
        final var actualGame = createGame(TEST_UUID, USERNAME_ONE, hand);
        final var constraintViolations = validator.validate(actualGame);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing hand validating constrains with wrong parameters")
    void handIsNull(Hand hand) {
        final var actualGame = createGame(TEST_UUID, USERNAME_ONE, hand);
        final var constraintViolations = validator.validate(actualGame);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(HAND_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
