package editor;

import editor.utils.Color;
import editor.utils.Vec2D;

public class Polygon extends SimpleShape {
    private int nbSide;
    private double sideLength;

    public Polygon(int nbSide, double sideLength, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.nbSide = nbSide;
        this.sideLength = sideLength;
    }

    public int getNbSide() {
        return nbSide;
    }

    public double getSideLength() {
        return sideLength;
    }

    @Override
    public void draw(Object context) {
        rendering.drawPolygon(context, this);
    }
}
