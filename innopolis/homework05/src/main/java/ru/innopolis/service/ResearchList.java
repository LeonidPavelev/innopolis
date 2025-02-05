package ru.innopolis.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ResearchList {
    public static void researchList () {
        try (BufferedReader reader = new BufferedReader(new FileReader("files/inputList.txt"));
             FileWriter writer = new FileWriter("files/outputList.txt")) {
            List<Integer> list = new ArrayList<>();
            String[] inputSplit = reader.readLine().split(", ");
            for (String element : inputSplit) {
                list.add(Integer.parseInt(element));
            }
            writer.write("Список: " + list + "\n");

            Collections.sort(list);
            writer.write("Список после сортировки в натуральном порядке: " + list + "\n");

            Collections.sort(list, Collections.reverseOrder());
            writer.write("Список после сортировки в обратном порядке: " + list + "\n");

            Collections.shuffle(list);
            writer.write("Список после перемешивания: " + list + "\n");

            Collections.rotate(list, 1);
            writer.write("Список после циклического сдвига на 1 элемент: " + list + "\n");

            Set<Integer> uniqueSet = new HashSet<>(list);
            writer.write("Список с уникальными элементами: " + uniqueSet + "\n");

            Set<Integer> uniqueElements = new HashSet<>();
            Set<Integer> duplicates = new HashSet<>();
            for (Integer element : list) {
                if (!uniqueElements.add(element)) {
                    duplicates.add(element);
                }
            }
            List<Integer> result = new ArrayList<>(duplicates);
            writer.write("Список с дублирующимися элементами: " + result + "\n");

            Integer[] newArray = list.toArray(new Integer[0]);
            writer.write("Массив из списка: " + Arrays.toString(newArray) + "\n");

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
