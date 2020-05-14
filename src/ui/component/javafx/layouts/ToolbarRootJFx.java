package ui.component.javafx.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import ui.component.Layout;
import ui.Mediator;
import ui.component.javafx.area.TrashJFx;

import static ui.ApplicationI.*;

public class ToolbarRootJFx extends BorderPane implements Layout {
    private Mediator mediator;

    public ToolbarRootJFx(){
        this.setMaxHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        this.setPrefWidth(TOOLBAR_WIDTH);
        this.setStyle("-fx-background-color: lightgray");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ToolbarRoot";
    }

    public void setTrashBottom(TrashJFx component) {
        this.setBottom(component);
        BorderPane.setAlignment(component, Pos.CENTER);
        BorderPane.setMargin(component, new Insets(TOOLBAR_SPACING));
    }
}
