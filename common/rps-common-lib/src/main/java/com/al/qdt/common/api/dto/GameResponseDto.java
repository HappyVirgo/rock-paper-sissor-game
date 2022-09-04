package com.al.qdt.common.api.dto;

import com.al.qdt.common.enums.Player;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = GameResponseDto.GameResponseDtoBuilder.class)
public class GameResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "user_choice")
    String userChoice;

    @JsonProperty(value = "machine_choice")
    String machineChoice;

    Player result;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GameResponseDtoBuilder {
    }
}
