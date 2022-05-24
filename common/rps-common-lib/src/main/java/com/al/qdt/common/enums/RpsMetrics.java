package com.al.qdt.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * This enum contains Rock Paper Scissors custom metrics.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RpsMetrics {
    GAME_PLAYED_COUNT_METRIC("rps.game.played.count", "Counts number of played games"),
    GAME_DELETED_COUNT_METRIC("rps.game.deleted.count", "Counts number of deleted games");

    private static final Map<String, RpsMetrics> BY_NAME = new HashMap<>();

    private final String name;
    private final String description;

    static {
        Stream.of(values()).forEach(item -> BY_NAME.put(item.name, item));
    }

    public static RpsMetrics valueOfName(String metricsName) {
        return BY_NAME.get(metricsName);
    }
}
