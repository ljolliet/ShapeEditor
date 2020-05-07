package editor.save;

import editor.core.Scene;
import editor.core.Toolbar;
import editor.shapes.Polygon;
import editor.shapes.Rectangle;
import editor.utils.Color;
import editor.utils.Point2D;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;

public class JSONImportManager implements ImportManager {

    @Override
    public void restore(String data) {
        JSONParser parser = new JSONParser();
        Toolbar t = new Toolbar();
        Scene s = new Scene();
        try {
            JSONObject obj = (JSONObject) parser.parse(data);

            JSONObject editor = (JSONObject) obj.get("editor");
            JSONArray toolbar = (JSONArray) editor.get("toolbar");
            JSONArray scene = (JSONArray) editor.get("scene");

            Iterator<JSONObject> iterator = toolbar.iterator();
            JSONObject shape;
            while (iterator.hasNext()) {
                if((shape = iterator.next()).get("rectangle") != null){
                    JSONObject rectangle = (JSONObject) shape.get("rectangle");
                    t.addShape(this.getRectangle(rectangle));
                }
                else if(shape.get("polygon") != null){
                    JSONObject polygon = (JSONObject) shape.get("polygon");
                    t.addShape(this.getPolygon(polygon));
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Polygon getPolygon(JSONObject polygon) {
        long nbSides = (long) polygon.get("nbSides");
        double sideLength = (double) polygon.get("sideLength");

        JSONObject pos = (JSONObject) polygon.get("position");
        Point2D position = new Point2D((double)pos.get("x"), (double) pos.get("y"));

        JSONObject col = (JSONObject) polygon.get("color");
        long red = (long) col.get("r");
        long green = (long) col.get("g");
        long blue = (long) col.get("b");
        Color color = new Color((int)red, (int)green, (int)blue);

        JSONObject rotCenter = (JSONObject) polygon.get("rotationCenter");
        Point2D rotationCenter = new Point2D((double)rotCenter.get("x"), (double) rotCenter.get("y"));
        double rotation = (double) polygon.get("rotation");

        return new Polygon((int)nbSides, sideLength, position, color, rotationCenter, rotation);
    }

    private Rectangle getRectangle(JSONObject rectangle) {
        double width = (double) rectangle.get("width");
        double height = (double) rectangle.get("height");
        long borderRadius = (long) rectangle.get("borderRadius");

        JSONObject pos = (JSONObject) rectangle.get("position");
        Point2D position = new Point2D((double)pos.get("x"), (double) pos.get("y"));

        JSONObject col = (JSONObject) rectangle.get("color");
        long red = (long) col.get("r");
        long green = (long) col.get("g");
        long blue = (long) col.get("b");
        Color color = new Color((int)red, (int)green, (int)blue);

        JSONObject rotCenter = (JSONObject) rectangle.get("rotationCenter");
        Point2D rotationCenter = new Point2D((double)rotCenter.get("x"), (double) rotCenter.get("y"));
        double rotation = (double) rectangle.get("rotation");

        return new Rectangle(width, height, (int) borderRadius, position, color, rotationCenter, rotation);
    }
}
