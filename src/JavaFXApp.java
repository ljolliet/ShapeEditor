import editor.core.Editor;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.ApplicationI;
import ui.component.javafx.RenderingJFx;
import ui.component.javafx.area.*;
import ui.component.javafx.buttons.*;
import ui.component.javafx.layouts.EditorLayoutJFx;
import ui.component.javafx.layouts.OptionLayoutJFx;
import ui.component.javafx.layouts.ToolbarRootJFx;
import ui.component.javafx.layouts.WindowLayoutJFx;


public class JavaFXApp extends Application implements ApplicationI {

    /*
            Stage
            |  Scene
            |  |  StackPane (windowPane)
            |  |  |  VBox (windowLayout)
            |  |  |  |  HBox (optionLayout)
            |  |  |  |  HBox (editorLayout)
            |  |  |  |  |  BorderPane (toolbarRoot)
            |  |  |  |  |  |   VBox (toolbarBox)
            |  |  |  |  |  |   ImageView (trashIm)
            |  |  |  |  |  Group (root)
            |  |  |  |  |  |  Canvas
            |  |  |  Group (shadowGroup)
            |  |  |  |  Canvas
    */

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setResizable(false);

        Editor editor = Editor.getInstance();
        editor.restoreToolbar();

        RenderingJFx rendering = new RenderingJFx();
        editor.setRendering(rendering);
        rendering.registerStage(primaryStage);

        //Main layout
        rendering.registerComponent(new WindowLayoutJFx());

        //Options
        rendering.registerComponent(new OptionLayoutJFx());
        rendering.registerComponent(new SaveButtonJFx());
        rendering.registerComponent(new OpenButtonJFx());
        rendering.registerComponent(new UndoButtonJFx());
        rendering.registerComponent(new RedoButtonJFx());

        // Editor layout
        rendering.registerComponent(new EditorLayoutJFx());
        //Toolbar
        rendering.registerComponent(new ToolbarRootJFx());
        rendering.registerComponent(new ToolbarJFx());
        rendering.registerComponent(new TrashJFx());

        // Scene
        rendering.registerComponent(new SceneJFx());

        rendering.drawEditor();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Editor.getInstance().saveToolbar();
        super.stop();
    }
}
