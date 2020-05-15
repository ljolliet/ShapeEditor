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

    private ToolbarJFx toolbar;
    private int toolbarRowCount;
    private RowConstraints rowConstraints;

    private SceneJFx root;

    private WindowPaneJFx windowPane;

    /* Edition */
    private EditionDialogI dialogED;
    private ContextMenu contextMenu = new ContextMenu();
    private Stage editStage = new Stage();

    private EditDialogJFx editDialog;

    /* Drag & drop */
    private boolean fromToolbar;

    /* Shadow shape */
    private Group shadowShape;
    private Group shadowGroup;
    private Point2D shadowShapeThreshold;

    private OptionLayoutJFx optionLayout;
    private Scene scene;
    private WindowLayoutJFx windowLayout;
    private Stage primaryStage;
    private EditorLayoutJFx editorLayout;
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
                this.scene = new Scene(windowPane);
                this.primaryStage.setScene(scene);
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
        // Clear shapes
        this.initEditor();

        // Draw scene
        for (ShapeI s: editor.getScene().getShapes())
            s.drawInScene(this);

        // Draw toolbar
        for (ShapeI s: editor.getToolbar().getShapes())
            s.drawInToolbar(this);

        drawSelectionFrame();
    }

    @Override
    public void drawInScene(Rectangle r) {
        root.getChildren().add(ShapeBuilderJFx.createSceneRectangle(r));
    }

    @Override
    public void drawInScene(Polygon p) {
        root.getChildren().add(ShapeBuilderJFx.createScenePolygon(p));
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double max = Math.max(r.getHeight(), r.getWidth());
        double ratio = ApplicationI.TOOLBAR_CELL_HEIGHT / max;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeBuilderJFx.createToolbarRectangle(r, ratio));
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_CELL_HEIGHT / 2d;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeBuilderJFx.createToolbarPolygon(p, radius));
    }

    @Override
    public void drawInToolbar(ShapeGroup group) {
        double max = Math.max(group.getHeight(), group.getWidth());
        double ratio = ApplicationI.TOOLBAR_CELL_HEIGHT / max;

        toolbar.getRowConstraints().add(toolbarRowCount, rowConstraints);
        toolbar.addRow(toolbarRowCount++, ShapeBuilderJFx.createToolbarGroup(group, ratio));
    }

    @Override
    public Object getShadowShape(ShapeI shape) {
        if (shape instanceof Rectangle)
            return getShadowShape((Rectangle) shape);
        if (shape instanceof Polygon)
            return getShadowShape((Polygon) shape);
        if (shape instanceof ShapeGroup)
            return getShadowShape((ShapeGroup) shape);

        return null;
    }


    @Override
    public void drawEditionDialog(EditionDialogI shapeED, Point2D position) {
        if(shapeED instanceof RectangleEditionDialog)
            this.registerComponent(new RectangleEditJFx((RectangleEditionDialog) shapeED));
        else if(shapeED instanceof PolygonEditionDialog)
            this.registerComponent(new PolygonEditJFx((PolygonEditionDialog) shapeED));
        else if(shapeED instanceof GroupEditionDialog)
            this.registerComponent(new GroupEditJFx((GroupEditionDialog) shapeED));
    }


    @Override
    public void hideEditionDialog(){
        contextMenu.hide();
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
    public void dragFromToolbar(ShapeI shape) {
        // Shadow shape
        shadowShape = (Group) getShadowShape(shape);
        shadowGroup.getChildren().add(shadowShape);
        shadowGroup.toFront();
        shadowShape.setVisible(false);

        this.fromToolbar = true;
        windowPane.setCursor(Cursor.CLOSED_HAND);
        Editor.getInstance().setShapeDragged(shape);
    }

    @Override
    public void dragFromScene(Point2D position) {
        ShapeI shape = new ShapeGroup();
        for (ShapeI s: Editor.getInstance().getScene().getSelectedShapes())
            shape.addShape(s);

        // Shadow shape
        shadowShape = (Group) getShadowShape(shape);
        shadowGroup.getChildren().add(shadowShape);
        shadowGroup.toFront();
        shadowShape.setVisible(false);
        this.shadowShapeThreshold = new Point2D(shape.getPosition().x - position.x,
                shape.getPosition().y - position.y);

        this.fromToolbar = false;
        windowPane.setCursor(Cursor.CLOSED_HAND);
        Editor.getInstance().setShapeDragged(shape);
    }

    @Override
    public void dropInToolbar() {
        Editor editor = Editor.getInstance();

        // Clone the shape and paste it to the toolbar
        ShapeI newShape = editor.getShapeDragged().clone();
        editor.addShapeToToolbar(newShape);

        editor.setShapeDragged(null);
    }

    @Override
    public void dropInScene(Point2D position) {
        Editor editor = Editor.getInstance();
        // If from toolbar --> clone the shape
        if (fromToolbar) {
            // Clone the shape and paste it to the scene
            ShapeI newShape = editor.getShapeDragged().clone();
            newShape.setPosition(position);
            editor.addShapeToScene((Shape) newShape);
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
        ShapeI shape = editor.getShapeDragged();

        if (shape != null) {
            if (editor.toolbarContains(shape)) { // From toolbar
                editor.removeShapeFromToolbar(shape);
            }
            else { // From scene
                for (ShapeI s: editor.getScene().getSelectedShapes())
                    if (editor.sceneContains((Shape) s))
                        editor.removeShapeFromScene((Shape) s);
                    else
                        throw new EditorManagementException("Trying to delete an unknown shape");
            }

        }

        setSelectedShapes(new ArrayList<>());
        editor.setShapeDragged(null);
    }

    @Override
    public void selectShape(ShapeI shape) {
        List<ShapeI> list = new ArrayList<>();
        list.add(shape);
        Editor.getInstance().setSceneSelectedShapes(list);
    }

    public void toggleSelectedShape(ShapeI shape) {
        List<ShapeI> list = Editor.getInstance().getScene().getSelectedShapes();

        // Toggle
        if (list.contains(shape))
            list.remove(shape);
        else
            list.add(shape);

        Editor.getInstance().setSceneSelectedShapes(list);
    }

    public void setSelectedShapes(List<ShapeI> shapes) {
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
    public void stopSelection(List<ShapeI> shapes) {
        Editor.getInstance().getSelectionShape().setOn(false);
        setSelectedShapes(shapes);
        drawEditor();
    }

    @Override
    public void showMenu(ShapeI shape, Point2D position) {
        contextMenu.getItems().clear();
        if(shape instanceof ShapeGroup)
            addGroupEditToDialog(shape);
        else {
            addEditToDialog();
            dialogED = shape.createEditionDialog();
            dialogED.setPosition(position);
            dialogED.draw(this);
        }
        contextMenu.show(this.root, position.x, position.y);
    }

    @Override
    public void showContextMenu(List<ShapeI> shapes, Point2D position) {
        contextMenu.getItems().clear();
        final MenuItem group = new MenuItem("Group");
        group.setOnAction(event -> createGroup(shapes));
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

    private void addGroupEditToDialog(ShapeI shape){
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> {
            editStage.setTitle("Group Edition");
            editStage.setScene(new Scene(editDialog));
            editStage.showAndWait();
        });
        final MenuItem ungroup = new MenuItem("Ungroup");
        ungroup.setOnAction(event -> ungroupShape(shape));
        contextMenu.getItems().add(edit);
        contextMenu.getItems().add(ungroup);
    }

    private void createGroup(List<ShapeI> shapes){
        ShapeGroup group = new ShapeGroup();
        for(ShapeI s : shapes){
            Editor.getInstance().getScene().getSelectedShapes().remove(s);
            Editor.getInstance().getScene().removeShape((Shape) s);
            ((Shape) s).removeObservers();
            group.addShape(s);
        }
        group.addObserver(Editor.getInstance().getObserver());
        Editor.getInstance().getScene().addShape(group);
    }

    private void ungroupShape(ShapeI group) {
        editor.core.Scene scene = Editor.getInstance().getScene();
        scene.removeShape((Shape) group);
        scene.clearSelectedShapes();
        for(ShapeI child : group.getChildren()){
            Editor.getInstance().getScene().addShape((Shape) child);
            ((Shape) child).addObserver(Editor.getInstance().getObserver());
        }
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
        //Editor.getInstance().getSelectionShape().setOn(false);
        //setSelectedShapes(new ArrayList<>());

        hideEditionDialog();
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

    private Object getShadowShape(Rectangle r) {
        r = new Rectangle(r.getWidth(), r.getHeight(), r.getBorderRadius(), new Point2D(0, 0),
                r.getColor(), r.getRotationCenter(), r.getRotation());

        return new Group(ShapeBuilderJFx.createSceneRectangle(r));
    }

    private Object getShadowShape(Polygon p) {
        p = new Polygon(p.getNbSides(), p.getSideLength(), new Point2D(0, 0),
                p.getColor(), p.getRotationCenter(), p.getRotation());

        return new Group(ShapeBuilderJFx.createScenePolygon(p));
    }

    private Object getShadowShape(ShapeGroup g) {
        Group grp = new Group();

        for (ShapeI shape: g.getChildren()) {
            Group JFxGroup = (Group) getShadowShape(shape);
            JFxGroup.setTranslateX(shape.getPosition().x - g.getPosition().x);
            JFxGroup.setTranslateY(shape.getPosition().y - g.getPosition().y);
            grp.getChildren().add(JFxGroup);
        }

        return grp;
    }
}
