package com.al.qdt.rps.qry.api;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.rps.qry.exceptions.GameNotFoundException;
import com.al.qdt.rps.qry.services.RpsServiceV1;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing game rounds.
 */
@Slf4j(topic = "outbound-logs")
@RestController
@RequestMapping(path = "${api.version-one}/${api.endpoint-games}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("game")
@Tag(name = "Game", description = "the rock paper scissors game query REST API endpoints")
public class RpsControllerV1 {
    private final RpsServiceV1 rpsService;

    /**
     * Returns all games.
     *
     * @return collection of games
     */
    @Operation(operationId = "all-json",
            summary = "Returns all games",
            description = "Returns all games from the database.",
            deprecated = true,
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
    public Iterable<GameDto> all() {
        log.info("REST CONTROLLER: Getting all games...");
        return this.rpsService.all();
    }

    /**
     * Finds game by id.
     *
     * @param id game id, must not be null or empty
     * @return found game
     */
    @Operation(operationId = "find-by-id-json",
            summary = "Finds game by id",
            description = "Finds a game in the database by its id. For valid response try String ids.",
            deprecated = true,
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
    public GameDto findById(@Parameter(description = "Id of game that needs to be fetched", example = TEST_ID, required = true)
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
    @Operation(operationId = "find-by-username-json",
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
    public Iterable<GameDto> findByUsername(@Parameter(description = "Username of games that need to be fetched", example = USERNAME_ONE, required = true)
                                            @Valid @NotBlank @PathVariable(value = "username") String username)
            throws GameNotFoundException {
        log.info("REST CONTROLLER: Finding game by username: {}.", username);
        return this.rpsService.findByUsername(username);
    }
}
