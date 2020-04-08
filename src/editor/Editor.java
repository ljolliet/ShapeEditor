package editor;

public class Editor {

    private final Scene scene;
    private final Toolbar toolbar;

    public Editor() {
        this.scene = new Scene();
        this.toolbar = new Toolbar();
    }

    public Scene getScene() {
        return scene;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }
}
