package editor.save.io;

import java.io.File;

public interface ExportManager extends IOManager{
    String getExtension();
    void saveToolbar(File file);
    void saveScene(File file);
}
