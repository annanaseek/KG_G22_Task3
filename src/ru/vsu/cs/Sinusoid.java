package ru.vsu.cs;

import ru.vsu.cs.drawers.LineDrawer;

import java.util.LinkedList;
import java.util.List;

public class Sinusoid implements IFigure {
    double a, w, f, c;
    ScreenConverter sc;
    private double step = 0.05;

    public Sinusoid(double a, double w, double f, double c) {
        this.a = a;
        this.w = w;
        this.f = f;
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
        return a * Math.sin(w * x + f) + c;
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
