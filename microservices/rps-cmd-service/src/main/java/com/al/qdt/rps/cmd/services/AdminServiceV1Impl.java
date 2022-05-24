package com.al.qdt.rps.cmd.services;

import com.al.qdt.common.dto.BaseResponseDto;
import com.al.qdt.cqrs.infrastructure.CommandDispatcher;
import com.al.qdt.rps.cmd.commands.RestoreDbCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.al.qdt.common.helpers.Constants.SUCCESS_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceV1Impl implements AdminServiceV1 {
    private final CommandDispatcher commandDispatcher;

    @Override
    public BaseResponseDto restoreDb() {
        this.commandDispatcher.send(RestoreDbCommand.builder()
                .id(UUID.randomUUID())
                .build());
        return BaseResponseDto.builder()
                .message(SUCCESS_MESSAGE)
                .build();
    }
}
