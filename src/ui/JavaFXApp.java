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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaFXApp extends Application implements ApplicationI {

    private Rendering rendering;
    private Editor editor;

    private Group root;
    private VBox toolbarBox;
    private BorderPane borderPane;
    private ImageView trashImage;

    // Temporary
    private Rectangle rec;
    private Group grp;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);

        this.editor = Editor.getInstance();

/*
        Stage
        |  Scene
        |  |  StackPane
        |  |  |  VBox (windowLayout)
        |  |  |  |  HBox (optionLayout)
        |  |  |  |  HBox (editorLayout)
        |  |  |  |  |  BorderPane (toolbarRoot)
        |  |  |  |  |  |   VBox (toolbarBox)
        |  |  |  |  |  |   ImageView (trashIm)
        |  |  |  |  |  Group (root)
        |  |  |  |  |  |  Canvas
        |  |  |  Group
        |  |  |  |  Canvas
*/

        VBox windowLayout = new VBox();
        StackPane stackPane = new StackPane(windowLayout);
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);

        // Temporary
        // Shadow shape when drag & drop
        rec = new Rectangle(100,100);
        // Create a group to move the shadow shape inside
        grp = new Group(new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT));
        // The group is completely transparent to mouse events
        grp.setMouseTransparent(true);
        stackPane.getChildren().add(grp);

        windowLayout.toFront();

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
        //editorLayout.setPrefWidth(WINDOW_WIDTH);
        windowLayout.getChildren().add(editorLayout);

        //Toolbar root
        this.borderPane = new BorderPane();
        //borderPane.setPrefHeight(WINDOW_HEIGHT);
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
        this.trashImage = new ImageView(new Image(getClass().getClassLoader().getResource("trash.png").toString()));
        trashImage.setPreserveRatio(true);
        trashImage.setFitHeight(TRASH_HEIGHT);
        borderPane.setBottom(trashImage);
        BorderPane.setAlignment(trashImage, Pos.CENTER);
        BorderPane.setMargin(trashImage, new Insets(TOOLBAR_SPACING));
        editorLayout.getChildren().add(borderPane);

        // Scene layout
        this.root = new Group();
        editorLayout.getChildren().add(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
        root.getChildren().add(canvas);

        /* Set events */
        setToolbarEvents();
        setSceneEvents();

        // Event on stack pane
        stackPane.setOnMouseDragged(event -> {
            //System.out.println("x = " + event.getX() + " ; y = " + event.getY());
            rec.setX(event.getX());
            rec.setY(event.getY());
        });

        stackPane.setOnMouseClicked(event -> {
            System.out.println("coucou");
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
        rendering = new JFxRendering(toolbarBox, root);
        editor.setRendering(rendering);

        // Draw editor
        rendering.drawEditor();

        primaryStage.show();
    }

    private void setToolbarEvents() {
        toolbarBox.setOnDragDetected(event -> {
            System.out.println("[TOOLBAR] Drag detected");
            // Start drag event
            toolbarBox.startFullDrag();

            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {
                int i = 0;
                // Search for shape selected
                for (Node node : toolbarBox.getChildren()) {
                    if (event.getPickResult().getIntersectedNode() == node) {
                        editor.setShapeDragged(editor.getToolbar().getShapes().get(i)); // Found
                        break;
                    }
                    i++;
                }
            }
        });
        // Empty
        toolbarBox.setOnMouseDragged(event -> {
            //TODO display shape
        });
        // Empty
        toolbarBox.setOnMouseDragReleased(event -> {
            System.out.println("[TOOLBAR] Drag released");
        });

        borderPane.setOnMouseDragReleased(event -> {
            System.out.println("[BORDERPANE] Drag released");
        });

        borderPane.setOnMouseReleased(mouseEvent -> {
            System.out.println("[BORDERPANE] Mouse released");

            // Detect left click
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                // If release over trash --> deleted selected shape
                if (mouseEvent.getPickResult().getIntersectedNode() == this.trashImage) {
                    editor.removeShapeFromToolbar(editor.getShapeDragged());
                    editor.removeShapeFromScene((ShapeObservable) editor.getShapeDragged());
                }
            }
        });
    }

    private void setSceneEvents() {
        // Empty
        root.setOnDragDetected(event -> {
            root.startFullDrag();
            System.out.println("[ROOT] Drag detected");
        });

        root.setOnMouseDragged(event -> {
            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {

                // If there is a shape to drag
                if (editor.getShapeDragged() != null) {

                    // If mouse is outside the scene
                    if (!grp.getChildren().contains(rec) && (event.getX() < 0 || event.getY() < 0)) {
                        // Create shadow shape for dropping in toolbar
                        grp.getChildren().add(rec);
                        grp.toFront();
                    }
                    // Inside the scene
                    else if (event.getX() > 0 && event.getY() > 0) {
                        // Update shape position
                        editor.getShapeDragged().setPosition(new Point2D(event.getX(), event.getY()));

                        // Hide shadow shape
                        if (grp.getChildren().contains(rec)) {
                            grp.getChildren().remove(rec);
                            grp.toBack();
                        }
                    }
                }
                // Else, it is a select action
                else {
                    editor.getSelectionShape().setSelectionEndPoint(new Point2D(event.getX(), event.getY()));
                    rendering.drawSelectionFrame();
                }
            }
        });

        root.setOnMouseDragReleased(event -> {
            System.out.println("[ROOT] Drag released");

            // If there is a shape dragged
            if (editor.getShapeDragged() != null)
                try {
                    // Clone the shape and paste it to the scene
                    Shape newShape = editor.getShapeDragged().clone();
                    newShape.setPosition(new Point2D(event.getX(), event.getY()));
                    editor.addShapeToScene((ShapeObservable) newShape);
                    editor.setShapeDragged(null);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
        });

        root.setOnMousePressed(event -> {
            // Close edition dialog
            rendering.hideEditionDialog();

            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {
                System.out.println("[ROOT] Mouse pressed");

                boolean inShape = false;
                // Look if the mouse is on an existing shape
                for (Shape s : editor.getScene().getShapes()) {
                    if (s.contains(new Point2D(event.getX(), event.getY()))) { // Found
                        root.setCursor(Cursor.CLOSED_HAND);
                        editor.setShapeDragged(s);
                        inShape = true;
                        break;
                    }
                }
                // If no shape found --> start select action
                if (!inShape)
                    editor.getSelectionShape().setSelectionStartPoint(new Point2D(event.getX(), event.getY()));
            }
        });

        root.setOnMouseReleased(event -> {
            System.out.println("[ROOT] Mouse released");

            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {
                // Hide shadow shape
                grp.getChildren().remove(rec);
                grp.toBack();

                // If it is a select action
                if (editor.getSelectionShape() != null) {
                    ArrayList<ShapeObservable> selectedShapes = new ArrayList<>();

                    // Add all selected shapes in array
                    for (ShapeObservable s : editor.getScene().getShapes())
                        if (s.contained(editor.getSelectionShape())) {
                            selectedShapes.add(s);
                            System.out.println(s + " selected");
                        }

                    // Save selected shapes
                    editor.getScene().setSelectedShapes(selectedShapes);
                }

                root.setCursor(Cursor.DEFAULT);
                editor.setShapeDragged(null);
                rendering.drawEditor();
            }
        });

        root.setOnMouseClicked(e -> {
            System.out.println("[ROOT] Mouse clicked");

            // Close edition dialog
            rendering.hideEditionDialog();

            // Detect right click
            if (e.getButton() == MouseButton.SECONDARY) {
                // Click on a selection
                if (editor.getScene().getSelectedShapes().size() != 0) {
                    System.out.println("right click on selection");
                }
                // Click on a single shape
                else {
                    // Look for the clicked shape
                    for (Shape s : editor.getScene().getShapes()) {
                        if (s.contains(new Point2D(e.getX(), e.getY()))) { // Found
                            System.out.println("right click on shape");
                            // Create edition dialog
                            EditionDialogI ed = s.createEditionDialog();
                            ed.setPosition(new Point2D(e.getScreenX(), e.getScreenY()));
                            // Draw it
                            ed.draw(rendering);
                        }
                    }
                }
            }
        });
    }
}
