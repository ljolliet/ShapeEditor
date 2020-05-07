package ui.javafx;

import editor.utils.Point2D;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import ui.Component;
import ui.Mediator;

public class WindowPaneJFx extends StackPane implements Component {

    private Mediator mediator;

    public WindowPaneJFx() {
        super();
        this.setOnMouseDragged(this::onMouseDragged);
        this.setOnMouseDragReleased(this::onMouseDragReleased);
    }

    public WindowPaneJFx(Node node) {
        super(node);
        this.setOnMouseDragged(this::onMouseDragged);
        this.setOnMouseDragReleased(this::onMouseDragReleased);
    }

    private void onMouseDragged(MouseEvent event) {
        mediator.moveShadowShape(new Point2D(event.getX(), event.getY()));
    }

    private void onMouseDragReleased(MouseEvent event) {
        mediator.clearShadowShape();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "windowPane";
    }
}
