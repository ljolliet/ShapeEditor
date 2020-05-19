/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.observer;

import java.util.Set;

public interface ObservableI {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void removeObservers();
    void notifyObservers();
    Set<Observer> getObservers();
}
