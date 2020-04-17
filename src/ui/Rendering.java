package ui;

import editor.Polygon;
import editor.Rectangle;
import editor.ShapeGroup;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;

public interface Rendering {

    void drawEditor();
    void drawSelectionFrame();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);

    void drawEditionDialog(ShapeEditionDialog polED);
    void drawEditionDialog(ShapeGroup polED);
    void drawEditionDialog(RectangleEditionDialog recED);
    void drawEditionDialog(PolygonEditionDialog polED);
    void hideEditionDialog();
}
