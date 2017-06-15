package org.fogallego.tictactoelearner;

import org.fogallego.tictactoelearner.domain.Board;

public class ExperimentGenerator {

    /**
     *
     * We take here the most simple hypothesis: new game. But we can implement new strategies.
     *
     * @param evaluationFunction
     * @return
     */
    public Board generateExperiment(EvaluationFunction evaluationFunction) {
        return new Board();
    }

}
