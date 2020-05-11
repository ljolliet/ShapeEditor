package editor.save.io;

import java.io.File;

public interface ExportManager extends IOManager{
    void saveToolbar();
    void saveScene(File file);
}
