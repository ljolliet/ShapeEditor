package editor.shapes;

import editor.edition.RectangleEditionDialog;
import editor.core.EditorVisitor;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.Vec2D;
import ui.ApplicationI;
import editor.edition.EditionDialogI;
import ui.Rendering;

public class Rectangle extends SimpleShape {
    private double width;
    private double height;
    private int borderRadius;

    public Rectangle(double width, double height, int borderRadius, Point2D position, Color color, Point2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
    }

    @Override
    public void drawInScene(Rendering rendering) {
        rendering.drawInScene(this);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        rendering.drawInToolbar(this);
    }

    // Thanks to https://stackoverflow.com/a/53907763/11256750 for computing rotation
    @Override
    public boolean contains(Point2D position) {
        Point2D rotationCenter = new Point2D(
                this.getRotationCenter().x + this.getPosition().x + this.getTranslation().width,
                this.getRotationCenter().y + this.getPosition().y + this.getTranslation().height);
        // Rotate point
        Point2D newPoint = position.rotateAround(-this.getRotation(), rotationCenter);

        return  getPosition().x + getTranslation().width <= newPoint.x &&
                newPoint.x <= getPosition().x + getTranslation().width + width &&
                getPosition().y + getTranslation().height <= newPoint.y &&
                newPoint.y <= getPosition().y + getTranslation().height + height;
    }
    

    @Override
    public Point2D[] getPoints() {
        Point2D rotationCenter = new Point2D(
                this.getRotationCenter().x + this.getPosition().x,
                this.getRotationCenter().y + this.getPosition().y);

        // Compute points
        Point2D topLeft     = new Point2D(getX(), getY());
        Point2D topRight    = new Point2D(getX() + width, getY());
        Point2D bottomLeft  = new Point2D(getX(), getY() + height);
        Point2D bottomRight = new Point2D(getX() + width, getY() + height);

        // Rotate points
        topLeft = topLeft.rotateAround(this.getRotation(), rotationCenter);
        topRight = topRight.rotateAround(this.getRotation(), rotationCenter);
        bottomLeft = bottomLeft.rotateAround(this.getRotation(), rotationCenter);
        bottomRight = bottomRight.rotateAround(this.getRotation(), rotationCenter);

        // Translate points
        topLeft = topLeft.translate(this.getTranslation());
        topRight = topRight.translate(this.getTranslation());
        bottomLeft = bottomLeft.translate(this.getTranslation());
        bottomRight = bottomRight.translate(this.getTranslation());

        return new Point2D[] {topLeft, topRight, bottomLeft, bottomRight};
    }

    @Override
    public void setPosition(Point2D pos) {
        pos = new Point2D(pos.x + getTranslation().width, pos.y + getTranslation().height);

        double x = Math.max(0, Math.min(ApplicationI.SCENE_WIDTH - width, pos.x));
        double y = Math.max(0, Math.min(ApplicationI.SCENE_HEIGHT - height, pos.y));

        super.setPosition(new Point2D(x - getTranslation().width, y - getTranslation().height));
    }

    @Override
    public EditionDialogI createEditionDialog() {
        return new RectangleEditionDialog(this);
    }

    @Override
    public void accept(EditorVisitor visitor) {
        visitor.visit(this);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setWidth(double width) {
        if(this.width != width) {
            this.width = width;
            notifyObservers();
        }
    }

    public void setHeight(double height) {
        if(this.height != height) {
            this.height = height;
            notifyObservers();
        }
    }

    public void setBorderRadius(int borderRadius) {
        if(this.borderRadius != borderRadius) {
            this.borderRadius = borderRadius;
            notifyObservers();
        }
    }


    public void setAllRectangleValues(double width, double height, int borderRadius, Point2D position, Color color, double rotation, Vec2D translation, Point2D rotationCenter){
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
        this.setAllValues(position,color,rotation, translation, rotationCenter);
    }
}
