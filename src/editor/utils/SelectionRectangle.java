package editor.utils;

public class SelectionRectangle implements SelectionShape {

    private Point2D selectionStartPoint;
    private Point2D selectionEndPoint;

    @Override
    public Point2D getPosition() {
        double x = Math.min(selectionStartPoint.x, selectionEndPoint.x);
        double y = Math.min(selectionStartPoint.y, selectionEndPoint.y);
        return new Point2D(x,y);
    }

    @Override
    public boolean contains(Point2D p) {
        Point2D position = getPosition();
        return  getPosition().x <= position.x && position.x <= getPosition().x + getWidth()
                && getPosition().y <= position.y && position.y <= getPosition().y + getHeight();
    }

    @Override
    public void setSelectionStartPoint(Point2D selectionStartPoint) {
        this.selectionStartPoint = selectionStartPoint;
    }
    @Override
    public void setSelectionEndPoint(Point2D selectionEndPoint) {
        this.selectionEndPoint = selectionEndPoint;
    }

    public double getWidth() {
        return Math.abs(selectionEndPoint.x - selectionStartPoint.x);
    }
    public double getHeight() {
        return Math.abs(selectionEndPoint.y - selectionStartPoint.y);
    }
}
