package ui;

import editor.*;
import editor.edition.PolygonEditionDialog;
import editor.edition.RectangleEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.utils.Point2D;
import editor.utils.SelectionRectangle;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;


public class JFxRendering implements Rendering {

    private Editor editor;
    private VBox toolbarBox;
    private Group root;
    private ContextMenu contextMenu;

    JFxRendering(Editor editor, VBox toolbarBox, Group root) {
        this.editor = editor;
        this.toolbarBox = toolbarBox;
        this.root = root;
        this.contextMenu = new ContextMenu();

    }

    private void init() {
        // Remove all shapes of the scene and of the toolbar
        root.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
        toolbarBox.getChildren().removeIf(child -> child instanceof javafx.scene.shape.Shape);
    }

    @Override
    public void drawEditor() {
        // Clear shapes
        this.init();

        // Draw scene
        for (Shape s: editor.getScene().getShapes())
            s.drawInScene(this);

        // Draw toolbar
        for (Shape s: editor.getToolbar().getShapes())
            s.drawInToolbar(this);
    }

    @Override
    public void drawSelectionFrame(){
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
        javafx.scene.shape.Rectangle rectangle = createRectangle(r, r.getWidth(), r.getHeight());
        // Set coords
        rectangle.setX(r.getX());
        rectangle.setY(r.getY());
        // Set Translation
        rectangle.setTranslateX(r.getTranslation().width);
        rectangle.setTranslateY(r.getTranslation().height);

        root.getChildren().add(rectangle);
    }

    @Override
    public void drawInScene(Polygon p) {
        javafx.scene.shape.Polygon polygon = createPolygon(p, p.getRadius());
        // Set Translation
        polygon.setTranslateX(p.getTranslation().width);
        polygon.setTranslateY(p.getTranslation().height);

        root.getChildren().add(polygon);
    }

    @Override
    public void drawInToolbar(Rectangle r) {
        double max = Math.max(r.getHeight(), r.getWidth());
        double ratio = (ApplicationI.TOOLBAR_WIDTH / 2d) / max;

        javafx.scene.shape.Rectangle rectangle = createRectangle(r, r.getWidth() * ratio, r.getHeight() * ratio);
        // For toolbar, special rotation
        rectangle.getTransforms().clear();
        rectangle.setRotate(r.getRotation());
        // Set border radius
        rectangle.setArcWidth(r.getBorderRadius() * ratio);
        rectangle.setArcHeight(r.getBorderRadius() * ratio);

        toolbarBox.getChildren().add(rectangle);
    }

    @Override
    public void drawInToolbar(Polygon p) {
        double radius = ApplicationI.TOOLBAR_WIDTH / 4d;

        javafx.scene.shape.Polygon polygon = createPolygon(p, radius);
        // For toolbar, special rotation
        polygon.getTransforms().clear();
        polygon.setRotate(p.getRotation());

        toolbarBox.getChildren().add(polygon);
    }

    @Override
    public void drawEditionDialog(ShapeEditionDialog polED) {

    }

    @Override
    public void drawEditionDialog(ShapeGroup polED) {

    }

    @Override
    public void drawEditionDialog(RectangleEditionDialog recED) {
        contextMenu.getItems().clear();
        addPositionToDialog(recED);
        addWidthToDialog(recED);
        addHeightToDialog(recED);
        addRotationToDialog(recED);
        addBorderRadiusToDialog(recED);
        addColorToDialog(recED);
        contextMenu.show(this.root, recED.getPosition().x, recED.getPosition().y);
    }


    @Override
    public void drawEditionDialog(PolygonEditionDialog polED) {
        contextMenu.getItems().clear();
        addColorToDialog(polED);
        contextMenu.show(this.root, polED.getPosition().x, polED.getPosition().y);
    }

    @Override
    public void hideEditionDialog(){
        contextMenu.hide();
    }



    /*      Rectangle       */

