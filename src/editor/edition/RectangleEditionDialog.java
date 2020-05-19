package editor.edition;

import editor.shapes.Rectangle;
import ui.Rendering;

public class RectangleEditionDialog extends ShapeEditionDialog {

    private double width;
    private double height;
    private int borderRadius;

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.borderRadius = rectangle.getBorderRadius();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
    }

    public Rectangle getTarget() {
        return (Rectangle) super.getTarget();
    }

    @Override
    public void applyEdition() {
        this.getTarget().setAllRectangleValues(
                this.getWidth(),
                this.getHeight(),
                this.getBorderRadius(),
                this.getPosition(),
                this.getColor(),
                this.getRotation(),
                this.getTranslation(),
                this.getRotationCenter());
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public int getBorderRadius() {
        return this.borderRadius;
    }
}
