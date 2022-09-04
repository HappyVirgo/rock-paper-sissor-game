package com.al.qdt.rps.cmd.domain.services;

import com.al.qdt.common.enums.Hand;
import com.al.qdt.common.enums.Player;
import com.al.qdt.rps.cmd.api.exceptions.InvalidUserInputException;
import com.al.qdt.rps.cmd.domain.services.engine.MarkovChain;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

import static com.al.qdt.common.enums.Hand.EMPTY;
import static com.al.qdt.common.enums.Player.DRAW;
import static com.al.qdt.common.enums.Player.MACHINE;
import static com.al.qdt.common.enums.Player.USER;

/**
 * This class implements Rock Paper Scissor game functionality.
 */
@Service
public class GameServiceImpl implements GameService {
    private final MarkovChain markovChain;
    private Hand userChoice;
    private Hand machineChoice;

    public GameServiceImpl() {
        this.markovChain = new MarkovChain();
    }

    @Override
    public void pickUserChoice(Hand userChoice) {
        if (userChoice == null || userChoice == EMPTY) {
            throw new InvalidUserInputException();
        }
        this.userChoice = userChoice;
    }

    @Override
    public Hand pickMachineChoice() {
        final var rndFloat = ThreadLocalRandom.current().nextFloat();
        this.machineChoice = this.markovChain.nextMove(rndFloat);
        return this.machineChoice;
    }

    @Override
    public void updateChain(Hand userChoice) {
        this.markovChain.updateChain(userChoice);
    }

    @Override
    public Player calculateResult() {
        if (this.machineChoice == this.userChoice) {
            return DRAW;
        }
        if (this.machineChoice.isWinBy(this.userChoice)) {
            return USER;
        }
        return MACHINE;
    }

    @Override
    public float[][] getMarkovChain() {
        return this.markovChain.getChain();
    }
}
