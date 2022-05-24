package com.al.qdt.rps.cmd.services.base;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.enums.Player;
import com.al.qdt.rps.cmd.services.GameService;
import com.al.qdt.rps.cmd.services.engine.RoundResult;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

import static com.al.qdt.common.enums.RpsMetrics.GAME_DELETED_COUNT_METRIC;
import static com.al.qdt.common.enums.RpsMetrics.GAME_PLAYED_COUNT_METRIC;

@Slf4j
@RequiredArgsConstructor
public abstract class RpsBaseService {
    private final GameService gameService;
    private final MeterRegistry meterRegistry;

    private Counter playedGamesCounter;
    private Counter deletedGamesCounter;

    @PostConstruct
    public void init() {
        this.playedGamesCounter = this.meterRegistry.counter(GAME_PLAYED_COUNT_METRIC.name()); // initialize
        this.deletedGamesCounter = this.meterRegistry.counter(GAME_DELETED_COUNT_METRIC.name()); // initialize
    }

    protected RoundResult play(Hand userChoice) {
        this.gameService.pickUserChoice(userChoice);
        final var machineChoice = this.gameService.pickMachineChoice();
        final var winner = this.gameService.calculateResult();

        log.info("You played {} and the machine played {}",
                userChoice.name(),
                machineChoice.name());

        if (Player.USER == winner) {
            log.info("You won!");
        }
        if (Player.MACHINE == winner) {
            log.info("Machine won!");
        }
        if (Player.DRAW == winner) {
            log.info("Draw!");
        }

        // Update the Markov Chain with the last game human player choice
        this.gameService.updateChain(userChoice);

        return RoundResult.builder()
                .machineChoice(machineChoice)
                .winner(winner)
                .build();
    }

    /**
     * Returns game round statistics for Markov chain.
     *
     * @param x 1st dimension
     * @param y 2nd dimension
     * @return game round statistics for Markov chain
     */
    protected float getStatistics(int x, int y) {
        return this.gameService.getMarkovChain()[x][y];
//        System.out.println("Rock to Rock: " + this.gameService.getMarkovChain()[0][0] + " Rock to Paper: " + this.gameService.getMarkovChain()[0][1] + " Rock to Scissors: " + this.gameService.getMarkovChain()[0][2]);
//        System.out.println("Paper to Rock: " + this.gameService.getMarkovChain()[1][0] + " Paper to Paper: " + this.gameService.getMarkovChain()[1][1] + " Paper to Scissors: " + this.gameService.getMarkovChain()[1][2]);
//        System.out.println("Scissors to Rock: " + this.gameService.getMarkovChain()[2][0] + " Scissors to Paper: " + this.gameService.getMarkovChain()[2][1] + " Scissors to Scissors: " + this.gameService.getMarkovChain()[2][2]);
    }

    /**
     * Updates played game metrics.
     */
    protected void updatePlayedGameMetrics() {
        // Increment count of games played metrics
        this.playedGamesCounter.increment();
    }

    /**
     * Updates delete game metrics.
     */
    protected void updateDeleteGameMetrics() {
        // Increment count of deleted games metrics
        this.deletedGamesCounter.increment();
    }
}
