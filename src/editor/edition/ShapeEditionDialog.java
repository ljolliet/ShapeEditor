package editor.edition;

import editor.Shape;
import editor.utils.Color;
import editor.utils.Point2D;

public abstract class ShapeEditionDialog implements  EditionDialogI{
    private Point2D position;
    private final Shape target;

    public ShapeEditionDialog(Shape shape) {
        this.target = shape;
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
}
