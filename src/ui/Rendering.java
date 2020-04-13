package ui;

import editor.Polygon;
import editor.Rectangle;
import editor.utils.Point2D;

public interface Rendering {

    void drawEditor();
    void drawSelectionFrame(Point2D position, double width, double height);

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);
}
