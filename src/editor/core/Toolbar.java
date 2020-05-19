/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.core;

import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.shapes.Shape;
import editor.shapes.ShapeFactory;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {

    List<Shape> shapes = new ArrayList<>();

    public Toolbar() {
        addShape(ShapeFactory.createSimpleRectangle());
        addShape(ShapeFactory.createSimpleHexagon());
        addShape(ShapeFactory.createGroupOfGroup());
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
    }

    public boolean contains(Shape shape) {
        return this.shapes.contains(shape);
    }

    public void accept(EditorVisitor v) {
        v.visit(this);
    }

    public void checkInitialised() {
        boolean containsRectangle = false, containsPolygon = false;
        for(Shape s: shapes)
            if(s instanceof Rectangle)
                containsRectangle = true;
            else if(s instanceof Polygon)
                containsPolygon = true;
        if(!containsRectangle)
            shapes.add(0, ShapeFactory.createSimpleRectangle());
        if(!containsPolygon)
            shapes.add(1, ShapeFactory.createSimpleHexagon());
    }
}
