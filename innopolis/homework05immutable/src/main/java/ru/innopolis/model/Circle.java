package ru.innopolis.model;

import ru.innopolis.api.Movable;

public class Circle extends Ellipse implements Movable {
    private double x;
    private double y;
    private final double radius;

    public Circle(double x, double y, double radius) {
        super(radius, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
