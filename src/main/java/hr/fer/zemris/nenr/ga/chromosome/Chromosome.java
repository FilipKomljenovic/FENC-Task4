package hr.fer.zemris.nenr.ga.chromosome;

import hr.fer.zemris.nenr.functions.IFunction;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Chromosome {
    private double[] genes;
    private double fitness;

    public Chromosome(double[] genes) {
        this.genes = genes;
    }

    public Chromosome(double[] genes, IFunction function) {
        this.genes = genes;
        this.fitness = function.valueAt(genes);
    }
}
