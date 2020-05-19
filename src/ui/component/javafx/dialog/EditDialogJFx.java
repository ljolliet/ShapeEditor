package ui.component.javafx.dialog;

import editor.edition.ShapeEditionDialog;
import editor.shapes.Shape;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.ApplicationI;
import ui.component.DialogBox;
import ui.Mediator;

public class EditDialogJFx extends GridPane implements DialogBox {
    private static final double DIALOG_MIN_WIDTH = 500.;
    private static final double DIALOG_MIN_HEIGHT = 200.;
    private static final double DIALOG_PADDING = 10.;
    private static final double ELEMENTS_MARGIN = 5.;
    private Mediator mediator;

    public EditDialogJFx(){
        super();
        this.setMinSize(DIALOG_MIN_WIDTH, DIALOG_MIN_HEIGHT);
        this.setPadding(new Insets(DIALOG_PADDING));
        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(ELEMENTS_MARGIN);
        this.setHgap(ELEMENTS_MARGIN);
        this.setButtons();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "EditDialog";
    }

    private void setButtons() {
        Button okButton = new Button("OK");
        okButton.setOnAction(actionEvent -> mediator.applyAndQuitEdit());
        this.add(okButton, 2, 9);

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(actionEvent -> mediator.applyEdit());
        this.add(applyButton, 3, 9);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(actionEvent -> mediator.cancelEdit());
        this.add(cancelButton, 4, 9);
    }

    void addColorToGridPane(ShapeEditionDialog shapeED) {
        editor.utils.Color originalColor;

        originalColor = shapeED.getTarget().getColor();
        if (originalColor == null)
            originalColor = new editor.utils.Color(0, 0, 0, 0);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.rgb(originalColor.r, originalColor.g, originalColor.b));
        colorPicker.setOnAction(event ->
            shapeED.setColor(colorFromJFxColor(colorPicker.getValue())));

        final Label label = new Label("Color");
        this.add(label, 0, 0);
        this.add(colorPicker, 1, 0);
    }
    void addPositionToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> posXSpinner = new Spinner<>();
        posXSpinner.setMaxWidth(75);
        posXSpinner.setEditable(true);

        final double initialValueX = shapeED.getTarget().getPosition().x;
        posXSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        posXSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setPositionX(newValue));

        Spinner<Double> posYSpinner = new Spinner<>();
        posYSpinner.setMaxWidth(75);
        posYSpinner.setEditable(true);

        final double initialValueY = shapeED.getTarget().getPosition().y;
        posYSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_HEIGHT, initialValueY));
        posYSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setPositionY(newValue));

        HBox h = new HBox();
        h.setSpacing(10);
        h.getChildren().addAll(posXSpinner, posYSpinner);
        final Label label = new Label("Position");
        this.add(label, 0, 1);
        this.add(h, 1, 1);
    }

    void addRotationToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> rotationSpinner = new Spinner<>();
        rotationSpinner.setEditable(true);

        final double initialValue = shapeED.getTarget().getRotation();
        rotationSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_ROTATION, Shape.MAX_ROTATION, initialValue));
        rotationSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setRotation(newValue));

        final Label label = new Label("Rotation");
        this.add(label, 0, 2);
        this.add(rotationSpinner, 1, 2);
    }

    void addTranslationToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> transWidthSpinner = new Spinner<>();
        transWidthSpinner.setMaxWidth(75);
        transWidthSpinner.setEditable(true);

        final double initialValueX;
        if (shapeED.getTranslation() != null)
            initialValueX = shapeED.getTarget().getTranslation().width;
        else
            initialValueX = 0;

        transWidthSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        transWidthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setTranslationWidth(newValue));

        Spinner<Double> transHeightSpinner = new Spinner<>();
        transHeightSpinner.setMaxWidth(75);
        transHeightSpinner.setEditable(true);

        final double initialValueY;
        if(shapeED.getTranslation() != null)
            initialValueY = shapeED.getTarget().getTranslation().height;
        else
            initialValueY = 0;

        transHeightSpinner.setValueFactory
                (new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueY));
        transHeightSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setTranslationHeight(newValue));

        HBox h = new HBox();
        h.setSpacing(10);
        h.getChildren().addAll(transWidthSpinner, transHeightSpinner);
        final Label label = new Label("Translation");
        this.add(label, 0, 3);
        this.add(h, 1, 3);
    }

    void addRotationCenterToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> rotateCenterXSpinner = new Spinner<>();
        rotateCenterXSpinner.setMaxWidth(75);
        rotateCenterXSpinner.setEditable(true);

        final double initialValueX;
        if(shapeED.getRotationCenter() != null)
            initialValueX = shapeED.getTarget().getRotationCenter().x;
        else
            initialValueX = 0;

        rotateCenterXSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        rotateCenterXSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setRotationCenterX(newValue));

        Spinner<Double> rotateCenterYSpinner = new Spinner<>();
        rotateCenterYSpinner.setMaxWidth(75);
        rotateCenterYSpinner.setEditable(true);

        final double initialValueY;
        if(shapeED.getRotationCenter() != null)
            initialValueY = shapeED.getTarget().getRotationCenter().y;
        else
            initialValueY = 0;

        rotateCenterYSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_HEIGHT, initialValueY));
        rotateCenterYSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.setRotationCenterY(newValue));

        HBox h = new HBox();
        h.setSpacing(10);
        h.getChildren().addAll(rotateCenterXSpinner, rotateCenterYSpinner);
        final Label label = new Label("Rotation Center");
        this.add(label, 0, 4);
        this.add(h, 1, 4);
    }

    editor.utils.Color colorFromJFxColor(Color value) {
        return new editor.utils.Color((int) (value.getRed()*255),
                (int)(value.getGreen()*255),
                (int) (value.getBlue()*255),
                value.getOpacity());
    }

}
