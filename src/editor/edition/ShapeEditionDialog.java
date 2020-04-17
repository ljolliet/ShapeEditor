package editor.edition;

import editor.Shape;
import editor.utils.Color;
import editor.utils.Point2D;

public abstract class ShapeEditionDialog implements  EditionDialogI{
    private Point2D position;
    private Shape target;

    public ShapeEditionDialog(Shape shape) {
        this.target = shape;
    }
    public void setColor(Color c){
        this.target.setColor(c);
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


}
