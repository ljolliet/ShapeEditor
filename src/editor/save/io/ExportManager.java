package editor.save.io;

import java.io.File;

public interface ExportManager extends IOManager{
    void saveToolbar();
    void saveToolbar(File file);
    void saveScene(File file);
}
