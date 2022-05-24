package com.al.qdt.rps.qry.queries;

import com.al.qdt.rps.qry.base.ValidationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static com.al.qdt.rps.qry.domain.Game.USERNAME_MUST_NOT_BE_BLANK;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing FindGamesByUsernameQuery class")
@Tag(value = "query")
class FindGamesByUsernameQueryTest extends ValidationBaseTest {
    FindGamesByUsernameQuery expectedFindGamesByUsernameQuery;

    @BeforeEach
    void setUp() {
        this.expectedFindGamesByUsernameQuery = new FindGamesByUsernameQuery(USERNAME_ONE);
    }

    @AfterEach
    void tearDown() {
        this.expectedFindGamesByUsernameQuery = null;
    }

    @Test
    @DisplayName("Testing FindGamesByUsernameQuery properties")
    void findGamesByUsernameQueryPropertiesTest() {
        assertAll("FindGamesByUsernameQuery properties",
                () -> assertEquals(USERNAME_ONE, this.expectedFindGamesByUsernameQuery.getUsername(), "Username didn't match!")
        );
    }

    @Test
    @DisplayName("Testing FindGamesByUsernameQuery equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualFindGamesByUsernameQuery = new FindGamesByUsernameQuery(USERNAME_ONE);

        assertTrue(this.expectedFindGamesByUsernameQuery.equals(actualFindGamesByUsernameQuery) &&
                actualFindGamesByUsernameQuery.equals(this.expectedFindGamesByUsernameQuery));
        assertEquals(this.expectedFindGamesByUsernameQuery.hashCode(), actualFindGamesByUsernameQuery.hashCode());
    }

    @Test
    @DisplayName("Testing username validating constrains with right parameters")
    void usernameIsValid() {
        final var constraintViolations = validator.validate(this.expectedFindGamesByUsernameQuery);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Testing username validating constrains with wrong parameters")
    void usernameIsBlank(String username) {
        final var actualFindGamesByUsernameQuery = new FindGamesByUsernameQuery(username);
        final var constraintViolations = validator.validate(actualFindGamesByUsernameQuery);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(USERNAME_MUST_NOT_BE_BLANK, constraintViolations.iterator().next().getMessage());
    }
}
