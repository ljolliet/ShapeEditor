package ui;

import editor.core.Editor;
import editor.edition.EditionDialogI;
import editor.shapes.Shape;
import editor.shapes.ShapeI;
import editor.utils.EditorManagementException;
import editor.utils.Point2D;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaFXApp extends Application implements ApplicationI {

    private Editor editor;
    private Rendering rendering;

    private Group root;
    private VBox toolbarBox;
    private BorderPane borderPane;
    private ImageView trashImage;

    private boolean fromToolbar = false;

    private javafx.scene.shape.Shape shadowShape;
    private Group shadowGroup;
    private Point2D shadowShapeThreshold = new Point2D(0,0);

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
        |  |  |  Group (shadowGroup)
        |  |  |  |  Canvas
*/

        VBox windowLayout = new VBox();
        StackPane stackPane = new StackPane(windowLayout);
        Scene scene = new Scene(stackPane);
        primaryStage.setScene(scene);

        // Create a group to move the shadow shape inside
        shadowGroup = new Group(new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT));
        // The group is completely transparent to mouse events
        shadowGroup.setMouseTransparent(true);
        stackPane.getChildren().add(shadowGroup);

        windowLayout.toFront();

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        //optionLayout.setPrefWidth(WINDOW_WIDTH);
        optionLayout.setSpacing(OPTION_SPACING);
        optionLayout.setPadding(new Insets(OPTION_SPACING));
        optionLayout.setStyle("-fx-background-color: firebrick");
        windowLayout.getChildren().add(optionLayout);

        //TODO refacto
        final double scale = (double) OPTION_HEIGHT/2;
        ImageView saveIm = new ImageView( new Image(getClass().getClassLoader().getResource("save.png").toString()));
        ImageView openIm = new ImageView( new Image(getClass().getClassLoader().getResource("open.png").toString()));
        ImageView undoIm = new ImageView( new Image(getClass().getClassLoader().getResource("undo.png").toString()));
        ImageView redoIm = new ImageView( new Image(getClass().getClassLoader().getResource("redo.png").toString()));
        ImageView[] optionsIm = new ImageView[] {saveIm, openIm, undoIm, redoIm};
        for(ImageView iv : new ArrayList<>(Arrays.asList(optionsIm))){
            iv.setPreserveRatio(true);
            iv.setFitHeight(scale);
        }
        Button saveBtn = new Button("", saveIm);
        Button openBtn = new Button("", openIm);
        Button undoBtn = new Button("", undoIm);
        Button redoBtn = new Button("", redoIm);
        Button[] optionsBtn = new Button[] {saveBtn, openBtn, undoBtn, redoBtn};
        for(Button b : new ArrayList<>(Arrays.asList(optionsBtn))) {
            b.setStyle("-fx-background-color: darkred");
            optionLayout.getChildren().add(b);
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")); //TODO set extension once decided
        openBtn.setOnMouseClicked(mouseEvent -> {
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                System.out.println("open file : " + file.getName());
                //TODO open file method
            }
        });

        saveBtn.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                File file = fileChooser.showSaveDialog(primaryStage);
                if (file != null) {
                    System.out.println("save file : " + file.getName());
                    //TODO save file method
                }
            }
        });

        undoBtn.setOnMouseClicked(mouseEvent -> editor.undo());
        redoBtn.setOnMouseClicked(mouseEvent -> editor.redo());

        // Editor layout
        HBox editorLayout = new HBox();
        //editorLayout.setPrefHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        //editorLayout.setPrefWidth(WINDOW_WIDTH);
        windowLayout.getChildren().add(editorLayout);

        //Toolbar root
        this.borderPane = new BorderPane();
        borderPane.setMaxHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        borderPane.setPrefWidth(TOOLBAR_WIDTH);
        borderPane.setStyle("-fx-background-color: lightgray");
        // Toolbar
        this.toolbarBox = new VBox();
        toolbarBox.setPadding(new Insets(TOOLBAR_SPACING));
        toolbarBox.setAlignment(Pos.BASELINE_CENTER);
        toolbarBox.setSpacing(TOOLBAR_SPACING);
        //toolbarBox.setPrefHeight(WINDOW_HEIGHT);
        borderPane.setCenter(toolbarBox);

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

        // Events on stack pane
        stackPane.setOnMouseDragged(event -> {
            // Move shadow shape to mouse coords
            if (shadowShape != null) {
                shadowShape.setTranslateX(event.getX() + shadowShapeThreshold.x);
                shadowShape.setTranslateY(event.getY() + shadowShapeThreshold.y);
            }
        });

        stackPane.setOnMouseDragReleased(event -> {
            // Delete all shadow shapes
            shadowGroup.getChildren().removeIf(node -> node instanceof javafx.scene.shape.Shape);
            // Bring editor to foreground
            shadowGroup.toBack();

            shadowShapeThreshold = new Point2D(0, 0);
        });

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
            fromToolbar = true;

            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {
                int i = 0;
                // Search for shape selected
                for (Node node : toolbarBox.getChildren()) {
                    if (event.getPickResult().getIntersectedNode() == node) { // Found
                        ShapeI s = editor.getToolbar().getShapes().get(i);

                        // Create shadow shape with dragged shape
                        shadowShape = (javafx.scene.shape.Shape) rendering.getShadowShape(s);
                        // Display shadow shape
                        shadowGroup.getChildren().add(shadowShape);
                        shadowGroup.toFront();

                        shadowGroup.setCursor(Cursor.CLOSED_HAND);
                        editor.setShapeDragged(s);
                        break;
                    }
                    i++;
                }
            }
        });

        borderPane.setOnMouseDragReleased(event -> {
            System.out.println("[BORDERPANE] Drag released");

            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {
                if (editor.getShapeDragged() != null) {
                    Node intersectedNode = event.getPickResult().getIntersectedNode();

                    // If release over trash --> delete selected shape
                    if (intersectedNode == this.trashImage) {
                        if(editor.toolbarContains(editor.getShapeDragged()))
                            editor.removeShapeFromToolbar(editor.getShapeDragged());
                        else if(editor.sceneContains(editor.getShapeDragged()) )
                            editor.removeShapeFromScene((Shape) editor.getShapeDragged());
                        else
                            throw new EditorManagementException("Trying to delete an unknown shape");
                    }
                    // If release over toolbar --> add shape to toolbar
                    else if (intersectedNode == this.toolbarBox) {
                        try {
                            // Clone the shape and paste it to the toolbar
                            ShapeI newShape = editor.getShapeDragged().clone();
                            editor.addShapeToToolbar(newShape);
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    }

                    shadowGroup.setCursor(Cursor.DEFAULT);
                    editor.setShapeDragged(null);
                }
            }
        });
    }

    private void setSceneEvents() {
        root.setOnDragDetected(event -> {
            System.out.println("[ROOT] Drag detected");
            root.startFullDrag();
            fromToolbar = false;
        });

        root.setOnMouseDragged(event -> {
            // Detect left click
            if (event.getButton() == MouseButton.PRIMARY) {

                // If there is a shape to drag
                if (editor.getShapeDragged() != null) {
                    // Display shadow shape
                    if (!shadowGroup.getChildren().contains(shadowShape)) {
                        shadowGroup.getChildren().add(shadowShape);
                        shadowGroup.toFront();
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
            if (editor.getShapeDragged() != null) {
                // If from toolbar --> clone the shape
                if (fromToolbar) {
                    try {
                        // Clone the shape and paste it to the scene
                        ShapeI newShape = editor.getShapeDragged().clone();
                        newShape.setPosition(new Point2D(event.getX(), event.getY()));
                        editor.addShapeToScene((Shape) newShape);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                // Else --> update position
                else {
                    editor.getShapeDragged().setPosition(new Point2D(event.getX() + shadowShapeThreshold.x,
                            event.getY() + shadowShapeThreshold.y));
                }

                editor.setShapeDragged(null);
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
                for (ShapeI s : editor.getScene().getShapes()) {
                    if (s.contains(new Point2D(event.getX(), event.getY()))) { // Found
                        // Create shadow shape with dragged shape
                        shadowShape = (javafx.scene.shape.Shape) rendering.getShadowShape(s);
                        shadowShapeThreshold = new Point2D(s.getPosition().x - event.getX(),
                                s.getPosition().y - event.getY());

                        shadowGroup.setCursor(Cursor.CLOSED_HAND);
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
                shadowGroup.getChildren().remove(shadowShape);
                shadowGroup.toBack();

                // If it is a select action
                if (editor.getSelectionShape() != null) {
                    ArrayList<Shape> selectedShapes = new ArrayList<>();

                    // Add all selected shapes in array
                    for (Shape s : editor.getScene().getShapes())
                        if (s.contained(editor.getSelectionShape())) {
                            selectedShapes.add(s);
                            System.out.println(s + " selected");
                        }

                    // Save selected shapes
                    editor.getScene().setSelectedShapes(selectedShapes);
                }


                shadowGroup.setCursor(Cursor.DEFAULT);
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
                if (editor.getScene().getSelectedShapes().size() > 1) {
                   // rendering.showGroupDialog(new Point2D(e.getScreenX(), e.getScreenY()));    //TODO Refacto
                }
                // Click on a single shape
                else {
                    // Look for the clicked shape
                    for (ShapeI s : editor.getScene().getShapes()) {
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
