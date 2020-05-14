package ui.javafx;

import ui.Mediator;
import ui.Spinner;

public abstract class SpinnerDoubleJFx extends javafx.scene.control.Spinner<Double> implements Spinner {
    public Mediator mediator;

    public SpinnerDoubleJFx(){
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}
