package ui.component.javafx.area;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ui.ApplicationI;
import ui.component.Component;
import ui.Mediator;

public class TrashJFx extends ImageView implements Component {

    private final String IMAGE_URL = "trash.png";
    private Mediator mediator;

    public TrashJFx() {
        super();
        this.setImage(new Image(getClass().getClassLoader().getResource(IMAGE_URL).toString()));
        this.setPreserveRatio(true);
        this.setFitHeight(ApplicationI.TRASH_HEIGHT);
        this.setOnMouseDragReleased(this::onMouseDragReleased);
    }

    private void onMouseDragReleased(MouseEvent event) {
        // Detect right click
        if (event.getButton() == MouseButton.PRIMARY)
            mediator.dropInTrash();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Trash";
    }
}
