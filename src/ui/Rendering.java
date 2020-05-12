package ui;

import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.shapes.ShapeGroup;
import editor.shapes.ShapeI;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.utils.Point2D;

public interface Rendering extends Mediator {

    void drawEditor();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);
    void drawInToolbar(ShapeGroup group);

    Object getShadowShape(ShapeI shape);

    void setEditionDialog(ShapeEditionDialog shapeED);
    void setEditionDialog(ShapeGroup polED);
    void setEditionDialog(PolygonEditionDialog polED);
    void setEditionDialog(RectangleEditionDialog recED);

    void showEditionDialog(Point2D position);
    void showGroupDialog(Point2D position);
    void hideEditionDialog();

    void setRectangleDialog(RectangleEditionDialog recED);
    void setPolygonDialog(PolygonEditionDialog polED);

}
