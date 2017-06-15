package org.fogallego.tictactoelearner.domain;

import org.fogallego.tictactoelearner.IllegalMoveException;

import java.util.ArrayList;
import java.util.List;

// TODO: update code to support ties
public class Board {

    public static final int CROSS = 1;
    public static final int CIRCLE = -1;
    public static final int EMPTY = 100;

    private static final int SUMX1 = 201;
    private static final int SUMX2 = 199;
    private static final int SUMX3 = 102;
    private static final int SUMX4 = 98;
    private static final int SUMX5 = 3;

    private int[][] squares;
    private int lastMark;
    private int winner;

    public Board() {
        squares = new int[3][3];
        for (int[] row : squares) {
            for (int j = 0; j < row.length; j++) {
                row[j] = EMPTY;
            }
        }
        lastMark = EMPTY;
        winner = 0;
    }

    public Board(Board previous) {
        int[][] squaresPrevious = previous.getSquares();
        squares = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(squaresPrevious[i], 0, squares[i], 0, 3);
        }
        lastMark = previous.getLastMark();
    }

    public void addCross(int x, int y) throws IllegalMoveException {
        addMove(x,y,CROSS);
    }

    public void addCircle(int x, int y) throws IllegalMoveException {
        addMove(x,y,CIRCLE);
    }

    public void addMove(int x, int y, int mark) throws IllegalMoveException {
        if (squares[x][y] != EMPTY) {
            throw new IllegalMoveException("Position (" + x + ", " + y + ") is not empty.");
        }

        squares[x][y] = mark;
        lastMark = mark;
    }

    public int getLastMark() {
        return lastMark;
    }

    public boolean checkEndGame() {

        boolean noEmpty = true;
        boolean anyWin = false;

        for (int i = 0; noEmpty && i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                noEmpty = squares[i][j] != EMPTY;
            }
        }

        for (int i = 0; !anyWin && i < 3; i++) {
            int sumRow = 0;
            int sumCol = 0;
            for (int j = 0; j < 3; j++) {
                sumRow += squares[i][j];
                sumCol += squares[j][i];
            }
            if (sumRow == CIRCLE*3 || sumCol == CIRCLE*3) {
                anyWin = true;
                winner = CIRCLE;
            } else if (sumRow == CROSS*3 || sumCol == CROSS*3) {
                anyWin = true;
                winner = CROSS;
            }
        }

        if (!anyWin) {
            int diag1 = squares[0][0] + squares[1][1] + squares[2][2];
            int diag2 = squares[2][0] + squares[1][1] + squares[0][2];

            if (diag1 == CIRCLE*3 || diag2 == CIRCLE*3) {
                anyWin = true;
                winner = CIRCLE;
            } else if (diag1 == CROSS*3 || diag2 == CROSS*3) {
                anyWin = true;
                winner = CROSS;
            }
        }

        return noEmpty || anyWin;
    }

    /*
                V(b) = w0 + w1*x1 + w2*x2 + w3*x3 + w4*x4 + w5*x5 + w6*x6

                x1 = # of lines where there is an x in a completely open row.
                x2 = # of lines where there is an o in a completely open row.
                x3 = # of lines where there are 2 x's in a row with an open subsequent square.
                x4 = # of lines where there are 2 o's in a row with an open subsequent square.
                x5 = # of lines of 3 x's in a row (value of 1 signifies end game)
                x6 = # of lines of 3 o's in a row (value of 1 signifies end game)
             */
    public List<Integer> getXs() {
        List<Integer> lstXs = new ArrayList<>();
        int x1=0, x2=0, x3=0, x4=0, x5=0, x6=0;

        int[][] squares = this.getSquares();
        // rows and columns
        for (int i = 0; i < SUMX5; i++) {
            int accRow = 0;
            int accCol = 0;
            for (int j = 0; j < SUMX5; j++) {
                accRow += squares[i][j];
                accCol += squares[j][i];
            }
            x1 = aggX(accRow, SUMX1) + aggX(accCol, SUMX1);
            x2 = aggX(accRow, SUMX2) + aggX(accCol, SUMX2);
            x3 = aggX(accRow, SUMX3) + aggX(accCol, SUMX3);
            x4 = aggX(accRow, SUMX4) + aggX(accCol, SUMX4);
            x5 = aggX(accRow, SUMX5) + aggX(accCol, SUMX5);
            x6 = aggX(accRow, -SUMX5) + aggX(accCol, -SUMX5);
        }

        // diagonal
        int diag1 = squares[0][0] + squares[1][1] + squares[2][2];
        int diag2 = squares[2][0] + squares[1][1] + squares[0][2];

        x1 = aggX(diag1, SUMX1) + aggX(diag2, SUMX1);
        x2 = aggX(diag1, SUMX2) + aggX(diag2, SUMX2);
        x3 = aggX(diag1, SUMX3) + aggX(diag2, SUMX3);
        x4 = aggX(diag1, SUMX4) + aggX(diag2, SUMX4);
        x5 = aggX(diag1, SUMX5) + aggX(diag2, SUMX5);
        x6 = aggX(diag1, -SUMX5) + aggX(diag2, -SUMX5);

        lstXs.add(x1);
        lstXs.add(x2);
        lstXs.add(x3);
        lstXs.add(x4);
        lstXs.add(x5);
        lstXs.add(x6);

        return lstXs;
    }

    private int aggX(int accRow, int i) {
        return accRow==i?1:0;
    }

    public int[][] getSquares() {
        return squares;
    }

    public void setSquares(int[][] squares) {
        this.squares = squares;
    }

    public int getWinner() {
        return winner;
    }
}
