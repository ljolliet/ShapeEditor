package ui;

import editor.Editor;
import editor.Shape;
import editor.ShapeObservable;
import editor.edition.EditionDialogI;
import editor.utils.Point2D;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
        Scene scene = new Scene(windowLayout);
        primaryStage.setScene(scene);

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        //optionLayout.setPrefWidth(WINDOW_WIDTH);
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

        undoIm.setOnMouseClicked(mouseEvent -> editor.undo());
        redoIm.setOnMouseClicked(mouseEvent -> editor.redo());

        // Editor layout
        HBox editorLayout = new HBox();
        //editorLayout.setPrefHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
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
        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
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

        //drop in trash
        borderPane.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                editor.removeShapeFromToolbar(editor.getShapeDragged());
            }
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
                    editor.addShapeToScene((ShapeObservable) newShape);
                    editor.setShapeDragged(null);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            event.consume();
        });

        /* scene to scene drag and drop */

        this.root.setOnMousePressed(event -> {
            rendering.hideEditionDialog();
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("mouse pressed");
                boolean inShape = false;
                //click on an existing shape
                for (Shape s : editor.getScene().getShapes()) {
                    if (s.contains(new Point2D(event.getX(), event.getY()))) {
                        System.out.println("found: " + s);
                        root.setCursor(Cursor.CLOSED_HAND);
                        editor.setShapeDragged(s);
                        inShape = true;
                        break;
                    }
                }
                // selecte shapes
                if(!inShape){
                    editor.getSelectionShape().setSelectionStartPoint(new Point2D(event.getX(), event.getY()));
                }
            }
            event.consume();
        });

        this.root.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (editor.getShapeDragged() != null) {
                    editor.getShapeDragged().setPosition(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
                } else {
                    editor.getSelectionShape().setSelectionEndPoint(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
                    rendering.drawSelectionFrame();
                }
            }
        });

        this.root.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                System.out.println("mouse released");
                rendering.drawEditor();
                ArrayList<ShapeObservable> selectedShapes = new ArrayList<>();
                if (editor.getSelectionShape() != null)
                    for (ShapeObservable s : editor.getScene().getShapes())
                        if (s.contained(editor.getSelectionShape())) {
                            selectedShapes.add(s);
                            System.out.println(s + " selected");
                        }
                editor.getScene().setSelectedShapes(selectedShapes);

                root.setCursor(Cursor.DEFAULT);
                editor.setShapeDragged(null);
                rendering.drawEditor();
            }
            mouseEvent.consume();
        });

        this.root.setOnMouseClicked(e -> {
            rendering.hideEditionDialog();
            if (e.getButton() == MouseButton.SECONDARY) {
                if(editor.getScene().getSelectedShapes().size() != 0){
                    System.out.println("right click on selection");
                }
                else
                    for(Shape s : editor.getScene().getShapes()){
                        if(s.contains(new Point2D(e.getX(), e.getY()))) {
                            System.out.println("right click on shape");
                            EditionDialogI ed = s.createEditionDialog();
                            ed.setPosition(new Point2D(e.getScreenX(), e.getScreenY()));
                            ed.draw(rendering);
                        }
                    }
            }
        });
//          TODO : to group
//        MenuItem groupShape = new MenuItem("Group");
//        groupShape.setOnAction(e -> {
//            ShapeObservable group = new ShapeGroup();
//            for(ShapeObservable s : editor.getScene().getSelectedShapes()){
//                group.addShape(s);
//                editor.removeShapeToScene(s);
//            }
//            editor.addShapeInScene(group);
//        });

        // Set rendering
        this.editor = new Editor();
        rendering = new JFxRendering(this.editor, toolbarBox, root);
        editor.setRendering(rendering);

        // Draw editor
        rendering.drawEditor();

        primaryStage.show();
    }
}
