package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import ui.Rendering;

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
        this.radius = sideLength / (2 * Math.sin(Math.PI / nbSide));
    }

    /**
     * A polygon is composed of points depending on the number of sides,
     * @return A list of points
     */
    public double[] getPoints(double radius) {
        double[] points = new double[nbSide * 2];
        int cpt = 0;

        for (int i = 0; i < nbSide; i++) {
            points[cpt++] = getX() + radius * Math.cos(2 * Math.PI * i / nbSide + Math.toRadians(getRotation())); // X
            points[cpt++] = getY() + radius * Math.sin(2 * Math.PI * i / nbSide + Math.toRadians(getRotation())); // Y
        }

        return points;
    }

    public double[] getPoints() {
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

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
        computeRadius();
    }

    public int getNbSide() {
        return nbSide;
    }

    public double getSideLength() {
        return sideLength;
    }
}
