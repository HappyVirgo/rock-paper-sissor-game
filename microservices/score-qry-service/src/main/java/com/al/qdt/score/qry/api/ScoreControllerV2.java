package com.al.qdt.score.qry.api;

import com.al.qdt.common.errors.ApiError;
import com.al.qdt.rps.grpc.v1.common.Player;
import com.al.qdt.rps.grpc.v1.dto.ScoreDto;
import com.al.qdt.rps.grpc.v1.services.ListOfScoresResponse;
import com.al.qdt.score.qry.services.ScoreServiceV2;
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

import static com.al.qdt.common.helpers.Constants.MALFORMED_JSON;
import static com.al.qdt.common.helpers.Constants.SCORES_BY_WINNER_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.SCORES_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.SCORES_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.SCORE_BY_ID_NOT_FOUND_JSON;
import static com.al.qdt.common.helpers.Constants.SCORE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.TEST_ID;
import static com.al.qdt.common.helpers.Constants.TEST_WINNER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing game scores.
 */
@Slf4j
@RestController
@RequestMapping(path = "${api.version-two}/${api.endpoint-scores}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("score")
@Tag(name = "Score", description = "the score query REST API endpoints")
public class ScoreControllerV2 {
    private final ScoreServiceV2 scoreService;

    /**
     * Returns all scores.
     *
     * @return paged list of scores
     */
    @Operation(operationId = "all-proto",
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
    public ListOfScoresResponse all() {
        log.info("REST CONTROLLER: Getting all scores.");
        return this.scoreService.all();
    }

    /**
     * Finds scores by id.
     *
     * @param id score id, must not be null or empty
     * @return found score
     */
    @Operation(operationId = "find-by-id-proto",
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
    public ScoreDto findById(@Parameter(description = "Id of score that needs to be fetched",
            schema = @Schema(type = "string"), example = TEST_ID, required = true)
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
    @Operation(operationId = "find-by-winner-proto",
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
    public ListOfScoresResponse findByWinner(@Parameter(description = "Winner of scores that need to be fetched",
            schema = @Schema(type = "string", allowableValues = {"USER", "DRAW", "MACHINE"}), example = TEST_WINNER, required = true)
                                             @Valid @NotNull @PathVariable(value = "winner") Player winner) {
        log.info("REST CONTROLLER: Finding scores by winner: {}.", winner);
        return this.scoreService.findByWinner(winner);
    }
}
