/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
import editor.core.Editor;
import editor.save.io.ExportManager;
import editor.save.io.ImportManager;
import editor.save.memento.Caretaker;
import editor.save.memento.Memento;
import editor.shapes.*;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IOTests {

    @Test
    public void caretakerTest() {
        Caretaker c = new Caretaker(3);
        Memento m1 = new TestMemento("State #1");
        Memento m2 = new TestMemento("State #2");
        Memento m3 = new TestMemento("State #3");
        Memento m4 = new TestMemento("State #4");
        //check push
        c.push(m1);
        assertEquals(c.getCurrentMemento(), m1);
        //check undo when there is only a unique shape
        c.undo();// do nothing
        assertEquals(c.getCurrentMemento(), m1);
        //check push
        c.push(m2);
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m2);
        //check undo doesn't change size and set the current save
        c.undo();
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m1);
        //check redo
        c.redo();
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m2);
        //check undo + push (instead of redo)
        c.undo();
        c.push(m3);// m3 overwrite m2
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m3);
        assertFalse(c.contains(m2));//m2 overwritten
        //check redo when there is no more recent save
        c.redo();// do nothing
        assertEquals(c.getCurrentMemento(), m3);
        //check push to the limite size
        c.push(m2);
        c.push(m4); //m1 deleted
        assertEquals(3, c.getHistorySize(), 0);
    }

    @Test
    public void jsonSceneSaveTest(){
        Editor e = Editor.getInstance();
        File f = new File(".test.json");
        //Scene
        e.getScene().addShape(ShapeFactory.createSimpleHexagon());
        List<Shape> shapesBeforeSave = e.getScene().getShapes();
        e.saveScene(f);
        e.getScene().setShapes(new ArrayList<>()); // erase data in scene
        assertEquals(0, e.getScene().getShapes().size());
        e.restoreScene(f);
        assertEquals(e.getScene().getShapes().size(), shapesBeforeSave.size());

        // cannot check if shapes are equals because save/load change the identity
        Shape shapeBefore = shapesBeforeSave.get(0);
        Shape shapeAfter = e.getScene().getShapes().get(0);
        assert shapeAfter instanceof Polygon;
        assert shapeBefore instanceof Polygon;
        assertEquals(shapeAfter.getPosition(), shapeBefore.getPosition());
        assertEquals(shapeAfter.getColor(), shapeBefore.getColor());
        assertEquals(shapeAfter.getRotation(), shapeBefore.getRotation(),0.);
        assertEquals(shapeAfter.getRotationCenter(), shapeBefore.getRotationCenter());
        assertEquals(shapeAfter.getTranslation(), shapeBefore.getTranslation());
        assertEquals(((Polygon)shapeAfter).getNbSides(), ((Polygon)shapeBefore).getNbSides());
        assertEquals(((Polygon)shapeAfter).getSideLength(), ((Polygon)shapeBefore).getSideLength(),0.);
        f.delete();
    }

    @Test
    public void jsonToolbarSaveTest(){
        Editor e = Editor.getInstance();
        File f = new File(".test.json");
        //toolbar
        e.getToolbar().addShape(ShapeFactory.createSimpleRectangle());
        List<Shape> toolbarBefore = e.getToolbar().getShapes();
        //save
        ExportManager exportManager = e.getExportVisitor();
        exportManager.saveToolbar(f);
        e.getToolbar().setShapes(new ArrayList<>()); // erase data in toolbar
        assertEquals(0, e.getScene().getShapes().size());
        //load
        ImportManager importManager = e.getImportManager();
        importManager.restore(f);
        //there is already shapes in the toolbar
        assertEquals(e.getToolbar().getShapes().size(), toolbarBefore.size());

        // cannot check if shapes are equals because save/load change the identity
        int lastShape = toolbarBefore.size()-1;
        Shape shapeBefore = toolbarBefore.get(lastShape);
        Shape shapeAfter =  e.getToolbar().getShapes().get(lastShape);
        assert shapeAfter instanceof Rectangle;
        assert shapeBefore instanceof Rectangle;
        assertEquals(shapeAfter.getPosition(), shapeBefore.getPosition());
        assertEquals(shapeAfter.getColor(), shapeBefore.getColor());
        assertEquals(shapeAfter.getRotation(), shapeBefore.getRotation(),0.);
        assertEquals(shapeAfter.getRotationCenter(), shapeBefore.getRotationCenter());
        assertEquals(shapeAfter.getTranslation(), shapeBefore.getTranslation());
        assertEquals(((Rectangle)shapeAfter).getBorderRadius(), ((Rectangle)shapeBefore).getBorderRadius());
        assertEquals(((Rectangle)shapeAfter).getWidth(), ((Rectangle)shapeBefore).getWidth(),0.);
        assertEquals(((Rectangle)shapeAfter).getHeight(), ((Rectangle)shapeBefore).getHeight(),0.);

        f.delete();
    }

    @Test
    public void checkEmptyToolbarBehavior(){
        //if toolbar empty on load, a polygon and a rectangle are added
        Editor e = Editor.getInstance();
        File f = new File(".test.json");
        // erase data in toolbar
        e.getToolbar().setShapes(new ArrayList<>());
        assertEquals(0, e.getToolbar().getShapes().size());
        //save
        ExportManager exportManager = e.getExportVisitor();
        exportManager.saveToolbar(f);
        //load
        ImportManager importManager = e.getImportManager();
        importManager.restore(f);
        assertEquals(0, e.getToolbar().getShapes().size()); //list still empty
        e.getToolbar().checkInitialised();
        assertEquals(2, e.getToolbar().getShapes().size());
        boolean containsPolygon = false, containsRectangle = false;
        for(Shape s : e.getToolbar().getShapes()) {
            if (s instanceof Polygon)
                containsPolygon = true;
            else if (s instanceof Rectangle)
                containsRectangle = true;
        }
        assertTrue(containsPolygon);
        assertTrue(containsRectangle);

        f.delete();
    }

}
