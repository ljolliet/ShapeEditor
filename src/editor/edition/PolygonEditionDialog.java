package editor.edition;

import editor.Polygon;
import ui.Rendering;

public class PolygonEditionDialog extends ShapeEditionDialog {

    public PolygonEditionDialog(Polygon polygon) {
        super(polygon);
    }

    @Override
    public void draw(Rendering rendering) {
        //TODO
    }
    public Polygon getTarget() {
        return (Polygon)super.getTarget();
    }
}
