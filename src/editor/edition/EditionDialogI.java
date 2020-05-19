/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.edition;

import editor.shapes.Shape;
import ui.Rendering;

public interface EditionDialogI {
    void draw(Rendering rendering);
    Shape getTarget();
    void applyEdition();
}
