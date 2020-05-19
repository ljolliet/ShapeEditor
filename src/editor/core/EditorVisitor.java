/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.core;

import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.shapes.ShapeGroup;

public interface EditorVisitor {
    void visit(Editor editor);
    void visit(Toolbar toolbar);
    void visit(Scene scene);
    void visit(ShapeGroup g);
    void visit(Rectangle r);
    void visit(Polygon p);
}
