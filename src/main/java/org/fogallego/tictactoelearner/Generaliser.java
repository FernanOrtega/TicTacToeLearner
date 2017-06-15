package org.fogallego.tictactoelearner;

import java.util.List;

public class Generaliser {

    private double eta;

    public Generaliser(double eta) {
        this.eta = eta;
    }

    public void train(List<List<Integer>> trainingSet, EvaluationFunction evaluationFunction) {
        trainingSet.forEach(row -> {

            double computedValue = evaluationFunction.compute(row);

            for (int i = 0; i < 7; i++) {
                double value = evaluationFunction.getW(i) + eta * ( row.get(6) - computedValue) * row.get(i);
                evaluationFunction.updateW(i, value);
            }
        });
    }

}
