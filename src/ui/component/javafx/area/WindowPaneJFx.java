/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.area;

import editor.utils.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import ui.Mediator;
import ui.component.DragAndDropArea;

public class WindowPaneJFx extends StackPane implements DragAndDropArea {

    private Mediator mediator;

    public WindowPaneJFx() {
        super();
        this.initEvents();
    }

    public WindowPaneJFx(Node node) {
        super(node);
        this.initEvents();
    }

    private void initEvents() {
        this.setOnMouseDragged(this::onMouseDragged);
        this.setOnMouseReleased(this::onMouseReleased);
    }

    private void onMouseDragged(MouseEvent event) {
        mediator.moveShadowShape(new Point2D(event.getX(), event.getY()));
    }

    private void onMouseReleased(MouseEvent event) {
        mediator.clearShadowShape();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "WindowPane";
    }
}
