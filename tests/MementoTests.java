import editor.Editor;
import editor.ShapeFactory;
import editor.ShapeObservable;
import editor.ShapeObserver;
import editor.save.Caretaker;
import editor.save.Memento;
import editor.save.TestMemento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MementoTests {

    @Test
    public void checkCareTaker(){
        Caretaker c = new Caretaker(3);
        Memento m1 = new TestMemento("State #1");
        Memento m2 = new TestMemento("State #2");
        Memento m3 = new TestMemento("State #3");
        Memento m4 = new TestMemento("State #4");
        c.push(m1);
        assertEquals(c.getCurrentMemento(), m1);
        c.undo();// do nothing
        assertEquals(c.getCurrentMemento(), m1);
        c.push(m2);
        assertEquals(2, c.getHistorySize(),0);
        assertEquals(c.getCurrentMemento(), m2);
        c.undo();
        assertEquals(2, c.getHistorySize(),0);
        assertEquals(c.getCurrentMemento(), m1);
        c.redo();
        assertEquals(2, c.getHistorySize(),0);
        assertEquals(c.getCurrentMemento(), m2);
        c.undo();
        c.push(m3);// m3 overwrite m2
        assertEquals(2, c.getHistorySize(),0);
        assertEquals(c.getCurrentMemento(), m3);
        c.redo();// do nothing
        assertEquals(c.getCurrentMemento(), m3);
        c.push(m2);
        c.push(m4); //m1 deleted
        assertEquals(3, c.getHistorySize(),0);
    }
    @Test
    public void checkMementoState(){
        Editor e = Editor.getInstance();
        ShapeObservable s1 = ShapeFactory.makePolygone(5);
        s1.addObserver(new ShapeObserver());
        e.addShapeToScene(s1);
        //State #1
        Memento m1 = e.saveToMemento();
        s1.setRotation(180);
        //State #2
        Memento m2 = e.saveToMemento();
        Memento currentM = e.getHistory().getCurrentMemento();
        //Check State
        assertEquals(m2,currentM);

        //Undo -> State #1
        e.undo();
        currentM = e.getHistory().getCurrentMemento();
        assertEquals(m1, currentM);
        //Redo -> State #2
        e.redo();
        currentM = e.getHistory().getCurrentMemento();
        assertEquals(m2, currentM);

    }
}
