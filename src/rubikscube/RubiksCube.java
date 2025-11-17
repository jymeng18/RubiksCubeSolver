package rubikscube;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RubiksCube {

    private final char[][] cubeState;
    Corner[] corners;
    Edge[] edges;

    /**
     * @param fileName file to read from
     * @throws IOException If file is not read properly
     * @throws IncorrectFormatException Creates a Rubik's Cube from the description in fileName
     */
    public RubiksCube(String fileName) throws IOException, IncorrectFormatException {
        if(fileName == null){
            throw new IllegalArgumentException("Filename must be valid");
        }
        this.cubeState = new char[9][12];

        // Read file 'fileName'
        BufferedReader input = new BufferedReader(new FileReader(fileName));

        // Read each line in the file
        String line;
        for(int i = 0; i < cubeState.length; i++){
            line = input.readLine();

            // Ensure we are reading only and all 9 rows
            if(line == null){
                throw new IncorrectFormatException("Incorrect format, Filename: " + fileName);
            }

            // Each row has a maximum of 12 chars
            if(line.length() > cubeState[i].length){
                throw new IncorrectFormatException("Incorrect format, Filename: " + fileName);
            }

            // Store our read data onto our cube
            for(int j = 0; j < line.length(); j++){
                cubeState[i][j] = line.charAt(j);
            }
        }
        this.corners = CubieMapper.getCorners(cubeState);
        this.edges = CubieMapper.getEdges(cubeState);
        input.close();
    }

    /**
     * Applies the sequence of moves on the Rubik's Cube
     */
    public void applyMoves(String moves) {

        for(int i = 0; i < moves.length(); i++) {
            char move = moves.charAt(i);

            switch (move) {
                case 'F':
                    moveFront();
                    break;
                case 'B':
                    moveBack();
                    break;
                case 'R':
                    moveRight();
                    break;
                case 'L':
                    moveLeft();
                    break;
                case 'U':
                    moveUp();
                    break;
                case 'D':
                    moveDown();
                    break;
            }
        }
    }

    /**
     * Rotate front face 90 degrees clockwise
     */
    public void moveFront() {
        // UFL -> UFR -> DFR -> DFL
        cycleCornersClockwise(0, 1, 5, 4);

        // Update corner orientations **after cycling**
        corners[0].setOrientation((corners[0].getOrientation() + 1) % 3);
        corners[1].setOrientation((corners[1].getOrientation() + 2) % 3);
        corners[5].setOrientation((corners[5].getOrientation() + 1) % 3);
        corners[4].setOrientation((corners[4].getOrientation() + 2) % 3);

        // UF -> FR -> DF -> FL
        cycleEdgesClockwise(0, 9, 4, 8);

        edges[0].setOrientation((edges[0].getOrientation() + 1) % 2);
        edges[9].setOrientation((edges[9].getOrientation() + 1) % 2);
        edges[4].setOrientation((edges[4].getOrientation() + 1) % 2);
        edges[8].setOrientation((edges[8].getOrientation() + 1) % 2);
    }

    /**
     * Rotate back face 90 degrees clockwise
     */
    public void moveBack() {
        // UBR -> UBL -> DBL -> DBR
        cycleCornersClockwise(3, 2, 6, 7);

        corners[3].setOrientation((corners[3].getOrientation() + 1) % 3);
        corners[2].setOrientation((corners[2].getOrientation() + 2) % 3);
        corners[6].setOrientation((corners[6].getOrientation() + 1) % 3);
        corners[7].setOrientation((corners[7].getOrientation() + 2) % 3);

        // UB -> BL -> DB -> BR
        cycleEdgesClockwise(3, 10, 7, 11);

        edges[3].setOrientation((edges[3].getOrientation() + 1) % 2);
        edges[10].setOrientation((edges[10].getOrientation() + 1) % 2);
        edges[7].setOrientation((edges[7].getOrientation() + 1) % 2);
        edges[11].setOrientation((edges[11].getOrientation() + 1) % 2);
    }

    /**
     * Rotate right face 90 degrees clockwise
     */
    public void moveRight() {
        // UFR -> UBR -> DBR -> DFR
        cycleCornersClockwise(1, 3, 7, 5);

        corners[1].setOrientation((corners[1].getOrientation() + 1) % 3);
        corners[3].setOrientation((corners[3].getOrientation() + 2) % 3);
        corners[7].setOrientation((corners[7].getOrientation() + 1) % 3);
        corners[5].setOrientation((corners[5].getOrientation() + 2) % 3);

        // UR -> BR -> DR -> FR
        cycleEdgesClockwise(2, 11, 6, 9);
    }

    /**
     * Rotate left face 90 degrees clockwise
     */
    public void moveLeft() {
        // UBL -> UFL -> DFL -> DBL
        cycleCornersClockwise(2, 0, 4, 6);

        corners[2].setOrientation((corners[2].getOrientation() + 1) % 3);
        corners[0].setOrientation((corners[0].getOrientation() + 2) % 3);
        corners[4].setOrientation((corners[4].getOrientation() + 1) % 3);
        corners[6].setOrientation((corners[6].getOrientation() + 2) % 3);

        // UL -> FL -> DL -> BL
        cycleEdgesClockwise(1, 8, 5, 10);
    }

    /**
     * Rotate up face 90 degrees clockwise
     * No corner orientation changes
     */
    public void moveUp() {
        // UBL -> UBR -> UFR -> UFL
        cycleCornersClockwise(2, 3, 1, 0);

        // UB -> UR -> UF -> UL
        cycleEdgesClockwise(3, 2, 0, 1);
    }

    /**
     * Rotate down face 90 degrees clockwise
     * No corner orientation changes
     */
    public void moveDown() {
        // DFL -> DFR -> DBR -> DBL
        cycleCornersClockwise(4, 5, 7, 6);

        // DF -> DR -> DB -> DL
        cycleEdgesClockwise(4, 6, 7, 5);
    }


    /**
     * Helper method to cycle 4 corners clockwise
     * Example: a -> b -> c -> d -> a
     */
    private void cycleCornersClockwise(int a, int b, int c, int d) {
        Corner temp = corners[a];
        corners[a] = corners[d];
        corners[d] = corners[c];
        corners[c] = corners[b];
        corners[b] = temp;
    }

    /**
     * Helper method to cycle 4 edges clockwise
     * Example: a -> b -> c -> d -> a
     */
    private void cycleEdgesClockwise(int a, int b, int c, int d) {
        Edge temp = edges[a];
        edges[a] = edges[d];
        edges[d] = edges[c];
        edges[c] = edges[b];
        edges[b] = temp;
    }

    /**
     * Check if cube is solved using cubie representation
     * All pieces must be in their home positions with orientation 0
     */
    public boolean isSolved() {
        // Check all corners are in home position with correct orientation
        for(int i = 0; i < 8; i++) {
            if(corners[i].getPieceId() != i || corners[i].getOrientation() != 0) {
                return false;
            }
        }

        // Check all edges are in home position with correct orientation
        for(int i = 0; i < 12; i++) {
            if(edges[i].getPieceId() != i || edges[i].getOrientation() != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Must turn our 2D array into a String
        for(int i = 0; i < cubeState.length; i++){
            if(i < 3 || i > 5) {
                // Rows 0-2 and 6-8: Output only 6 chars including spaces
                for (int j = 0; j < 6; j++) {
                    sb.append(cubeState[i][j]);
                }
            }

            else{
                // Rows 3-5: Outrputs all 12 chars
                for(int j = 0; j < 12; j++){
                    sb.append(cubeState[i][j]);
                }
            }
            sb.append('\n');
        }

        // Method returns String, not StringBuilder obj.
        return sb.toString();
    }

    public Corner[] getCorners(){
        return this.corners;
    }

    public Edge[] getEdges(){
        return this.edges;
    }
}

