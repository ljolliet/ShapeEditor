package editor.mediator;

import javafx.scene.Node;

public class ButtonJFx extends javafx.scene.control.Button implements Button {
    Mediator mediator;

    public ButtonJFx(String s, Node node) {
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return null;
    }
}
