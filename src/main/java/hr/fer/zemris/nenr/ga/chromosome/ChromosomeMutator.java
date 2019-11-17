package hr.fer.zemris.nenr.ga.chromosome;

import hr.fer.zemris.nenr.functions.IFunction;
import lombok.Value;

import java.util.Random;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.*;

@Value
public class ChromosomeMutator {

    private static final Random random = new Random();
    private final double mutationPercentage;
    private final IFunction function;

    public Chromosome mutateChromosome(Chromosome chromosome) {
        double[] genes = chromosome.getGenes().clone();
        for (int i = 0; i < genes.length; i++) {
            if (random.nextDouble() < mutationPercentage) {
                genes[i] = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            }
        }

        return new Chromosome(genes, function);
    }
}
