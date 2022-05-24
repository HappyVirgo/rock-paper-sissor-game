package com.al.qdt.score.cmd.api;

import com.al.qdt.common.errors.ApiError;
import com.al.qdt.score.cmd.services.ScoreServiceV1;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@RestController
@RequestMapping(path = "${api.version-one}/${api.endpoint-scores}", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Timed("score")
@Tag(name = "Score", description = "the score command REST API endpoints")
public class ScoreControllerV1 {
    private final ScoreServiceV1 scoreService;

    /**
     * Deletes score by id.
     *
     * @param id score id, must not be null or empty
     */
    @Operation(operationId = "delete-by-id-json",
            summary = "Deletes score by id",
            description = "Deletes a score from the database by its id. For valid response try String ids.",
            deprecated = true,
            tags = {"score"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Successful operation",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid score id supplied",
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
    @Timed(value = "score.deleteById", description = "Time taken to delete score by id", longTask = true)
    public void deleteById(@Parameter(description = "Id of score that needs to be deleted", example = TEST_ID, required = true)
                           @Valid @NotNull @PathVariable(value = "id") UUID id) {
        log.info("REST CONTROLLER: Deleting scores by id: {}.", id.toString());
        this.scoreService.deleteById(id);
    }
}
