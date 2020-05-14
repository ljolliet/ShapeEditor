package ui.component;

import ui.Mediator;

public interface Component {
    void setMediator(Mediator mediator);
    String getName();
}
