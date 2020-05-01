package editor.shapes;

import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.ApplicationI;
import editor.edition.EditionDialogI;
import ui.Rendering;

import java.util.*;

public class ShapeGroup extends Shape {

    private Set<ShapeI> shapes;

    public ShapeGroup(){
        shapes = new HashSet<>();
    }

    @Override
    public void addShape(ShapeI s) {
        shapes.add(s);
    }

    @Override
    public void removeShape(ShapeI s) {
        shapes.remove(s);
    }

    @Override
    public Set<ShapeI> getChildren() {
        return new HashSet<>(shapes);
    }

    @Override
    public void setChildren(Set<ShapeI> shapes) {
        this.shapes = shapes;
    }

    @Override
    public ShapeI clone() throws CloneNotSupportedException {
        ShapeI c = super.clone();

        Set<ShapeI> set = new HashSet<>();
        for (ShapeI shape: this.getChildren())
            set.add(shape.clone());

        c.setChildren(set);
        return c;
    }

    @Override
    public void drawInScene(Rendering rendering) {
        for(ShapeI s : shapes)
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
        for(ShapeI child : shapes)
            if(child.contains(position))
                return true;
        return false;
    }

    @Override
    public boolean contained(SelectionShape s) {
        return false;
    }

    @Override
    public Point2D[] getPoints() {
        List<Point2D> points = new ArrayList<>();

        for (ShapeI s: shapes)
            points.addAll(Arrays.asList(s.getPoints()));

        return (Point2D[]) points.toArray();
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
        for (ShapeI s: shapes)
            s.setPosition(new Point2D(
                    s.getPosition().x + deltaX,
                    s.getPosition().y + deltaY
            ));
    }

    @Override
    public void setColor(Color color) {
        for (ShapeI s: shapes)
            s.setColor(color);
    }

    @Override
    public void setRotation(double angle) {
        for (ShapeI s: shapes)
            s.setRotation(angle);
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        for (ShapeI s: shapes)
            s.setRotationCenter(pos);
    }

    @Override
    public void setTranslation(Vec2D translation) {
        for (ShapeI s: shapes)
            s.setTranslation(translation);
    }

    @Override
    public Point2D getPosition() {
        double minX = ApplicationI.SCENE_WIDTH;
        double minY = ApplicationI.SCENE_HEIGHT;

        for (ShapeI s: shapes) {
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

        for (ShapeI s: shapes) {
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

        for (ShapeI s: shapes) {
            for (Point2D point : s.getPoints()) {
                minY = Math.min(minY, point.y);
                maxY = Math.max(maxY, point.y);
            }
        }

        return maxY - minY;
    }

    /*
     * TODO Write all getters
     */

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public double getRotation() {
        return 0;
    }

    @Override
    public Point2D getRotationCenter() {
        return null;
    }

    @Override
    public Vec2D getTranslation() {
        return null;
    }

    @Override
    public EditionDialogI createEditionDialog() {
        return null; //TODO Group
    }
}
