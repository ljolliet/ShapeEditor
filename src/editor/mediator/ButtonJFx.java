package editor.mediator;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static ui.ApplicationI.OPTION_HEIGHT;


public abstract class ButtonJFx extends javafx.scene.control.Button implements Button {
    Mediator mediator;

    public ButtonJFx(String s, String imageSrc) {
        super(s);
        ImageView im = new ImageView(new Image(getClass().getClassLoader().getResource(imageSrc).toString()));
        im.setPreserveRatio(true);
        im.setFitHeight((double) OPTION_HEIGHT/2);
        this.setGraphic(im);
        this.setStyle("-fx-background-color: darkred");
    }

    public ButtonJFx(String s, Node node) {
        super(s, node);
    }

    public ButtonJFx(String s) {
        super(s);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }
}
