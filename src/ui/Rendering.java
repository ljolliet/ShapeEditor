package ui;

import editor.Polygon;
import editor.Rectangle;
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

    void setEditionGridPane(ShapeEditionDialog shapeED);
    void setEditionGridPane(ShapeGroup polED);
    void setEditionGridPane(PolygonEditionDialog polED);
    void setEditionGridPane(RectangleEditionDialog recED);
    void showEditionDialog(Point2D position);
    void hideEditionDialog();
    void setRectangleGridPane(RectangleEditionDialog recED);
    void setPolygonGridPane(PolygonEditionDialog polED);
}
