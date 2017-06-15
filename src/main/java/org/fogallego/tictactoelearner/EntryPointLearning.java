package org.fogallego.tictactoelearner;

import org.fogallego.tictactoelearner.domain.Board;

import java.util.List;

public class EntryPointLearning {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Incorrect number of arguments.");
            System.out.println("Usage: EntryPointLearning <number of learning iterations>");
        } else {

            Integer numIterations = Integer.valueOf(args[0]);
            EvaluationFunction evaluationFunction = new EvaluationFunction(1,1,1,1,1,1,1);
            ExperimentGenerator experimentGenerator = new ExperimentGenerator();
            PerformanceSystem performanceSystem = new PerformanceSystem();
            Critic critic = new Critic();
            Generaliser generaliser = new Generaliser(0.1);

            System.out.println("Starting " + numIterations + " training's iterations.");

            for (int i = 0; i < numIterations; i++) {
                Board board = experimentGenerator.generateExperiment(evaluationFunction);
                List<Board> lstMoves = performanceSystem.generateCompleteGame(board, evaluationFunction);
                List<List<Integer>> trainingExamples = critic.getTrainingExamples(lstMoves);
                generaliser.train(trainingExamples, evaluationFunction);
                System.out.println("Iteration num: " + i + " - Winner: " + lstMoves.get(lstMoves.size() - 1).getWinner());
            }

            System.out.println("Training finished!");

            System.out.println("W values: ");

            double[] weights = evaluationFunction.getWeights();
            for (int i = 0; i < weights.length; i++) {
                double value = weights[i];
                System.out.println("W"+i+": " + value);
            }

        }

    }

}
