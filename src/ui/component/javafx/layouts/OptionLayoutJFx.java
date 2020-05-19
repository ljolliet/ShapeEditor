/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.layouts;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import ui.component.Layout;
import ui.Mediator;

import static ui.ApplicationI.*;


public class OptionLayoutJFx extends HBox implements Layout {
    private Mediator mediator;

    public OptionLayoutJFx(){
        this.setPrefHeight(OPTION_HEIGHT);
        this.setSpacing(OPTION_SPACING);
        this.setSpacing(OPTION_SPACING);
        this.setPadding(new Insets(OPTION_SPACING));
        this.setStyle("-fx-background-color: firebrick");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "OptionLayout";
    }
}
