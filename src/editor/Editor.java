package editor;

import ui.Rendering;

public class Editor {

    private final Scene scene;
    private final Toolbar toolbar;

    public Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
    }

    public void draw(Rendering rendering) {
        rendering.init();

        // Draw scene
        for (Shape s: scene.getShapes())
            s.drawInScene(rendering);

        // Draw toolbar
        for (Shape s: toolbar.getShapes())
            s.drawInToolbar(rendering);
    }

    public Scene getScene() {
        return scene;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
