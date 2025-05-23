package ru.innopolis.model;

import ru.innopolis.api.Movable;

public class Square extends Rectangle implements Movable {
    private double x;
    private double y;

    public Square(double x, double y, double side) {
        super(side, side);
        this.x = x;
        this.y = y;
    }

    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
}
