package editor.mediator;

import editor.core.Editor;
import javafx.stage.FileChooser;

public class ConcreteMediator implements Mediator {
    //private UndoButtonJFx undo;

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
//        switch (component.getName()) {
//            case "UndoButton":
//                undo = (UndoButtonJFx) component;
//                break;
//        }
    }

    @Override
    public void undo() {
        Editor.getInstance().undo();
    }

    @Override
    public void redo() {
        Editor.getInstance().redo();
    }

    @Override
    public void save() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")); //TODO set extension once decided

//        File file = fileChooser.showSaveDialog(primaryStage);
//        if (file != null) {
//            System.out.println("save file : " + file.getName());
//            //TODO save file method
//        }
    }

    @Override
    public void open() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")); //TODO set extension once decided

//        File file = fileChooser.showOpenDialog(primaryStage);
//        if (file != null) {
//            System.out.println("open file : " + file.getName());
//            //TODO open file method
//        }
    }
}
