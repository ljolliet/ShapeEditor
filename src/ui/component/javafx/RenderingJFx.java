/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx;

import editor.core.Editor;
import editor.edition.EditionDialogI;
import editor.edition.GroupEditionDialog;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.save.io.IOManager;
import editor.shapes.*;
import editor.utils.EditorManagementException;
import editor.utils.Point2D;
import editor.utils.SelectionRectangle;
import editor.utils.Vec2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.ApplicationI;
import ui.component.Component;
import ui.Rendering;
import ui.component.javafx.area.SceneJFx;
import ui.component.javafx.area.ToolbarJFx;
import ui.component.javafx.area.TrashJFx;
import ui.component.javafx.area.WindowPaneJFx;
import ui.component.javafx.buttons.ButtonJFx;
import ui.component.javafx.dialog.EditDialogJFx;
import ui.component.javafx.dialog.GroupEditJFx;
import ui.component.javafx.dialog.PolygonEditJFx;
import ui.component.javafx.dialog.RectangleEditJFx;
import ui.component.javafx.layouts.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ui.ApplicationI.SCENE_HEIGHT;
import static ui.ApplicationI.SCENE_WIDTH;


public class RenderingJFx implements Rendering {

    private SceneJFx root;
    private ToolbarJFx toolbar;
    private int toolbarRowCount;
    private final RowConstraints rowConstraints;

    /* Edition */
    private final ContextMenu contextMenu = new ContextMenu();
    private final Stage editStage = new Stage();
    private EditionDialogI dialogED;
    private EditDialogJFx editDialog;

    /* Drag & drop */
    private boolean fromToolbar;

    /* Shadow shape */
    private Group shadowShape;
    private Group shadowGroup;
    private Point2D shadowShapeThreshold;

    /* Layout */
    private Stage primaryStage;
    private WindowPaneJFx windowPane;
    private WindowLayoutJFx windowLayout;
    private EditorLayoutJFx editorLayout;
    private OptionLayoutJFx optionLayout;
    private ToolbarRootJFx toolbarRoot;


