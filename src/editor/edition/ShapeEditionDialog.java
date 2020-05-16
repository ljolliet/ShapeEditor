package editor.edition;

import editor.shapes.Shape;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.Vec2D;
import javafx.scene.control.ContextMenu;
import ui.Rendering;

public class ShapeEditionDialog implements EditionDialogI{
    private Point2D position;
    public double posX;
    public double posY;
    public Color color;
    public double rotation;
    public Vec2D translation;
    public double transHeight;
    public double transWidth;
    public Point2D rotationCenter;
    public double rotateCenterX;
    public double rotateCenterY;
    private final Shape target;

    public ShapeEditionDialog(Shape shape) {
        this.target = shape;
        this.posX = shape.getPosition().x;
        this.posY = shape.getPosition().y;
        this.color = shape.getColor();
        this.rotation = shape.getRotation();
        this.translation = shape.getTranslation();
        this.transHeight = 0;
        this.transWidth = 0;
        if(shape.getRotationCenter() != null) {
            this.rotateCenterX = shape.getRotationCenter().x;
            this.rotateCenterY = shape.getRotationCenter().y;
        }
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this, this.getPosition());
    }

    @Override
    public void setPosition(Point2D position) {
        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return this.position;
    }

    @Override
    public Shape getTarget(){
        return this.target;
    }

    @Override
    public void applyEdition() {
        this.getTarget().setAllValues(new Point2D(posX, posY), color, rotation,
                new Vec2D(transWidth, transHeight), new Point2D(rotateCenterX, rotateCenterY));
    }

    public void setColor(Color c){
        this.target.setColor(c);
    }

    public void setRotation(double value){
        this.target.setRotation(value);
    }

    public void setPositionX(double x){
        double y = this.target.getPosition().y;
        this.target.setPosition(new Point2D(x, y));
    }

    public void setPositionY(double y){
        double x = this.target.getPosition().x;
        this.target.setPosition(new Point2D(x, y));
    }

    public void setTranslationHeight(double height){
        double width = this.target.getTranslation().width;
        this.target.setTranslation(new Vec2D(height, width));
    }

    public void setTranslationWidth(double width){
        double height = this.target.getTranslation().height;
        this.target.setTranslation(new Vec2D(height, width));
    }


    @Override
    public void setEditionDialog(ContextMenu contextMenu) {


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

    public void setTranslation(Vec2D translation) {
        this.translation = translation;
    }

    public Point2D getRotationCenter() {
        return rotationCenter;
    }

    public void setRotationCenter(Point2D rotationCenter) {
        this.rotationCenter = rotationCenter;
    }
}
