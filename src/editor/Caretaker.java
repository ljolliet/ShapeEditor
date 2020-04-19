package editor;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {

    private String state;
    private List<Memento> mementoHistory;
    private int currentMementoIndex = 0;

    public Caretaker (){
        this.mementoHistory = new ArrayList<>();
    }

    public void push(Memento m){
        this.mementoHistory.add(m);
        System.out.println("history size : " + mementoHistory.size());
        currentMementoIndex++;
        //TODO check limit size
    }

    public boolean undo(){
        Memento m = mementoHistory.get(--currentMementoIndex);
        m.restore();
        return true;    //TODO
    }

    public boolean redo(){
        return true;    //TODO
    }
}
