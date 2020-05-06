package editor.save;

import editor.core.Editor;
import editor.core.Scene;
import editor.core.Toolbar;
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
