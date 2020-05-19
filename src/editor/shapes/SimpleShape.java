package editor.shapes;

import editor.observer.Observable;
import editor.utils.*;

import java.util.Set;

public abstract class SimpleShape extends Observable implements Shape {
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
    public Set<Shape> getChildren() {
        return null;
    }

    @Override
    public void setChildren(Set<Shape> shapes) {
        throw new ShapeException("A simple shape has no children");
    }

    @Override
    public boolean containsChild(Shape s) {
        return false;
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
    public Shape clone() {
        try {
            Shape clone =  (Shape) super.clone();
            clone.removeObservers();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
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
        if(this.position != pos) {
            this.position = pos;
            notifyObservers();
        }
    }

    @Override
    public void setColor(Color color) {
        if(this.color != color) {
            this.color = color;
            notifyObservers();
        }
    }

    @Override
    public void setRotation(double angle) {
        if(this.rotation != angle) {
            this.rotation = angle;
            notifyObservers();
        }
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        if(this.rotationCenter != pos) {
            this.rotationCenter = pos;
            notifyObservers();
        }

    }

    @Override
    public void setTranslation(Vec2D translation) {
        if(this.translation != translation) {
            this.translation = translation;
            notifyObservers();
        }
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

    @Override
    public void setAllValues(Point2D position, Color color, double rotation, Vec2D translation, Point2D rotationCenter) {
        if (this.position != position || this.color != color || this.rotation != rotation || this.translation != translation || this.rotationCenter != rotationCenter) {
            this.position = position;
            this.color = color;
            this.rotation = rotation;
            this.translation = translation;
            this.rotationCenter = rotationCenter;
            notifyObservers();
        }
    }

    public boolean setAllValuesWithoutNotify(Point2D position, Color color, double rotation, Vec2D translation, Point2D rotationCenter) {
        boolean change = false;
        if (this.position != position || this.color != color ||
                this.rotation != rotation || this.translation != translation ||
                this.rotationCenter != rotationCenter) {
            change = true;
        }

        this.position = position;
        this.color = color;
        this.rotation = rotation;
        this.translation = translation;
        this.rotationCenter = rotationCenter;
        return change;
    }

}
