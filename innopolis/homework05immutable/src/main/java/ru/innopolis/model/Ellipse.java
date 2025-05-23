package ru.innopolis.model;

public class Ellipse extends Figure{
    private final double axisA;
    private final double axisB;

    public Ellipse(double semiMajorAxis, double semiMinorAxis) {
        this.axisA = semiMajorAxis;
        this.axisB = semiMinorAxis;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * Math.sqrt((axisA * axisA + axisB * axisB) / 2);
    }
}

