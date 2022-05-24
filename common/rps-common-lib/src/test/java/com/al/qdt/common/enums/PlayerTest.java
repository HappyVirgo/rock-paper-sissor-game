package com.al.qdt.common.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.al.qdt.common.enums.Player.DRAW;
import static com.al.qdt.common.enums.Player.MACHINE;
import static com.al.qdt.common.enums.Player.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testing Player enum")
class PlayerTest {
    private static final int TOTAL_ITEMS = 3; // total number of items defined in enum

    @Test
    void valuesTest() {
        assertEquals(TOTAL_ITEMS, Player.values().length);
    }

    @Test
    @DisplayName("Testing valueOf() method")
    void valueOfTest() {
        assertEquals(USER, Player.valueOf("USER"));
        assertEquals(MACHINE, Player.valueOf("MACHINE"));
        assertEquals(DRAW, Player.valueOf("DRAW"));
    }
}
