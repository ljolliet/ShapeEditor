package ui;

import editor.core.Editor;
import editor.shapes.*;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.utils.Point2D;
import editor.utils.SelectionRectangle;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


public class JFxRendering implements Rendering {

    private VBox toolbarBox;
    private Group root;
    private ContextMenu contextMenu;
    private Stage editStage;
    private GridPane editGridPane;
    private Scene editScene;

    JFxRendering(VBox toolbarBox, Group root) {
        this.toolbarBox = toolbarBox;
        this.root = root;
        this.contextMenu = new ContextMenu();
        this.editGridPane = new GridPane();
        this.editStage = new Stage();
        this.editScene = new Scene(editGridPane);

    }

    private void init() {
        // Remove all shapes of the scene and of the toolbar
        root.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
        toolbarBox.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
    }

    @Override
    public void drawEditor() {
        Editor editor = Editor.getInstance();
        // Clear shapes
        this.init();

        // Draw scene
        for (ShapeI s: editor.getScene().getShapes())
            s.drawInScene(this);

        // Draw toolbar
        for (ShapeI s: editor.getToolbar().getShapes())
            s.drawInToolbar(this);
    }

    @Override
    public void drawSelectionFrame(){
        Editor editor = Editor.getInstance();
        drawEditor();
        SelectionRectangle s = (SelectionRectangle)editor.getSelectionShape();

        javafx.scene.shape.Rectangle selectionFrame = new javafx.scene.shape.Rectangle(s.getWidth(), s.getHeight());
        selectionFrame.setStroke(Color.DARKRED);
        selectionFrame.setFill(Color.TRANSPARENT);
        selectionFrame.setX(s.getPosition().x);
        selectionFrame.setY(s.getPosition().y);

        root.getChildren().add(selectionFrame);
    }

    @Override
    public void drawInScene(Rectangle r) {
        javafx.scene.shape.Rectangle rectangle = createSceneRectangle(r);
        root.getChildren().add(rectangle);
    }

    @Override
    public void drawInScene(Polygon p) {
        javafx.scene.shape.Polygon polygon = createScenePolygon(p);

        root.getChildren().add(polygon);
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double max = Math.max(r.getHeight(), r.getWidth());
        double ratio = (ApplicationI.TOOLBAR_WIDTH / 2d) / max;

        javafx.scene.shape.Rectangle rectangle = createToolbarRectangle(r, ratio);

        toolbarBox.getChildren().add(rectangle);
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_WIDTH / 4d;

        javafx.scene.shape.Polygon polygon = createToolbarPolygon(p, radius);

        toolbarBox.getChildren().add(polygon);
    }

    @Override
    public void drawInToolbar(ShapeGroup group) {
        double max = Math.max(group.getHeight(), group.getWidth());
        double ratio = (ApplicationI.TOOLBAR_WIDTH / 2d) / max;

        // TODO
    }

    @Override
    public Object getShadowShape(ShapeI shape) {
        if (shape instanceof Rectangle)
            return getShadowShape((Rectangle) shape);
        if (shape instanceof Polygon)
            return getShadowShape((Polygon) shape);
//        if (shape instanceof ShapeGroup)
//            return getShadowShape(shape.getChildren().iterator().next());

        return null;
    }

    @Override
    public void setEditionGridPane(ShapeEditionDialog shapeED) {
        addColorToGridPane(shapeED);
        addPositionToGridPane(shapeED);
        addRotationToGridPane(shapeED);
    }
    @Override
    public void setEditionGridPane(RectangleEditionDialog recED) {
        addWidthToGridPane(recED);
        addHeightToGridPane(recED);
        addBorderRadiusGridPane(recED);
        addEditToDialog(recED);
    }

    @Override
    public void setEditionGridPane(PolygonEditionDialog polED) {
        addSideLenghtGridPane(polED);
        addNbSideGridPane(polED);
        addEditToDialog(polED);
    }

    @Override
    public void setEditionGridPane(ShapeGroup polED) {

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
        edit.setOnAction(event -> setRectangleGridPane(recED));
        contextMenu.getItems().add(edit);
    }

    private void addEditToDialog(PolygonEditionDialog polED){
        contextMenu.getItems().clear();
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> setPolygonGridPane(polED));
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
    public void setRectangleGridPane(RectangleEditionDialog recED){
        editGridPane.getChildren().clear();
        setGridPane(recED);
        setEditionGridPane((ShapeEditionDialog)recED);
        setEditionGridPane(recED);

        //Show gridpane
        editStage.setTitle("Rectangle Edition");
        editStage.setScene(editScene);
        editStage.showAndWait();
    }

