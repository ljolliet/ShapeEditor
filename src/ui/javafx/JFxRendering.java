package ui.javafx;

import editor.core.Editor;
import editor.edition.EditionDialogI;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.save.io.IOManager;
import editor.shapes.*;
import editor.utils.EditorManagementException;
import editor.utils.Point2D;
import editor.utils.SelectionRectangle;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.ApplicationI;
import ui.Component;
import ui.Rendering;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class JFxRendering implements Rendering {

    private ToolbarJFx toolbar;
    private int toolbarRowCount;
    private RowConstraints rowConstraints;

    private RootJFx root;

    private WindowPaneJFx windowPane;

    /* Edition */
    private ContextMenu contextMenu;
    private Stage editStage;
    private GridPane editGridPane;
    private Scene editScene;

    /* Drag & drop */
    private boolean fromToolbar;

    /* Shadow shape */
    private Group shadowShape;
    private Group shadowGroup;
    private Point2D shadowShapeThreshold;


    public JFxRendering() {
        this.fromToolbar = false;
        this.initToolbar();
        this.initEdition();
    }

    private void initToolbar() {
        this.toolbarRowCount = 0;
        this.rowConstraints = new RowConstraints(ApplicationI.TOOLBAR_CELL_HEIGHT);
    }

    private void initEdition() {
        this.contextMenu = new ContextMenu();
        this.editGridPane = new GridPane();
        this.editStage = new Stage();
        this.editScene = new Scene(editGridPane);
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
    public void setEditionDialog(ShapeEditionDialog shapeED) {
        addColorToGridPane(shapeED);
        addPositionToGridPane(shapeED);
        addRotationToGridPane(shapeED);
    }
    @Override
    public void setEditionDialog(RectangleEditionDialog recED) {
        addWidthToGridPane(recED);
        addHeightToGridPane(recED);
        addBorderRadiusGridPane(recED);
        addEditToDialog(recED);
    }

    @Override
    public void setEditionDialog(PolygonEditionDialog polED) {
        addSideLenghtGridPane(polED);
        addNbSideGridPane(polED);
        addEditToDialog(polED);
    }

    @Override
    public void setEditionDialog(ShapeGroup polED) {

    }

    @Override
    public void showEditionDialog(Point2D position) {
        contextMenu.show(this.root, position.x, position.y);
    }

    @Override
    public void hideEditionDialog(){
        contextMenu.hide();
    }

    private void addEditToDialog(RectangleEditionDialog recED){
        contextMenu.getItems().clear();
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> setRectangleDialog(recED));
        contextMenu.getItems().add(edit);
    }

    private void addGroupToDialog(){
        contextMenu.getItems().clear();
        MenuItem groupShape = new MenuItem("Group");
        Editor editor = Editor.getInstance();
        groupShape.setOnAction(e -> {
            Shape group = new ShapeGroup();
            for(ShapeI s : editor.getScene().getSelectedShapes()){
                group.addShape(s);
                editor.removeShapeFromScene((Shape) s);
            }
            editor.addShapeToScene(group);
        });
        contextMenu.getItems().add(groupShape);

    }

    private void addEditToDialog(PolygonEditionDialog polED){
        contextMenu.getItems().clear();
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> setPolygonDialog(polED));
        contextMenu.getItems().add(edit);
    }

    private void setGridPane(ShapeEditionDialog shapeED){
        //Setting size for the pane
        editGridPane.setMinSize(500, 200);
        //Setting the padding
        editGridPane.setPadding(new Insets(10, 10, 10, 10));
        //Setting the vertical and horizontal gaps between the columns
        editGridPane.setVgap(5);
        editGridPane.setHgap(5);

        Button buttonOK = new Button("OK");
        buttonOK.setOnAction(actionEvent -> {
            shapeED.applyEdition();
            editStage.close();
        });
        Button buttonApply = new Button("Apply");
        buttonApply.setOnAction(actionEvent -> {
            shapeED.applyEdition();
        });
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(actionEvent -> {
            editStage.close();
        });

        editGridPane.add(buttonOK, 2, 6);
        editGridPane.add(buttonApply, 3, 6);
        editGridPane.add(buttonCancel, 4, 6);
    }

    @Override
    public void setRectangleDialog(RectangleEditionDialog recED){
        editGridPane.getChildren().clear();
        setGridPane(recED);
        setEditionDialog((ShapeEditionDialog)recED);
        setEditionDialog(recED);

        //Show gridpane
        editStage.setTitle("Rectangle Edition");
        editStage.setScene(editScene);
        editStage.showAndWait();
    }

    @Override
    public void setPolygonDialog(PolygonEditionDialog polED){
        editGridPane.getChildren().clear();
        setGridPane(polED);
        setEditionDialog((ShapeEditionDialog)polED);
        setEditionDialog(polED);

        //Show gridpane
        editStage.setTitle("Polygon Edition");
        editStage.setScene(editScene);
        editStage.showAndWait();
    }

    @Override
    public void showGroupDialog(Point2D position) {
        addGroupToDialog();
        this.showEditionDialog(position);
    }

    ///////////////////////////
    //         Shape         //
    ///////////////////////////
    private void addColorToGridPane(ShapeEditionDialog shapeED) {
        final editor.utils.Color originalColor = shapeED.getTarget().getColor();
        final ColorPicker colorPicker = new ColorPicker(Color.rgb(originalColor.r, originalColor.g, originalColor.b));
        colorPicker.setOnAction(event -> {
            editor.utils.Color c = colorFromJFxColor(colorPicker.getValue());
            shapeED.color = c;
        });
        final Label label = new Label("Color");
        editGridPane.add(label, 0, 0);
        editGridPane.add(colorPicker, 1, 0);
    }

    private void addPositionToGridPane(ShapeEditionDialog shapeED) {
        final Spinner<Double> spinnerX = new Spinner<>();
        spinnerX.setMaxWidth(75);
        spinnerX.setEditable(true);
        final double initialValueX = shapeED.getTarget().getPosition().x;
        final double maxValueX = ApplicationI.SCENE_WIDTH;
        spinnerX.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueX, initialValueX));
        spinnerX.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.posX = newValue);

        final Spinner<Double> spinnerY = new Spinner<>();
        spinnerY.setMaxWidth(75);
        spinnerY.setEditable(true);
        final double initialValueY = shapeED.getTarget().getPosition().y;
        final double maxValueY = ApplicationI.SCENE_WIDTH;
        spinnerY.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueY, initialValueY));
        spinnerX.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.posY = newValue);

        HBox h = new HBox();
        h.setSpacing(10.);
        h.getChildren().addAll(spinnerX, spinnerY);
        final Label label = new Label("Position");
        editGridPane.add(label, 0, 1);
        editGridPane.add(h, 1, 1);

    }

    private void addRotationToGridPane(ShapeEditionDialog shapeED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = shapeED.getTarget().getRotation();
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_ROTATION, Shape.MAX_ROTATION, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.rotation = newValue);
        final Label label = new Label("Rotation");
        editGridPane.add(label, 0, 2);
        editGridPane.add(spinner, 1, 2);
    }

    ///////////////////////////
    //       Rectangle       //
    ///////////////////////////

    private void addWidthToGridPane(RectangleEditionDialog recED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = recED.getTarget().getWidth();
        final double maxValue = ApplicationI.SCENE_WIDTH - recED.getTarget().getPosition().x;
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(ShapeI.MIN_SIZE, maxValue, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.width = newValue);
        final Label label = new Label("Width");
        editGridPane.add(label, 0, 3);
        editGridPane.add(spinner, 1, 3);
    }

    private void addHeightToGridPane(RectangleEditionDialog recED){
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = recED.getTarget().getHeight();
        final double maxValue = ApplicationI.SCENE_HEIGHT - recED.getTarget().getPosition().y;
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(ShapeI.MIN_SIZE, maxValue, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.height = newValue);
        final Label label = new Label("Height");
        editGridPane.add(label, 0, 4);
        editGridPane.add(spinner, 1, 4);
    }

    private void addBorderRadiusGridPane(RectangleEditionDialog recED) {
        final Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        final int initialValue = recED.getTarget().getBorderRadius();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(ShapeI.MIN_RADIUS, ShapeI.MAX_RADIUS, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.borderRadius = newValue);
        final Label label = new Label("Border Radius");
        editGridPane.add(label, 0, 5);
        editGridPane.add(spinner,1, 5);
    }

    ///////////////////////////
    //        Polygon        //
    ///////////////////////////

    private void addSideLenghtGridPane(PolygonEditionDialog polED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = polED.getTarget().getSideLength();
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_RADIUS, Shape.MAX_RADIUS, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.sideLength = newValue);
        final Label label = new Label("Side length");
        editGridPane.add(label, 0, 3);
        editGridPane.add(spinner,1, 3);
    }

    private void addNbSideGridPane(PolygonEditionDialog polED){
        final Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        final int initialValue = polED.getTarget().getNbSides();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_RADIUS, Shape.MAX_RADIUS, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.nbSides = newValue);
        final Label label = new Label("Sides number");
        editGridPane.add(label, 0, 4);
        editGridPane.add(spinner,1, 4);
    }

    private editor.utils.Color colorFromJFxColor(Color value) {
        return new editor.utils.Color((int) (value.getRed()*255),
                (int)(value.getGreen()*255),
                (int) (value.getBlue()*255),
                value.getOpacity());
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

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "Toolbar":
                toolbar = (ToolbarJFx) component;
                break;
            case "Root":
                root = (RootJFx) component;
                break;
            case "windowPane":
                windowPane = (WindowPaneJFx) component;
                this.initShadowShape();
                break;
        }
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

        File file = fileChooser.showSaveDialog(null);//TODO put primary Stage
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

        File file = fileChooser.showOpenDialog(null);   //TODO put primary stage
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
    public void dragFromScene(Point2D coords) {
        ShapeI shape = new ShapeGroup();
        for (ShapeI s: Editor.getInstance().getScene().getSelectedShapes())
            shape.addShape(s);

        // Shadow shape
        shadowShape = (Group) getShadowShape(shape);
        shadowGroup.getChildren().add(shadowShape);
        shadowGroup.toFront();
        shadowShape.setVisible(false);
        this.shadowShapeThreshold = new Point2D(shape.getPosition().x - coords.x,
                shape.getPosition().y - coords.y);

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
    public void dropInScene(Point2D coords) {
        Editor editor = Editor.getInstance();
        // If from toolbar --> clone the shape
        if (fromToolbar) {
            // Clone the shape and paste it to the scene
            ShapeI newShape = editor.getShapeDragged().clone();
            newShape.setPosition(coords);
            editor.addShapeToScene((Shape) newShape);
        }
        // Else --> update position
        else {
            editor.getShapeDragged().setPosition(new Point2D(coords.x + shadowShapeThreshold.x,
                    coords.y + shadowShapeThreshold.y));
        }

        editor.setShapeDragged(null);
    }

    @Override
    public void dropInTrash() {
        Editor editor = Editor.getInstance();
        ShapeI shape = editor.getShapeDragged();

        if (shape != null) {
            if(editor.toolbarContains(shape))
                editor.removeShapeFromToolbar(shape);
            else if(editor.sceneContains((Shape) shape))
                editor.removeShapeFromScene((Shape) shape);
            else
                throw new EditorManagementException("Trying to delete an unknown shape");
        }

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
    public void showEditionDialog(ShapeI shape, Point2D coords) {
        // Create edition dialog
        EditionDialogI ed = shape.createEditionDialog();
        ed.setPosition(coords);
        // Draw it
        ed.draw(this);
    }

    @Override
    public void showGroupEditionDialog(Point2D point) {
        // TODO
    }

    @Override
    public void moveShadowShape(Point2D coords) {
        if (shadowShape != null) {
            if (!shadowShape.isVisible())
                shadowShape.setVisible(true);

            shadowShape.setTranslateX(coords.x + shadowShapeThreshold.x);
            shadowShape.setTranslateY(coords.y + shadowShapeThreshold.y);
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
}
