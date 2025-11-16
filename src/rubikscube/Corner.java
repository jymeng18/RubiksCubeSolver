package rubikscube;

/**
 * Represents a corner piece on the Rubik's Cube
 * Each corner has 3 possible orientations and 3 colored stickers
 */
public class Corner {
    // 0-7
    private final int pieceID;

    // Orientation: 0 = correct, 1 = clockwise, 2 = counter-clockwise
    private int ori;

    // Three colors on each corner (Order matters here)
    private final char[] colors;

    public Corner(int pieceID, char color1, char color2, char color3){
        this.pieceID = pieceID;
        this.colors = new char[]{color1, color2, color3};
        this.ori = 0;
    }

    public int getPieceId() { return pieceID; }
    public int getOrientation() { return ori; }
    public char[] getColors() { return colors; }
    public void setOrientation(int orientation) { this.ori = orientation; }
}
