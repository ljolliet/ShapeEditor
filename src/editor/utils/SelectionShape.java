/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.utils;

public interface SelectionShape {
    Point2D getPosition();
    boolean contains(Point2D p);
    void setSelectionStartPoint(Point2D p);
    void setSelectionEndPoint(Point2D p);

    void setOn(boolean b);
    boolean isOn();
}
