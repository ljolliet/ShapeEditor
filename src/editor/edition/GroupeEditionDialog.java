package editor.edition;

import editor.shapes.Polygon;
import editor.shapes.Shape;
import editor.shapes.ShapeGroup;
import editor.shapes.ShapeI;
import editor.utils.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.paint.Color;
import ui.Rendering;

public class GroupeEditionDialog extends ShapeEditionDialog {

    public GroupeEditionDialog(ShapeGroup shapeGroup) {
        super(shapeGroup);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
        rendering.showEditionDialog(this.getPosition());
    }

    @Override
    public void applyEdition() {
        this.getTarget().setAllValues(new Point2D(this.posX, this.posY), this.color, this.rotation);
    }

    public ShapeGroup getTarget(){
        return (ShapeGroup) super.getTarget();
    }

    @Override
    public void setEditionDialog(ContextMenu contextMenu) {

    }

}
