package com.al.qdt.rps.cmd.domain.services.grpc.observers;

import com.al.qdt.rps.grpc.v1.services.GameResponse;
import io.grpc.stub.StreamObserver;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class GameStreamResponseObserver implements StreamObserver<GameResponse> {
    private final List<GameResponse> responses;
    private Throwable error;
    private boolean completed;

    public GameStreamResponseObserver() {
        this.responses = new LinkedList<>();
    }

    public boolean getCompleted() {
        return this.completed;
    }

    @Override
    public void onNext(GameResponse gameResponse) {
        this.responses.add(gameResponse);
    }

    @Override
    public void onError(Throwable throwable) {
        this.error = throwable;
    }

    @Override
    public void onCompleted() {
        this.completed = true;
    }
}
