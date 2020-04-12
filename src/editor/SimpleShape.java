package editor;

import editor.utils.Color;
import editor.utils.ShapeException;
import editor.utils.Vec2D;

import java.util.Set;

public abstract class SimpleShape extends ShapeObservable {
    private Vec2D position;

    private Color color;
    private Vec2D rotationCenter;
    private double rotation;
    // translation TODO

    public SimpleShape(Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        this.position = position;
        this.color = color;
        this.rotationCenter = rotationCenter;
        this.rotation = rotation;
    }

    @Override
    public Set<Shape> getChild() {
        return null;
    }

    @Override
    public void addShape(Shape s) {
        throw new ShapeException("Cannot add a shape to a SimpleShape");
    }

    @Override
    public void removeShape(Shape s) {
        throw new ShapeException("Cannot remove a shape to a SimpleShape");
    }

    @Override
    public void collapse() {
        throw new ShapeException("Cannot collapse a SimpleShape");
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    @Override
    public void setPosition(Vec2D pos) {
        this.position = pos;
        notifyObservers();
    }

    public Vec2D getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Vec2D getRotationCenter() {
        return rotationCenter;
    }

    public double getRotation() {
        return rotation;
    }
}
