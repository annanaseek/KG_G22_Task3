package ru.vsu.cs.drawers;

import ru.vsu.cs.ScreenPoint;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;

    public DDALineDrawer(PixelDrawer pd){  // alt+insert
        this.pd = pd;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX(), y1 = p1.getY();
        int x2 = p2.getX(), y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) { // горизонтальная прямая
            if (x1 > x2) {
                int x = x1;
                x1 = x2;
                x2 = x;
                int y = y1;
                y1 = y2;
                y2 = y;
            }
            double k = dy / dx;
            for (int j = x1; j < x2; j++) {
                double i = k * (j - x1) + y1; // y1 = kx1 + b; y = kx + y1 - kx1 = k * (x - x1) + y1;
                pd.colorPixel(j, (int) i, Color.black);
            }
        } else {
            if (y1 > y2) {
                int x = x1;
                x1 = x2;
                x2 = x;
                int y = y1;
                y1 = y2;
                y2 = y;
            }
            double kObr = dx / dy;
            for (int i = y1; i < y2; i++) {
                double j = kObr * (i - y1) + x1; // y1 = kx1 + b; y = kx + y1 - kx1 = k * (x - x1) + y1;
                pd.colorPixel((int) j, i, Color.black);
            }
        }
    }
}
