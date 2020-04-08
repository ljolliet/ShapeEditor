package editor;

import editor.utils.Color;
import editor.utils.Vec2D;

public class Rectangle extends SimpleShape {
    private double width;
    private double height;
    private int borderRadius;

    public Rectangle(double width, double height, int borderRadius, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
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
    public void draw(Object context) {
        rendering.drawRectangle(context, this);
    }
}
