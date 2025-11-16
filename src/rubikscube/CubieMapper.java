package rubikscube;

public class CubieMapper {
    /**
     * Coordinate in 2D cubeState array
     */
    private static class Coord {
        int row, col;
        Coord(int row, int col){
            this.row = row;
            this.col = col;
        }
    }

    // Define the 8 corner positions and their facelet coordinates
    // Each corner has 3 facelets in a specific order
    private static final Coord[][] CORNER_COORDS = {
        // Corner 0: UFL Orange White Green
        {new Coord(2, 3), new Coord(3, 3), new Coord(3, 2) },
        // Corner 1: UFR Orange White Blue
        {new Coord(2, 5), new Coord(3, 5), new Coord(3, 6)},
        // Corner 2: UBL Orange Yellow Green
        {new Coord(0, 3), new Coord(3, 11), new Coord(3, 0)},
        // Corner 3: UBR Orange Yellow Blue
        {new Coord(0, 5), new Coord(3, 9), new Coord(3, 8)},
        // Corner 4: DFL Red White Green
        {new Coord(6, 3), new Coord(5, 3), new Coord(5, 2)},
        // Corner 5: DFR Red White Blue
        {new Coord(6, 5), new Coord(5, 5), new Coord(5, 6)},
        // Corner 6: DBL Red Yellow Green
        {new Coord(8, 3), new Coord(5, 11), new Coord(5, 0)},
        // Corner 7: DBR Red Yellow Blue
        {new Coord(8, 5), new Coord(5, 9), new Coord(5, 8)},
    };

    // Define 12 edge positions and their facelet coordinates
    // Every edge has 2 facelets
    private static final Coord[][] EDGE_COORDS = {
            // Edge 0: UF
            {new Coord(2, 4), new Coord(3, 4)},
            // Edge 1: UL
            {new Coord(1, 3), new Coord(3, 1)},
            // Edge 2: UR
            {new Coord(1, 5), new Coord(3, 7)},
            // Edge 3: UB
            {new Coord(0, 4), new Coord(3, 10)},
            // Edge 4: DF
            {new Coord(6, 4), new Coord(5, 4)},
            // Edge 5: DL
            {new Coord(7, 3), new Coord(5, 1)},
            // Edge 6: DR
            {new Coord(7, 5), new Coord(5, 7)},
            // Edge 7: DB
            {new Coord(8, 4), new Coord(5, 10)},
            // Edge 8: FL
            {new Coord(4, 3), new Coord(4, 2)},
            // Edge 9: FR
            {new Coord(4, 5), new Coord(4, 6)},
            // Edge 10: BL
            {new Coord(4, 11), new Coord(4, 0)},
            // Edge 11: BR
            {new Coord(4, 9), new Coord(4, 8)},
    };

    /**
     * Get all corners from the cubeState
     */
    public static Corner[] getCorners(char [][] cubeState){
        Corner[] corners = new Corner[8];
        for(int i = 0; i < 8; i++){
            Coord[] coords = CORNER_COORDS[i];
            char c1 = cubeState[coords[0].row][coords[0].col];
            char c2 = cubeState[coords[1].row][coords[1].col];
            char c3 = cubeState[coords[2].row][coords[2].col];
            corners[i] = new Corner(i ,c1, c2, c3);
        }
        return corners;
    }
}
