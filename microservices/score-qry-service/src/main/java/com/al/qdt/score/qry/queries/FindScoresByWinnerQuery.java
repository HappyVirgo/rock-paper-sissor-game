package com.al.qdt.score.qry.queries;

import com.al.qdt.cqrs.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@AllArgsConstructor
public class FindScoresByWinnerQuery implements BaseQuery {
    public static final String WINNER_MUST_NOT_BE_BLANK = "Winner must not be null or empty";

    @NotBlank(message = WINNER_MUST_NOT_BE_BLANK)
    String winner;
}
