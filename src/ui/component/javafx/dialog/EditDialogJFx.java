package ui.component.javafx.dialog;

import editor.edition.ShapeEditionDialog;
import editor.shapes.Shape;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.ApplicationI;
import ui.Mediator;
import ui.component.DialogBox;

public class EditDialogJFx extends GridPane implements DialogBox {
    private static final double DIALOG_MIN_WIDTH = 500.;
    private static final double DIALOG_MIN_HEIGHT = 200.;
    private static final double DIALOG_PADDING = 10.;
    private static final double ELEMENTS_MARGIN = 5.;
    private static final double ELEMENTS_SPACING = 10.;
    private static final double MAX_WIDTH = 75.;

    private Mediator mediator;
    final ShapeEditionDialog editionDialog;
    int rowID = 0;
    int columnID = 0;
    int buttonColumnID = 2;

    public EditDialogJFx(ShapeEditionDialog ed){
        super();
        editionDialog = ed;
        this.addColorToGridPane();
        this.addPositionToGridPane();
        this.addRotationToGridPane();
        this.addTranslationToGridPane();
        this.addRotationCenterToGridPane();

        this.setMinSize(DIALOG_MIN_WIDTH, DIALOG_MIN_HEIGHT);
        this.setPadding(new Insets(DIALOG_PADDING));
        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(ELEMENTS_MARGIN);
        this.setHgap(ELEMENTS_MARGIN);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "EditDialog";
    }

    void setButtons() {
        Button okButton = new Button("OK");
        okButton.setOnAction(actionEvent -> mediator.applyAndQuitEdit());
        this.add(okButton, buttonColumnID, rowID);

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(actionEvent -> mediator.applyEdit());
        this.add(applyButton, ++buttonColumnID, rowID);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(actionEvent -> mediator.cancelEdit());
        this.add(cancelButton, ++buttonColumnID, rowID);
        rowID++;
    }

    void addColorToGridPane() {
        editor.utils.Color originalColor;

        originalColor = editionDialog.getTarget().getColor();
        if (originalColor == null)
            originalColor = new editor.utils.Color(0, 0, 0, 0);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.rgb(originalColor.r, originalColor.g, originalColor.b));
        colorPicker.setOnAction(event ->
            editionDialog.setColor(colorFromJFxColor(colorPicker.getValue())));

        final Label label = new Label("Color");
        this.add(label, columnID, rowID);
        this.add(colorPicker, columnID + 1, rowID);
        rowID++;
    }
    void addPositionToGridPane() {
        Spinner<Double> posXSpinner = new Spinner<>();
        posXSpinner.setMaxWidth(MAX_WIDTH);
        posXSpinner.setEditable(true);

        final double initialValueX = editionDialog.getTarget().getPosition().x;
        posXSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        posXSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setPositionX(newValue));

        Spinner<Double> posYSpinner = new Spinner<>();
        posYSpinner.setMaxWidth(MAX_WIDTH);
        posYSpinner.setEditable(true);

        final double initialValueY = editionDialog.getTarget().getPosition().y;
        posYSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_HEIGHT, initialValueY));
        posYSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setPositionY(newValue));

        HBox h = new HBox();
        h.setSpacing(ELEMENTS_SPACING);
        h.getChildren().addAll(posXSpinner, posYSpinner);
        final Label label = new Label("Position");
        this.add(label, columnID, rowID);
        this.add(h, columnID + 1, rowID);
        rowID++;
    }

    void addRotationToGridPane() {
        Spinner<Double> rotationSpinner = new Spinner<>();
        rotationSpinner.setEditable(true);

        final double initialValue = editionDialog.getTarget().getRotation();
        rotationSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_ROTATION, Shape.MAX_ROTATION, initialValue));
        rotationSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setRotation(newValue));

        final Label label = new Label("Rotation");
        this.add(label, columnID, rowID);
        this.add(rotationSpinner, columnID + 1, rowID);
        rowID++;
    }

    void addTranslationToGridPane() {
        Spinner<Double> transWidthSpinner = new Spinner<>();
        transWidthSpinner.setMaxWidth(MAX_WIDTH);
        transWidthSpinner.setEditable(true);

        final double initialValueX;
        if (editionDialog.getTranslation() != null)
            initialValueX = editionDialog.getTarget().getTranslation().width;
        else
            initialValueX = 0;

        transWidthSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        transWidthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setTranslationWidth(newValue));

        Spinner<Double> transHeightSpinner = new Spinner<>();
        transHeightSpinner.setMaxWidth(MAX_WIDTH);
        transHeightSpinner.setEditable(true);

        final double initialValueY;
        if(editionDialog.getTranslation() != null)
            initialValueY = editionDialog.getTarget().getTranslation().height;
        else
            initialValueY = 0;

        transHeightSpinner.setValueFactory
                (new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueY));
        transHeightSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setTranslationHeight(newValue));

        HBox h = new HBox();
        h.setSpacing(ELEMENTS_SPACING);
        h.getChildren().addAll(transWidthSpinner, transHeightSpinner);
        final Label label = new Label("Translation");
        this.add(label, columnID, rowID);
        this.add(h, columnID + 1, rowID);
        rowID++;
    }

    void addRotationCenterToGridPane() {
        Spinner<Double> rotateCenterXSpinner = new Spinner<>();
        rotateCenterXSpinner.setMaxWidth(MAX_WIDTH);
        rotateCenterXSpinner.setEditable(true);

        final double initialValueX;
        if(editionDialog.getRotationCenter() != null)
            initialValueX = editionDialog.getTarget().getRotationCenter().x;
        else
            initialValueX = 0;

        rotateCenterXSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_WIDTH, initialValueX));
        rotateCenterXSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setRotationCenterX(newValue));

        Spinner<Double> rotateCenterYSpinner = new Spinner<>();
        rotateCenterYSpinner.setMaxWidth(MAX_WIDTH);
        rotateCenterYSpinner.setEditable(true);

        final double initialValueY;
        if(editionDialog.getRotationCenter() != null)
            initialValueY = editionDialog.getTarget().getRotationCenter().y;
        else
            initialValueY = 0;

        rotateCenterYSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(0, ApplicationI.SCENE_HEIGHT, initialValueY));
        rotateCenterYSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                editionDialog.setRotationCenterY(newValue));

        HBox h = new HBox();
        h.setSpacing(ELEMENTS_SPACING);
        h.getChildren().addAll(rotateCenterXSpinner, rotateCenterYSpinner);
        final Label label = new Label("Rotation Center");
        this.add(label, columnID, rowID);
        this.add(h, columnID + 1, rowID);
        rowID++;
    }

    editor.utils.Color colorFromJFxColor(Color value) {
        return new editor.utils.Color((int) (value.getRed()*255),
                (int)(value.getGreen()*255),
                (int) (value.getBlue()*255),
                value.getOpacity());
    }

}
