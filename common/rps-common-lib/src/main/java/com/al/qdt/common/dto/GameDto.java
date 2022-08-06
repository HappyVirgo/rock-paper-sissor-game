package com.al.qdt.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = GameDto.GameDtoBuilder.class)
public class GameDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    String id;

    @NotBlank
    String username;

    @NotBlank
    String hand;

    @JsonPOJOBuilder(withPrefix = "")
    public static class GameDtoBuilder {
    }
}
