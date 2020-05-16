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
        // Rotate around rotation center by -angle
        double sin = Math.sin(Math.toRadians(-getRotation()));
        double cos = Math.cos(Math.toRadians(-getRotation()));

        // Set origin to rotation center
        Point2D newPoint = new Point2D(position.x - (getRotationCenter().x + getPosition().x),
                position.y - (getRotationCenter().y + getPosition().y));

        // Rotate
        newPoint = new Point2D(newPoint.x * cos - newPoint.y * sin,
                newPoint.x * sin + newPoint.y * cos);

        // Put origin back
        newPoint = new Point2D(newPoint.x + (getRotationCenter().x + getPosition().x),
                newPoint.y + (getRotationCenter().y + getPosition().y));

        return  getPosition().x + getTranslation().width <= newPoint.x &&
                newPoint.x <= getPosition().x + getTranslation().width + width &&
                getPosition().y + getTranslation().height <= newPoint.y &&
                newPoint.y <= getPosition().y + getTranslation().height + height;
    }
    

    @Override
    public Point2D[] getPoints() {
        return new Point2D[] {
                new Point2D(getX(), getY()),                           // Top left
                new Point2D(getX() + width, getY()),                // Top right
                new Point2D(getX(), getY() + height),               // Bottom left
                new Point2D(getX() + width, getY() + height)     // Bottom right
        };
    }

    @Override
    public void setPosition(Point2D pos) {
        double x = Math.max(0, Math.min(ApplicationI.SCENE_WIDTH - width, pos.x));
        double y = Math.max(0, Math.min(ApplicationI.SCENE_HEIGHT - height, pos.y));

        super.setPosition(new Point2D(x, y));
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
