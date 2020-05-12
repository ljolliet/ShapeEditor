package editor.utils;

import java.io.Serializable;
import java.util.Objects;

public class Vec2D implements Serializable {
    public final double width;
    public final double height;

    public Vec2D(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2D vec2D = (Vec2D) o;
        return Double.compare(vec2D.width, width) == 0 &&
                Double.compare(vec2D.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
