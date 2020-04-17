package editor.edition;

import editor.Polygon;
import ui.Rendering;

public class PolygonEditionDialog extends ShapeEditionDialog {

    public PolygonEditionDialog(Polygon polygon) {
        super(polygon);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
        rendering.showEditionDialog(this.getPosition());
    }

    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }
}
