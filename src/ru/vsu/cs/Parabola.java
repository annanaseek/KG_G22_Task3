package ru.vsu.cs;

public class Parabola implements IFunction {
    double a, b, c;

    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateY(double x) {
        return a * x * x + b * x + c;
    }
}
