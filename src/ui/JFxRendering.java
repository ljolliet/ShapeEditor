package ui;

import editor.Polygon;
import editor.Rectangle;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class JFxRendering implements Rendering {
    @Override
    public void drawRectangle(Object context, Rectangle rectangle) {
        javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(rectangle.getWidth(), rectangle.getHeight());
        r.setX(rectangle.getPosition().x);
        r.setY(rectangle.getPosition().y);
        r.setFill(Color.rgb(rectangle.getColor().r, rectangle.getColor().g, rectangle.getColor().b));
        ((Pane)context).getChildren().add(r);
        //TODO add other attributs rotation,rotationCenter, translation, radius
    }

    @Override
    public void drawPolygon(Object context, Polygon polygon) {
        javafx.scene.shape.Polygon p = new javafx.scene.shape.Polygon();
        //create all the polygon corner positions (https://bit.ly/3ecEGmm)
        final double angleStep = Math.PI * 2 / polygon.getNbSide();
        double angle = polygon.getRotation();
        for (int i = 0; i < polygon.getNbSide(); i++) {
            // calculate radius fom side length (https://www.mathopenref.com/polygonradius.html)
            double radius = polygon.getSideLength() / (2 * Math.sin(Math.PI / polygon.getNbSide()));
            p.getPoints().addAll(
                    Math.sin(angle) * radius + polygon.getRotationCenter().x,
                    Math.cos(angle) * radius + polygon.getRotationCenter().y
            );
            angle += angleStep;
        }
        p.setFill(Color.rgb(polygon.getColor().r, polygon.getColor().g, polygon.getColor().b));
        ((Pane)context).getChildren().add(p);
        //TODO include position/rotationCenter, translation
    }
}
