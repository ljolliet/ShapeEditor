package ui.component.javafx.dialog;

import editor.edition.RectangleEditionDialog;
import editor.shapes.ShapeI;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import ui.ApplicationI;

public class RectangleEditJFx extends EditDialogJFx {
    public RectangleEditJFx(RectangleEditionDialog recED) {
        super();
        super.addColorToGridPane(recED);
        super.addPositionToGridPane(recED);
        super.addRotationToGridPane(recED);
        this.addWidthToGridPane(recED);
        this.addHeightToGridPane(recED);
        this.addBorderRadiusGridPane(recED);
    }

    void addWidthToGridPane(RectangleEditionDialog recED) {
        Spinner<Double> widthSpinner = new Spinner<>();
        widthSpinner.setEditable(true);
        final double initialValue = recED.getTarget().getWidth();
        final double maxValue = ApplicationI.SCENE_WIDTH - recED.getTarget().getPosition().x;
        widthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(ShapeI.MIN_SIZE, maxValue, initialValue));
        widthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.width = newValue);
        final Label label = new Label("Width");
        this.add(label, 0, 3);
        this.add(widthSpinner, 1, 3);
    }

    void addHeightToGridPane(RectangleEditionDialog recED){
        Spinner<Double> heightSpinner = new Spinner<>();
        heightSpinner.setEditable(true);
        final double initialValue = recED.getTarget().getHeight();
        final double maxValue = ApplicationI.SCENE_HEIGHT - recED.getTarget().getPosition().y;
        heightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(ShapeI.MIN_SIZE, maxValue, initialValue));
        heightSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.height = newValue);
        final Label label = new Label("Height");
        this.add(label, 0, 4);
        this.add(heightSpinner, 1, 4);
    }

    void addBorderRadiusGridPane(RectangleEditionDialog recED) {
        Spinner<Integer> borderRadiusSpinner = new Spinner<>();
        borderRadiusSpinner.setEditable(true);
        final int initialValue = recED.getTarget().getBorderRadius();
        borderRadiusSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(ShapeI.MIN_RADIUS, ShapeI.MAX_RADIUS, initialValue));
        borderRadiusSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.borderRadius = newValue);
        final Label label = new Label("Border Radius");
        this.add(label, 0, 5);
        this.add(borderRadiusSpinner,1, 5);
    }

}
