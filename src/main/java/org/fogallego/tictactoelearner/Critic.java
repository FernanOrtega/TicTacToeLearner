package org.fogallego.tictactoelearner;

import org.fogallego.tictactoelearner.domain.Board;

import java.util.List;
import java.util.stream.Collectors;

// TODO: update code to support ties
public class Critic {

    public List<List<Integer>> getTrainingExamples(List<Board> lstBoard) {

        int winnerMark = lstBoard.get(lstBoard.size() - 1).getWinner();

        return lstBoard.stream().map(board -> {
            List<Integer> result = board.getXs();
            int score;
            if (winnerMark == 0) {
                score = EvaluationFunction.TIE_SCORE;
            }else if (board.getLastMark() == winnerMark) {
                score = EvaluationFunction.WINNER_SCORE;
            } else {
                score = EvaluationFunction.LOSER_SCORE;
            }
            result.add(score);

            return result;
        }).collect(Collectors.toList());
    }

}
