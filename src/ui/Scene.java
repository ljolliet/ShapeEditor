package ui;

import editor.Shape;

import java.util.HashSet;
import java.util.Set;

public class Scene {

    Set<Shape> shapes = new HashSet<>();

    public void addShape(Shape s) {
        shapes.add(s);
    }

}
