package editor.edition;

import editor.shapes.Polygon;
import ui.Rendering;

public class PolygonEditionDialog extends ShapeEditionDialog {
    private int nbSides;
    private double sideLength;

    public PolygonEditionDialog(Polygon polygon) {
        super(polygon);
        this.nbSides = polygon.getNbSides();
        this.sideLength = polygon.getSideLength();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
    }

    @Override
    public void applyEdition(){
        this.getTarget().setAllPolygonValues(
                this.getNbSides(),
                this.getSideLength(),
                this.getPosition(),
                this.getColor(),
                this.getRotation(),
                this.getTranslation(),
                this.getRotationCenter());
    }

    public Polygon getTarget() {
        return (Polygon) super.getTarget();
    }

    public void setNbSides(int nbSides) {
        this.nbSides = nbSides;
    }

    public void setSideLength(double sideLength) {
        this.sideLength = sideLength;
    }

    public int getNbSides() {
        return this.nbSides;
    }

    public double getSideLength() {
        return this.sideLength;
    }
}
