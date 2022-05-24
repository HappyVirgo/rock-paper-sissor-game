package com.al.qdt.rps.qry.queries;

import com.al.qdt.cqrs.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
@AllArgsConstructor
public class FindGamesByUsernameQuery extends BaseQuery {
    public static final String USERNAME_MUST_NOT_BE_BLANK = "Username must not be null or empty";

    @NotBlank(message = USERNAME_MUST_NOT_BE_BLANK)
    String username;
}
