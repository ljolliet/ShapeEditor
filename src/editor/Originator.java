package editor;

import java.io.IOException;

public interface Originator {
    Memento saveToMemento() throws IOException;
    void restoreFromMemento(Memento m);
}
