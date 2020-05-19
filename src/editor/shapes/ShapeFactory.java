/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.shapes;

import editor.utils.Color;
import editor.utils.Point2D;

public class ShapeFactory {
    public static Rectangle createSimpleRectangle(){
        return new Rectangle(100, 50, 0,
                                new Point2D(0,0),
                                new Color(55,255,55, 1.),
                                new Point2D(0,0), 0);
    }

    public static Polygon createSimplePolygon(int nbSide){
        return new Polygon(nbSide, 50,
                new Point2D(0,0),
                new Color(255,55,55, 1.),
                new Point2D(0,0), 90);
    }

    public static Polygon createSimpleHexagon() {
        return createSimplePolygon(6);
    }

    public static Shape createGroupOfGroup() {
        Shape group = new ShapeGroup();
        Shape group2 = new ShapeGroup();
        Shape rec1 = new Rectangle(100, 50, 20,  new Point2D(100,100),
                                    new Color(55,55,255, 1.), new Point2D(50,25), 0);
        Shape rec2 = new Rectangle(100, 50, 20,  new Point2D(200,200),
                                    new Color(55,255,55, 1.), new Point2D(50,25), 0);
        Shape pol1 = new Polygon(6, 50,  new Point2D(100,0),
                                    new Color(255,55,55, 1.), new Point2D(0,0), 90);
        group.addShape(rec1);
        group.addShape(rec2);
        group2.addShape(pol1);
        group2.addShape(group);

        return group2;
    }
}
