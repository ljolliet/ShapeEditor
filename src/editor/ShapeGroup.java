package editor;

import editor.utils.Color;
import editor.utils.Point2D;
import editor.utils.SelectionShape;
import editor.utils.Vec2D;
import ui.ApplicationI;
import editor.edition.EditionDialogI;
import ui.Rendering;

import java.util.*;

public class ShapeGroup extends ShapeObservable {

    private Set<Shape> shapes;

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
    public Set<Shape> getChild() {
        return new HashSet<>(shapes);
    }

    @Override
    public void setChild(Set<Shape> shapes) {
        this.shapes = shapes;
    }

    @Override
    public void collapse() {
        //TODO how to collapse a group ?
    }

    @Override
    public Shape clone() throws CloneNotSupportedException {
        Shape c = super.clone();
        c.setChild(new HashSet<>(this.getChild()));
        return c;
    }

    @Override
    public void drawInScene(Rendering rendering) {
        for(Shape s : shapes)
            s.drawInScene(rendering);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        for(Shape s : shapes)
            s.drawInToolbar(rendering);
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

    @Override
    public boolean contained(SelectionShape s) {
        return false;
    }

    @Override
    public Point2D[] getPoints() {
        List<Point2D> points = new ArrayList<>();

        for (Shape s: shapes)
            points.addAll(Arrays.asList(s.getPoints()));

        return (Point2D[]) points.toArray();
    }

    @Override
    public void setPosition(Point2D newPos) {
        Point2D position = getPosition();

        double deltaX = newPos.x - position.x;
        double deltaY = newPos.y - position.y;

        for (Shape s: shapes)
            s.setPosition(new Point2D(
                    s.getPosition().x + deltaX,
                    s.getPosition().y + deltaY
            ));
    }

    @Override
    public void setColor(Color color) {
        for (Shape s: shapes)
            s.setColor(color);
    }

    @Override
    public void setRotation(double angle) {
        for (Shape s: shapes)
            s.setRotation(angle);
    }

    @Override
    public void setRotationCenter(Point2D pos) {
        for (Shape s: shapes)
            s.setRotationCenter(pos);
    }

    @Override
    public void setTranslation(Vec2D translation) {
        for (Shape s: shapes)
            s.setTranslation(translation);
    }

    @Override
    public Point2D getPosition() {
        double minX = 0, minY = 0,
                maxX = ApplicationI.SCENE_WIDTH,
                maxY = ApplicationI.SCENE_HEIGHT;

        for (Shape s: shapes) {
            for (Point2D point: s.getPoints()) {
                minX = Math.min(minX, point.x);
                minY = Math.min(minY, point.y);
                maxX = Math.min(maxX, point.x);
                maxY = Math.min(maxY, point.y);
            }
        }

        return new Point2D((minX + maxX) / 2, (minY + maxY) / 2);
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
