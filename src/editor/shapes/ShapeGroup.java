package editor.shapes;

import editor.core.EditorVisitor;
import editor.edition.EditionDialogI;
import editor.edition.GroupEditionDialog;
import editor.observer.Observable;
import editor.observer.Observer;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.ApplicationI;
import ui.Rendering;

import java.util.*;

public class ShapeGroup extends Observable implements Shape {

    private Set<Shape> shapes;

    private Color color = null;
    private double rotation = -1;
    private Point2D rotationCenter = null;
    private Vec2D translation = null;
    private Set<Observer> observers = new HashSet<>();

    public ShapeGroup(){
        shapes = new HashSet<>();
    }

    @Override
    public void addShape(Shape s) {
        shapes.add(s);
    }

    @Override
    public void removeShape(Shape s) {
        shapes.remove(s);
    }

    @Override
    public Set<Shape> getChildren() {
        return new HashSet<>(shapes);
    }

    @Override
    public void setChildren(Set<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public boolean containsChild(Shape s) {
        for(Shape c : this.shapes)
            if(c.equals(s) || c.containsChild(s))
                return true;
        return false;
    }

    @Override
    public Shape clone() {
        try {
            Shape clone = (Shape) super.clone();
            clone.removeObservers();
            clone.setChildren(new HashSet<>());
            for (Shape shape: this.getChildren()) {
                clone.addShape(shape.clone());
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void drawInScene(Rendering rendering) {
        for(Shape s : shapes)
            s.drawInScene(rendering);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        rendering.drawInToolbar(this);
    }

    /**
     * @return true if the position is contained by a child shape
     */
    @Override
    public boolean contains(Point2D position) {
        for(Shape child : shapes)
            if(child.contains(position))
                return true;
        return false;
    }

    /**
     * @return true if all children is contained
     */
    @Override
    public boolean contained(SelectionShape s) {
        for(Shape child : shapes)
            if(!child.contained(s))
                return false;
        return true;
    }

    @Override
    public Point2D[] getPoints() {
        List<Point2D> points = new ArrayList<>();

        for (Shape s: shapes)
            points.addAll(Arrays.asList(s.getPoints()));

        Point2D[] pointsArray = new Point2D[points.size()];
        for (int i = 0; i < points.size(); i++)
            pointsArray[i] = points.get(i);

        return pointsArray;
    }

    @Override
    public void setPosition(Point2D newPos) {
        Point2D position = getPosition();

        // The group cannot be out of bounds
        double x = Math.max(0, Math.min(ApplicationI.SCENE_WIDTH - getWidth(), newPos.x));
        double y = Math.max(0, Math.min(ApplicationI.SCENE_HEIGHT - getHeight(), newPos.y));

        // Compute delta
        double deltaX = x - position.x;
        double deltaY = y - position.y;

        // Set new position for all children
        for (Shape s: shapes) {
            s.setPosition(new Point2D(
                    s.getPosition().x + deltaX,
                    s.getPosition().y + deltaY
            ));
        }
        notifyObservers();
    }

    @Override
    public void setColor(Color color) {
        if (color != null) {
            this.color = color;
            for (Shape s : shapes)
                s.setColor(color);
            notifyObservers();
        }
    }

    @Override
    public void setRotation(double angle) {
        if (angle > -1) {
            this.rotation = angle;
            for (Shape s: shapes)
                s.setRotation(angle);
            notifyObservers();
        }
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        if (pos != null) {
            this.rotationCenter = pos;
            for(Shape s : shapes)
                s.setRotationCenter(this.rotationCenter);
            notifyObservers();
        }
    }

    @Override
    public void setTranslation(Vec2D translation) {
        if (translation != null) {
            this.translation = translation;
            for(Shape s : shapes)
                s.setTranslation(this.translation);
            notifyObservers();
        }
    }

    @Override
    public void setAllValues(Point2D position, Color color, double rotation, Vec2D translation, Point2D rotationCenter) {
        this.setPosition(position);
        this.setColor(color);
        this.setRotation(rotation);
        this.setTranslation(translation);
        this.setRotationCenter(rotationCenter);
    }


    @Override
    public Point2D getPosition() {
        double minX = ApplicationI.SCENE_WIDTH;
        double minY = ApplicationI.SCENE_HEIGHT;

        for (Shape s: shapes) {
            for (Point2D point: s.getPoints()) {
                minX = Math.min(minX, point.x);
                minY = Math.min(minY, point.y);
            }
        }

        return new Point2D(minX, minY);
    }

    public double getWidth() {
        double maxX = 0;
        double minX = ApplicationI.SCENE_WIDTH;

        for (Shape s: shapes) {
            for (Point2D point : s.getPoints()) {
                minX = Math.min(minX, point.x);
                maxX = Math.max(maxX, point.x);
            }
        }

        return maxX - minX;
    }

    public double getHeight() {
        double maxY = 0;
        double minY = ApplicationI.SCENE_HEIGHT;

        for (Shape s: shapes) {
            for (Point2D point : s.getPoints()) {
                minY = Math.min(minY, point.y);
                maxY = Math.max(maxY, point.y);
            }
        }

        return maxY - minY;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getRotation() {
        return this.rotation;
    }

    @Override
    public Point2D getRotationCenter() {
        return this.rotationCenter;
    }

    @Override
    public Vec2D getTranslation() {
        return this.translation;
    }

    @Override
    public EditionDialogI createEditionDialog() {
        return new GroupEditionDialog(this);
    }

    @Override
    public void accept(EditorVisitor visitor) {
        visitor.visit(this);
    }
}
