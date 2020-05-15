package editor.save.io;

import java.io.File;

public interface ImportManager extends IOManager{
    void restore(File file);
    void restoreToolbar();
}