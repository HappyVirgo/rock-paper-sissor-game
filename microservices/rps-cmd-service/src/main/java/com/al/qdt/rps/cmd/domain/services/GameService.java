package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.enums.Player;

/**
 * This interface describes a Rock Paper Scissor game.
 */
public interface GameService {

    /**
     * Returns user player choice.
     *
     * @param userChoice user player choice
     */
    void pickUserChoice(Hand userChoice);

    /**
     * Returns machine player choice.
     *
     * @return machine player choice
     */
    Hand pickMachineChoice();

    /**
     * Updates the Markov Chain with the last game human player choice
     */
    void updateChain(Hand userChoice);

    /**
     * Calculates game round result.
     *
     * @return winner of the round
     */
    Player calculateResult();

    /**
     * Returns Markov chain array.
     *
     * @return Markov chain array
     */
    float[][] getMarkovChain();
}
