package editor.mediator;

import javafx.scene.image.ImageView;

public class UndoButtonJFx extends ButtonJFx{

    public UndoButtonJFx(String s, ImageView undoIm) {
        super(s,undoIm);
    }

    @Override
    public String getName() {
        return "UndoButton";
    }

    @Override
    public void fire() {
        System.out.println("fire");
        super.fire();
        this.mediator.undo();
    }

}
