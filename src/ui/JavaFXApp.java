package ui;

import editor.Editor;
import editor.Shape;
import editor.utils.Vec2D;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.Inet4Address;
import java.util.*;

public class JavaFXApp extends Application implements ApplicationI {

    private Rendering rendering;
    private Editor editor;

    private Group root;
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
        |  |  |  |  BorderPane (toolbarRoot)
        |  |  |  |  |   VBox (toolbarBox)
        |  |  |  |  Group (root)
        |  |  |  |  |  Canvas
*/

        VBox windowLayout = new VBox();
        Scene scene = new Scene(windowLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        optionLayout.setPrefWidth(WINDOW_WIDTH);
        optionLayout.setSpacing(OPTION_SPACING);
        optionLayout.setPadding(new Insets(OPTION_SPACING));
        optionLayout.setStyle("-fx-background-color: darkred");
        windowLayout.getChildren().add(optionLayout);

        //TODO refacto
        final double scale = (double) OPTION_HEIGHT/2;
        ImageView saveIm = new ImageView( new Image(getClass().getClassLoader().getResource("save.png").toString()));
        ImageView openIm = new ImageView( new Image(getClass().getClassLoader().getResource("open.png").toString()));
        ImageView undoIm = new ImageView( new Image(getClass().getClassLoader().getResource("undo.png").toString()));
        ImageView redoIm = new ImageView( new Image(getClass().getClassLoader().getResource("redo.png").toString()));
        ImageView[] optionsIm = new ImageView[] {saveIm, openIm, undoIm, redoIm};
        List<ImageView> options = new ArrayList<>(Arrays.asList(optionsIm));
        for(ImageView iv : options){
            iv.setPreserveRatio(true);
            iv.setFitHeight(scale);
            optionLayout.getChildren().add(iv);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")); //TODO set extension once decided
        openIm.setOnMouseClicked(mouseEvent -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                System.out.println("open file : " + file.getName());
                //TODO open file method
            }
        });

        saveIm.setOnMouseClicked(mouseEvent -> {
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                System.out.println("save file : " + file.getName());
                //TODO save file method
            }
        });


        // Editor layout
        HBox editorLayout = new HBox();
        editorLayout.setPrefHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        editorLayout.setPrefWidth(WINDOW_WIDTH);
        windowLayout.getChildren().add(editorLayout);

        //Toolbar root
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefHeight(WINDOW_HEIGHT);
        borderPane.setPrefWidth(TOOLBAR_WIDTH);
        borderPane.setStyle("-fx-background-color: lightgray");
        // Toolbar
        this.toolbarBox = new VBox();
        toolbarBox.setPadding(new Insets(TOOLBAR_SPACING));
        toolbarBox.setAlignment(Pos.BASELINE_CENTER);
        toolbarBox.setSpacing(TOOLBAR_SPACING);
        borderPane.setTop(toolbarBox);

        //trash
        //TODO refacto
        ImageView trashim = new ImageView( new Image(getClass().getClassLoader().getResource("trash.png").toString()));
        trashim.setPreserveRatio(true);
        trashim.setFitHeight(TRASH_HEIGHT);
        borderPane.setBottom(trashim);
        BorderPane.setAlignment(trashim, Pos.CENTER);
        BorderPane.setMargin(trashim, new Insets(TOOLBAR_SPACING));
        editorLayout.getChildren().add(borderPane);

        // Scene layout
        this.root = new Group();
        editorLayout.getChildren().add(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(WINDOW_WIDTH - TOOLBAR_WIDTH);
        canvas.setHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        root.getChildren().add(canvas);

        this.root.setOnMouseClicked(event -> {
            System.out.println("click scene");
            System.out.println(event.getX() + " - " + event.getY());
            boolean dragging = true; //TODO remove once drag and drop implemented
            //click on an existing shape
            for (Shape s : editor.getScene().getShapes()) {
                if (s.contains(new Vec2D(event.getX(), event.getY()))) {
                    System.out.println("found: " + s);
                    dragging = false;
                    break;
                }
            }

            // create a shape
            if(dragging && shapeDragged != null)
                try {
                    Shape newShape = shapeDragged.clone();
                    newShape.setPosition(new Vec2D(event.getX(), event.getY()));
                    editor.getScene().addShape(newShape);
                    editor.draw();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            event.consume();
        });

        this.toolbarBox.setOnMouseClicked(event -> {
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
        });


        // Set rendering
        this.rendering = new JFxRendering(toolbarBox, root);
        this.editor = new Editor(this.rendering);

        // Draw editor
        editor.draw();

        primaryStage.show();
    }

    /*  Drag and drop try   */
//    this.toolbarBox.setOnDragDone(event -> {
//        System.out.println("mouse released");
//        System.out.println(event.getX() + " - " + event.getY());
//        if(shapeDragged != null)
//            try {
//                Shape newShape = shapeDragged.clone();
//                newShape.setPosition(new Vec2D(event.getX(), event.getY()));
//                editor.getScene().addShape(newShape);
//                editor.draw(rendering);
//            } catch (CloneNotSupportedException e) {
//                e.printStackTrace();
//            }
//        event.consume();
//    });
//    root.setOnDragOver(event -> {
//            /* data is dragged over the target */
//            System.out.println("onDragOver");
//
//            /*
//             * accept it only if it is not dragged from the same node and if it
//             * has a string data
//             */
//            if (event.getGestureSource() != root && event.getDragboard().hasString()) {
//                /* allow for both copying and moving, whatever user chooses */
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//
//            event.consume();
//        });
//
//
//            root.setOnDragDropped(event -> {
//            /* data dropped */
//            System.out.println("onDragDropped");
//            /* if there is a string data on dragboard, read it and use it */
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            if (db.hasString()) {
//                //root.setText(db.getString());
//                success = true;
//            }
//            /*
//             * let the source know whether the string was successfully
//             * transferred and used
//             */
//            event.setDropCompleted(success);
//
//            event.consume();
//        });
//
//
//            this.toolbarBox.setOnDragDetected(event -> {
//            System.out.println("click toolbar");
//            int i = 0;
//            for (Node node: toolbarBox.getChildren()) {
//                System.out.println(node);
//                if (event.getPickResult().getIntersectedNode() == node) {
//                    shapeDragged = editor.getToolbar().getShapes().get(i);
//                    System.out.println("found: " + shapeDragged);
//                    break;
//                }
//                i++;
//            }
//            /* allow any transfer mode */
//            Dragboard db = toolbarBox.startDragAndDrop(TransferMode.ANY);
//
//            /* put a string on dragboard */
//            ClipboardContent content = new ClipboardContent();
//            content.putString(toolbarBox.toString());
//            db.setContent(content);
//
//            event.consume();
//        });

}
