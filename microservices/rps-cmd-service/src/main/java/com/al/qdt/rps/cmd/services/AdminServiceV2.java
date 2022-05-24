package com.al.qdt.rps.cmd.services;

import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;

public interface AdminServiceV2 {

    /**
     * This interface describes admin service functionality.
     */
    BaseResponseDto restoreDb();
}
