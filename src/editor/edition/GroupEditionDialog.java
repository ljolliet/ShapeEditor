package editor.edition;

import editor.shapes.ShapeGroup;
import ui.Rendering;

public class GroupEditionDialog extends ShapeEditionDialog {

    public GroupEditionDialog(ShapeGroup shapeGroup) {
        super(shapeGroup);
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
    }

    @Override
    public void applyEdition() {
        this.getTarget().setAllValues(
                super.getPosition(),
                super.getColor(),
                super.getRotation(),
                super.getTranslation(),
                super.getRotationCenter());
    }

    public ShapeGroup getTarget(){
        return (ShapeGroup) super.getTarget();
    }

}
