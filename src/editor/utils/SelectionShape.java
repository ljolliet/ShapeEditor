package editor.utils;

public interface SelectionShape {
    Point2D getPosition();
    boolean contains(Point2D p);

    void setSelectionStartPoint(Point2D p);
    void setSelectionEndPoint(Point2D p);
}
