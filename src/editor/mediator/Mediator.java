package editor.mediator;

public interface Mediator {
    void registerComponent(Component component);
    //list of services
    void undo();
}
