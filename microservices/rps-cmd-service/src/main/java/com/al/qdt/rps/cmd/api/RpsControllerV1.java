package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.dto.GameDto;
import com.al.qdt.common.dto.GameResponseDto;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.rps.cmd.services.RpsServiceV1;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Servers;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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

import static com.al.qdt.common.helpers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


/**
 * Controller for managing game rounds.
 */
@Slf4j
@RestController
@RequestMapping(path = "${api.version-one}/${api.endpoint-games}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("game") // enables timings on every request handler in the controller
@Tag(name = "Game", description = "the game command REST API endpoints")
public class RpsControllerV1 {
    private final RpsServiceV1 rpsService;

    /**
     * Plays game.
     *
     * @param gameDto game round user inputs, must not be null
     * @return game result
     */
    @Operation(operationId = "play-json",
            summary = "Plays game",
            description = "Plays game and adds result to the database.",
            deprecated = true,
            tags = {"game"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = GameResponseDto.class),
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
    @PostMapping
    @Timed(value = "game.play",
            description = "Time taken to play a round",
            longTask = true, // long task timers require a separate metric name
            percentiles = {0.5, 0.90})
    public GameResponseDto play(@Parameter(description = "Game inputs that needs to be processed", required = true)
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
     * Deletes game by id.
     *
     * @param id game id, must not be null or empty
     */
    @Operation(operationId = "delete-by-id-json",
            summary = "Deletes game by id",
            description = "Deletes game from the database by its id. For valid response try String ids.",
            deprecated = true,
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
    @DeleteMapping(path = "/{id}")
    @Timed(value = "game.deleteById", description = "Time taken to delete game by id", longTask = true)
    public void deleteById(@Parameter(description = "Id of game that needs to be deleted", example = TEST_ID, required = true)
                           @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Deleting game by id: {}.", id.toString());
        this.rpsService.deleteById(id);
    }
}
