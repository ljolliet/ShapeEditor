package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import ui.Rendering;

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

    @Override
    public void drawInScene(Rendering rendering) {
        rendering.drawInScene(this);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        rendering.drawInToolbar(this);
    }

    @Override
    public boolean contains(Vec2D pos) {
        return  getPosition().x <= pos.x && pos.x <= getPosition().x + width
                && getPosition().y <= pos.y && pos.y <= getPosition().y + height;
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
    public void setPosition(Vec2D position) {
        super.setPosition(new Vec2D(position.x - width/2, position.y - height/2));
    }
}
