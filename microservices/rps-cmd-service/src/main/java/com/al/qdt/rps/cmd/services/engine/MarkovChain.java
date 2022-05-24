package com.al.qdt.rps.cmd.services.engine;

import com.al.qdt.common.enums.Hand;
import lombok.Getter;

import java.util.stream.IntStream;

import static com.al.qdt.common.enums.Hand.*;

/**
 * This class contains Markov Chain Algorithm functionality.
 */
public class MarkovChain {
    private static final int IDX = 0; // zero index
    private static final int HAND_LENGTH = 3; // total number of options

    @Getter
    private final float[][] chain; // Markov Chain for the AI of machine player (stores probabilities)
    private final int[] timesPlayed;

    private int lastMove; // last move of human player

    public MarkovChain() {
        this.chain = new float[][]{
                {0.33f, 0.33f, 0.33f},
                {0.33f, 0.33f, 0.33f},
                {0.33f, 0.33f, 0.33f}};
        this.timesPlayed = new int[]{0, 0, 0};
    }

    /**
     * Predicts the human player's next move and make move according the prediction.
     *
     * @param randomFloat random float value
     * @return machine move
     */
    public Hand nextMove(float randomFloat) {
        if (randomFloat <= this.chain[this.lastMove][1]) {
            return PAPER;
        } else if (randomFloat <= this.chain[this.lastMove][2] + this.chain[this.lastMove][1]) {
            return SCISSORS;
        }
        return ROCK;
    }

    /**
     * Updates Markov chain with results of the last game.
     *
     * @param userChoice human player input from last game
     */
    public void updateChain(Hand userChoice) {
        // Second to last move of human player
        final var penultimateMove = this.lastMove;
        if (userChoice == ROCK) {
            this.lastMove = 0;
        } else if (userChoice == PAPER) {
            this.lastMove = 1;
        } else {
            this.lastMove = 2;
        }
        // Multiply everything in the appropriate column of the Markov Chain by timesPlayed[penultimateMove]
        IntStream.range(IDX, HAND_LENGTH)
                .forEach(i -> this.chain[penultimateMove][i] *= this.timesPlayed[penultimateMove]);
        // Increment the appropriate row value by one
        this.chain[penultimateMove][this.lastMove] += 1;
        // Increment timesPlayed[penultimateMove] by one
        this.timesPlayed[penultimateMove]++;
        // Divide all values in Markov Chain by timesPlayed[penultimateMove] value
        IntStream.range(0, HAND_LENGTH)
                .forEach(i -> this.chain[penultimateMove][i] /= this.timesPlayed[penultimateMove]);
    }
}
