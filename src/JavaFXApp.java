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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.ApplicationI;
import ui.Rendering;
import ui.javafx.*;

import java.util.ArrayList;


public class JavaFXApp extends Application implements ApplicationI {

    private Editor editor;
    private Rendering rendering;

    private RootJFx root;
    private ToolbarJFx toolbarBox;
    private BorderPane borderPane;
    private ImageView trashImage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);

        this.editor = Editor.getInstance();
        rendering = new JFxRendering();
        editor.setRendering(rendering);
/*
        Stage
        |  Scene
        |  |  StackPane (windowPane)
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
        WindowPaneJFx windowPane = new WindowPaneJFx(windowLayout);
        Scene scene = new Scene(windowPane);
        primaryStage.setScene(scene);

        rendering.registerComponent(windowPane);
        windowLayout.toFront();

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        optionLayout.setSpacing(OPTION_SPACING);
        optionLayout.setSpacing(OPTION_SPACING);
        optionLayout.setPadding(new Insets(OPTION_SPACING));
        optionLayout.setStyle("-fx-background-color: firebrick");
        windowLayout.getChildren().add(optionLayout);

        ButtonJFx saveBtn = new SaveButtonJFx();
        ButtonJFx openBtn = new OpenButtonJFx();
        ButtonJFx undoBtn = new UndoButtonJFx();
        ButtonJFx redoBtn = new RedoButtonJFx();

        rendering.registerComponent(saveBtn);
        rendering.registerComponent(openBtn);
        rendering.registerComponent(undoBtn);
        rendering.registerComponent(redoBtn);

        optionLayout.getChildren().addAll(saveBtn, openBtn, undoBtn, redoBtn);

        // Editor layout
        HBox editorLayout = new HBox();
        windowLayout.getChildren().add(editorLayout);

        //Toolbar root
        this.borderPane = new BorderPane();
        borderPane.setMaxHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        borderPane.setPrefWidth(TOOLBAR_WIDTH);
        borderPane.setStyle("-fx-background-color: lightgray");
        // Toolbar
        this.toolbarBox = new ToolbarJFx();
        toolbarBox.setPadding(new Insets(TOOLBAR_SPACING));
        toolbarBox.setAlignment(Pos.BASELINE_CENTER);
        borderPane.setCenter(toolbarBox);
        rendering.registerComponent(toolbarBox);

        //trash
        // TODO Refactor
        this.trashImage = new ImageView(new Image(getClass().getClassLoader().getResource("trash.png").toString()));
        trashImage.setPreserveRatio(true);
        trashImage.setFitHeight(TRASH_HEIGHT);

        borderPane.setBottom(trashImage);
        BorderPane.setAlignment(trashImage, Pos.CENTER);
        BorderPane.setMargin(trashImage, new Insets(TOOLBAR_SPACING));
        editorLayout.getChildren().add(borderPane);

        // Scene layout
        this.root = new RootJFx();
        editorLayout.getChildren().add(root);
        rendering.registerComponent(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(SCENE_WIDTH);
        canvas.setHeight(SCENE_HEIGHT);
        root.getChildren().add(canvas);

        // Draw editor
        rendering.drawEditor();

        primaryStage.show();
    }
}
