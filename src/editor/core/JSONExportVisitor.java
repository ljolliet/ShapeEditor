package editor.core;

import editor.save.EditorVisitor;
import editor.shapes.*;
import org.json.simple.JSONObject;

public class JSONExportVisitor implements EditorVisitor {

    JSONObject save;
    JSONObject editor;
    JSONObject scene;
    JSONObject toolbar;
    JSONObject currentObject;

    @Override
    public void visit(Editor editor) {
        save = new JSONObject();
        this.editor = new JSONObject();
        this.currentObject = this.editor;
        editor.getScene().accept(this);
        editor.getToolbar().accept(this);
        save.put("editor", this.editor);
        System.out.println(getSave());
    }

    @Override
    public void visit(Toolbar toolbar) {
        this.toolbar = new JSONObject();
        this.currentObject = this.toolbar;
        for(ShapeI s : toolbar.shapes)
            s.accept(this);
        this.editor.put("toolbar", this.toolbar);
    }

    @Override
    public void visit(Scene scene) {
        this.scene = new JSONObject();
        this.currentObject = this.scene;
        for(ShapeI s : scene.shapes)
            s.accept(this);
        this.editor.put("scene", this.scene);
    }

    @Override
    public void visit(ShapeGroup g) {
//        JSONObject parent = this.currentObject;
//        JSONObject son = new JSONObject();
//        for(ShapeI s : g.getChildren()) {
//            this.currentObject = son;
//            s.accept(this);
//        }
//        parent.put("group", son);
        //TODO
    }

    @Override
    public void visit(Rectangle r) {
        JSONObject rectangle = getShape(r);

        rectangle.put("width", r.getWidth());
        rectangle.put("height", r.getHeight());
        rectangle.put("borderRadius", r.getBorderRadius());

        currentObject.put("rectangle", rectangle);
    }


    @Override
    public void visit(Polygon p) {
        JSONObject polygon = getShape(p);

        polygon.put("nbSide", p.getNbSides());
        polygon.put("sideLength", p.getSideLength());

        currentObject.put("polygon", polygon);
    }

    public String getSave(){
        return save.toString();
    }

    private JSONObject getShape(Shape s) {
        JSONObject shape = new JSONObject();

        JSONObject position = new JSONObject();
        position.put("x", s.getPosition().x);
        position.put("y", s.getPosition().y);
        shape.put("position", position);

        JSONObject color = new JSONObject();
        color.put("r", s.getColor().r);
        color.put("g", s.getColor().g);
        color.put("b", s.getColor().b);
        shape.put("color", color);

        JSONObject rotationCenter = new JSONObject();
        rotationCenter.put("x", s.getRotationCenter().x);
        rotationCenter.put("y", s.getRotationCenter().y);
        shape.put("rotationCenter", rotationCenter);
        shape.put("rotation", s.getRotation());

        JSONObject translation = new JSONObject();
        translation.put("x", s.getTranslation().width);
        translation.put("y", s.getTranslation().height);
        shape.put("translation", translation);
        return shape;
    }
}
