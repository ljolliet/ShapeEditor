package ui.javafx;

import editor.core.Editor;
import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.shapes.ShapeGroup;
import editor.shapes.ShapeI;
import editor.utils.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class ShapeBuilderJFx {

    private ShapeBuilderJFx() {}

    public static javafx.scene.shape.Rectangle createSceneRectangle(Rectangle r) {
        Editor editor = Editor.getInstance();

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(r.getWidth(), r.getHeight());

        // Color
        Color color = Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b);
        rectangle.setFill(color);

        // Add stroke if selected
        if (editor.getScene().getSelectedShapes().contains(r))
            rectangle.setStroke(color.darker());

        // Rotation
        Rotate rotate = new Rotate(r.getRotation());
        rotate.setPivotX(r.getX() + r.getRotationCenter().x);
        rotate.setPivotY(r.getY() + r.getRotationCenter().y);
        rectangle.getTransforms().add(rotate);

        // Radius
        rectangle.setArcHeight(r.getBorderRadius());
        rectangle.setArcWidth(r.getBorderRadius());

        // Set coords
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());

        // Set Translation
        rectangle.setTranslateX(r.getTranslation().width);
        rectangle.setTranslateY(r.getTranslation().height);

        return rectangle;
    }

    public static javafx.scene.shape.Polygon createScenePolygon(Polygon p) {
        Editor editor = Editor.getInstance();

        double[] points = getPolygonPoints(p.getPoints(), p.getNbSides());
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);

        // Color
        Color color = Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b);
        polygon.setFill(color);

        // Add stroke if selected
        if (editor.getScene().getSelectedShapes().contains(p))
            polygon.setStroke(color.darker());

        // Rotation
        Rotate rotate = new Rotate(p.getRotation());
        rotate.setPivotX(p.getX() + p.getRotationCenter().x);
        rotate.setPivotY(p.getY() + p.getRotationCenter().y);
        polygon.getTransforms().add(rotate);

        // Translation
        polygon.setTranslateX(p.getTranslation().width);
        polygon.setTranslateY(p.getTranslation().height);

        return polygon;
    }

    public static javafx.scene.shape.Rectangle createToolbarRectangle(Rectangle r, double ratio) {
        javafx.scene.shape.Rectangle rectangle =
                new javafx.scene.shape.Rectangle(r.getWidth() * ratio, r.getHeight() * ratio);

        // Color
        Color color = Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b);
        rectangle.setFill(color);

        // Rotation
        rectangle.setRotate(r.getRotation());

        // Border radius
        rectangle.setArcWidth(r.getBorderRadius() * ratio);
        rectangle.setArcHeight(r.getBorderRadius() * ratio);

        return rectangle;
    }

    public static javafx.scene.shape.Polygon createToolbarPolygon(Polygon p, double radius) {
        double[] points = getPolygonPoints(p.getPoints(radius), p.getNbSides(), p.getPosition().x, p.getPosition().y);
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);

        // Color
        Color color = Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b);
        polygon.setFill(color);

        // Rotation
        polygon.setRotate(p.getRotation());

        return polygon;
    }

    private static double[] getPolygonPoints(Point2D[] pre_points, int nbSides, double deltaX, double deltaY) {
        double[] points = new double[nbSides * 2];
        int cpt = 0;

        for (int i = 0; i < nbSides; i++) {
            points[cpt++] = pre_points[i].x - deltaX;
            points[cpt++] = pre_points[i].y - deltaY;
        }

        return points;
    }

    private static double[] getPolygonPoints(Point2D[] pre_points, int nbSides) {
        return getPolygonPoints(pre_points, nbSides, 0, 0);
    }

    public static Group createToolbarGroup(ShapeGroup group, double ratio) {
        Group jfxGroup = new Group();

        for (ShapeI shape: group.getChildren()) {
            Group jfxShape = new Group();

            // Create shapes
            if (shape instanceof Rectangle)
                jfxShape.getChildren().add(createToolbarRectangle((Rectangle) shape, ratio));
            else if (shape instanceof Polygon)
                jfxShape.getChildren().add(createToolbarPolygon((Polygon) shape,
                        ((Polygon) shape).getRadius() * ratio));
            else if (shape instanceof ShapeGroup)
                jfxShape.getChildren().add(createToolbarGroup((ShapeGroup) shape, ratio));

            // Create translation related to position
            jfxShape.setTranslateX(shape.getPosition().x * ratio);
            jfxShape.setTranslateY(shape.getPosition().y * ratio);
            // Add to group
            jfxGroup.getChildren().add(jfxShape);
        }

        return jfxGroup;
    }
}