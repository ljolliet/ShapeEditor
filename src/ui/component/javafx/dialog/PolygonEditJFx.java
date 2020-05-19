package ui.component.javafx.dialog;

import editor.edition.PolygonEditionDialog;
import editor.shapes.Shape;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class PolygonEditJFx extends EditDialogJFx {

    public PolygonEditJFx(PolygonEditionDialog polED){
        super();
        super.addColorToGridPane(polED);
        super.addPositionToGridPane(polED);
        super.addRotationToGridPane(polED);
        super.addTranslationToGridPane(polED);
        super.addRotationCenterToGridPane(polED);
        this.addSideLengthGridPane(polED);
        this.addNbSideGridPane(polED);
    }

    void addSideLengthGridPane(PolygonEditionDialog polED) {
        Spinner<Double> sideLengthSpinner = new Spinner<>();
        sideLengthSpinner.setEditable(true);

        final double initialValue = polED.getTarget().getSideLength();
        sideLengthSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIDE_LENGTH, Shape.MAX_SIDE_LENGTH, initialValue));
        sideLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.setSideLength(newValue));

        final Label label = new Label("Side length");
        this.add(label, 0, 5);
        this.add(sideLengthSpinner,1, 5);
    }

    void addNbSideGridPane(PolygonEditionDialog polED){
        Spinner<Integer> nbSideSpinner = new Spinner<>();
        nbSideSpinner.setEditable(true);

        final int initialValue = polED.getTarget().getNbSides();
        nbSideSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_NB_SIDE, Shape.MAX_NB_SIDE, initialValue));
        nbSideSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.setNbSides(newValue));

        final Label label = new Label("Sides number");
        this.add(label, 0, 6);
        this.add(nbSideSpinner,1, 6);
    }

}
