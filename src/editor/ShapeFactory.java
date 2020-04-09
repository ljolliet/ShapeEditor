package editor;

import editor.utils.Color;
import editor.utils.Vec2D;
import ui.ApplicationI;

public class ShapeFactory {
    public static Rectangle makeRectangle(){
        return new Rectangle(ApplicationI.TOOLBAR_WIDTH / 2, ApplicationI.TOOLBAR_WIDTH / 3, 0,
                                new Vec2D(0,0),
                                new Color(55,55,255),
                                new Vec2D(0,0), 0);
    }

    public static Polygon makePolygone(int nbSide){
        return new Polygon(nbSide, ApplicationI.TOOLBAR_WIDTH / 3,  new Vec2D(0,0), new Color(255,55,55), new Vec2D(0,0), Math.PI);
    }
}
