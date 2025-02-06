package ru.innopolis;

import java.io.*;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class App {
    private static <T, R> Function<T, R> ternaryOperator(
            Predicate<? super T> condition,
            Function<? super T, ? extends R> ifTrue,
            Function<? super T, ? extends R> ifFalse) {

        return t -> condition.test(t) ? ifTrue.apply(t) : ifFalse.apply(t);
    }
    public static void main(String[] args) {
        Predicate<Object> condition = Objects::nonNull;

        Function<String, Integer> ifFalse = str -> 0;

        Function<String, Integer> ifTrue = String::length;

        Function<String, Integer> resultFunction = ternaryOperator(condition, ifTrue, ifFalse);

        try (BufferedReader reader = new BufferedReader(new FileReader("files/input.txt"));
             FileWriter writer = new FileWriter("files/output.txt")) {

            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = "null".equals(line) ? null : line;
                int result = resultFunction.apply(processedLine);
                writer.write(result + "\n");
                System.out.println(result);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
