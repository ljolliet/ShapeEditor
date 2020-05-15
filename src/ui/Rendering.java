package ui;

import editor.edition.EditionDialogI;
import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.shapes.ShapeGroup;
import editor.utils.Point2D;

public interface Rendering extends Mediator {

    void drawEditor();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);
    void drawInToolbar(ShapeGroup group);

    void setEditionDialog(EditionDialogI groupED, Point2D position);
}
