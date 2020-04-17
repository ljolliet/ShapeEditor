package editor.edition;

import editor.Rectangle;
import ui.Rendering;

public class RectangleEditionDialog extends ShapeEditionDialog {

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.drawPolygonEditionDialog(this);
    }

    public Rectangle getTarget() {
        return (Rectangle)super.getTarget();
    }

    public void setWidth(Double value) {
        this.getTarget().setWidth(value);
    }

    public void setHeight(Double value){
        this.getTarget().setHeight(value);
    }
}
