package editor.save.memento;

public class TestMemento implements Memento{
    private final String state;

    public TestMemento(String state) {
        this.state = state;
    }

    @Override
    public void restore() {
    }

    @Override
    public String getState() {
        return state;
    }
}