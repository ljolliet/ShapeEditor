package editor.edition;

import editor.shapes.Shape;
import editor.utils.Point2D;
import javafx.scene.control.ContextMenu;
import ui.Rendering;

public interface EditionDialogI {
    void draw(Rendering rendering);
    void setEditionDialog(ContextMenu contextMenu);
    void setPosition(Point2D position);
    Point2D getPosition();
    Shape getTarget();
    void applyEdition();
}
