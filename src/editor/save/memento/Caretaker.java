package editor.save.memento;

import java.util.*;

public class Caretaker{

    private final int sizeLimit;
    private final List<Memento> mementoHistory;
    private int currentMementoIndex = -1;

    public Caretaker (int sizeLimit){
        this.mementoHistory = new ArrayList<>();
        this.sizeLimit = sizeLimit;
    }

    public void push(Memento m){
        if(mementoHistory.size() == sizeLimit) {
            mementoHistory.remove(mementoHistory.get(0));
        }
        else if(currentMementoIndex < mementoHistory.size() - 1) {
            Set<Memento> toDelete = new HashSet();
            for (int i = currentMementoIndex + 1; i < mementoHistory.size(); i++)
                toDelete.add(mementoHistory.get(i));
            this.mementoHistory.removeAll(toDelete);
        }
        this.mementoHistory.add(m);
        currentMementoIndex = mementoHistory.size() - 1;
    }

    public boolean undo(){
        if(currentMementoIndex <= 0) {
            return false;
        }
        Memento m = mementoHistory.get(--currentMementoIndex);
        m.restore();
        return true;
    }

    public boolean redo(){
        if(currentMementoIndex == mementoHistory.size() - 1) {
            return false;
        }
        Memento m = mementoHistory.get(++currentMementoIndex);
        m.restore();
        return true;
    }

    public Memento getCurrentMemento() {
        return currentMementoIndex >= 0 ? mementoHistory.get(currentMementoIndex) : null;
    }

    public int getHistorySize() {
        return this.mementoHistory.size();
    }

    public boolean contains(Memento m) {
        return mementoHistory.contains(m);
    }

    public void clear() {
        this.mementoHistory.clear();
        this.currentMementoIndex = -1;
    }
}
