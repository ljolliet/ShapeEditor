package editor.utils;

import java.io.Serializable;

public class Vec2D implements Serializable {
    public final double width;
    public final double height;

    public Vec2D(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
