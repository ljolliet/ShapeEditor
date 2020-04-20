import editor.save.Caretaker;
import editor.save.Memento;
import editor.save.TestMemento;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MementoTests {

    @Test
    public void checkCareTaker() {
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
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m2);
        c.undo();
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m1);
        c.redo();
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m2);
        c.undo();
        c.push(m3);// m3 overwrite m2
        assertEquals(2, c.getHistorySize(), 0);
        assertEquals(c.getCurrentMemento(), m3);
        c.redo();// do nothing
        assertEquals(c.getCurrentMemento(), m3);
        c.push(m2);
        c.push(m4); //m1 deleted
        assertEquals(3, c.getHistorySize(), 0);
    }
}