    @Override
    public void setPolygonGridPane(PolygonEditionDialog polED){
        editGridPane.getChildren().clear();
        setGridPane(polED);
        setEditionGridPane((ShapeEditionDialog)polED);
        setEditionGridPane(polED);

        //Show gridpane
        editStage.setTitle("Polygon Edition");
        editStage.setScene(editScene);
        editStage.showAndWait();
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
        final double maxValueX = ApplicationI.SCENE_WIDTH;//TODO - shapeED.getTarget().getWidth();
        spinnerX.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueX, initialValueX));
        spinnerX.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.posX = newValue);

        final Spinner<Double> spinnerY = new Spinner<>();
        spinnerY.setMaxWidth(75);
        spinnerY.setEditable(true);
        final double initialValueY = shapeED.getTarget().getPosition().y;
        final double maxValueY = ApplicationI.SCENE_WIDTH; //TODO  - shapeED.getTarget().getWidth();
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
        return new editor.utils.Color((int)value.getRed()*255,
                (int)value.getGreen()*255,
                (int)value.getBlue()*255);
    }

    ///////////////////////////
    //     Create shapes     //
    ///////////////////////////

    private Object getShadowShape(Rectangle r) {
        javafx.scene.shape.Rectangle rectangle = createSceneRectangle(r);
        rectangle.setX(0);
        rectangle.setY(0);
        return rectangle;
    }

    private Object getShadowShape(Polygon p) {
        p = new Polygon(p.getNbSides(), p.getSideLength(), new Point2D(0, 0),
                p.getColor(), p.getRotationCenter(), p.getRotation());

        return createScenePolygon(p);
    }

    private javafx.scene.shape.Rectangle createSceneRectangle(Rectangle r) {
        Editor editor = Editor.getInstance();

        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(r.getWidth(), r.getHeight());

        // Color
        Color color = Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b);
        rectangle.setFill(color);

        // Add stroke if selected
        if (editor.getScene().getSelectedShapes().contains(r))
            rectangle.setStroke(color.darker());

        // Rotation
        Rotate rotate = new Rotate(r.getRotation());
        rotate.setPivotX(r.getX() + r.getRotationCenter().x);
        rotate.setPivotY(r.getY() + r.getRotationCenter().y);
        rectangle.getTransforms().add(rotate);

        // Radius
        rectangle.setArcHeight(r.getBorderRadius());
        rectangle.setArcWidth(r.getBorderRadius());

        // Set coords
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());

        // Set Translation
        rectangle.setTranslateX(r.getTranslation().width);
        rectangle.setTranslateY(r.getTranslation().height);

        return rectangle;
    }

    private javafx.scene.shape.Polygon createScenePolygon(Polygon p) {
        Editor editor = Editor.getInstance();

        double[] points = getPolygonPoints(p.getPoints(), p.getNbSides());
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);

        // Color
        Color color = Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b);
        polygon.setFill(color);

        // Add stroke if selected
        if (editor.getScene().getSelectedShapes().contains(p))
            polygon.setStroke(color.darker());

        // Rotation
        Rotate rotate = new Rotate(p.getRotation());
        rotate.setPivotX(p.getX() + p.getRotationCenter().x);
        rotate.setPivotY(p.getY() + p.getRotationCenter().y);
        polygon.getTransforms().add(rotate);

        // Translation
        polygon.setTranslateX(p.getTranslation().width);
        polygon.setTranslateY(p.getTranslation().height);

        return polygon;
    }

    private javafx.scene.shape.Rectangle createToolbarRectangle(Rectangle r, double ratio) {
        javafx.scene.shape.Rectangle rectangle =
                new javafx.scene.shape.Rectangle(r.getWidth() * ratio, r.getHeight() * ratio);

        // Color
        Color color = Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b);
        rectangle.setFill(color);

        // Rotation
        rectangle.getTransforms().clear();
        rectangle.setRotate(r.getRotation());

        // Border radius
        rectangle.setArcWidth(r.getBorderRadius() * ratio);
        rectangle.setArcHeight(r.getBorderRadius() * ratio);

        return rectangle;
    }

    private javafx.scene.shape.Polygon createToolbarPolygon(Polygon p, double radius) {
        double[] points = getPolygonPoints(p.getPoints(radius), p.getNbSides());
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);

        // Color
        Color color = Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b);
        polygon.setFill(color);

        // Rotation
        polygon.getTransforms().clear();
        polygon.setRotate(p.getRotation());

        return polygon;
    }

    private double[] getPolygonPoints(Point2D[] pre_points, int nbSides) {
        double[] points = new double[nbSides * 2];
        int cpt = 0;

        for (int i = 0; i < nbSides; i++) {
            points[cpt++] = pre_points[i].x;
            points[cpt++] = pre_points[i].y;
        }

        return points;
    }
}
