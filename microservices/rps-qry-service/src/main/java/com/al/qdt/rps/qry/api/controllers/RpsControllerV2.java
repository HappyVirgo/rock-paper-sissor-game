package com.al.qdt.rps.qry.api.controllers;

import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.services.ListOfGamesResponse;
import com.al.qdt.rps.qry.domain.services.RpsServiceV2;
import com.google.protobuf.StringValue;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.GAMES_BY_USER_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.GAMES_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.GAMES_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.GAME_BY_ID_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.GAME_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.MALFORMED_JSON;
import static com.al.qdt.common.helpers.Constants.TEST_ID;
import static com.al.qdt.common.helpers.Constants.USERNAME_ONE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing game rounds.
 */
@Slf4j(topic = "outbound-logs")
@RestController
@RequestMapping(path = "${api.version-two}/${api.endpoint-games}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("game")
@Tag(name = "Game", description = "the rock paper scissors game query REST API endpoints")
public class RpsControllerV2 {
    private final RpsServiceV2 rpsService;

    /**
     * Returns all games.
     *
     * @return collection of games
     */
    @Operation(operationId = "all-proto",
            summary = "Returns all games",
            description = "Returns all games from the database.",
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GameDto.class)),
                            examples = {
                                    @ExampleObject(
                                            value = GAMES_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "404",
                    description = "Games not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAMES_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Timed(value = "game.all", description = "Time taken to return all games", longTask = true)
    public ListOfGamesResponse all() {
        log.info("REST CONTROLLER: Getting all games...");
        return this.rpsService.all();
    }

    /**
     * Finds game by id.
     *
     * @param id game id, must not be null or empty
     * @return found game
     */
    @Operation(operationId = "find-by-id-proto",
            summary = "Finds game by id",
            description = "Finds a game in the database by its id. For valid response try String ids.",
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAME_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid id supplied",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = MALFORMED_JSON
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Games not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAME_BY_ID_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    @Timed(value = "game.findById", description = "Time taken to find game by id", longTask = true)
    public GameDto findById(@Parameter(description = "Id of game that needs to be fetched",
            schema = @Schema(type = "string"), example = TEST_ID, required = true)
                                @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Finding game by id: {}.", id.toString());
        return this.rpsService.findById(id);
    }

    /**
     * Finds games by username.
     *
     * @param username username, must not be null or empty
     * @return found collection of games
     */
    @Operation(operationId = "find-by-username-proto",
            summary = "Finds games by username",
            description = "Finds games in the database by username.",
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = GameDto.class)),
                            examples = {
                                    @ExampleObject(
                                            value = GAMES_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid username supplied",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = MALFORMED_JSON
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404",
                    description = "Games not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAMES_BY_USER_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users/{username}")
    @Timed(value = "game.findByUsername", description = "Time taken to find games by winner", longTask = true)
    public ListOfGamesResponse findByUsername(@Parameter(description = "Username of games that need to be fetched",
            schema = @Schema(type = "string"), example = USERNAME_ONE, required = true)
                                            @Valid @NotNull @PathVariable(value = "username") StringValue username) {
        log.info("REST CONTROLLER: Finding game by username: {}.", username.getValue());
        return this.rpsService.findByUsername(username);
    }
}
