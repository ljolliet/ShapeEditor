package editor.edition;

import editor.shapes.Polygon;
import editor.utils.Point2D;
import javafx.scene.control.ContextMenu;
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
        rendering.setEditionDialog(this, this.getPosition());
    }

    @Override
    public void applyEdition(){
        this.getTarget().setAllPolygonValues(this.nbSides, this.sideLength, new Point2D(this.posX, this.posY), this.color, this.rotation);
    }

    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }

    @Override
    public void setEditionDialog(ContextMenu contextMenu) {

    }

}
