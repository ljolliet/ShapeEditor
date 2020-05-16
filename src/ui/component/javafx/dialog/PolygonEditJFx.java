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
        addSideLenghtGridPane(polED);
        addNbSideGridPane(polED);
    }

    void addSideLenghtGridPane(PolygonEditionDialog polED) {
        Spinner<Double> sideLenghtSpinner = new Spinner();
        sideLenghtSpinner.setEditable(true);
        final double initialValue = polED.getTarget().getSideLength();
        sideLenghtSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_RADIUS, Shape.MAX_RADIUS, initialValue));
        sideLenghtSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.sideLength = newValue);
        final Label label = new Label("Side length");
        this.add(label, 0, 5);
        this.add(sideLenghtSpinner,1, 5);
    }

    void addNbSideGridPane(PolygonEditionDialog polED){
        Spinner<Integer> nbSideSpinner = new Spinner();
        nbSideSpinner.setEditable(true);
        final int initialValue = polED.getTarget().getNbSides();
        nbSideSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_RADIUS, Shape.MAX_RADIUS, initialValue));
        nbSideSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.nbSides = newValue);
        final Label label = new Label("Sides number");
        this.add(label, 0, 6);
        this.add(nbSideSpinner,1, 6);
    }

}
