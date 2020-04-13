import editor.Polygon;
import editor.Rectangle;
import editor.Shape;
import editor.ShapeGroup;
import org.junit.Test;
import editor.utils.Color;
import editor.utils.Point2D;

import static org.junit.Assert.*;

public class ShapeTests {

    public Polygon createPolygon(){
        return new Polygon(5, 2., new Point2D(0.,0.), new Color(255,255,255), new Point2D(0.,0.), 0.);
    }

    public Rectangle createRectangle(){
        return new Rectangle(5., 10., 0,  new Point2D(0.,0.), new Color(255,255,255), new Point2D(0.,0.), 0.);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Shape s1 = createPolygon();
        Shape s2 = createRectangle();
        Shape group = new ShapeGroup();
        group.addShape(s1);
        group.addShape(s2);
        Shape groupClone = group.clone();
        groupClone.addShape(createPolygon());
        assertNotEquals(s1, s1.clone());
        assertNotEquals(group, groupClone);
        // assertNotEquals(group.getChild(), groupClone.getChild()); TODO : child are the same and should not be
    }

}
