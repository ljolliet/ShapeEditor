package editor.edition;

import editor.shapes.Polygon;
import editor.utils.Point2D;
import editor.utils.Vec2D;
import javafx.scene.control.ContextMenu;
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
                super.getPosition(),
                super.getColor(),
                super.getRotation(),
                super.getTranslation(),
                super.getRotationCenter());
    }

    public Polygon getTarget() {
        return (Polygon) super.getTarget();
    }

    @Override
    public void setEditionDialog(ContextMenu contextMenu) {

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
