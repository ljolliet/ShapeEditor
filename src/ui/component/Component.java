/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component;

import ui.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
