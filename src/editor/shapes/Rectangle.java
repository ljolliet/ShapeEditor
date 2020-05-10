package editor.shapes;

import editor.edition.RectangleEditionDialog;
import editor.core.EditorVisitor;
import editor.utils.Color;
import editor.utils.Point2D;
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

    @Override
    public boolean contains(Point2D position) {
        return  getPosition().x <= position.x && position.x <= getPosition().x + width
                && getPosition().y <= position.y && position.y <= getPosition().y + height;
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
        this.width = width;
        notifyObservers();
    }

    public void setHeight(double height) {
        this.height = height;
        notifyObservers();
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        notifyObservers();
    }


    public void setAllRectangleValues(double width, double height, int borderRadius, Point2D position, Color color, double rotation){
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
        this.setAllValues(position,color,rotation);
    }
}
