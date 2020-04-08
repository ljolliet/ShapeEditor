package ui;

import editor.*;
import editor.utils.Color;
import editor.utils.Vec2D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application implements ApplicationI {

    private final Editor editor = new Editor();

    private Group root;
    private VBox toolbarBox;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);

//        Stage
//        |  Scene
//        |  |  VBox (windowLayout)
//        |  |  |  HBox (optionLayout)
//        |  |  |  HBox (editorLayout)
//        |  |  |  |  VBox (toolbarBox)
//        |  |  |  |  Group (root)
//        |  |  |  |  |  Canvas

        VBox windowLayout = new VBox();

        // Top option bar
        HBox optionLayout = new HBox();
        optionLayout.setPrefHeight(OPTION_HEIGHT);
        optionLayout.setPrefWidth(WINDOW_WIDTH);
        optionLayout.setStyle("-fx-background-color: darkred");
        windowLayout.getChildren().add(optionLayout);

        // Editor layout
        HBox editorLayout = new HBox();
        editorLayout.setPrefHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        editorLayout.setPrefWidth(WINDOW_WIDTH);
        windowLayout.getChildren().add(editorLayout);

        // Toolbar
        this.toolbarBox = new VBox();
        toolbarBox.setPrefHeight(WINDOW_HEIGHT);
        toolbarBox.setPrefWidth(TOOLBAR_WIDTH);
        toolbarBox.setStyle("-fx-background-color: lightgray");
        editorLayout.getChildren().add(toolbarBox);

        // Scene layout
        this.root = new Group();
        editorLayout.getChildren().add(root);

        Canvas canvas = new Canvas();
        canvas.setWidth(WINDOW_WIDTH - TOOLBAR_WIDTH);
        canvas.setHeight(WINDOW_HEIGHT - OPTION_HEIGHT);
        root.getChildren().add(canvas);

//        drawShape(new Rectangle(100, 200, new Vec2D(200, 200),
//                  new Color(0,0,0), new Vec2D(0,0), 0));

//        drawShape(new Polygon(6, 20, new Vec2D(300, 300),
//                  new Color(255,0,255), new Vec2D(0,0), 45));

        Scene scene = new Scene(windowLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);

        Shape s = new Rectangle(100, 200, null,
                new Color(255,0,255), new Vec2D(0,0), 45);

        Shape s2 = new Rectangle(200, 100, null,
                new Color(0,255,255), new Vec2D(0,0), 45);

        editor.getToolbar().addShape(s);
        editor.getToolbar().addShape(s2);

        this.root.setOnMouseClicked(event -> {
            Vec2D coords = new Vec2D(event.getX(), event.getY());
            Shape shape = new Polygon(6, 20, coords,
                    new Color(255,0,255), new Vec2D(0,0), 45);

            editor.getScene().addShape(shape);
        });

        this.toolbarBox.setOnMousePressed(event -> {
            System.out.println("click toolbar");

            int i = 0;
            for (Node node: toolbarBox.getChildren()) {
                // TODO it's always different because left: javafx.Rectangle - right: editor.Rectangle
                if (event.getPickResult().getIntersectedNode() == toolbarBox.getChildren().get(i)) {
                    System.out.println("found: " + editor.getToolbar().getShapes().get(i));
                    break;
                }
                i++;
            }


        });

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                root.getChildren().clear();
                toolbarBox.getChildren().clear();

                for (Shape shape: editor.getScene().getShapes())
                    drawShape(shape);

                drawToolbar();
            }
        }.start();

        primaryStage.show();
    }

    public void drawShape(Shape shape) {
        if (shape instanceof Rectangle)
            drawShape((Rectangle) shape);
        else if (shape instanceof Polygon)
            drawShape((Polygon) shape);
        else if (shape instanceof ShapeGroup)
            drawShape((ShapeGroup) shape);
    }

    public void drawShape(Rectangle r) {
        javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        this.root.getChildren().add(rect);
    }

    public void drawShape(Polygon p) {
        javafx.scene.shape.Polygon polygon = new javafx.scene.shape.Polygon(p.getPoints());
        float r = p.getColor().r / 255f;
        float g = p.getColor().g / 255f;
        float b = p.getColor().b / 255f;
        polygon.setFill(new javafx.scene.paint.Color(r, g, b, 1));
        this.root.getChildren().add(polygon);
    }

    public void drawShape(ShapeGroup group) {
        for (Shape s: group.getChild())
            drawShape(s);
    }

    public void drawToolbar() {
        for (Shape s: editor.getToolbar().getShapes()) {
            if (s instanceof Rectangle) {
                Rectangle r = (Rectangle) s;
                double ratio = (r.getWidth() / TOOLBAR_WIDTH) / 2 ;
                javafx.scene.shape.Rectangle rect = new javafx.scene.shape.Rectangle(r.getWidth() * ratio, r.getHeight() * ratio);
                this.toolbarBox.getChildren().add(rect);
            }
        }
    }
}
