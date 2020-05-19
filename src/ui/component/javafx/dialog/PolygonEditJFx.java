/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package ui.component.javafx.dialog;

import editor.edition.PolygonEditionDialog;
import editor.shapes.Shape;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class PolygonEditJFx extends EditDialogJFx {

    public PolygonEditJFx(PolygonEditionDialog polED){
        super(polED);
        this.addSideLengthGridPane();
        this.addNbSideGridPane();
        this.setButtons();
    }

    void addSideLengthGridPane() {
        PolygonEditionDialog polED = (PolygonEditionDialog) editionDialog;
        Spinner<Double> sideLengthSpinner = new Spinner<>();
        sideLengthSpinner.setEditable(true);

        final double initialValue = polED.getTarget().getSideLength();
        sideLengthSpinner.setValueFactory(
                new SpinnerValueFactory.DoubleSpinnerValueFactory(Shape.MIN_SIDE_LENGTH, Shape.MAX_SIDE_LENGTH, initialValue));
        sideLengthSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.setSideLength(newValue));

        final Label label = new Label("Side length");
        this.add(label, columnID, rowID);
        this.add(sideLengthSpinner,columnID + 1, rowID);
        rowID++;
    }

    void addNbSideGridPane(){
        PolygonEditionDialog polED = (PolygonEditionDialog) editionDialog;
        Spinner<Integer> nbSideSpinner = new Spinner<>();
        nbSideSpinner.setEditable(true);

        final int initialValue = polED.getTarget().getNbSides();
        nbSideSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(Shape.MIN_NB_SIDE, Shape.MAX_NB_SIDE, initialValue));
        nbSideSpinner.valueProperty().addListener((obs, oldValue, newValue) ->
                polED.setNbSides(newValue));

        final Label label = new Label("Sides number");
        this.add(label, columnID, rowID);
        this.add(nbSideSpinner,columnID + 1, rowID);
        rowID++;
    }

}
