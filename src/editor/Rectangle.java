package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import javafx.scene.layout.Pane;

public class Rectangle extends SimpleShape {
    private double width;
    private double height;
    private int radius;

    public Rectangle(double width, double height, int radius, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.width = width;
        this.height = height;
        this.radius = radius;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public int getRadius() {
        return radius;
    }



    @Override
    public void draw(Object context) {
        rendering.draw(context, this);
        /*
            javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle(s.getWidth(), s.getHeight());
            r.setX(s.getPosition().x);
            r.setY(s.getPosition().y);
            r.setFill(Color.rgb(s.getColor().r, s.getColor().g, s.getColor().b));
            toolbarBox.getChildren().add(r);
        */
    }
}
