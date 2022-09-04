package com.al.qdt.rps.cmd.api.controllers;

import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.rps.cmd.domain.services.RpsServiceV2;
import com.al.qdt.rps.grpc.v1.dto.GameDto;
import com.al.qdt.rps.grpc.v1.dto.GameResultDto;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.al.qdt.common.helpers.Constants.GAME_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.GAME_RESPONSE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.HANDLER_ERROR_JSON;
import static com.al.qdt.common.helpers.Constants.MALFORMED_JSON;
import static com.al.qdt.common.helpers.Constants.MULTIPLE_HANDLERS_ERROR_JSON;
import static com.al.qdt.common.helpers.Constants.TEST_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing game rounds.
 */
@Slf4j(topic = "outbound-logs")
@RestController
@RequestMapping(produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("game") // enables timings on every request handler in the controller
@Tag(name = "Game", description = "the game command REST API endpoints")
public class RpsControllerV2 {
    private final RpsServiceV2 rpsService;

    /**
     * Plays game.
     *
     * @param gameDto game round user inputs, must not be null
     * @return game result
     */
    @Operation(operationId = "play-proto",
            summary = "Plays game",
            description = "Plays game and adds result to the database.",
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAME_RESPONSE_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid game inputs supplied",
                                            value = MALFORMED_JSON
                                    ),
                                    @ExampleObject(
                                            name = "No command handler",
                                            value = HANDLER_ERROR_JSON
                                    ),
                                    @ExampleObject(
                                            name = "Multiple command handlers",
                                            value = MULTIPLE_HANDLERS_ERROR_JSON
                                    )}
                    ))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("${api.version-two}/${api.endpoint-games}")
    @Timed(value = "game.play", description = "Time taken to play a round", longTask = true, percentiles = {0.5, 0.90})
    public GameResultDto play(@Parameter(description = "Game inputs that needs to be processed", required = true)
                              @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                      content = @Content(
                                              mediaType = APPLICATION_JSON_VALUE,
                                              schema = @Schema(implementation = GameDto.class),
                                              examples = {
                                                      @ExampleObject(
                                                              value = GAME_EXPECTED_JSON
                                                      )
                                              }))
                              @Valid @NotNull @RequestBody GameDto gameDto) {
        log.info("REST CONTROLLER: Playing game...");
        return this.rpsService.play(gameDto);
    }

    /**
     * Plays game asynchronously.
     *
     * @param gameDto game round user inputs, must not be null
     * @return game result
     */
    @Operation(operationId = "play-async-proto",
            summary = "Plays game asynchronously",
            description = "Plays game and adds result to the database asynchronously.",
            tags = {"game-async"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = GAME_RESPONSE_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid game inputs supplied",
                                            value = MALFORMED_JSON
                                    ),
                                    @ExampleObject(
                                            name = "No command handler",
                                            value = HANDLER_ERROR_JSON
                                    ),
                                    @ExampleObject(
                                            name = "Multiple command handlers",
                                            value = MULTIPLE_HANDLERS_ERROR_JSON
                                    )}
                    ))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("${api.version-two-async}/${api.endpoint-games}")
    @Timed(value = "game.play.async", description = "Time taken to play a round asynchronously", longTask = true, percentiles = {0.5, 0.90})
    public CompletableFuture<GameResultDto> playAsync(@Parameter(description = "Game inputs that needs to be processed", required = true)
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                              content = @Content(
                                                                      mediaType = APPLICATION_JSON_VALUE,
                                                                      schema = @Schema(implementation = GameDto.class),
                                                                      examples = {
                                                                              @ExampleObject(
                                                                                      value = GAME_EXPECTED_JSON
                                                                              )
                                                                      }))
                                                      @Valid @NotNull @RequestBody GameDto gameDto) {
        log.info("REST CONTROLLER: Playing game asynchronously...");
        return this.rpsService.playAsync(gameDto);
    }

    /**
     * Deletes game by id.
     *
     * @param id game id, must not be null
     */
    @Operation(operationId = "delete-by-id-proto",
            summary = "Deletes game by id",
            description = "Deletes game from the database by its id. For valid response try String ids.",
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Successful operation",
                    content = @Content),
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
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid game id supplied",
                                            value = MALFORMED_JSON
                                    ),
                                    @ExampleObject(
                                            name = "No command handler",
                                            value = HANDLER_ERROR_JSON
                                    ),
                                    @ExampleObject(
                                            name = "Multiple command handlers",
                                            value = MULTIPLE_HANDLERS_ERROR_JSON
                                    )}
                    ))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("${api.version-two}/${api.endpoint-games}/{id}")
    @Timed(value = "game.deleteById", description = "Time taken to delete game by id", longTask = true)
    public void deleteById(@Parameter(description = "Id of game that needs to be deleted",
            schema = @Schema(type = "string"), example = TEST_ID, required = true)
                           @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Deleting game by id: {}.", id);
        this.rpsService.deleteById(id);
    }

    /**
     * Deletes game by id asynchronously.
     *
     * @param id game id, must not be null
     */
    @Operation(operationId = "delete-by-id-async-proto",
            summary = "Deletes game by id asynchronously",
            description = "Deletes game from the database by its id asynchronously. For valid response try String ids.",
            tags = {"game-async"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Successful operation",
                    content = @Content),
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
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid game id supplied",
                                            value = MALFORMED_JSON
                                    ),
                                    @ExampleObject(
                                            name = "No command handler",
                                            value = HANDLER_ERROR_JSON
                                    ),
                                    @ExampleObject(
                                            name = "Multiple command handlers",
                                            value = MULTIPLE_HANDLERS_ERROR_JSON
                                    )}
                    ))
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("${api.version-two-async}/${api.endpoint-games}/{id}")
    @Timed(value = "game.deleteById.async", description = "Time taken to delete game by id asynchronously", longTask = true)
    public void deleteByIdAsync(@Parameter(description = "Id of game that needs to be deleted",
            schema = @Schema(type = "string"), example = TEST_ID, required = true)
                                @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Deleting game by id: {} asynchronously.", id);
        this.rpsService.deleteByIdAsync(id);
    }
}
