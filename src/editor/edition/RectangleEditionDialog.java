package editor.edition;

import editor.shapes.Rectangle;
import editor.utils.Point2D;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import ui.Rendering;

public class RectangleEditionDialog extends ShapeEditionDialog {
    public double width;
    public double height;
    public int borderRadius;

    public RectangleEditionDialog(Rectangle rectangle) {
        super(rectangle);
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        this.borderRadius = rectangle.getBorderRadius();
    }

    @Override
    public void draw(Rendering rendering) {
        rendering.setEditionDialog(this);
        rendering.showEditionDialog(this.getPosition());
    }

    public Rectangle getTarget() {
        return (Rectangle)super.getTarget();
    }

    public void setWidth(double value) {
        this.getTarget().setWidth(value);
    }

    public void setHeight(double value){
        this.getTarget().setHeight(value);
    }

    public void setBorderRadius(int value) {
        this.getTarget().setBorderRadius(value);
    }

    @Override
    public void applyEdition(){
        this.getTarget().setAllRectangleValues(this.width, this.height, this.borderRadius, new Point2D(this.posX, this.posY), this.color, this.rotation);
    }

/*    @Override
    public void setEditionDialog(ContextMenu contextMenu) {
        contextMenu.getItems().clear();
        final MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(event -> setRectangleDialog());
        contextMenu.getItems().add(edit);
    }

    public void setRectangleDialog(RectangleEditionDialog recED){
        setGridPane(recED);
        setEditionDialog((ShapeEditionDialog)recED);
        setEditionDialog(recED);

        //Show gridpane
        editStage.setTitle("Rectangle Edition");
        editStage.setScene(editScene);
        editStage.showAndWait();
    }*/
}
