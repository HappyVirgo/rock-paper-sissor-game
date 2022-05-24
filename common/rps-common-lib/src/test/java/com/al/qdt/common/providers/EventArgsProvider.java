package com.al.qdt.common.providers;

import com.al.qdt.common.base.EventTests;
import com.al.qdt.common.events.rps.GameDeletedEvent;
import com.al.qdt.common.events.rps.GamePlayedEvent;
import com.al.qdt.common.events.score.ScoresAddedEvent;
import com.al.qdt.common.events.score.ScoresDeletedEvent;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.al.qdt.common.helpers.Constants.TEST_KEY;

/**
 * Custom arguments provider class to create events test arguments.
 */
public class EventArgsProvider implements ArgumentsProvider, EventTests {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(TEST_KEY, createGamePlayedEvent(), GamePlayedEvent.class),
                Arguments.of(TEST_KEY, createGameDeletedEvent(), GameDeletedEvent.class),
                Arguments.of(TEST_KEY, createScoresAddedEvent(), ScoresAddedEvent.class),
                Arguments.of(TEST_KEY, createScoresDeletedEvent(), ScoresDeletedEvent.class));
    }
}
