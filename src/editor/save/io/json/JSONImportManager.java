package editor.save.io.json;

import editor.core.Editor;
import editor.save.io.ImportManager;
import editor.shapes.*;
import editor.utils.Color;
import editor.utils.Point2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONImportManager implements ImportManager {

    @Override
    public void restore(String data) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(data);

            JSONObject editor = (JSONObject) obj.get(EDITOR_TOKEN);
            JSONArray scene = (JSONArray) editor.get(SCENE_TOKEN);

            List<Shape> shapes = new ArrayList<>();
            this.getShapes(scene, shapes);
            Editor.getInstance().getScene().setShapes(shapes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void getShapes(JSONArray array, List<Shape> shapes) {
        Iterator<JSONObject> iterator = array.iterator();
        JSONObject shape;
        while (iterator.hasNext()) {
            if((shape = iterator.next()).get(RECTANGLE_TOKEN) != null){
                JSONObject rectangle = (JSONObject) shape.get(RECTANGLE_TOKEN);
                shapes.add(this.getRectangle(rectangle));
            }
            else if(shape.get(POLYGON_TOKEN) != null){
                JSONObject polygon = (JSONObject) shape.get(POLYGON_TOKEN);
                shapes.add(this.getPolygon(polygon));
            }
            else if(shape.get(GROUP_TOKEN) != null){
                JSONArray group = (JSONArray) shape.get(GROUP_TOKEN);
                ShapeGroup g = new ShapeGroup();
                List<Shape> childrenShapes = new ArrayList<>();
                this.getShapes(group, childrenShapes);
                for(ShapeI s : childrenShapes)
                    g.addShape(s);
                shapes.add(g);
            }
        }
    }

    //TODO avoid duplication
    private Polygon getPolygon(JSONObject polygon) {
        double nbSides = (double) polygon.get(NB_SIDES_TOKEN);
        double sideLength = (double) polygon.get(SIDE_LENGTH);

        JSONObject pos = (JSONObject) polygon.get(POSITION_TOKEN);
        Point2D position = new Point2D((double)pos.get(X_TOKEN), (double) pos.get(Y_TOKEN));

        JSONObject col = (JSONObject) polygon.get(COLOR_TOKEN);
        double red = (double) col.get(RED_TOKEN);
        double green = (double) col.get(GREEN_TOKEN);
        double blue = (double) col.get(BLUE_TOKEN);
        Color color = new Color((int)red, (int)green, (int)blue);

        JSONObject rotCenter = (JSONObject) polygon.get(ROTATION_CENTER_TOKEN);
        Point2D rotationCenter = new Point2D((double)rotCenter.get(X_TOKEN), (double) rotCenter.get(Y_TOKEN));
        double rotation = (double) polygon.get(ROTATION_TOKEN);

        return new Polygon((int)nbSides, sideLength, position, color, rotationCenter, rotation);
    }

    private Rectangle getRectangle(JSONObject rectangle) {
        double width = (double) rectangle.get(WIDTH_TOKEN);
        double height = (double) rectangle.get(HEIGHT_TOKEN);
        double borderRadius = (double) rectangle.get(BORDER_RADIUS_TOKEN);

        JSONObject pos = (JSONObject) rectangle.get(POSITION_TOKEN);
        Point2D position = new Point2D((double)pos.get(X_TOKEN), (double) pos.get(Y_TOKEN));

        JSONObject col = (JSONObject) rectangle.get(COLOR_TOKEN);
        double red = (double) col.get(RED_TOKEN);
        double green = (double) col.get(GREEN_TOKEN);
        double blue = (double) col.get(BLUE_TOKEN);
        Color color = new Color((int)red, (int)green, (int)blue);

        JSONObject rotCenter = (JSONObject) rectangle.get(ROTATION_CENTER_TOKEN);
        Point2D rotationCenter = new Point2D((double)rotCenter.get(X_TOKEN), (double) rotCenter.get(Y_TOKEN));
        double rotation = (double) rectangle.get(ROTATION_TOKEN);

        return new Rectangle(width, height, (int) borderRadius, position, color, rotationCenter, rotation);
    }
}
