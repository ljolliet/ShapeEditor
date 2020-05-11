package editor.save.io.json;

import editor.core.Editor;
import editor.core.EditorVisitor;
import editor.core.Scene;
import editor.core.Toolbar;
import editor.save.io.ExportManager;
import editor.save.io.IOManager;
import editor.shapes.*;

import java.io.File;

public class JSONExportVisitor implements EditorVisitor, ExportManager {

    static final char START_SYMBOL = '{';
    static final char END_SYMBOL = '}';
    static final char ARRAY_START_SYMBOL = '[';
    static final char ARRAY_END_SYMBOL = ']';
    static final char SEPARATOR_TOKEN = ',';
    static final char QUOTE = '\"';
    static final char COLON = ':';

    StringBuilder sb = new StringBuilder();

    @Override
    public void visit(Editor editor) {

    }

    @Override
    public void visit(Toolbar toolbar) {
        boolean start = true;
        sb.append(START_SYMBOL);
        this.addField(EDITOR_TOKEN);
        sb.append(START_SYMBOL);
        this.addField(TOOLBAR_TOKEN);
        sb.append(ARRAY_START_SYMBOL);
        for(ShapeI s : toolbar.getShapes()) {
            if(!start)
                sb.append(SEPARATOR_TOKEN);
            else
                start = false;
            s.accept(this);
        }
        sb.append(ARRAY_END_SYMBOL);
        sb.append(END_SYMBOL);
        sb.append(END_SYMBOL);
    }

    @Override
    public void visit(Scene scene) {
        //cannot avoid duplication because container don't have the same type
        boolean start = true;
        sb.append(START_SYMBOL);
        this.addField(EDITOR_TOKEN);
        sb.append(START_SYMBOL);
        this.addField(SCENE_TOKEN);
        sb.append(ARRAY_START_SYMBOL);
        for(ShapeI s : scene.getShapes()) {
            if(!start)
                sb.append(SEPARATOR_TOKEN);
            else
                start = false;
            s.accept(this);
        }
        sb.append(ARRAY_END_SYMBOL);
        sb.append(END_SYMBOL);
        sb.append(END_SYMBOL);
    }

    @Override
    public void visit(ShapeGroup g) {
        boolean start = true;
        sb.append(START_SYMBOL);
        this.addField(GROUP_TOKEN);
        sb.append(ARRAY_START_SYMBOL);
        for(ShapeI s : g.getChildren()) {
            if(!start)
                sb.append(SEPARATOR_TOKEN);
            else
                start = false;
            s.accept(this);
        }
        sb.append(ARRAY_END_SYMBOL);
        sb.append(END_SYMBOL);
    }

    @Override
    public void visit(Rectangle r) {
        sb.append(START_SYMBOL);
        this.addField(RECTANGLE_TOKEN);
        sb.append(START_SYMBOL);
        this.addShape(r);
        this.addFieldAndValue(WIDTH_TOKEN, r.getWidth(), true);
        this.addFieldAndValue(HEIGHT_TOKEN, r.getHeight(), true);
        this.addFieldAndValue(BORDER_RADIUS_TOKEN, r.getBorderRadius(), true);
        sb.append(END_SYMBOL);
        sb.append(END_SYMBOL);
    }

    @Override
    public void visit(Polygon p) {
        sb.append(START_SYMBOL);
        this.addField(POLYGON_TOKEN);
        sb.append(START_SYMBOL);
        this.addShape(p);
        this.addFieldAndValue(NB_SIDES_TOKEN, p.getNbSides(), true);
        this.addFieldAndValue(SIDE_LENGTH, p.getSideLength(), true);
        sb.append(END_SYMBOL);
        sb.append(END_SYMBOL);
    }

    @Override
    public String getExtension() {
        return ".json";
    }

    @Override
    public void saveScene(File file){
        sb.delete(0, sb.length());
        Editor.getInstance().getScene().accept(this);
        IOManager.writeInFile(file, sb.toString(), getExtension());
    }

    @Override
    public void saveToolbar(File file) {
        sb.delete(0, sb.length());
        Editor.getInstance().getToolbar().accept(this);
        IOManager.writeInFile(file, sb.toString(), getExtension());
    }

    private void addFieldAndValue(String token, double value, boolean separator) {
        if(separator)
            sb.append(SEPARATOR_TOKEN);
        this.addField(token);
        sb.append(value);
    }

    private void addField(String token) {
        sb.append(QUOTE);
        sb.append(token);
        sb.append(QUOTE);
        sb.append(COLON);
    }
    private void addSeparatorAndField(String token) {
        sb.append(SEPARATOR_TOKEN);
        this.addField(token);
    }

    private void addShape(Shape s) {

        this.addField(POSITION_TOKEN);
        sb.append(START_SYMBOL);
        this.addFieldAndValue(X_TOKEN, s.getPosition().x, false);
        this.addFieldAndValue(Y_TOKEN, s.getPosition().y, true);
        sb.append(END_SYMBOL);

        this.addSeparatorAndField(COLOR_TOKEN);
        sb.append(START_SYMBOL);
        addFieldAndValue(RED_TOKEN, s.getColor().r, false);
        addFieldAndValue(GREEN_TOKEN, s.getColor().g, true);
        addFieldAndValue(BLUE_TOKEN, s.getColor().b, true);
        addFieldAndValue(OPACITY_TOKEN, s.getColor().a, true);
        sb.append(END_SYMBOL);

        this.addSeparatorAndField(ROTATION_CENTER_TOKEN);
        sb.append(START_SYMBOL);
        this.addFieldAndValue(X_TOKEN, s.getRotationCenter().x, false);
        this.addFieldAndValue(Y_TOKEN, s.getRotationCenter().y, true);
        sb.append(END_SYMBOL);

        addFieldAndValue(ROTATION_TOKEN, s.getRotation(), true);

        this.addSeparatorAndField(TRANSLATION_TOKEN);
        sb.append(START_SYMBOL);
        addFieldAndValue(X_TOKEN, s.getTranslation().width, false);
        addFieldAndValue(Y_TOKEN, s.getTranslation().height, true);
        sb.append(END_SYMBOL);
    }
}
