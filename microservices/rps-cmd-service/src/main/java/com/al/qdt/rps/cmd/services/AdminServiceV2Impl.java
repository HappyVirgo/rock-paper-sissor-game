package com.al.qdt.rps.cmd.services;

import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;
import com.al.qdt.rps.grpc.v1.common.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceV2Impl implements AdminServiceV2 {
    private final CommandDispatcher commandDispatcher;

    @Override
    public BaseResponseDto restoreDb() {
        this.commandDispatcher.send(RestoreDbCommand.builder()
                .id(UUID.randomUUID())
                .build());
        return BaseResponseDto.newBuilder()
                .setMessage(SUCCESS_MESSAGE)
                .build();
    }
}
