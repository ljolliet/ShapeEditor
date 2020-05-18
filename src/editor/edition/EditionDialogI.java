package editor.edition;

import editor.shapes.Shape;
import ui.Rendering;

public interface EditionDialogI {
    void draw(Rendering rendering);
    Shape getTarget();
    void applyEdition();
}
