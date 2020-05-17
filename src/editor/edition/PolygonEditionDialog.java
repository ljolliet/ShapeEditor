package editor.edition;

import editor.shapes.Polygon;
import editor.utils.Point2D;
import editor.utils.Vec2D;
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
        rendering.setEditionDialog(this);
    }

    @Override
    public void applyEdition(){
        this.getTarget().setAllPolygonValues(
                this.nbSides,
                this.sideLength,
                this.getPosition(),
                this.getColor(),
                this.getRotation(),
                this.getTranslation(),
                this.getRotationCenter());
    }

    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }

    @Override
    public void setEditionDialog(ContextMenu contextMenu) {

    }

}
