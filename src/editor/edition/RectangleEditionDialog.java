package editor.edition;

import editor.Rectangle;
import ui.Rendering;

public class RectangleEditionDialog extends ShapeEditionDialog {

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
        rendering.showEditionDialog(this.getPosition());
    }

    public Rectangle getTarget() {
        return (Rectangle)super.getTarget();
    }

    public void setWidth(double value) {
        this.getTarget().setWidth(value);
    }

    public void setHeight(double value){
        this.getTarget().setHeight(value);
    }

    public void setBorderRadius(int value) {
        this.getTarget().setBorderRadius(value);
    }
}
