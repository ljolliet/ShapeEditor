package editor.core;

import editor.save.EditorVisitor;
import editor.shapes.*;

public class JSONExportVisitor implements EditorVisitor {

    StringBuilder sb = new StringBuilder();

    @Override
    public void visit(Editor editor) {
        sb.append("{ \"editor\" : {");
        editor.getToolbar().accept(this);
        editor.getScene().accept(this);
        sb.append("}");
        sb.append("}");
        System.out.println(sb.toString());
    }

    @Override
    public void visit(Toolbar toolbar) {
        boolean start = true;
        sb.append("\"toolbar\" : {");
        for(ShapeI s : toolbar.shapes) {
            if(!start)
                sb.append(",");
            else
                start = false;
            s.accept(this);
        }
        sb.append("}");
    }

    @Override
    public void visit(Scene scene) {
        boolean start = true;
        sb.append(",\"scene\" : {");
        for(ShapeI s : scene.shapes) {
            if(!start)
                sb.append(",");
            else
                start = false;
            s.accept(this);
        }
        sb.append("}");
    }

    @Override
    public void visit(ShapeGroup g) {
        boolean start = true;
        sb.append("\"group\" : {");
        for(ShapeI s : g.getChildren()) {
            if(!start)
                sb.append(",");
            else
                start = false;
            s.accept(this);
        }
        sb.append("}");
    }

    @Override
    public void visit(Rectangle r) {
        sb.append("\"rectangle\" : {");
        this.addShape(r);
        sb.append(", \"width\" : " + r.getWidth());
        sb.append(", \"height\" : " + r.getHeight());
        sb.append(", \"borderRadius\" : " + r.getBorderRadius());
        sb.append("}");
    }


    @Override
    public void visit(Polygon p) {
        sb.append("\"polygon\" : {");
        this.addShape(p);
        sb.append(", \"nbSides\" : " + p.getNbSides());
        sb.append(", \"sideLength\" : " + p.getSideLength());
        sb.append("}");
    }

    public String getSave(){
        return sb.toString();
    }

    private void addShape(Shape s) {

        sb.append("\"position\" : {");
        sb.append("\"x\" : " + s.getPosition().x);
        sb.append(",\"y\" : " + s.getPosition().y);
        sb.append("}");

        sb.append(",\"color\" : {");
        sb.append("\"r\" : " + s.getColor().r);
        sb.append(",\"g\" : " + s.getColor().g);
        sb.append(",\"b\" : " + s.getColor().b);
        sb.append("}");

        sb.append(",\"rotationCenter\" : {");
        sb.append("\"x\" : " + s.getRotationCenter().x);
        sb.append(",\"y\" : " + s.getRotationCenter().y);
        sb.append("}");

        sb.append(",\"rotation\" : " + s.getRotation());

        sb.append(",\"translation\" : {");
        sb.append("\"x\" : " + s.getTranslation().width);
        sb.append(",\"y\" : " + s.getTranslation().height);
        sb.append("}");
    }
}
