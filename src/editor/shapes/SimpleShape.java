package editor.shapes;

import editor.utils.*;

import java.util.Set;

public abstract class SimpleShape extends Shape {
    private Point2D position;

    private Color color;
    private Point2D rotationCenter;
    private double rotation;
    private Vec2D translation;

    public SimpleShape(Point2D position, Color color, Point2D rotationCenter, double rotation) {
        this.position = position;
        this.color = color;
        this.rotationCenter = rotationCenter;
        this.rotation = rotation;
        this.translation = new Vec2D(0,0);
    }

    public double getX() {
        return position.x;
    }

    public double getY() {
        return position.y;
    }

    @Override
    public Set<ShapeI> getChildren() {
        return null;
    }

    @Override
    public void setChildren(Set<ShapeI> shapes) {
        throw new ShapeException("A simple shape has no children");
    }

    @Override
    public void addShape(ShapeI s) {
        throw new ShapeException("Cannot add a shape to a SimpleShape");
    }

    @Override
    public void removeShape(ShapeI s) {
        throw new ShapeException("Cannot remove a shape to a SimpleShape");
    }

    @Override
    public ShapeI clone() {
        return super.clone();
    }

    @Override
    public boolean contained(SelectionShape s) {
        for(Point2D p : getPoints())
            if(!s.contains(p))
                return false;
        return true;
    }

    @Override
    public void setPosition(Point2D pos) {
        this.position = pos;
        notifyObservers();
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
        notifyObservers();
    }

    @Override
    public void setRotation(double angle) {
        this.rotation = angle;
        notifyObservers();
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        this.rotationCenter = pos;
        notifyObservers();
    }

    @Override
    public void setTranslation(Vec2D translation) {
        this.translation = translation;
        notifyObservers();
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    @Override
    public Point2D getRotationCenter() {
        return rotationCenter;
    }

    @Override
    public Vec2D getTranslation() {
        return translation;
    }

    public void setAllValues(Point2D position, Color color, double rotation){
        this.position = position;
        this.color = color;
        this.rotation = rotation;
        notifyObservers();
    }
}
