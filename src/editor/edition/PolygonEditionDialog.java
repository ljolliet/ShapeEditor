package editor.edition;

import editor.shapes.Polygon;
import editor.utils.Point2D;
import ui.Rendering;

public class PolygonEditionDialog extends ShapeEditionDialog {
    public int nbSides;
    public double sideLength;

    public PolygonEditionDialog(Polygon polygon) {
        super(polygon);
        this.nbSides = polygon.getNbSides();
        this.sideLength = polygon.getSideLength();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
        rendering.showEditionDialog(this.getPosition());
    }

    @Override
    public void applyEdition() {
        this.setNbSides(this.nbSides);
        this.setSideLength(this.sideLength);
        this.setPosition(new Point2D(this.posX, this.posY));
        this.setColor(this.color);
        this.setRotation(this.rotation);
    }

    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }

    public void setNbSides(int nbSides) {
        this.getTarget().setNbSides(nbSides);
    }

    public void setSideLength(double sideLength) {
        this.getTarget().setSideLength(sideLength);
    }
}
