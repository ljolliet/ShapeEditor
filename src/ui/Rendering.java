package ui;

import editor.Polygon;
import editor.Rectangle;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;

public interface Rendering {

    void drawEditor();
    void drawSelectionFrame();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);

    void hideEditionDialog();

    void drawPolygonEditionDialog(RectangleEditionDialog recED);

    void drawPolygonEditionDialog(PolygonEditionDialog polED);
}
