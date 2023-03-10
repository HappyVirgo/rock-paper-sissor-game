package com.al.qdt.rps.cmd.base;

import com.al.qdt.common.api.dto.BaseResponseDto;
import com.al.qdt.common.api.dto.GameDto;
import com.al.qdt.common.api.dto.GameResponseDto;

import static com.al.qdt.common.enums.Hand.ROCK;
import static com.al.qdt.common.enums.Hand.SCISSORS;
import static com.al.qdt.common.enums.Player.USER;
import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;
import static com.al.qdt.common.helpers.Constants.TEST_ID;

public interface DtoTests {

    /**
     * Creates test instance for base response dto object.
     *
     * @return base response dto object
     */
    default BaseResponseDto createBaseResponse() {
        return BaseResponseDto.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }

    /**
     * Creates test instance for game response dto object.
     *
     * @return game response dto object
     */
    default GameResponseDto createGameResponseDto() {
        return GameResponseDto.builder()
                .userChoice(ROCK.name())
                .machineChoice(SCISSORS.name())
                .result(USER)
                .build();
    }

    /**
     * Creates test instance for game dto object.
     *
     * @param username username
     * @return game dto object
     */
    default GameDto createGameDto(String username) {
        return GameDto.builder()
                .id(TEST_ID)
                .username(username)
                .hand(ROCK.name())
                .build();
    }

    /**
     * Creates test instance for game dto object without username.
     *
     * @return game dto object
     */
    default GameDto createGameDtoWithMissedUserName(){
        return GameDto.builder()
                .hand(ROCK.name())
                .build();
    }
}