    public RenderingJFx() {
        //init toolbar
        this.fromToolbar = false;
        this.toolbarRowCount = 0;
        this.rowConstraints = new RowConstraints(ApplicationI.TOOLBAR_CELL_HEIGHT);
    }

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "Toolbar":
                toolbar = (ToolbarJFx) component;
                toolbarRoot.setCenter(toolbar);
                break;
            case "ToolbarRoot":
                this.toolbarRoot = (ToolbarRootJFx) component;
                this.toolbarRoot.setCenter(toolbar);
                this.editorLayout.getChildren().add(toolbarRoot);
                break;
            case "Trash":
                this.toolbarRoot.setTrashBottom((TrashJFx)component);
                break;
            case "Scene":
                root = (SceneJFx) component;
                editorLayout.getChildren().add(root);
                Canvas canvas = new Canvas();
                canvas.setWidth(SCENE_WIDTH);
                canvas.setHeight(SCENE_HEIGHT);
                root.getChildren().add(canvas);
                break;
            case "WindowPane":
                windowPane = (WindowPaneJFx) component;
                this.initShadowShape();
                break;
            case "EditDialog":
                editDialog = (EditDialogJFx) component;
                break;
            case "OptionLayout":
                optionLayout = (OptionLayoutJFx)component;
                windowLayout.getChildren().add(optionLayout);
                break;
            case "UndoButton" :
            case "RedoButton" :
            case "SaveButton" :
            case "OpenButton" :
                optionLayout.getChildren().add((ButtonJFx)component);
                break;
            case "WindowLayout":
                this.windowLayout = (WindowLayoutJFx) component;
                this.registerComponent(new WindowPaneJFx(windowLayout));
                this.primaryStage.setScene(new Scene(windowPane));
                break;
            case "EditorLayout" :
                this.editorLayout = (EditorLayoutJFx) component;
                this.windowLayout.getChildren().add(editorLayout);
                break;
        }
    }

    public void registerStage(Stage s){
        this.primaryStage = s;
    }

    @Override
    public void drawEditor() {
        Editor editor = Editor.getInstance();
        this.initEditor();
        for (Shape s: editor.getScene().getShapes())
            s.drawInScene(this);
        for (Shape s: editor.getToolbar().getShapes())
            s.drawInToolbar(this);
        drawSelectionFrame();
    }

    @Override
    public void drawInScene(Rectangle r) {
        root.getChildren().add(ShapeFactoryJFx.createSceneRectangle(r));
    }

    @Override
    public void drawInScene(Polygon p) {
        root.getChildren().add(ShapeFactoryJFx.createScenePolygon(p));
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double max = Math.max(r.getHeight(), r.getWidth());
        double ratio = ApplicationI.TOOLBAR_CELL_HEIGHT / max;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeFactoryJFx.createToolbarRectangle(r, ratio));
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_CELL_HEIGHT / 2d;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeFactoryJFx.createToolbarPolygon(p, radius));
    }

    @Override
    public void drawInToolbar(ShapeGroup group) {
        double max = Math.max(group.getHeight(), group.getWidth());
        double ratio = ApplicationI.TOOLBAR_CELL_HEIGHT / max;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeFactoryJFx.createToolbarGroup(group, ratio));
    }

    @Override
    public void setEditionDialog(EditionDialogI shapeED) {
        if(shapeED instanceof RectangleEditionDialog)
            this.registerComponent(new RectangleEditJFx((RectangleEditionDialog) shapeED));
        else if(shapeED instanceof PolygonEditionDialog)
            this.registerComponent(new PolygonEditJFx((PolygonEditionDialog) shapeED));
        else if(shapeED instanceof GroupEditionDialog)
            this.registerComponent(new GroupEditJFx((GroupEditionDialog) shapeED));
    }

    @Override
    public void undo() {
        Editor.getInstance().undo();
    }

    @Override
    public void redo() {
        Editor.getInstance().redo();
    }

    @Override
    public void save() {
        final String extension = Editor.getInstance().getSaveExtension();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Save Files", "*" + extension));
        fileChooser.setInitialFileName(new Date() + extension);

        File saveDir = new File("save");
        if (! saveDir.exists()) {
            saveDir.mkdirs();
        }
        fileChooser.setInitialDirectory(saveDir);

        File file = fileChooser.showSaveDialog(this.primaryStage);
        Editor.getInstance().saveScene(file);
    }

    @Override
    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*" + Editor.getInstance().getSaveExtension()));
        File saveDir = new File(IOManager.SAVE_DIRECTORY);
        if (! saveDir.exists()) {
            saveDir.mkdirs();
        }
        fileChooser.setInitialDirectory(saveDir);

        File file = fileChooser.showOpenDialog(this.primaryStage);
        Editor.getInstance().restoreScene(file);
    }

    @Override
    public void dragFromToolbar(Shape shape) {
        // Shadow shape
        shadowShape = getShadowShape(shape);
        shadowGroup.getChildren().add(shadowShape);
        shadowGroup.toFront();
        shadowShape.setVisible(false);

        this.fromToolbar = true;
        windowPane.setCursor(Cursor.CLOSED_HAND);
        Editor.getInstance().setShapeDragged(shape);
    }

    @Override
    public void dragFromScene(Point2D position) {
        Editor editor = Editor.getInstance();
        Shape shape;

        // Drag multi shapes
        if (editor.getScene().getSelectedShapes().size() > 1) {
            shape = new ShapeGroup();
            for (Shape s: editor.getScene().getSelectedShapes())
                shape.addShape(s);
        }
        // Drag one shape
        else {
            shape = editor.getScene().getSelectedShapes().get(0);
        }

        // Shadow shape
        shadowShape = getShadowShape(shape);
        shadowGroup.getChildren().add(shadowShape);
        shadowGroup.toFront();
        shadowShape.setVisible(false);
        this.shadowShapeThreshold = new Point2D(shape.getPosition().x - position.x,
                shape.getPosition().y - position.y);

        this.fromToolbar = false;
        windowPane.setCursor(Cursor.CLOSED_HAND);
        editor.setShapeDragged(shape);
    }

    @Override
    public void dropInToolbar() {
        Editor editor = Editor.getInstance();

        if (!fromToolbar) {
            // Clone the shape and paste it to the toolbar
            Shape newShape = editor.getShapeDragged().clone();
            editor.addShapeToToolbar(newShape);
        }

        editor.setShapeDragged(null);
    }

    @Override
    public void dropInScene(Point2D position) {
        Editor editor = Editor.getInstance();
        // If from toolbar --> clone the shape
        if (fromToolbar) {
            // Clone the shape and paste it to the scene
            Shape newShape = editor.getShapeDragged().clone();
            newShape.setPosition(position);
            editor.addShapeToScene(newShape);
        }
        // Else --> update position
        else {
            editor.getShapeDragged().setPosition(new Point2D(position.x + shadowShapeThreshold.x,
                    position.y + shadowShapeThreshold.y));
        }

        editor.setShapeDragged(null);
    }

    @Override
    public void dropInTrash() {
        Editor editor = Editor.getInstance();
        Shape shape = editor.getShapeDragged();

        if (shape != null) {
            if (editor.toolbarContains(shape)) { // From toolbar
                editor.removeShapeFromToolbar(shape);
            }
            else { // From scene
                for (Shape s: editor.getScene().getSelectedShapes())
                    if (editor.sceneContains(s))
                        editor.removeShapeFromScene(s);
                    else
                        throw new EditorManagementException("Trying to delete an unknown shape");
            }

        }

        setSelectedShapes(new ArrayList<>());
        editor.setShapeDragged(null);
    }

    @Override
    public void selectShape(Shape shape) {
        List<Shape> list = new ArrayList<>();
        list.add(shape);
        Editor.getInstance().setSceneSelectedShapes(list);
    }

    public void toggleSelectedShape(Shape shape) {
        List<Shape> list = Editor.getInstance().getScene().getSelectedShapes();

        // Toggle
        if (list.contains(shape))
            list.remove(shape);
        else
            list.add(shape);

        Editor.getInstance().setSceneSelectedShapes(list);
    }

    public void setSelectedShapes(List<Shape> shapes) {
        Editor.getInstance().setSceneSelectedShapes(shapes);
    }

    @Override
    public void startSelection(Point2D startPoint) {
        Editor.getInstance().getSelectionShape().setOn(true);
        Editor.getInstance().getSelectionShape().setSelectionStartPoint(startPoint);
    }

    @Override
    public void moveSelection(Point2D point) {
        Editor.getInstance().getSelectionShape().setSelectionEndPoint(point);
        drawEditor();
    }

    @Override
    public void stopSelection(List<Shape> shapes) {
        Editor.getInstance().getSelectionShape().setOn(false);
        setSelectedShapes(shapes);
        drawEditor();
    }

    @Override
    public void showMenu(Shape shape, Point2D position) {
        contextMenu.getItems().clear();
        if(shape instanceof ShapeGroup)
            addGroupEditToDialog(shape);
        else
            addEditToDialog();
        dialogED = shape.createEditionDialog();
        dialogED.draw(this);
        contextMenu.show(this.root, position.x, position.y);
    }

    @Override
    public void showContextMenu(List<Shape> shapes, Point2D position) {
        contextMenu.getItems().clear();
        final MenuItem group = new MenuItem("Group");
        group.setOnAction(event -> Editor.getInstance().createGroup(shapes));
        contextMenu.getItems().add(group);
        contextMenu.show(this.root, position.x, position.y);
    }

    private void addEditToDialog(){
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> {
            editStage.setTitle("Edition");
            editStage.setScene(new Scene(editDialog));
            editStage.showAndWait();
        });
        contextMenu.getItems().add(edit);
    }

    private void addGroupEditToDialog(Shape shape){
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> {
            editStage.setTitle("Group Edition");
            editStage.setScene(new Scene(editDialog));
            editStage.showAndWait();
        });
        final MenuItem ungroup = new MenuItem("De-group");
        ungroup.setOnAction(event -> Editor.getInstance().deGroup(shape));
        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(ungroup);
    }

    @Override
    public void moveShadowShape(Point2D position) {
        if (shadowShape != null) {
            if (!shadowShape.isVisible())
                shadowShape.setVisible(true);

            shadowShape.setTranslateX(position.x + shadowShapeThreshold.x);
            shadowShape.setTranslateY(position.y + shadowShapeThreshold.y);
        }
    }

    @Override
    public void clearShadowShape() {
        // Delete all shadow shapes
        shadowGroup.getChildren().removeIf(node -> !(node instanceof Canvas));
        // Bring editor to foreground
        shadowGroup.toBack();

        shadowShapeThreshold = new Point2D(0, 0);

        windowPane.setCursor(Cursor.DEFAULT);
    }

    @Override
    public void clearEditorActions() {
        contextMenu.hide();
    }

    @Override
    public void cancelEdit() {
        editStage.close();
    }

    @Override
    public void applyEdit() {
        dialogED.applyEdition();
    }

    @Override
    public void applyAndQuitEdit() {
        dialogED.applyEdition();
        editStage.close();
    }

    private void initShadowShape() {
        shadowShapeThreshold = new Point2D(0, 0);

        shadowGroup = new Group(new Canvas(ApplicationI.WINDOW_WIDTH, ApplicationI.WINDOW_HEIGHT));
        // The group is completely transparent to mouse events
        shadowGroup.setMouseTransparent(true);
        windowPane.getChildren().add(shadowGroup);
    }

    private void initEditor() {
        // Remove all shapes of the scene and of the toolbar
        root.getChildren().removeIf(child -> !(child instanceof Canvas));
        toolbar.getChildren().clear();
        toolbar.getRowConstraints().clear();
        toolbarRowCount = 0;
    }

    private void drawSelectionFrame() {
        SelectionRectangle s = (SelectionRectangle) Editor.getInstance().getSelectionShape();

        if(s.isOn()) {
            javafx.scene.shape.Rectangle selectionFrame = new javafx.scene.shape.Rectangle(s.getWidth(), s.getHeight());
            selectionFrame.setStroke(Color.DARKRED);
            selectionFrame.setFill(Color.TRANSPARENT);
            selectionFrame.setX(s.getPosition().x);
            selectionFrame.setY(s.getPosition().y);

            root.getChildren().add(selectionFrame);
        }
    }

    ///////////////////////////
    //     Create shapes     //
    ///////////////////////////

    private Group getShadowShape(Shape shape) {
        if (shape instanceof Rectangle)
            return getShadowShape((Rectangle) shape);
        if (shape instanceof Polygon)
            return getShadowShape((Polygon) shape);
        if (shape instanceof ShapeGroup)
            return getShadowShape((ShapeGroup) shape);

        return null;
    }

    private Group getShadowShape(Rectangle r) {
        Vec2D translation = r.getTranslation();
        r = new Rectangle(r.getWidth(), r.getHeight(), r.getBorderRadius(), new Point2D(0, 0),
                r.getColor(), r.getRotationCenter(), r.getRotation());
        r.setTranslation(translation);

        return new Group(ShapeFactoryJFx.createSceneRectangle(r));
    }

    private Group getShadowShape(Polygon p) {
        Vec2D translation = p.getTranslation();
        p = new Polygon(p.getNbSides(), p.getSideLength(), new Point2D(0, 0),
                p.getColor(), p.getRotationCenter(), p.getRotation());
        p.setTranslation(translation);

        return new Group(ShapeFactoryJFx.createScenePolygon(p));
    }

    private Group getShadowShape(ShapeGroup g) {
        Group grp = new Group();

        for (Shape shape: g.getChildren()) {
            Group JFxGroup = getShadowShape(shape);
            JFxGroup.setTranslateX(shape.getPosition().x - g.getPosition().x);
            JFxGroup.setTranslateY(shape.getPosition().y - g.getPosition().y);
            grp.getChildren().add(JFxGroup);
        }

        return grp;
    }
}
