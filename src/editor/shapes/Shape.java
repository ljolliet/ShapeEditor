package editor.shapes;

import editor.observer.Observable;
import editor.utils.Color;

import java.util.HashSet;

public abstract class Shape extends Observable implements ShapeI {
    //private boolean selected = false;

    @Override
    public ShapeI clone() throws CloneNotSupportedException {
        Shape clone = (Shape) super.clone();
        clone.setObservers(new HashSet<>());

        return clone;
    }



/*    public void setSelectedShape(){
        this.selected = true;
    }

    public boolean isSelectedShape(){
        return selected;
    }*/
}
