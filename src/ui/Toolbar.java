package ui;

import editor.Rectangle;
import editor.Shape;
import editor.utils.Color;
import editor.utils.Vec2D;

import java.util.HashSet;
import java.util.Set;

public class Toolbar {

    Set<Shape> shapes = new HashSet<>();

    Toolbar(){
        double width = (double) (ApplicationI.TOOLBAR_WIDTH / 2);
        double height = (double) (ApplicationI.TOOLBAR_WIDTH / 3);
        addShape(new Rectangle(width, height, 0,  new Vec2D((ApplicationI.TOOLBAR_WIDTH-width)/2,100), new Color(55,55,255), new Vec2D(0.,0.), 0.));
    }

    public void addShape(Shape s) {
        shapes.add(s);
    }

}
