package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.functions.IFunction;
import hr.fer.zemris.nenr.ga.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.*;

public class GenerationGeneticAlgorithm extends GeneticAlgorithm {
    private final boolean elitismEnabled;

    public GenerationGeneticAlgorithm(int populationSize, double mutationPercentage, int maxIterations, IFunction function, boolean elitismEnabled) {
        super(populationSize, mutationPercentage, maxIterations, function);
        this.elitismEnabled = elitismEnabled;
    }

    public void runAlgorithm() {
        population = fillPopulation();
        runRouletteWheel();
    }

    private void runRouletteWheel() {
        int iterations = 0;
        Chromosome bestChromosome;
        do {
            iterations++;
            bestChromosome = findBestChromosome();
            System.out.println("Najbolja jedinka za " + iterations + "-tu generaciju je: " + bestChromosome.getFitness() +
                    " Njeno rješenje je: b0=" + bestChromosome.getGenes()[0] + " b1=" + bestChromosome.getGenes()[1] +
                    " b2=" + bestChromosome.getGenes()[2] + " b3=" + bestChromosome.getGenes()[3] + " b4=" + bestChromosome.getGenes()[4]);
            population = createPopulation(elitismEnabled);
        } while (iterations < maxIterations && bestChromosome.getFitness() > minError);
    }

    private List<Chromosome> createPopulation(boolean elitismEnabled) {
        List<Chromosome> generation = new ArrayList<>(populationSize);
        int size = populationSize;
        if (elitismEnabled) {
            generation.add(findBestChromosome());
            size -= 1;
        }

        double[] chromosomePickProbability = new double[populationSize];
        double fitnessSum = 0;

        for (Chromosome chromosome : population) {
            fitnessSum += chromosome.getFitness();
        }
        for (int i = 0; i < populationSize; i++) {
            chromosomePickProbability[i] = population.get(i).getFitness() / fitnessSum;
        }

        for (int i = 0; i < size; i++) {
            Chromosome parent1 = new Chromosome(new double[numberOfVariables]);
            Chromosome parent2 = new Chromosome(new double[numberOfVariables]);
            for (int i2 = 0; i2 < 2; i2++) {
                double current = 0;
                double randValue = random.nextDouble();
                for (int j = 0; j < populationSize; j++) {
                    current += chromosomePickProbability[j];
                    if (current <= randValue) {
                        if (i2 == 0) {
                            parent1 = population.get(j);
                        } else {
                            parent2 = population.get(j);
                        }
                    } else {
                        break;
                    }
                }
            }
            generation.add(chromosomeMutator.mutateChromosome(createChildUsingBlxAlpha(parent1, parent2)));
        }

        return generation;
    }

    private Chromosome findWorstChromosomeInPopulation() {
        double worst = -Double.MAX_VALUE;
        Chromosome worstChromosome = null;
        for (Chromosome chromosome : population) {
            if (chromosome.getFitness() > worst) {
                worst = chromosome.getFitness();
                worstChromosome = chromosome;
            }
        }
        return worstChromosome;
    }

}
