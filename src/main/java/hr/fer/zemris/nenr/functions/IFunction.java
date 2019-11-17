package hr.fer.zemris.nenr.functions;

import hr.fer.zemris.nenr.ga.chromosome.Chromosome;

public interface IFunction {
    double valueAt(Chromosome chromosome);
    double valueAt(double[] chromosomeGenes);
}
