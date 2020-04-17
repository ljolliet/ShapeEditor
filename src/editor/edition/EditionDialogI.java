package editor.edition;

import editor.Shape;
import editor.utils.Point2D;
import ui.Rendering;

public interface EditionDialogI {
    void draw(Rendering rendering);
    void setPosition(Point2D position);
    Point2D getPosition();
    Shape getTarget();
}
