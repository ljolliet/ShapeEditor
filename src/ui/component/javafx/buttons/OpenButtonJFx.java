/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.buttons;

public class OpenButtonJFx extends ButtonJFx {

    public OpenButtonJFx() {
        super("", OPEN_RES);
    }

    @Override
    public String getName() {
        return "OpenButton";
    }

    @Override
    public void fire() {
        super.fire();
        this.mediator.open();
    }
}
