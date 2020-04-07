package ui;

import editor.Polygon;
import editor.Rectangle;

public interface Rendering {
    void draw(Object context, Rectangle rectangle);
    void draw(Object context, Polygon rectangle);
}
