package editor;

import editor.utils.Color;
import editor.utils.Vec2D;

public class Rectangle extends SimpleShape {
    private double width;
    private double height;


    public Rectangle(double width, double height, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.width = width;
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
