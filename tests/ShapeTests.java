import editor.shapes.ShapeFactory;
import editor.shapes.ShapeGroup;
import editor.shapes.Shape;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeTests {

    @Test
    public void cloneTest() {
        Shape s1 = ShapeFactory.makeHexagon();
        Shape cloneS1 = s1.clone();
        assertNotEquals(s1, cloneS1);
        assertEquals(s1.getPosition(), cloneS1.getPosition());

        Shape s2 = ShapeFactory.makeHexagon();
        Shape cloneS2 = s2.clone();
        assertNotSame(s2.getObservers(), cloneS2.getObservers());
        assertEquals(s1.getPosition(), cloneS1.getPosition());


        Shape group = new ShapeGroup();
        group.addShape(s1);
        group.addShape(s2);
        Shape groupClone = group.clone();
        assertNotEquals(group, groupClone);

        assertNotSame(group.getChildren(), groupClone.getChildren());
        assertFalse(groupClone.getChildren().contains(s1));

        Shape s3 = ShapeFactory.makeHexagon();
        groupClone.addShape(s3);
        assertFalse(group.getChildren().contains(s3));
    }


}
