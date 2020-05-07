import editor.save.memento.Caretaker;
import editor.save.memento.Memento;
import editor.save.memento.TestMemento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MementoTests {

    @Test
    public void checkCareTaker() {
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
}
