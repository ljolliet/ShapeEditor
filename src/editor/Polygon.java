package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import ui.Rendering;

public class Polygon extends SimpleShape {
    private int nbSides;
    private double sideLength;

    private double radius;

    public Polygon(int nbSides, double sideLength, Vec2D position, Color color, Vec2D rotationCenter, double rotation) {
        super(position, color, rotationCenter, rotation);
        this.nbSides = nbSides;
        this.sideLength = sideLength;
        computeRadius();
    }

    private void computeRadius() {
        this.radius = sideLength / (2 * Math.sin(Math.PI / nbSides));
    }

    /**
     * A polygon is composed of points depending on the number of sides,
     * @return A 2D array of points
     */
    public double[][] getPoints(double radius) {
        double[][] points = new double[nbSides][2];

        for (int i = 0; i < nbSides; i++) {
            points[i][0] = getX() + radius * Math.cos(2 * Math.PI * i / nbSides + Math.toRadians(getRotation())); // X
            points[i][1] = getY() + radius * Math.sin(2 * Math.PI * i / nbSides + Math.toRadians(getRotation())); // Y
        }

        return points;
    }

    public double[][] getPoints() {
        return this.getPoints(this.radius);
    }

    @Override
    public void drawInScene(Rendering rendering) {
        rendering.drawInScene(this);
    }

    @Override
    public void drawInToolbar(Rendering rendering) {
        rendering.drawInToolbar(this);
    }

    @Override
    public boolean contains(Vec2D pos) {
        return false;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
        computeRadius();
    }

    public int getNbSides() {
        return nbSides;
    }

    public double getSideLength() {
        return sideLength;
    }

    public double getRadius() {
        return radius;
    }
}
