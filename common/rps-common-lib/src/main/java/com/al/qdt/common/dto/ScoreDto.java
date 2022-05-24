package com.al.qdt.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ScoreDto.ScoreDtoBuilder.class)
public class ScoreDto implements Serializable {
    private static final long serialVersionUID = 1L;

    String winner;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ScoreDtoBuilder {
    }
}
