package ru.vsu.cs;

import ru.vsu.cs.drawers.LineDrawer;

import java.util.LinkedList;
import java.util.List;

public class Parabola implements IFigure {
    double a, b, c;
    ScreenConverter sc;
    private double step = 0.05;

    public Parabola(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void draw(ScreenConverter screenConverter, LineDrawer ld) {
        sc = screenConverter;
        for (Line line: getParabolasLine()) {
            drawLine(ld, line);
        }
    }

    private double calculateY(double x){
        return a * Math.pow(x, 2) + b * x + c;
    }

    private List<Line> getParabolasLine(){
        List<Line> lines = new LinkedList<>();
        double startX = - sc.getRealW() / 2;
        RealPoint leftPoint = new RealPoint(startX, calculateY(startX));

        for (double x = startX + step; x <= sc.getRealW() / 2; x+=step) {
            RealPoint rightPoint = new RealPoint(x, calculateY(x));
            lines.add(new Line(leftPoint, rightPoint));
            leftPoint = rightPoint;
        }

        return lines;
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }
}
