package ru.vsu.cs;

import ru.vsu.cs.drawers.BufferedImagePixelDrawer;
import ru.vsu.cs.drawers.DDALineDrawer;
import ru.vsu.cs.drawers.LineDrawer;
import ru.vsu.cs.drawers.PixelDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {

    private ScreenConverter sc = new ScreenConverter(-2, 1.5, 4, 3, 800, 600);
    private ArrayList<Line> allLines = new ArrayList<>();
    private Graphics biGraphics;

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_RGB);
        biGraphics = bi.createGraphics();
        biGraphics.setColor(Color.WHITE);
        biGraphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        /**/
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);
        drawAll(ld);
        /**/
        g.drawImage(bi, 0, 0, null);
    }

    private void drawAll(LineDrawer ld) {
        drawAxes(ld);
        /*for (Line q: allLines) {
            drawLine(ld, q);
        }
        if (newLine != null) {
            drawLine(ld, newLine);
        }*/

        Parabola parabola = new Parabola(1, 0, 0);
        parabola.draw(sc, ld);

//        Sinusoid sinusoid = new Sinusoid(1, 1, 0, 0);
//        sinusoid.draw(sc, ld);

    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawAxes(LineDrawer ld) {
        double step = 0.5;
        double hatchSize = 0.2;
        Line xAxis = new Line(- sc.getRealW() / 2, 0, sc.getRealW(), 0);
        Line yAxis = new Line(0, - sc.getRealH() / 2, 0, sc.getRealH());
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        for (double i = step; i < sc.getRealW() / 2; i+= step) {
            RealPoint realPoint1 = new RealPoint(i, - hatchSize / 2);
            RealPoint realPoint2 = new RealPoint(i,  hatchSize / 2);
            Line rightHatch = new Line(realPoint1, realPoint2);

            drawLine(ld, rightHatch);
            drawString(new RealPoint(realPoint1.getX(), realPoint1.getY() - hatchSize / 2), String.valueOf(i));

            realPoint1 = new RealPoint(-i, - hatchSize / 2);
            realPoint2 = new RealPoint(-i,  hatchSize / 2);
            Line leftHatch = new Line(realPoint1, realPoint2);

            drawLine(ld, leftHatch);
            drawString(new RealPoint(realPoint1.getX(), realPoint1.getY() - hatchSize / 2), String.valueOf(-i));
        }

        for (double i = step; i < sc.getRealH() / 2; i+= step) {
            RealPoint realPoint1 = new RealPoint(- hatchSize / 2, i);
            RealPoint realPoint2 = new RealPoint( hatchSize / 2, i);
            Line upHatch = new Line(realPoint1, realPoint2);

            drawLine(ld, upHatch);
            drawString(new RealPoint(realPoint1.getX() - hatchSize, realPoint1.getY()), String.valueOf(i));

            realPoint1 = new RealPoint(- hatchSize / 2, -i);
            realPoint2 = new RealPoint( hatchSize / 2, -i);
            Line downHatch = new Line(realPoint1, realPoint2);

            drawLine(ld, downHatch);
            drawString(new RealPoint(realPoint1.getX() - hatchSize, realPoint1.getY()), String.valueOf(-i));
        }
    }

    private void drawString(RealPoint realPoint, String text) {
        ScreenPoint screenPoint = sc.r2s(realPoint);
        biGraphics.setColor(Color.BLACK);
        biGraphics.drawString(text, screenPoint.getX(), screenPoint.getY());
    }

    private ScreenPoint prevPoint = null;

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        ScreenPoint currentPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        if (prevPoint != null) {
            ScreenPoint deltaScreen = new ScreenPoint(currentPoint.getX() - prevPoint.getX(),
                    currentPoint.getY() - prevPoint.getY());
            RealPoint deltaReal = sc.s2r(deltaScreen);
            double vectorX = deltaReal.getX() - sc.getCornerX();
            double vectorY = deltaReal.getY() - sc.getCornerY();

            sc.setCornerX(sc.getCornerX() - vectorX);
            sc.setCornerY(sc.getCornerY() - vectorY);
            prevPoint = currentPoint;
        }

        if (newLine != null) {
            newLine.setP2(sc.s2r(currentPoint));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) { // на нажатие

    }

    private Line newLine = null;

    @Override
    public void mousePressed(MouseEvent e) { // на момент нажатия
        if (e.getButton() == MouseEvent.BUTTON3) {
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            newLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { // отжатия клавиши
        if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
            prevPoint = null;
        } else if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
            allLines.add(newLine);
            newLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double step = (clicks > 0) ? 1.05 : 0.95;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= step;
        }
        sc.setRealW(scale * sc.getRealW());
        sc.setRealH(scale * sc.getRealH());
        repaint();
    }
}
