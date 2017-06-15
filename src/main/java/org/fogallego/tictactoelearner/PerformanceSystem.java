package org.fogallego.tictactoelearner;

import org.fogallego.tictactoelearner.domain.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PerformanceSystem {

    public List<Board> generateCompleteGame(Board board, EvaluationFunction evaluationFunction) {

        Board lastMove = board;
        List<Board> lstBoard = new ArrayList<>();
        boolean endGame = false;
        Random rdm = new Random();
        int moveMark;
        int otherMark;
        if (rdm.nextBoolean()) {
            moveMark = Board.CIRCLE;
            otherMark = Board.CROSS;
        } else {
            moveMark = Board.CROSS;
            otherMark = Board.CIRCLE;
        }

        while(!endGame) {
            Board nextMove = getNextMove(lastMove, evaluationFunction, moveMark);
            lstBoard.add(nextMove);
            lastMove = nextMove;

            if (nextMove.checkEndGame()) {
                endGame = true;
            } else {
                int auxMark = moveMark;
                moveMark = otherMark;
                otherMark = auxMark;
            }
        }

        return lstBoard;
    }

    private Board getNextMove(Board board, EvaluationFunction evaluationFunction, int mark) {

        Board bestNextMove=null;
        double bestScore = Integer.MIN_VALUE;
        List<Board> lstCandidates = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board nextMove;
                nextMove = new Board(board);
                int[][] squares = nextMove.getSquares();
                if (squares[i][j] == Board.EMPTY) {
                    try {
                        nextMove.addMove(i,j,mark);
                        lstCandidates.add(nextMove);
                    } catch (IllegalMoveException e) {
                        System.err.println("Something went wrong!");
                    }
                }
            }
        }

        if (lstCandidates.size() == 0) {
            System.out.println();
        }

        Collections.shuffle(lstCandidates);

        for (Board candidate : lstCandidates) {
            double score = evaluationFunction.compute(candidate);
            if (score > bestScore) {
                bestNextMove = candidate;
                bestScore = score;
            }
        }

        if (bestNextMove == null) {
            Random rdm = new Random();
            if (lstCandidates.size() > 1) {
                bestNextMove = lstCandidates.get(rdm.nextInt(lstCandidates.size() - 1));
            } else {
                bestNextMove = lstCandidates.get(0);
            }
        }

        return bestNextMove;
    }
}
