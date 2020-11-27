package ru.vsu.cs;

public class Sinusoid implements IFunction, ITimeDependentParam {
    double a, w, f, c;

    public Sinusoid(double a, double w, double f, double c) {
        this.a = a;
        this.w = w;
        this.f = f;
        this.c = c;
    }

    @Override
    public double calculateY(double x) {
        return a * Math.sin(w * x + f) + c;
    }

    @Override
    public double getParamValue(double t) {
        return a * Math.sin(w * t + f) + c;
    }
}
