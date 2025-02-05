package ru.innopolis.service;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ResearchSet {
    public static void researchSet() {
        try (BufferedReader reader = new BufferedReader(new FileReader("files/inputSet.txt"));
             FileWriter writer = new FileWriter("files/outputSet.txt")) {
            Set<String> set = new HashSet<>();
            String[] inputSplit = reader.readLine().split(", ");
            Collections.addAll(set, inputSplit);
            writer.write("Исходное множество: " + set + "\n");

            set.add("eleven");
            set.add("twelve");
            set.add("thirteen");
            set.add("fourteen");
            set.add("fifteen");
            writer.write("Множество после добавления пяти элементов: " + set + "\n");

            writer.write("Элементы множества: " + set +  "\n");
            for (String item : set) {
                System.out.print(item + ", ");
            }

            set.add("eleven");
            writer.write("Множество после попытки добавления существующего элемента: " + set + "\n");

            String searchItem = "one";
            boolean containsItem = set.contains(searchItem);
            writer.write("Содержит ли множество элемент '" + searchItem + "': " + containsItem + "\n");

            String removeItem = "ten";
            set.remove(removeItem);
            writer.write("Множество после удаления элемента '" + removeItem + "': " + set + "\n");

            writer.write("Количество элементов в множестве: " + set.size() + "\n");

            set.clear();
            writer.write("Множество после удаления всех элементов: " + set + "\n");

            writer.write("Является ли множество пустым: " + set.isEmpty() + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

