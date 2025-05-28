package ru.innopolis.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordFrequencyAnalyzer {
    public static void analyzeAndSave(String inputFile, String outputFile, int topN) {
        try {
            String content = Files.readString(Path.of(inputFile));

            Map<String, Long> wordCounts = Arrays.stream(content.split("[^\\p{L}\\p{N}]+"))
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            String result = wordCounts.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                            .thenComparing(Map.Entry.comparingByKey()))
                    .limit(topN)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(" "));

            Files.writeString(Path.of(outputFile), result);
        } catch (IOException e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }
}
