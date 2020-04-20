package ui;

import editor.Polygon;
import editor.Rectangle;
import editor.ShapeI;
import editor.ShapeGroup;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.utils.Point2D;

public interface Rendering {

    void drawEditor();
    void drawSelectionFrame();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);

    Object getShadowShape(ShapeI shape);

    void setEditionDialog(ShapeEditionDialog polED);
    void setEditionDialog(ShapeGroup polED);
    void setEditionDialog(RectangleEditionDialog recED);
    void setEditionDialog(PolygonEditionDialog polED);
    void showEditionDialog(Point2D position);
    void hideEditionDialog();
}
