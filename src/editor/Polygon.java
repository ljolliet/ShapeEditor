package editor;

import editor.utils.Color;
import editor.utils.Vec2D;

import java.util.ArrayList;
import java.util.List;

public class Polygon extends SimpleShape {
    private int nbSide;
    private double sideLength;
    private double radius;

    public Polygon(int nbSide, double sideLength, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.nbSide = nbSide;
        this.sideLength = sideLength;
        computeRadius();
    }

    private void computeRadius() {
        this.radius = sideLength / (2 * Math.sin(180d / nbSide));
    }

    /**
     * A polygon is composed of points depending on the number of sides,
     * @return
     */
    public double[] getPoints() {
        double[] points = new double[nbSide * 2];
        int cpt = 0;

        for (int i = 0; i < nbSide; i++) {
            points[cpt++] = getX() + radius * Math.cos(2 * Math.PI * i / nbSide + Math.toRadians(getRotation())); // X
            points[cpt++] = getY() + radius * Math.sin(2 * Math.PI * i / nbSide + Math.toRadians(getRotation())); // Y
        }

        return points;
    }
}
