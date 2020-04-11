package ui;

import editor.Polygon;
import editor.Rectangle;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class JFxRendering implements Rendering {

    private VBox toolbarBox;
    private Group root;

    JFxRendering(VBox toolbarBox, Group root) {
        this.toolbarBox = toolbarBox;
        this.root = root;
    }

    @Override
    public void init() {
        // Remove all shapes of the scene and of the toolbar
        root.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
        toolbarBox.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
    }

    @Override
    public void drawInScene(Rectangle r) {
        javafx.scene.shape.Rectangle rectangle = createRectangle(r, r.getWidth(), r.getHeight());
        // Set coords
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());

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

        root.getChildren().add(polygon);
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double width = ApplicationI.TOOLBAR_WIDTH / 2d;
        double height = ApplicationI.TOOLBAR_WIDTH / 3d;

        javafx.scene.shape.Rectangle rectangle = createRectangle(r, width, height);
        // For toolbar, special rotation
        rectangle.getTransforms().clear();
        rectangle.setRotate(r.getRotation());

        toolbarBox.getChildren().add(rectangle);
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_WIDTH / 4d;

        javafx.scene.shape.Polygon polygon = createPolygon(p, radius);

        toolbarBox.getChildren().add(polygon);
    }

    private javafx.scene.shape.Rectangle createRectangle(Rectangle r, double width, double height) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        rectangle.setFill(Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b));
        Rotate rotate = new Rotate(r.getRotation());
        rotate.setPivotX(r.getX() + r.getRotationCenter().x);
        rotate.setPivotY(r.getY() + r.getRotationCenter().y);

        rectangle.getTransforms().add(rotate);
        // TODO add other attributes
        //  - translation
        //  - radius

        return rectangle;
    }

    private javafx.scene.shape.Polygon createPolygon(Polygon p, double radius) {
        double[] points = getPolygonPoints(p.getPoints(radius), p.getNbSides());

        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);
        polygon.setFill(Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b));
        // TODO add other attributes
        //  - rotationCenter
        //  - translation

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
