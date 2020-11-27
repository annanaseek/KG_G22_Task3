package ru.vsu.cs;

public class Sigmoid implements ITimeDependentParam {

    double a, b, c;

    @Override
    public double getParamValue(double t) {
        return a / (1 + Math.exp(-b * t)) + c;
    }
}
