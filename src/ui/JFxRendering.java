package ui;

import editor.Editor;
import editor.Polygon;
import editor.Rectangle;
import editor.Shape;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class JFxRendering implements Rendering {

    private Editor editor;
    private VBox toolbarBox;
    private Group root;

    JFxRendering(Editor editor, VBox toolbarBox, Group root) {
        this.editor = editor;
        this.toolbarBox = toolbarBox;
        this.root = root;
    }

    private void init() {
        // Remove all shapes of the scene and of the toolbar
        root.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
        toolbarBox.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
    }

    @Override
    public void drawEditor() {
        // Clear shapes
        this.init();

        // Draw scene
        System.out.println(editor);
        for (Shape s: editor.getScene().getShapes())
            s.drawInScene(this);

        // Draw toolbar
        for (Shape s: editor.getToolbar().getShapes())
            s.drawInToolbar(this);
    }

    @Override
    public void drawInScene(Rectangle r) {
        javafx.scene.shape.Rectangle rectangle = createRectangle(r, r.getWidth(), r.getHeight());
        // Set coords
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());
        // Set Translation
        rectangle.setTranslateX(r.getTranslation().width);
        rectangle.setTranslateY(r.getTranslation().height);

        root.getChildren().add(rectangle);
    }

    @Override
    public void drawInScene(Polygon p) {
//        javafx.scene.shape.Polygon p = new javafx.scene.shape.Polygon();
//        //create all the polygon corner positions (https://bit.ly/3ecEGmm)
//        final double angleStep = Math.PI * 2 / polygon.getNbSide();
//        double angle = polygon.getRotation();
//        for (int i = 0; i < polygon.getNbSide(); i++) {
//            // calculate radius fom side length (https://www.mathopenref.com/polygonradius.html)
//            double radius = polygon.getSideLength() / (2 * Math.sin(Math.PI / polygon.getNbSide()));
//            p.getPoints().addAll(
//                    Math.sin(angle) * radius + polygon.getRotationCenter().x,
//                    Math.cos(angle) * radius + polygon.getRotationCenter().y
//            );
//            angle += angleStep;
//        }

        javafx.scene.shape.Polygon polygon = createPolygon(p, p.getRadius());
        // Set Translation
        polygon.setTranslateX(p.getTranslation().width);
        polygon.setTranslateY(p.getTranslation().height);

        root.getChildren().add(polygon);
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double max = Math.max(r.getHeight(), r.getWidth());
        double ratio = (ApplicationI.TOOLBAR_WIDTH / 2d) / max;

        javafx.scene.shape.Rectangle rectangle = createRectangle(r, r.getWidth() * ratio, r.getHeight() * ratio);
        // For toolbar, special rotation
        rectangle.getTransforms().clear();
        rectangle.setRotate(r.getRotation());
        // Set border radius
        rectangle.setArcWidth(r.getBorderRadius() * ratio);
        rectangle.setArcHeight(r.getBorderRadius() * ratio);

        toolbarBox.getChildren().add(rectangle);
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_WIDTH / 4d;

        javafx.scene.shape.Polygon polygon = createPolygon(p, radius);
        // For toolbar, special rotation
        polygon.getTransforms().clear();
        polygon.setRotate(p.getRotation());

        toolbarBox.getChildren().add(polygon);
    }

    private javafx.scene.shape.Rectangle createRectangle(Rectangle r, double width, double height) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        // Color
        rectangle.setFill(Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b));
        // Rotation
        Rotate rotate = new Rotate(r.getRotation());
        rotate.setPivotX(r.getX() + r.getRotationCenter().x);
        rotate.setPivotY(r.getY() + r.getRotationCenter().y);
        rectangle.getTransforms().add(rotate);
        // Radius
        rectangle.setArcHeight(r.getBorderRadius());
        rectangle.setArcWidth(r.getBorderRadius());

        return rectangle;
    }

    private javafx.scene.shape.Polygon createPolygon(Polygon p, double radius) {
        double[] points = getPolygonPoints(p.getPoints(radius), p.getNbSides());

        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);
        // Color
        polygon.setFill(Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b));
        // Rotation
        Rotate rotate = new Rotate(p.getRotation());
        rotate.setPivotX(p.getX() + p.getRotationCenter().x);
        rotate.setPivotY(p.getY() + p.getRotationCenter().y);
        polygon.getTransforms().add(rotate);

        return polygon;
    }

    private double[] getPolygonPoints(double[][] pre_points, int nbSides) {
        double[] points = new double[nbSides * 2];
        int cpt = 0;

        for (int i = 0; i < nbSides; i++) {
            points[cpt++] = pre_points[i][0];
            points[cpt++] = pre_points[i][1];
        }

        return points;
    }
}
