package com.al.qdt.score.qry.api;

import com.al.qdt.common.dto.ScoreDto;
import com.al.qdt.common.enums.Player;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.score.qry.services.ScoreServiceV1;
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
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing game scores.
 */
@Slf4j(topic = "outbound-logs")
@RestController
@RequestMapping(path = "${api.version-one}/${api.endpoint-scores}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("score")
@Tag(name = "Score", description = "the score query REST API endpoints")
public class ScoreControllerV1 {
    private final ScoreServiceV1 scoreService;

    /**
     * Returns all scores.
     *
     * @return collection of scores
     */
    @Operation(operationId = "all-json",
            summary = "Returns all scores",
            description = "Returns all scores from the database.",
            tags = {"score"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ScoreDto.class)),
                            examples = {
                                    @ExampleObject(
                                            value = SCORES_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "404",
                    description = "Scores not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = SCORES_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    @Timed(value = "score.all", description = "Time taken to return all scores", longTask = true)
    public Iterable<ScoreDto> all() {
        log.info("REST CONTROLLER: Getting all scores.");
        return this.scoreService.all();
    }

    /**
     * Finds scores by id.
     *
     * @param id score id, must not be null or empty
     * @return found score
     */
    @Operation(operationId = "find-by-id-json",
            summary = "Finds scores by id",
            description = "Finds a score in the database by its id. For valid response try String ids.",
            tags = {"score"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ScoreDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = SCORE_EXPECTED_JSON
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
                    description = "Scores not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = SCORE_BY_ID_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}")
    @Timed(value = "score.findById", description = "Time taken to find a score by its id", longTask = true)
    public ScoreDto findById(@Parameter(description = "Id of score that needs to be fetched", example = TEST_ID, required = true)
                             @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Finding scores by id: {}.", id.toString());
        return this.scoreService.findById(id);
    }

    /**
     * Finds scores by winner.
     *
     * @param winner scores winner, must not be null
     * @return found collection of scores
     */
    @Operation(operationId = "find-by-winner-json",
            summary = "Finds scores by winner",
            description = "Finds scores in the database by a winner type.",
            tags = {"score"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ScoreDto.class)),
                            examples = {
                                    @ExampleObject(
                                            value = SCORES_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid winner supplied",
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
                    description = "Scores not found",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            value = SCORES_BY_WINNER_NOT_FOUND_JSON
                                    )
                            }
                    ))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users/{winner}")
    @Timed(value = "score.findByWinner", description = "Time taken to find scores by winner type", longTask = true)
    public Iterable<ScoreDto> findByWinner(
            @Parameter(description = "Winner of scores that need to be fetched", example = TEST_WINNER, required = true)
            @Valid @NotNull @PathVariable(value = "winner") Player winner) {
        log.info("REST CONTROLLER: Finding scores by winner: {}.", winner);
        return this.scoreService.findByWinner(winner);
    }
}
