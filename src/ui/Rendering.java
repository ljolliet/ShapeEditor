package ui;

import editor.Polygon;
import editor.Rectangle;

public interface Rendering {

    void init();

    void drawInScene(Rectangle rectangle);
    void drawInScene(Polygon polygon);

    void drawInToolbar(Rectangle rectangle);
    void drawInToolbar(Polygon polygon);

}
