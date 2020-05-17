package editor.edition;

import editor.shapes.Rectangle;
import editor.utils.Point2D;
import editor.utils.Vec2D;
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
        rendering.setEditionDialog(this);
    }

    public Rectangle getTarget() {
        return (Rectangle)super.getTarget();
    }


    @Override
    public void applyEdition(){
        this.getTarget().setAllRectangleValues(this.width, this.height, this.borderRadius,
                new Point2D(this.posX, this.posY),
                this.color,
                this.rotation,
                new Vec2D(transWidth, transHeight),
                new Point2D(rotateCenterX, rotateCenterY));
    }

}
