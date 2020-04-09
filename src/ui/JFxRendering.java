package ui;

import editor.Editor;
import editor.Polygon;
import editor.Rectangle;
import editor.Shape;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(r.getWidth(), r.getHeight());
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());
        rectangle.setFill(Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b));
        root.getChildren().add(rectangle);
        // TODO add other attributes rotation, rotationCenter, translation, radius
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

        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(p.getPoints());
        polygon.setFill(Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b));
        root.getChildren().add(polygon);
        // TODO include rotationCenter, translation
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double width = ApplicationI.TOOLBAR_WIDTH / 2d;
        double height = ApplicationI.TOOLBAR_WIDTH / 3d;

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        rectangle.setFill(Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b));
        toolbarBox.getChildren().add(rectangle);
        // TODO add other attributes rotation, rotationCenter, translation, radius
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double width = ApplicationI.TOOLBAR_WIDTH / 4d;

        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(p.getPoints(width));
        polygon.setFill(Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b));
        toolbarBox.getChildren().add(polygon);
    }
}
