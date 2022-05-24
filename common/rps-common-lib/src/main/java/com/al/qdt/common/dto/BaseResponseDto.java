package com.al.qdt.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = BaseResponseDto.BaseResponseDtoBuilder.class)
public class BaseResponseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    String message;

    @JsonPOJOBuilder(withPrefix = "")
    public static class BaseResponseDtoBuilder {
    }
}
