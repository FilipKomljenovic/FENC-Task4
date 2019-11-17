package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.functions.IFunction;
import hr.fer.zemris.nenr.ga.chromosome.Chromosome;
import hr.fer.zemris.nenr.ga.chromosome.ChromosomeMutator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.*;
import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.ALPHA;

public class GeneticAlgorithm {

    static final int numberOfVariables = 5;
    static final Random random = new Random();
    final int populationSize;
    final int maxIterations;
    final IFunction function;
    List<Chromosome> population;
    ChromosomeMutator chromosomeMutator;

    public GeneticAlgorithm(int populationSize, double mutationPercentage, int maxIterations, IFunction function) {
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.function = function;
        this.chromosomeMutator = new ChromosomeMutator(mutationPercentage, function);
    }

    Chromosome findBestChromosome() {
        return findBestChromosomeInList(population);
    }

    Chromosome findBestChromosomeInList(List<Chromosome> chromosomes) {
        double bestValue = Double.MAX_VALUE;
        Chromosome bestChromosome = null;
        for (Chromosome chromosome : chromosomes) {
            if (chromosome.getFitness() < bestValue) {
                bestValue = chromosome.getFitness();
                bestChromosome = chromosome;
            }
        }
        return bestChromosome;
    }

    Chromosome createChildUsingBlxAlpha(List<Chromosome> candidates) {
        return createChildUsingBlxAlpha(candidates.get(0), candidates.get(1));
    }

    Chromosome createChildUsingBlxAlpha(Chromosome parent1, Chromosome parent2) {
        double cMin, cMax, coef;
        double[] childGenes = new double[numberOfVariables];

        for (int i = 0; i < numberOfVariables; i++) {
            double[] parent1Genes = parent1.getGenes();
            double[] parent2Genes = parent2.getGenes();
            if (parent1Genes[i] < parent2Genes[i]) {
                cMin = parent1Genes[i];
                cMax = parent2Genes[i];
            } else {
                cMin = parent2Genes[i];
                cMax = parent1Genes[i];
            }
            coef = cMax - cMin;
            childGenes[i] = cMin - coef * ALPHA + (cMax + coef * ALPHA - (cMin - coef * ALPHA)) * random.nextDouble();
            if (childGenes[i] > rangeMax) {
                childGenes[i] = rangeMax;
            } else if (childGenes[i] < rangeMin) {
                childGenes[i] = rangeMin;
            }
        }

        return new Chromosome(childGenes, function);
    }

    List<Chromosome> fillPopulation() {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double[] genes = new double[numberOfVariables];
            for (int j = 0; j < numberOfVariables; j++) {
                genes[j] = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            }
            population.add(new Chromosome(genes, function));
        }

        return population;
    }

    void calculateFitness() {
        IntStream.range(0, populationSize).forEach(i -> population.get(i).setFitness(function.valueAt(population.get(i))));
    }
}
