package editor.edition;

import editor.shapes.ShapeGroup;
import editor.utils.Point2D;
import javafx.scene.control.ContextMenu;
import ui.Rendering;

public class GroupEditionDialog extends ShapeEditionDialog {

    public GroupEditionDialog(ShapeGroup shapeGroup) {
        super(shapeGroup);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.drawEditionDialog(this, this.getPosition());
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