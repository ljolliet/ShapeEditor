package editor.edition;

import editor.Polygon;
import editor.utils.Color;
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
        rendering.setEditionGridPane(this);
        rendering.showEditionDialog(this.getPosition());
    }

    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }

    @Override
    public void applyEdition() {
        this.setNbSides(this.nbSides);
        this.setSideLength(this.sideLength);
        this.setPosition(new Point2D(this.posX, this.posY));
        this.setColor(this.color);
        this.setRotation(this.rotation);
    }

    public void setNbSides(int nbSides) {
        this.getTarget().setNbSides(nbSides);
    }

    public void setSideLength(double sideLength) {
        this.getTarget().setSideLength(sideLength);
    }
}
