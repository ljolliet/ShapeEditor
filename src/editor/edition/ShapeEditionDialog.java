package editor.edition;

import editor.shapes.Shape;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.Vec2D;
import javafx.scene.control.ContextMenu;
import ui.Rendering;

public class ShapeEditionDialog implements EditionDialogI {

    private final Shape target;

    private Point2D position;
    private Color color;
    private double rotation;
    private Vec2D translation;
    private Point2D rotationCenter;

    public ShapeEditionDialog(Shape shape) {
        this.target = shape;
        this.position = shape.getPosition();
        this.color = shape.getColor();
        this.rotation = shape.getRotation();
        this.translation = shape.getTranslation();
        this.rotationCenter = shape.getRotationCenter();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
    }

    @Override
    public Shape getTarget() {
        return this.target;
    }

    @Override
    public void applyEdition() {
        this.getTarget().setAllValues(
                this.getPosition(),
                this.getColor(),
                this.getRotation(),
                this.getTranslation(),
                this.getRotationCenter());
    }

    @Override
    public void setEditionDialog(ContextMenu contextMenu) {

    }

    public void setPositionX(double x) {
        double y = this.position == null ? 0 : this.position.y;
        this.position = new Point2D(x, y);
    }

    public void setPositionY(double y) {
        double x = this.position == null ? 0 : this.position.x;
        this.position = new Point2D(x, y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setTranslationWidth(double width) {
        double height = this.translation == null ? 0 : this.translation.height;
        this.translation = new Vec2D(width, height);

    }
    public void setTranslationHeight(double height) {
        double width = this.translation == null ? 0 : this.translation.width;
        this.translation = new Vec2D(width, height);
    }

    public void setRotationCenterX(double x) {
        double y = this.rotationCenter == null ? 0 : this.rotationCenter.y;
        this.rotationCenter = new Point2D(x, y);
    }

    public void setRotationCenterY(double y) {
        double x = this.rotationCenter == null ? 0 : this.rotationCenter.x;
        this.rotationCenter = new Point2D(x, y);
    }

    public Point2D getPosition() {
        return this.position;
    }

    public Color getColor() {
        return color;
    }

    public double getRotation() {
        return rotation;
    }

    public Vec2D getTranslation() {
        return translation;
    }

    public Point2D getRotationCenter() {
        return rotationCenter;
    }
}
