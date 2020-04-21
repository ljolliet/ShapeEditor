package editor.edition;

import editor.ShapeI;
import editor.utils.Color;
import editor.utils.Point2D;

public abstract class ShapeEditionDialog implements  EditionDialogI{
    private Point2D position;
    public double posX;
    public double posY;
    public Color color;
    public double rotation;
    private final ShapeI target;

    public ShapeEditionDialog(ShapeI shape) {
        this.target = shape;
        this.posX = shape.getPosition().x;
        this.posY = shape.getPosition().y;
        this.color = shape.getColor();
        this.rotation = shape.getRotation();
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
    public ShapeI getTarget(){
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
