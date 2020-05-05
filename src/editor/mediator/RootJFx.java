package editor.mediator;

import javafx.scene.Group;

// TODO Test with a Pane
public class RootJFx extends Group implements DragAndDropArea{

    private Mediator mediator;

    @Override
    public void onClick() {

    }

    @Override
    public void onDrag() {

    }

    @Override
    public void onDrop() {

    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Root";
    }
}
