import utils.Color;
import utils.Vec2D;

public class Polygone extends SimpleShape {
    private int nbSide;
    private double sideLength;

    public Polygone(int nbSide, double sideLength, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.nbSide = nbSide;
        this.sideLength = sideLength;
    }
}
