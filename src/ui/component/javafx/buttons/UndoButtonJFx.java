/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.buttons;

public class UndoButtonJFx extends ButtonJFx{

    public UndoButtonJFx() {
        super("", UNDO_RES);
    }

    @Override
    public String getName() {
        return "UndoButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.undo();
    }

}
