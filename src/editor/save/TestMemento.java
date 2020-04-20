package editor.save;

public class TestMemento implements Memento{
    private final String state;

    public TestMemento(String state) {
        this.state = state;
    }

    @Override
    public void restore() {
    }
}
