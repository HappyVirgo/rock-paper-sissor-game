package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.common.api.dto.BaseResponseDto;

import java.util.concurrent.CompletableFuture;

/**
 * This interface describes admin service functionality.
 */
public interface AdminServiceV1 {

    /**
     * Restore database.
     *
     * @return operation result
     */
    BaseResponseDto restoreDb();

    /**
     * Restore database asynchronously.
     *
     * @return operation result
     */
    CompletableFuture<BaseResponseDto> restoreDbAsync();
}
