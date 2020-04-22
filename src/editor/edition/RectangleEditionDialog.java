package editor.edition;

import editor.shapes.Rectangle;
import editor.utils.Point2D;
import ui.Rendering;

public class RectangleEditionDialog extends ShapeEditionDialog {
    public double width;
    public double height;
    public int borderRadius;

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.borderRadius = rectangle.getBorderRadius();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionGridPane(this);
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

    @Override
    public void applyEdition(){
        this.setWidth(this.width);
        this.setHeight(this.height);
        this.setBorderRadius(this.borderRadius);
        this.setPosition(new Point2D(this.posX, this.posY));
        this.setColor(this.color);
        this.setRotation(this.rotation);
    }


}
