import utils.Color;
import utils.Vec2D;

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
}
