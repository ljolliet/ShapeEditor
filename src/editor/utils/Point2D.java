package editor.utils;

import java.io.Serializable;
import java.util.Objects;

public class Point2D implements Serializable {
    public final double x;
    public final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point2D rotateAround(double angle, Point2D rotationCenter) {
        // Rotate around rotation center by angle
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));

        // Set origin to rotation center
        Point2D newPoint = new Point2D(this.x - rotationCenter.x, this.y - rotationCenter.y);

        // Rotate
        newPoint = new Point2D(newPoint.x * cos - newPoint.y * sin,
                newPoint.x * sin + newPoint.y * cos);

        // Put origin back
         return new Point2D(newPoint.x + rotationCenter.x, newPoint.y + rotationCenter.y);
    }

    public Point2D translate(Vec2D translation) {
        return new Point2D(this.x + translation.width, this.y + translation.height);
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Double.compare(point2D.x, x) == 0 &&
                Double.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
