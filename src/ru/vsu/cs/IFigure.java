package ru.vsu.cs;

import ru.vsu.cs.drawers.LineDrawer;
import ru.vsu.cs.drawers.PixelDrawer;

public interface IFigure {
    void draw(ScreenConverter screenConverter, LineDrawer lineDrawer);
}
