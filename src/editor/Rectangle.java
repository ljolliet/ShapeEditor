package editor;

import editor.utils.Color;
import editor.utils.Point2D;
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    @Override
    public void setPosition(Point2D position) {
        super.setPosition(new Point2D(position.x - width/2, position.y - height/2));
    }
}
