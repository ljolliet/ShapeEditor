package ui;

import editor.*;
import editor.utils.Color;
import editor.utils.Vec2D;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFXApp extends Application implements ApplicationI {

    private final Editor editor = new Editor();

    private Pane root;
    private VBox toolbarBox;
    Shape shapeDragged = null;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);

/*
        Stage
        |  Scene
        |  |  VBox (windowLayout)
        |  |  |  HBox (optionLayout)
        |  |  |  HBox (editorLayout)
        |  |  |  |  VBox (toolbarBox)
        |  |  |  |  Pane (root)
        |  |  |  |  |  Canvas
*/

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
        this.toolbarBox = new VBox();
        toolbarBox.setPrefHeight(WINDOW_HEIGHT);
        toolbarBox.setPrefWidth(TOOLBAR_WIDTH);
        toolbarBox.setStyle("-fx-background-color: lightgray");
        toolbarBox.setPadding(new Insets(TOOLBAR_SPACING));
        toolbarBox.setAlignment(Pos.BASELINE_CENTER);
        toolbarBox.setSpacing(TOOLBAR_SPACING);
        for(Shape s : editor.getToolbar().getShapes()) {
            s.setRendering(new JFxRendering());
            s.draw(toolbarBox);
        }

        // Scene layout
        this.root = new Pane();

        editorLayout.getChildren().add(toolbarBox);
        editorLayout.getChildren().add(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(WINDOW_WIDTH - TOOLBAR_WIDTH);
        canvas.setHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        root.getChildren().add(canvas);



        this.toolbarBox.setOnDragDone(event -> {
            System.out.println("mouse released");
            System.out.println(event.getX() + " - " + event.getY());
            if(shapeDragged != null)
                try {
                    Shape newShape = shapeDragged.clone();
                    newShape.setPosition(new Vec2D(event.getX(), event.getY()));
                    editor.getScene().addShape(newShape);
                    newShape.draw(root);

                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            event.consume();
        });

        root.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /*
             * accept it only if it is not dragged from the same node and if it
             * has a string data
             */
            if (event.getGestureSource() != root && event.getDragboard().hasString()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });


        root.setOnDragDropped(event -> {
            /* data dropped */
            System.out.println("onDragDropped");
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                //root.setText(db.getString());
                success = true;
            }
            /*
             * let the source know whether the string was successfully
             * transferred and used
             */
            event.setDropCompleted(success);

            event.consume();
        });


        this.toolbarBox.setOnDragDetected(event -> {
            System.out.println("click toolbar");
            int i = 0;
            for (Node node: toolbarBox.getChildren()) {
                System.out.println(node);
                if (event.getPickResult().getIntersectedNode() == node) {
                    shapeDragged = editor.getToolbar().getShapes().get(i);
                    System.out.println("found: " + shapeDragged);
                    break;
                }
                i++;
            }
            /* allow any transfer mode */
            Dragboard db = toolbarBox.startDragAndDrop(TransferMode.ANY);

            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            content.putString(toolbarBox.toString());
            db.setContent(content);

            event.consume();
        });



        Scene scene = new Scene(windowLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
