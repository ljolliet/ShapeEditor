package editor.save.io;

public interface ExportManager extends IOManager{
    /**
     * @return A String corresponding to the save, that can be written in a file.
     */
    String getSave();
    /**
     * @return The save file extension
     */
    String getExtension();
}
