package editor.edition;

import editor.shapes.Polygon;
import editor.shapes.ShapeGroup;
import editor.shapes.ShapeI;
import javafx.scene.paint.Color;
import ui.Rendering;

public class GroupeEditionDialog extends ShapeEditionDialog {
    public Color color;

    public GroupeEditionDialog(ShapeGroup shapeGroup) {
        super(shapeGroup);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
    }

    @Override
    public void applyEdition() {

    }

    public ShapeGroup getTarget(){
        return (ShapeGroup) super.getTarget();
    }

    public void setColor(editor.utils.Color c){
        for (ShapeI s: getTarget().getChildren()) {
            s.setColor(c);
        }
    }
}
