package com.al.qdt.common.base;

import com.al.qdt.common.dto.BaseResponseDto;
import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.dto.GameResponseDto;
import com.al.qdt.common.dto.ScoreDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.enums.Hand.SCISSORS;
import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;

@Tag(value = "dto")
public interface DtoTests {
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Creates test instance for base response dto object.
     *
     * @return base response dto object
     */
    default BaseResponseDto createBaseResponseJsonDto() {
        return BaseResponseDto.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }

    /**
     * Creates test instance for game response dto object.
     *
     * @return game response dto object
     */
    default GameResponseDto createGameResponseJsonDto() {
        return GameResponseDto.builder()
                .userChoice(ROCK.name())
                .machineChoice(SCISSORS.name())
                .result(USER)
                .build();
    }

    /**
     * Creates test instance for game dto object.
     *
     * @return game dto object
     */
    default GameDto createGameJsonDto() {
        return GameDto.builder()
                .username(USERNAME_ONE)
                .hand(ROCK.name())
                .build();
    }

    /**
     * Creates test instance for score dto object.
     *
     * @return score dto object
     */
    default ScoreDto createScoreJsonDto() {
        return ScoreDto.builder()
                .winner(USER.name())
                .build();
    }
}