    private void addWidthToDialog(RectangleEditionDialog recED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = recED.getTarget().getWidth();
        final double maxValue = ApplicationI.SCENE_WIDTH - recED.getTarget().getPosition().x;
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIZE, maxValue, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setWidth(newValue));
        final MenuItem item = new MenuItem("Width",spinner);
        contextMenu.getItems().add(item);
    }

    private void addHeightToDialog(RectangleEditionDialog recED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = recED.getTarget().getHeight();
        final double maxValue = ApplicationI.SCENE_HEIGHT - recED.getTarget().getPosition().y;
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIZE, maxValue, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setHeight(newValue));
        final MenuItem item = new MenuItem("Height",spinner);
        contextMenu.getItems().add(item);
    }

    private void addBorderRadiusToDialog(RectangleEditionDialog recED) {
        final Spinner<Integer> spinner = new Spinner<>();
        spinner.setEditable(true);
        final int initialValue = recED.getTarget().getBorderRadius();
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_RADIUS, Shape.MAX_RADIUS, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setBorderRadius(newValue));
        final MenuItem item = new MenuItem("Border Radius",spinner);
        contextMenu.getItems().add(item);
    }

    /*      Shape       */

    private void addPositionToDialog(ShapeEditionDialog shapeED) {
        final Spinner<Double> spinnerX = new Spinner<>();
        spinnerX.setMaxWidth(75);
        spinnerX.setEditable(true);
        final double initialValueX = shapeED.getTarget().getPosition().x;
        final double maxValueX = ApplicationI.SCENE_WIDTH;//TODO - shapeED.getTarget().getWidth();
        spinnerX.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueX, initialValueX));
        spinnerX.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setPositionX(newValue));

        final Spinner<Double> spinnerY = new Spinner<>();
        spinnerY.setMaxWidth(75);
        spinnerY.setEditable(true);
        final double initialValueY = shapeED.getTarget().getPosition().y;
        final double maxValueY = ApplicationI.SCENE_WIDTH; //TODO  - shapeED.getTarget().getWidth();
        spinnerY.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueY, initialValueY));
        spinnerY.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setPositionY(newValue));

        HBox h = new HBox();
        h.setSpacing(10.);
        h.getChildren().addAll(spinnerX, spinnerY);
        final MenuItem item = new MenuItem("Position",h);
        contextMenu.getItems().add(item);
    }

    private void addRotationToDialog(ShapeEditionDialog shapeED) {
        final Spinner<Double> spinner = new Spinner<>();
        spinner.setEditable(true);
        final double initialValue = shapeED.getTarget().getRotation();
        spinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_ROTATION, Shape.MAX_ROTATION, initialValue));
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setRotation(newValue));
        final MenuItem item = new MenuItem("Rotation",spinner);
        contextMenu.getItems().add(item);
    }

    private void addColorToDialog(ShapeEditionDialog shapeED) {
        final editor.utils.Color originalColor = shapeED.getTarget().getColor();
        final ColorPicker colorPicker = new ColorPicker(Color.rgb(originalColor.r, originalColor.g, originalColor.b ));
        final MenuItem item = new MenuItem("Color",colorPicker);
        item.setOnAction(event -> {
            editor.utils.Color c = colorFromJFxColor(colorPicker.getValue());
            shapeED.setColor(c);
        });
        contextMenu.getItems().add(item);
    }

    private editor.utils.Color colorFromJFxColor(Color value) {
        return new editor.utils.Color((int)value.getRed()*255,
                (int)value.getGreen()*255,
                (int)value.getBlue()*255);
    }

    private javafx.scene.shape.Rectangle createRectangle(Rectangle r, double width, double height) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(width, height);
        // Color
        Color color = Color.rgb(r.getColor().r, r.getColor().g, r.getColor().b);
        rectangle.setFill(color);
        if(editor.getScene().getSelectedShapes().contains(r))
            rectangle.setStroke(color.darker());
        // Rotation
        Rotate rotate = new Rotate(r.getRotation());
        rotate.setPivotX(r.getX() + r.getRotationCenter().x);
        rotate.setPivotY(r.getY() + r.getRotationCenter().y);
        rectangle.getTransforms().add(rotate);
        // Radius
        rectangle.setArcHeight(r.getBorderRadius());
        rectangle.setArcWidth(r.getBorderRadius());

        return rectangle;
    }

    private javafx.scene.shape.Polygon createPolygon(Polygon p, double radius) {
        double[] points = getPolygonPoints(p.getPoints(radius), p.getNbSides());

        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(points);
        // Color
        Color color = Color.rgb(p.getColor().r, p.getColor().g, p.getColor().b);
        polygon.setFill(color);
        if(editor.getScene().getSelectedShapes().contains(p))
            polygon.setStroke(color.darker());
        // Rotation
        Rotate rotate = new Rotate(p.getRotation());
        rotate.setPivotX(p.getX() + p.getRotationCenter().x);
        rotate.setPivotY(p.getY() + p.getRotationCenter().y);
        polygon.getTransforms().add(rotate);

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
