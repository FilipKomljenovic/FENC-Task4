package hr.fer.zemris.nenr;

import hr.fer.zemris.nenr.ga.GenerationGeneticAlgorithm;
import hr.fer.zemris.nenr.functions.IFunction;
import hr.fer.zemris.nenr.ga.TournamentGeneticAlgorithm;
import hr.fer.zemris.nenr.functions.TransmissionFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Debug {
    public static void main(String[] args) throws IOException {
//        runGenerationGeneticAlgorithm("E:\\FAX\\DIPL\\NENR\\dz4\\src\\main\\resources\\dataset1.txt");
        runGenerationGeneticAlgorithm("E:\\FAX\\DIPL\\NENR\\dz4\\src\\main\\resources\\dataset2.txt");
//        runSteadyStateGeneticAlgorithm("E:\\FAX\\DIPL\\NENR\\dz4\\src\\main\\resources\\dataset1.txt");
//        runSteadyStateGeneticAlgorithm("E:\\FAX\\DIPL\\NENR\\dz4\\src\\main\\resources\\dataset2.txt");
    }

    private static void runGenerationGeneticAlgorithm(String filePath) throws IOException {
        List<String> dataset1Lines = Files.readAllLines(Paths.get(filePath));
        List<double[]> dataset = extractDatasetFromList(dataset1Lines);
        IFunction function = new TransmissionFunction(dataset);
        GenerationGeneticAlgorithm generationGeneticAlgorithm = new GenerationGeneticAlgorithm(100, 0.02, 10000, function, true);
        generationGeneticAlgorithm.runAlgorithm();
    }

    private static void runSteadyStateGeneticAlgorithm(String filePath) throws IOException {
        List<String> dataset1Lines = Files.readAllLines(Paths.get(filePath));
        List<double[]> dataset = extractDatasetFromList(dataset1Lines);
        IFunction function = new TransmissionFunction(dataset);
        TournamentGeneticAlgorithm tournamentGeneticAlgorithm = new TournamentGeneticAlgorithm(100, 0.02, 10000, function);
        tournamentGeneticAlgorithm.runAlgorithm();
    }

    private static List<double[]> extractDatasetFromList(List<String> allLines) {
        return allLines.stream()
                .map(line -> line.split("\\s+"))
                .map(line -> Arrays.stream(line)
                        .mapToDouble(Double::parseDouble)
                        .toArray())
                .collect(toList());
    }
}
