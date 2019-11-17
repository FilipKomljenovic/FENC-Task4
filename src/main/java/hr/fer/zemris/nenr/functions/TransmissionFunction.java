package hr.fer.zemris.nenr.functions;

import hr.fer.zemris.nenr.ga.chromosome.Chromosome;

import java.util.List;

import static java.lang.Math.*;

public class TransmissionFunction implements IFunction {

    private List<double[]> inputValues;

    public TransmissionFunction(List<double[]> inputValues) {
        this.inputValues = inputValues;
    }

    public double valueAt(Chromosome chromosome) {
        return valueAt(chromosome.getGenes());
    }

    @Override
    public double valueAt(double[] chromosomeGenes) {
        double squaredError = 0;

        for (double[] inputValue : inputValues) {
            squaredError += pow(inputValue[2] - (sin(chromosomeGenes[0] + chromosomeGenes[1] * inputValue[0])
                    + chromosomeGenes[2] * cos(inputValue[0] * (chromosomeGenes[3] + inputValue[1])) *
                    (1. / (1 + exp(pow(inputValue[0] - chromosomeGenes[4], 2))))), 2);
        }

        return squaredError / inputValues.size();
    }
}
