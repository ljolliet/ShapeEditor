package ui.component.javafx.dialog;

import editor.edition.RectangleEditionDialog;
import editor.shapes.Shape;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import ui.ApplicationI;

public class RectangleEditJFx extends EditDialogJFx {

    public RectangleEditJFx(RectangleEditionDialog recED) {
        super(recED);
        this.addWidthToGridPane();
        this.addHeightToGridPane();
        this.addBorderRadiusGridPane();
        this.setButtons();
    }

    void addWidthToGridPane() {
        RectangleEditionDialog recED = (RectangleEditionDialog) editionDialog;
        Spinner<Double> widthSpinner = new Spinner<>();
        widthSpinner.setEditable(true);

        final double initialValue = recED.getTarget().getWidth();
        final double maxValue = ApplicationI.SCENE_WIDTH - recED.getTarget().getPosition().x;
        widthSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIZE, maxValue, initialValue));
        widthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setWidth(newValue));

        final Label label = new Label("Width");
        this.add(label, columnID, rowID);
        this.add(widthSpinner, columnID + 1, rowID);
        rowID++;
    }

    void addHeightToGridPane(){
        RectangleEditionDialog recED = (RectangleEditionDialog) editionDialog;
        Spinner<Double> heightSpinner = new Spinner<>();
        heightSpinner.setEditable(true);

        final double initialValue = recED.getTarget().getHeight();
        final double maxValue = ApplicationI.SCENE_HEIGHT - recED.getTarget().getPosition().y;
        heightSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIZE, maxValue, initialValue));
        heightSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setHeight(newValue));

        final Label label = new Label("Height");
        this.add(label, columnID, rowID);
        this.add(heightSpinner, columnID + 1, rowID);
        rowID++;
    }

    void addBorderRadiusGridPane() {
        RectangleEditionDialog recED = (RectangleEditionDialog) editionDialog;
        Spinner<Integer> borderRadiusSpinner = new Spinner<>();
        borderRadiusSpinner.setEditable(true);

        final int initialValue = recED.getTarget().getBorderRadius();
        borderRadiusSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_BORDER_RADIUS, Shape.MAX_BORDER_RADIUS, initialValue));
        borderRadiusSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                recED.setBorderRadius(newValue));

        final Label label = new Label("Border Radius");
        this.add(label, columnID, rowID);
        this.add(borderRadiusSpinner,columnID + 1, rowID);
        rowID++;
    }

}
