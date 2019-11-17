package hr.fer.zemris.nenr.ga;

import hr.fer.zemris.nenr.functions.IFunction;
import hr.fer.zemris.nenr.ga.chromosome.Chromosome;

import java.util.ArrayList;
import java.util.List;

import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.minError;
import static hr.fer.zemris.nenr.ga.GeneticAlgorithmConstants.tournamentSize;


public class TournamentGeneticAlgorithm extends GeneticAlgorithm {

    public TournamentGeneticAlgorithm(int populationSize, double mutationPercentage, int maxIterations, IFunction function) {
        super(populationSize, mutationPercentage, maxIterations, function);
    }

    public void runAlgorithm() {
        population = fillPopulation();
        runTournament();
    }

    private void runTournament() {
        int iterations = 0;
        Chromosome bestChromosome;
        do {
            createPopulationTournament();
            iterations++;
            bestChromosome = findBestChromosome();
            System.out.println("Najbolja jedinka za " + iterations + "-tu generaciju je: " + bestChromosome.getFitness() +
                    " Njeno rješenje je: b0=" + bestChromosome.getGenes()[0] + " b1=" + bestChromosome.getGenes()[1] +
                    " b2=" + bestChromosome.getGenes()[2] + " b3=" + bestChromosome.getGenes()[3] + " b4=" + bestChromosome.getGenes()[4]);
        } while (iterations < maxIterations && bestChromosome.getFitness() > minError);
    }

    private void createPopulationTournament() {
        for (int i = 0; i < populationSize; i++) {
            List<Chromosome> candidates = new ArrayList<>(tournamentSize);
            for (int j = 0; j < tournamentSize; j++) {
                candidates.add(population.get(random.nextInt(populationSize)));
            }
            Chromosome worstChromosome = extractWorstChromosomeFromList(candidates);
            int indexOfWorstChromosome = population.indexOf(worstChromosome);
            population.set(indexOfWorstChromosome, chromosomeMutator.mutateChromosome(createChildUsingBlxAlpha(candidates)));
        }
    }

    private Chromosome extractWorstChromosomeFromList(List<Chromosome> candidates) {
        double worst = -Double.MAX_VALUE;
        Chromosome worstChromosome = null;
        for (Chromosome candidate : candidates) {
            if (candidate.getFitness() > worst) {
                worst = candidate.getFitness();
                worstChromosome = candidate;
            }
        }
        candidates.remove(worstChromosome);
        return worstChromosome;
    }

}
