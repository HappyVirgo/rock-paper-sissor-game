package com.al.qdt.rps.qry.base;

import com.al.qdt.common.dto.GameDto;

import static com.al.qdt.common.enums.Hand.ROCK;

public interface DtoTests {

    /**
     * Creates test instance for game dto object.
     *
     * @param username username
     * @return game dto object
     */
    default GameDto createGameDto(String username) {
        return GameDto.builder()
                .username(username)
                .hand(ROCK.name())
                .build();
    }
}
