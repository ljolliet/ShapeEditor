package ui;

import editor.Polygon;
import editor.Rectangle;

public interface Rendering {
    void drawRectangle(Object context, Rectangle rectangle);
    void drawPolygon(Object context, Polygon polygon);
}
