import editor.ShapeFactory;
import editor.ShapeGroup;
import editor.ShapeI;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class ShapeTests {

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        ShapeI s1 = ShapeFactory.makePolygone(5);
        ShapeI s2 = ShapeFactory.makeRectangle();
        ShapeI group = new ShapeGroup();
        group.addShape(s1);
        group.addShape(s2);
        ShapeI groupClone = group.clone();
        groupClone.addShape(ShapeFactory.makePolygone(5));
        assertNotEquals(s1, s1.clone());
        assertNotEquals(group, groupClone);
        assertNotEquals(group.getChild(), groupClone.getChild());
    }

}
