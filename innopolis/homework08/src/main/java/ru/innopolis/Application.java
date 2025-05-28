package ru.innopolis;

import ru.innopolis.service.GrayCodeGenerator;
import ru.innopolis.service.WordFrequencyAnalyzer;

public class Application {
    public static void main(String[] args) {
        System.out.println("Testing Gray Codes:");
        GrayCodeGenerator.cycleGrayCode(3)
                .limit(10)
                .forEach(System.out::println);

        System.out.println("\nTesting Word Frequency:");
        WordFrequencyAnalyzer.analyzeAndSave("files/input.txt", "files/output.txt", 10);
        System.out.println("Analysis completed. Results saved to output.txt");
    }

}