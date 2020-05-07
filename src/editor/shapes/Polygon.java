package editor.shapes;

import editor.edition.EditionDialogI;
import editor.edition.PolygonEditionDialog;
import editor.core.EditorVisitor;
import editor.utils.Color;
import editor.utils.Point2D;
import ui.ApplicationI;
import ui.Rendering;

public class Polygon extends SimpleShape {
    private int nbSides;
    private double sideLength;
    private double radius;

    public Polygon(int nbSides, double sideLength, Point2D position, Color color, Point2D rotationCenter, double rotation) {
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
    public Point2D[] getPoints(double radius) {
        Point2D[] points = new Point2D[nbSides];

        for (int i = 0; i < nbSides; i++) {
            double x = getX() + radius * Math.cos(2 * Math.PI * i / nbSides); // X
            double y = getY() + radius * Math.sin(2 * Math.PI * i / nbSides); // Y
            points[i] = new Point2D(x,y);
        }

        return points;
    }

    @Override
    public Point2D[] getPoints() {
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
    public boolean contains(Point2D position) {
        //inspired by :
        //https://web.archive.org/web/20161108113341/https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
        boolean result = false;
        Point2D[] points = getPoints();
        for (int i = 0, j = points.length - 1; i < points.length; j = i++)
            if ((points[i].y > position.y) != (points[j].y > position.y) &&
                    (position.x < (points[j].x - points[i].x) * (position.y - points[i].y)
                            / (points[j].y - points[i].y) + points[i].x))
                result = !result;
        return result;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
        computeRadius();
        notifyObservers();
    }

    public void setNbSides(int nbsides){
        this.nbSides = nbsides;
        computeRadius();
        notifyObservers();
    }

    @Override
    public void setPosition(Point2D pos) {
        double x = Math.max(radius, Math.min(ApplicationI.SCENE_WIDTH - radius, pos.x));
        double y = Math.max(radius, Math.min(ApplicationI.SCENE_HEIGHT - radius, pos.y));

        super.setPosition(new Point2D(x, y));
    }

    @Override
    public EditionDialogI createEditionDialog() {
        return new PolygonEditionDialog(this);
    }

    @Override
    public void accept(EditorVisitor visitor) {
        visitor.visit(this);
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
