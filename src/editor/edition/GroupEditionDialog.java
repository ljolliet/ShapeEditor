/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
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
                this.getPosition(),
                this.getColor(),
                this.getRotation(),
                this.getTranslation(),
                this.getRotationCenter());
    }

    public ShapeGroup getTarget(){
        return (ShapeGroup) super.getTarget();
    }

}
