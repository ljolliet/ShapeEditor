package ui;

import editor.Polygon;
import editor.Rectangle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class JFxRendering implements Rendering {
    @Override
    public void draw(Object context, Rectangle rectangle) {
        javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(rectangle.getWidth(), rectangle.getHeight());
        r.setX(rectangle.getPosition().x);
        r.setY(rectangle.getPosition().y);
        r.setFill(Color.rgb(rectangle.getColor().r, rectangle.getColor().g, rectangle.getColor().b));
        ((Pane)context).getChildren().add(r);
        //TODO add other attributs
    }

    @Override
    public void draw(Object context, Polygon rectangle) {
        //TODO draw a polygon
    }
}
