package com.al.qdt.rps.cmd.services;

import com.al.qdt.common.dto.BaseResponseDto;

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
}
