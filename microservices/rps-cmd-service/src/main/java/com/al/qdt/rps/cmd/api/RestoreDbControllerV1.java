package com.al.qdt.rps.cmd.api;

import com.al.qdt.common.dto.BaseResponseDto;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.rps.cmd.services.AdminServiceV1;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.al.qdt.common.helpers.Constants.BASE_RESPONSE_EXPECTED_JSON;
import static com.al.qdt.common.helpers.Constants.HANDLER_ERROR_JSON;
import static com.al.qdt.common.helpers.Constants.MULTIPLE_HANDLERS_ERROR_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controller for managing database.
 */
@Slf4j
@RestController
@RequestMapping(path = "${api.version-one}/${api.endpoint-admin}")
@RequiredArgsConstructor
@Timed("admin")
@Tag(name = "Admin", description = "the admin command REST API endpoints")
public class RestoreDbControllerV1 {
    private final AdminServiceV1 adminService;

    /**
     * Restores database.
     *
     * @return operation result
     */
    @Operation(operationId = "restore-db-json",
            summary = "Restores database",
            description = "Restores database from event storage.",
            deprecated = true,
            tags = {"admin"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successful operation",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseResponseDto.class),
                            examples = {
                                    @ExampleObject(
                                            value = BASE_RESPONSE_EXPECTED_JSON
                                    )
                            })),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request",
                    content = @Content(
                            mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiError.class),
                            examples = {
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
    @Timed(value = "admin", description = "Time taken to restore database", longTask = true)
    public BaseResponseDto restoreDb() {
        log.info("REST CONTROLLER: Restoring database...");
        return this.adminService.restoreDb();
    }
}
