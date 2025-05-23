package ru.innopolis;

import ru.innopolis.api.Movable;
import ru.innopolis.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Figure> figures = readFiguresFromFile("files/figures.txt");

        for (Figure figure : figures) {
            System.out.println("Perimeter: " + figure.getPerimeter());

            if (figure instanceof Movable) {
                ((Movable) figure).move(10, 10);
                System.out.println("Figure moved");
            }
        }

        writeResultsToFile("files/output.txt", figures);
    }

    private static List<Figure> readFiguresFromFile(String filename) {
        List<Figure> figures = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                switch (parts[0]) {
                    case "Circle" -> figures.add(new Circle(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                    case "Square" -> figures.add(new Square(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2]),
                            Double.parseDouble(parts[3])
                    ));
                    case "Rectangle" -> figures.add(new Rectangle(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2])
                    ));
                    case "Ellipse" -> figures.add(new Ellipse(
                            Double.parseDouble(parts[1]),
                            Double.parseDouble(parts[2])
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return figures;
    }

    private static void writeResultsToFile(String filename, List<Figure> figures) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Figure figure : figures) {
                writer.println("Figure: " + figure.getClass().getSimpleName());
                writer.println("Perimeter: " + figure.getPerimeter());
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}