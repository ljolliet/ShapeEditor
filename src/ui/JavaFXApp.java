package ui;

import editor.Shape;
import editor.utils.Vec2D;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXApp extends Application implements ApplicationI {

    Editor editor;
    Shape shapeDragged = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);
        editor = new Editor();
//        Stage
//        |  Scene
//        |  |  VBox (windowLayout)
//        |  |  |  HBox (optionLayout)
//        |  |  |  HBox (editorLayout)
//        |  |  |  |  VBox (toolbarBox)
//        |  |  |  |  Pane (root)
//        |  |  |  |  |  Canvas

        VBox windowLayout = new VBox();

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        optionLayout.setPrefWidth(WINDOW_WIDTH);
        optionLayout.setStyle("-fx-background-color: darkred");
        windowLayout.getChildren().add(optionLayout);


        // Editor layout
        HBox editorLayout = new HBox();
        editorLayout.setPrefHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        editorLayout.setPrefWidth(WINDOW_WIDTH);
        windowLayout.getChildren().add(editorLayout);

        // Toolbar
        VBox toolbarBox = new VBox();
        toolbarBox.setPrefHeight(WINDOW_HEIGHT);
        toolbarBox.setPrefWidth(TOOLBAR_WIDTH);
        toolbarBox.setStyle("-fx-background-color: lightgray");
        toolbarBox.setPadding(new Insets(TOOLBAR_SPACING));
        toolbarBox.setAlignment(Pos.BASELINE_CENTER);
        toolbarBox.setSpacing(TOOLBAR_SPACING);
        editorLayout.getChildren().add(toolbarBox);
        for(Shape s : editor.toolbar.shapes) {
            VBox elem = new VBox();
            elem.setOnMousePressed(mouseEvent -> selectShapeInToolbar(toolbarBox, elem, mouseEvent));
            elem.setAlignment(Pos.BASELINE_CENTER);
            s.setRendering(new JFxRendering());
            s.draw(elem);
            toolbarBox.getChildren().add(elem);
        }

        // Scene layout
        Pane root = new Pane();
        //root.setOnMouseReleased(mouseEvent -> dropShapeInScene(root, mouseEvent));
        editorLayout.getChildren().add(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(WINDOW_WIDTH - TOOLBAR_WIDTH);
        canvas.setHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        root.getChildren().add(canvas);

        Scene scene = new Scene(windowLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void selectShapeInToolbar(VBox toolbarBox, VBox elem, MouseEvent mouseEvent) {
        System.out.println("mouse pressed");
        mouseEvent.setDragDetect(true);
        for(int i = 0; i< toolbarBox.getChildren().size(); i++){
            if(toolbarBox.getChildren().get(i) == elem){
                shapeDragged = editor.toolbar.shapes.get(i);
            }
        }
    }

    private void dropShapeInScene(Pane editorLayout, MouseEvent mouseEvent) {
        System.out.println("mouse released");
        if(shapeDragged != null)
            try {
                Shape newShape = shapeDragged.clone();
                editor.scene.addShape(newShape);
                newShape.setPosition(new Vec2D(mouseEvent.getX(), mouseEvent.getY()));
                newShape.setRendering(new JFxRendering());
                newShape.draw(editorLayout);

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
    }
}
