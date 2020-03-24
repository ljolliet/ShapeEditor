import utils.Color;
import utils.Vec2D;

public abstract class SimpleShape {
    private Vec2D position;
    private Color color;
    private Vec2D rotationCenter;
    private double rotation;
    // translation TODO

    public SimpleShape(Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        this.position = position;
        this.color = color;
        this.rotationCenter = rotationCenter;
        this.rotation = rotation;
    }
}
