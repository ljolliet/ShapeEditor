package ui.javafx;

import ui.Mediator;
import ui.Spinner;

public abstract class SpinnerIntegerJFx extends javafx.scene.control.Spinner<Integer> implements Spinner {
    public Mediator mediator;

    public SpinnerIntegerJFx(){
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}
