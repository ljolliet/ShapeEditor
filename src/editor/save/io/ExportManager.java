/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.save.io;

import java.io.File;

public interface ExportManager extends IOManager{
    void saveToolbar();
    void saveToolbar(File file);
    void saveScene(File file);
}