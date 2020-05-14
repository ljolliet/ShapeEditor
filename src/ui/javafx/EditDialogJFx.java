package ui.javafx;

import editor.edition.GroupEditionDialog;
import editor.edition.ShapeEditionDialog;
import editor.shapes.Shape;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import ui.ApplicationI;
import ui.DialogBox;
import ui.Mediator;

public class EditDialogJFx extends GridPane implements DialogBox {
    private Mediator mediator;


    public EditDialogJFx(){
        super();
        this.setMinSize(500, 200);
        this.setPadding(new Insets(10, 10, 10, 10));
        //Setting the vertical and horizontal gaps between the columns
        this.setVgap(5);
        this.setHgap(5);
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
        this.add(okButton, 2, 6);

        Button applyButton = new Button("Apply");
        applyButton.setOnAction(actionEvent -> mediator.applyEdit());
        this.add(applyButton, 3, 6);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(actionEvent -> mediator.cancelEdit());
        this.add(cancelButton, 4, 6);
    }

    void addColorToGridPane(ShapeEditionDialog shapeED) {
        final editor.utils.Color originalColor;
        if (shapeED instanceof GroupEditionDialog)
            originalColor = new editor.utils.Color(0, 0, 0, 0);
        else
            originalColor = shapeED.getTarget().getColor();
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.rgb(originalColor.r, originalColor.g, originalColor.b));
        colorPicker.setOnAction(event -> {
            editor.utils.Color c = colorFromJFxColor(colorPicker.getValue());
            shapeED.color = c;
        });
        final Label label = new Label("Color");
        this.add(label, 0, 0);
        this.add(colorPicker, 1, 0);
    }
    void addPositionToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> posXSpinner = new Spinner();
        posXSpinner.setMaxWidth(75);
        posXSpinner.setEditable(true);
        final double initialValueX = shapeED.getTarget().getPosition().x;
        final double maxValueX = ApplicationI.SCENE_WIDTH;
        posXSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueX, initialValueX));
        posXSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.posX = newValue);

        Spinner<Double> posYSpinner = new Spinner();
        posYSpinner.setMaxWidth(75);
        posYSpinner.setEditable(true);
        final double initialValueY = shapeED.getTarget().getPosition().y;
        final double maxValueY = ApplicationI.SCENE_WIDTH;
        posYSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0., maxValueY, initialValueY));
        posYSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.posY = newValue);

        HBox h = new HBox();
        h.setSpacing(10.);
        h.getChildren().addAll(posXSpinner, posYSpinner);
        final Label label = new Label("Position");
        this.add(label, 0, 1);
        this.add(h, 1, 1);
    }

    void addRotationToGridPane(ShapeEditionDialog shapeED) {
        Spinner<Double> rotationSpinner = new Spinner();
        rotationSpinner.setEditable(true);
        final double initialValue = shapeED.getTarget().getRotation();
        rotationSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_ROTATION, Shape.MAX_ROTATION, initialValue));
        rotationSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                shapeED.rotation = newValue);
        final Label label = new Label("Rotation");
        this.add(label, 0, 2);
        this.add(rotationSpinner, 1, 2);
    }

    editor.utils.Color colorFromJFxColor(Color value) {
        return new editor.utils.Color((int) (value.getRed()*255),
                (int)(value.getGreen()*255),
                (int) (value.getBlue()*255),
                value.getOpacity());
    }

}
