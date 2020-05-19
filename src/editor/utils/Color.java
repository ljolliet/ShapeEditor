/*
 * Copyright (c) 2020 ShapeEditor.
 * @authors L. Jolliet, L. Sicardon, P. Vigneau
 * @version 1.0
 * Architecture Logicielle - Université de Bordeaux
 */
package editor.utils;

import java.io.Serializable;

public class Color implements Serializable {
    public final int r;
    public final int g;
    public final int b;
    public final double a;

    public Color(int red, int green, int blue, double opacity) {
        if (red < 0 || red > 255)
            throw new IllegalArgumentException("Color's red value (" + red + ") must be in the range 0-255");
        if (green < 0 || green > 255)
            throw new IllegalArgumentException("Color's green value (" + green + ") must be in the range 0-255");
        if (blue < 0 || blue > 255)
            throw new IllegalArgumentException("Color's blue value (" + blue + ") must be in the range 0-255");
        if (opacity < 0. || opacity > 1.)
            throw new IllegalArgumentException("Color's blue value (" + blue + ") must be in the range 0-255");

        this.r = red;
        this.g = green;
        this.b = blue;
        this.a = opacity;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (!(obj instanceof Color))
            return false;

        Color other = (Color) obj;
        return r == other.r && g == other.g && b == other.b && a == other.a;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", a=" + a +
                '}';
    }
}
