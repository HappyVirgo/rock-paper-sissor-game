package com.al.qdt.rps.qry.api.queries;

import com.al.qdt.rps.qry.base.ValidationBaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.TEST_UUID;
import static com.al.qdt.cqrs.messages.Message.ID_MUST_NOT_BE_NULL;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing FindGameByIdQuery class")
@Tag(value = "query")
class FindGameByIdQueryTest extends ValidationBaseTest {
    FindGameByIdQuery expectedFindGameByIdQuery;

    @BeforeEach
    void setUp() {
        this.expectedFindGameByIdQuery = new FindGameByIdQuery(TEST_UUID);
    }

    @AfterEach
    void tearDown() {
        this.expectedFindGameByIdQuery = null;
    }

    @Test
    @DisplayName("Testing FindGameByIdQuery properties")
    void findGameByIdQueryPropertiesTest() {
        assertAll("FindGameByIdQuery properties",
                () -> assertEquals(TEST_UUID, this.expectedFindGameByIdQuery.getId(), "Id didn't match!")
        );
    }

    @Test
    @DisplayName("Testing FindGameByIdQuery equals() and hashCode() methods")
    void equalsAndHashCodeTest() {
        final var actualFindGameByIdQuery = new FindGameByIdQuery(TEST_UUID);

        assertTrue(this.expectedFindGameByIdQuery.equals(actualFindGameByIdQuery) &&
                actualFindGameByIdQuery.equals(this.expectedFindGameByIdQuery));
        assertEquals(this.expectedFindGameByIdQuery.hashCode(), actualFindGameByIdQuery.hashCode());
    }

    @Test
    @DisplayName("Testing identification validating constrains with right parameters")
    void identificationIsValid() {
        final var constraintViolations = validator.validate(this.expectedFindGameByIdQuery);

        assertEquals(ZERO_VIOLATIONS, constraintViolations.size());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("Testing identification validating constrains with wrong parameters")
    void identificationIsNull(UUID id) {
        final var actualFindGameByIdQuery = new FindGameByIdQuery(id);
        final var constraintViolations = validator.validate(actualFindGameByIdQuery);

        assertEquals(SINGLE_VIOLATION, constraintViolations.size());
        assertEquals(ID_MUST_NOT_BE_NULL, constraintViolations.iterator().next().getMessage());
    }
}
