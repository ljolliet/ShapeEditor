/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.shapes;

import editor.core.EditorVisitor;
import editor.edition.EditionDialogI;
import editor.edition.GroupEditionDialog;
import editor.observer.Observable;
import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.ApplicationI;
import ui.Rendering;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ShapeGroup extends Observable implements Shape {

    private Set<Shape> shapes;

    private Point2D position = new Point2D(0, 0);
    private Color color = null;
    private double rotation = -1;
    private Point2D rotationCenter = null;
    private Vec2D translation = null;

    public ShapeGroup(){
        shapes = new HashSet<>();
    }

    @Override
    public void addShape(Shape s) {
        shapes.add(s);
        this.position = computePosition();
    }

    @Override
    public void removeShape(Shape s) {
        shapes.remove(s);
        this.position = computePosition();
    }

    @Override
    public Set<Shape> getChildren() {
        return new HashSet<>(shapes);
    }

    @Override
    public void setChildren(Set<Shape> shapes) {
        this.shapes = shapes;
        this.position = computePosition();
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

    private void setPositionWithoutNotify(Point2D newPos) {
        Point2D oldPosition = computePosition();

        // The group cannot be out of bounds
        double x = Math.max(0, Math.min(ApplicationI.SCENE_WIDTH - getWidth(), newPos.x));
        double y = Math.max(0, Math.min(ApplicationI.SCENE_HEIGHT - getHeight(), newPos.y));

        // Compute delta
        double deltaX = x - oldPosition.x;
        double deltaY = y - oldPosition.y;

        // Set new position for all children
        for (Shape s: shapes) {
            s.setPosition(new Point2D(
                    s.getPosition().x + deltaX,
                    s.getPosition().y + deltaY
            ));
        }

        this.position = computePosition();
    }

    @Override
    public void setPosition(Point2D position) {
        if ((this.position == null && position != null)
            || (this.position != null && !this.position.equals(position))) {
            this.setPositionWithoutNotify(position);
            notifyObservers();
        }
    }

    private void setColorWithoutNotify(Color color) {
        this.color = color;
        for (Shape s : shapes)
            s.setColor(color);
    }

    @Override
    public void setColor(Color color) {
        if ((this.color == null && color != null)
                || (this.color != null && !this.color.equals(color))) {
            this.setColorWithoutNotify(color);
            notifyObservers();
        }
    }

    private void setRotationWithoutNotify(double angle) {
        this.rotation = angle;
        for (Shape s: shapes)
            s.setRotation(angle);
    }

    @Override
    public void setRotation(double angle) {
        if (this.rotation != angle) {
            this.setRotationWithoutNotify(angle);
            notifyObservers();
        }
    }


    private void setRotationCenterWithoutNotify(Point2D pos) {
        this.rotationCenter = pos;
        for(Shape s : shapes)
            s.setRotationCenter(this.rotationCenter);
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        if ((this.rotationCenter == null && pos != null)
                || (this.rotationCenter != null && !this.rotationCenter.equals(pos))) {
            this.setRotationCenterWithoutNotify(pos);
            notifyObservers();
        }
    }

    private void setTranslationWithoutNotify(Vec2D translation) {
        this.translation = translation;
        for(Shape s : shapes)
            s.setTranslation(this.translation);
    }

    @Override
    public void setTranslation(Vec2D translation) {
        if ((this.translation == null && translation != null)
                || (this.translation != null && !this.translation.equals(translation))) {
            this.setTranslationWithoutNotify(translation);
            notifyObservers();
        }
    }

    private void applyChanges() {
        if (this.position != null)
            this.setPositionWithoutNotify(this.position);
        if (this.color != null)
            this.setColorWithoutNotify(this.color);
        if (this.rotation >= 0)
            this.setRotationWithoutNotify(this.rotation);
        if (this.translation != null)
            this.setTranslationWithoutNotify(this.translation);
        if (this.rotationCenter != null)
            this.setRotationCenterWithoutNotify(this.rotationCenter);

        notifyObservers();
    }

    @Override
    public void setAllValues(Point2D position, Color color,
                             double rotation, Vec2D translation, Point2D rotationCenter) {
        // Set values only if there are changes
        // in order to don't notify observers for nothing
        if (isDifferent(position, color, rotation, translation, rotationCenter)) {
            this.position = position;
            this.color = color;
            this.rotation = rotation;
            this.translation = translation;
            this.rotationCenter = rotationCenter;

            this.applyChanges();
        }
    }

    private boolean isDifferent(Point2D position, Color color,
                                double rotation, Vec2D translation, Point2D rotationCenter) {
        return (this.position == null && position != null)
                || (this.position != null && !this.position.equals(position))
                || (this.color == null && color != null)
                || (this.color != null && !this.color.equals(color))
                || this.rotation != rotation
                || (this.translation == null && translation != null)
                || (this.translation != null && !this.translation.equals(translation))
                || (this.rotationCenter == null && rotationCenter != null)
                || (this.rotationCenter != null && !this.rotationCenter.equals(rotationCenter));
    }

    private Point2D computePosition() {
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
    public Point2D getPosition() {
        return this.position;
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
