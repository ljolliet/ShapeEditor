package editor.shapes;

import editor.utils.Color;
import editor.utils.Point2D;

public class ShapeFactory {
    public static Rectangle makeRectangle(){
        return new Rectangle(100, 50, 0,
                                new Point2D(0,0),
                                new Color(55,255,55, 1.),
                                new Point2D(0,0), 0);
    }

    public static Polygon makePolygon(int nbSide){
        return new Polygon(nbSide, 50,
                new Point2D(0,0),
                new Color(255,55,55, 1.),
                new Point2D(0,0), 90);
    }

    public static Polygon makeHexagon() {
        return makePolygon(6);
    }
}
