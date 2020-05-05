package editor.mediator;

import editor.core.Editor;

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
}
