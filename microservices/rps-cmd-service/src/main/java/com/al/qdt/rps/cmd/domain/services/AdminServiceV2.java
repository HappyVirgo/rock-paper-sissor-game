package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;

import java.util.concurrent.CompletableFuture;

/**
 * This interface describes admin service functionality.
 */
public interface AdminServiceV2 {

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
