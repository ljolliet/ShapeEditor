package editor.save.io;

public interface ImportManager extends IOManager{
    /**
     * Restore the state of editor with a save.
     * @param data The save to restore.
     */
    void restore(String data);
}
