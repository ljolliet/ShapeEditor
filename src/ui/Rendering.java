package ui;

import editor.Polygon;
import editor.Rectangle;
import editor.edition.RectangleEditionDialog;

public interface Rendering {

    void drawEditor();
    void drawSelectionFrame();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);

    void drawRectangleEditionDialog(RectangleEditionDialog recED);
}
