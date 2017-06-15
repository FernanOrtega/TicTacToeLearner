package org.fogallego.tictactoelearner;

import org.fogallego.tictactoelearner.domain.Board;

import java.util.List;

// TODO: update code to support ties
public class EvaluationFunction {


    public static final int WINNER_SCORE = 100;
    public static final int TIE_SCORE = 0;
    public static final int LOSER_SCORE = -100;

    private double[] weights;

    public EvaluationFunction(int w0, int w1, int w2, int w3, int w4, int w5, int w6) {

        this.weights = new double[7];

        this.weights[0] = w0;
        this.weights[1] = w1;
        this.weights[2] = w2;
        this.weights[3] = w3;
        this.weights[4] = w4;
        this.weights[5] = w5;
        this.weights[6] = w6;
    }

    public double compute(Board board) {

        List<Integer> lstXs = board.getXs();

        return compute(lstXs);
    }

    public double compute(List<Integer> lstXs) {
        return weights[0] + weights[1]*lstXs.get(0) + weights[2]*lstXs.get(1) +
                weights[3]*lstXs.get(2) + weights[4]*lstXs.get(3) + weights[5]*lstXs.get(4) + weights[6]*lstXs.get(5);
    }

    public void updateW(int i, double value) {
        if (i < 0 || i > 6) {
            System.err.println("Weight " + i + " doesn't exist.");
        } else {
            weights[i] = value;
        }
    }

    public double getW(int i) {

        double result = -1;

        if (i < 0 || i > 6) {
            System.err.println("Weight " + i + " doesn't exist.");
        } else {
            result = weights[i];
        }

        return result;
    }

    public double[] getWeights() {
        return weights;
    }
}
