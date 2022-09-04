package com.al.qdt.common.providers;

import com.al.qdt.common.domain.base.ProtoTests;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

import static com.al.qdt.common.helpers.Constants.BASE_RESPONSE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.GAME_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.GAME_RESPONSE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.SCORE_EXPECTED_JSON;

/**
 * Custom arguments provider class to proto arguments.
 */
public class ProtoArgsProvider implements ArgumentsProvider, ProtoTests {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(
                Arguments.of(BASE_RESPONSE_EXPECTED_JSON, createBaseResponseProtoDto()),
                Arguments.of(GAME_RESPONSE_EXPECTED_JSON, createGameResultProtoDto()),
                Arguments.of(GAME_EXPECTED_JSON, createGameProtoDto()),
                Arguments.of(SCORE_EXPECTED_JSON, createScoreProtoDto()));
    }
}
