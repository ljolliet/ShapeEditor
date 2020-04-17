package editor.edition;

import editor.Rectangle;
import ui.Rendering;

public class RectangleEditionDialog extends EditionDialog {

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.drawRectangleEditionDialog(this);
    }

    public Rectangle getTarget() {
        return (Rectangle)super.getTarget();
    }
}
