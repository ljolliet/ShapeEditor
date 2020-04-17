package editor.edition;

import editor.Shape;
import editor.utils.Point2D;

public abstract class EditionDialog implements  EditionDialogI{
    private Point2D position;
    private Shape target;

    public EditionDialog(Shape shape) {
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
}
