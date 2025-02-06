package ru.innopolis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader("files/inputHashMap.txt"));
             FileWriter writer = new FileWriter("files/outputHashMap.txt")) {
            Map<String, String> map = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] pairs = line.split(", ");

                for (String pair : pairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        map.put(keyValue[0], keyValue[1]);
                    }
                }
            }
            writer.write("Исходная коллекция: " + map + "\n");
            System.out.println(map);

            map.put("mouse", "small");
            map.put("dog", "big");
            map.put("whale", "big");
            map.put("horse", "racing");
            map.put("toad", "small");

            writer.write("Коллекция после добавления 5 элементов: " + map + "\n");

            writer.write("Прямой перебор коллекции:\n");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + " => " + entry.getValue() + "\n");
            }

            map.put("mouse", "new_value");
            writer.write("После добавления нового элемента с существующим ключом:\n");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + " => " + entry.getValue() + "\n");
            }

            Set<String> keys = map.keySet();
            writer.write("Список всех ключей:\n");
            for (String key : keys) {
                writer.write(key + "\n");
            }

            Set<String> uniqueValues = new HashSet<>(map.values());
            writer.write("Количество уникальных значений: " + uniqueValues.size() + "\n");

            String searchKey = "toad";
            writer.write("Содержит ли коллекция ключ " + searchKey + "? " + map.containsKey(searchKey) + "\n");

            String searchValue = "black";
            writer.write("Содержит ли коллекция значение " + searchValue + "? " + map.containsValue(searchValue) + "\n");

            writer.write("Количество элементов в коллекции: " + map.size() + "\n");

            map.remove("mouse");
            map.values().remove("blue");
            writer.write("После удаления элемента по ключу и значению:\n");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.write(entry.getKey() + " => " + entry.getValue() + "\n");
            }


        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }
}
