package ui;

import editor.Editor;
import editor.Shape;
import editor.ShapeObservable;
import editor.utils.Point2D;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

public class JavaFXApp extends Application implements ApplicationI {

    private Editor editor;
    private Rendering rendering;

    private Group root;
    private VBox toolbarBox;

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
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    System.out.println("save file : " + file.getName());
                    //TODO save file method
                }
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

        //toolbar to scene drag and drop
        toolbarBox.setOnDragDetected(event -> {
            System.out.println("drag detected");
            toolbarBox.startFullDrag();

            if (event.getButton() == MouseButton.PRIMARY) {
                int i = 0;
                for (Node node : toolbarBox.getChildren()) {
                    if (event.getPickResult().getIntersectedNode() == node) {
                        editor.setShapeDragged(editor.getToolbar().getShapes().get(i));
                        break;
                    }
                    i++;
                }
            }

            event.consume();
        });

        toolbarBox.setOnMouseDragged(event -> {
            //TODO display shape
            event.consume();
        });

        root.setOnMouseDragReleased(event -> {
            System.out.println("drag released");

            // create a shape
            if (editor.getShapeDragged() != null)
                try {
                    Shape newShape = editor.getShapeDragged().clone();
                    newShape.setPosition(new Point2D(event.getX(), event.getY()));
                    editor.addShapeInScene((ShapeObservable) newShape);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            event.consume();
        });

        /* scene to scene drag and drop */

        this.root.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                boolean inShape = false;
                //click on an existing shape
                for (Shape s : editor.getScene().getShapes()) {
                    if (s.contains(new Point2D(event.getX(), event.getY()))) {
                        System.out.println("found: " + s);
                        editor.setShapeDragged(s);
                        inShape = true;
                        break;
                    }
                }
                // selecte shapes
                if(!inShape){
                    editor.setSelectionStartPoint(new Point2D(event.getX(), event.getY()));
                }
            }
            event.consume();
        });

        this.root.setOnMouseDragged(mouseEvent -> {
            if(editor.getShapeDragged() != null) {
                editor.getShapeDragged().setPosition(new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            }
            else{
                editor.setSelectionEndPoint(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
                rendering.drawSelectionFrame();
            }
        });

        this.root.setOnMouseReleased(mouseEvent -> {
            editor.setShapeDragged(null);
            rendering.drawEditor();
        });

        //Contextual menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit_shape = new MenuItem("Edit");
        edit_shape.setOnAction(e -> System.out.println("Edit Shape"));
        MenuItem Delete = new MenuItem("Delete");
        Delete.setOnAction(e -> System.out.println("Delate Shape"));
        contextMenu.getItems().addAll(edit_shape, Delete);

        this.root.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                for(Shape s : editor.getScene().getShapes()){
                    if(s.contains(new Point2D(e.getX(), e.getY()))) {
                        System.out.println("right click on shape");
                        contextMenu.show(this.root, e.getScreenX(), e.getScreenY());
                    }
                }
            } else {
                contextMenu.hide();
            }
        });

        // Set rendering
        this.editor = new Editor();
        rendering = new JFxRendering(this.editor, toolbarBox, root);
        editor.setRendering(rendering);

        // Draw editor
        rendering.drawEditor();

        primaryStage.show();
    }
}
