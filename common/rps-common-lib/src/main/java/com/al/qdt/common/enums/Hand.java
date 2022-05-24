package com.al.qdt.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This enum contains Rock Paper Scissors game options.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Hand {
    ROCK(1, 2),
    PAPER(2, 3),
    SCISSORS(3, 1),
    EMPTY(-1, -1);

    private static final Map<Integer, Hand> BY_ID = new HashMap<>();

    private final int id;
    private final int winBy;

    static {
        Stream.of(values()).forEach(item -> BY_ID.put(item.id, item));
    }

    public static Hand valueOfInt(int userEnteredOption) {
        final var hand = BY_ID.get(userEnteredOption);
        return hand == null ? EMPTY : hand;
    }

    public boolean isWinBy(Hand hand) {
        return this.winBy == hand.id;
    }
}
