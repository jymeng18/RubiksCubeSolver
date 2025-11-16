package rubikscube;

/**
 * Represents an edge piece on the Rubik's Cube.
 * An edge has 2 colored stickers and can be oriented 2 ways (flipped or not).
 */
public class Edge {
    // Which edge piece this is (0-11)
    private int pieceId;

    // Orientation: 0 = correct, 1 = flipped
    private int orientation;

    // The two colors on this edge (in a specific order)
    private final char[] colors;

    public Edge(int pieceId, char color1, char color2) {
        this.pieceId = pieceId;
        this.colors = new char[]{color1, color2};
        this.orientation = 0;
    }

    public int getPieceId() { return pieceId; }
    public void setPieceId(int pieceId){ this.pieceId = pieceId; }
    public int getOrientation() { return orientation; }
    public char[] getColors() { return colors; }
    public void setOrientation(int orientation) { this.orientation = orientation; }

    @Override
    public String toString() {
        return "Edge[piece=" + pieceId + ", orient=" + orientation +
                ", colors=" + colors[0] + colors[1] + "]";
    }
}