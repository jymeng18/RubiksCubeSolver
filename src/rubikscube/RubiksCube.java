package rubikscube;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RubiksCube {

    private final char[][] cubeState;
    private final char[][] cubeInit;

    /**
     * default constructor
     * Creates a Rubik's Cube in an initial state:
     *    OOO
     *    OOO
     *    OOO
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     *    RRR
     *    RRR
     *    RRR
     */
    public RubiksCube() {
        this.cubeState = new char[9][12];
        this.cubeInit = new char[9][12];

        // Fill Rows 0-2 and 6-8: positions 0-2 should be spaces
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cubeState[row][col] = ' ';
            }
        }

        for (int row = 6; row < 9; row++) {
            for (int col = 0; col < 3; col++) {
                cubeState[row][col] = ' ';
            }
        }

        // Fill Face - Up (Orange)
        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 6; col++) {
                cubeState[row][col] = 'O';
            }
        }

        // Fill Face - Left (Green)
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                cubeState[row][col] = 'G';
            }
        }

        // Fill Face - Front (White)
        for (int row = 3; row < 6; row++) {
            for (int col = 3; col < 6; col++) {
                cubeState[row][col] = 'W';
            }
        }

        // Fill Face - Right (Blue)
        for (int row = 3; row < 6; row++) {
            for (int col = 6; col < 9; col++) {
                cubeState[row][col] = 'B';
            }
        }

        // Fill Face - Back (Yellow)
        for (int row = 3; row < 6; row++) {
            for (int col = 9; col < 12; col++) {
                cubeState[row][col] = 'Y';
            }
        }

        // Fill Face - Down (Red)
        for (int row = 6; row < 9; row++) {
            for (int col = 3; col < 6; col++) {
                cubeState[row][col] = 'R';
            }
        }

        // Make a copy
        for(int row = 0; row < cubeState.length; row++){
            System.arraycopy(cubeState[row], 0, this.cubeInit[row], 0, cubeState[row].length);
        }
    }

    /**
     * @param fileName file to read from
     * @throws IOException If file is not read properly
     * @throws IncorrectFormatException Creates a Rubik's Cube from the description in fileName
     */
    public RubiksCube(String fileName) throws IOException, IncorrectFormatException {
        // Precondition
        if(fileName == null){
            throw new IllegalArgumentException("Filename must be valid");
        }

        this.cubeState = new char[9][12];
        this.cubeInit = new char[9][12];

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

        // Make a copy
        for(int row = 0; row < cubeState.length; row++){
            System.arraycopy(cubeState[row], 0, this.cubeInit[row], 0, cubeState[row].length);
        }

        input.close();
    }

    /**
     * @param moves Applies the sequence of moves on the Rubik's Cube
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

    public void moveFront(){
        char[] temp = new char[3];

        for (int i = 0; i < 3; i++) {
            temp[i] = cubeState[3 + i][6];
        }

        for (int i = 0; i < 3; i++) {
            cubeState[3+i][6] = cubeState[2][3 + i];
        }

        for (int i = 0; i < 3; i++) {
            cubeState[2][3 + i] = cubeState[5 - i ][2];
        }

        for (int i = 0; i < 3; i++) {
            cubeState[5 - i][2] = cubeState[6][5 - i];
        }

        for (int i = 0; i < 3; i++) {
            cubeState[6][5 - i] = temp[i];
        }
        rotateClockwise(3, 3);
    }

    public void moveBack(){
        char[] temp = new char[3];

        // Save Up top row
        System.arraycopy(cubeState[0], 3, temp, 0, 3);

        // Right-> up
        for (int i = 0; i < 3; i++) {
            cubeState[0][3 + i] = cubeState[3 + i][8];
        }

        // Down to Right
        for (int i = 0; i < 3; i++) {
            cubeState[3 + i][8] = cubeState[8][5-i];
        }

        // Left to bottom
        for (int i = 0; i < 3; i++) {
            cubeState[8][5-i] = cubeState[5-i][0];
        }

        // Up to Right
        for (int i = 0; i < 3; i++) {
            cubeState[5-i][0] = temp[i];
        }
        rotateClockwise(3, 9);
    }

    public void moveRight(){
        char[] temp = new char[3];

        for(int i = 0; i < 3; i++) {
            temp[i] = cubeState[3+i][5];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[3+i][5] = cubeState[6+i][5];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[6+i][5] = cubeState[5-i][9];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[5-i][9] = cubeState[i][5];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[i][5] = temp[i];
        }
        rotateClockwise(3, 6);
    }

    public void moveLeft(){
        char[] temp = new char[3];

        for(int i = 0; i < 3; i++) {
            temp[i] = cubeState[i][3];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[i][3] = cubeState[3+i][3];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[3+i][3] = cubeState[6+i][3];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[6+i][3] = cubeState[3+i][9];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[3+i][9] = temp[i];
        }
        rotateClockwise(3, 0);
    }

    public void moveUp(){
        char[] temp = new char[3];

        System.arraycopy(cubeState[3], 3, temp, 0, 3);

        for(int i = 0; i < 3; i++) {
            cubeState[3][3+i] = cubeState[3][6+i];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[3][6+i] = cubeState[3][9+i];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[3][9+i] = cubeState[3][i];
        }

        System.arraycopy(temp, 0, cubeState[3], 0, 3);
        rotateClockwise(0, 3);
    }

    public void moveDown(){
        char[] temp = new char[3];

        System.arraycopy(cubeState[5], 3, temp, 0, 3);

        for(int i = 0; i < 3; i++) {
            cubeState[5][3+i] = cubeState[5][i];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[5][i] = cubeState[5][9+i];
        }

        for(int i = 0; i < 3; i++) {
            cubeState[5][9+i] = cubeState[5][6+i];
        }

        System.arraycopy(temp, 0, cubeState[5], 6, 3);
        rotateClockwise(6, 3);
    }

    /**
     * @param row Top left y
     * @param col Top left x
     * Attention! We are passing in our starting row, col of our face, this is
     * found at the top leftt of the face
     * Layout: C E C
     *         E X E
     *         C E C     * : Corners, E: Edges, X: Middle(Irrelevant, b/c rotating clockwise doesn't affect middle)
     */
    private void rotateClockwise(int row, int col) {

        // Save corners and edges for rotation
        char[] corners = new char[4];
        char[] edges = new char[4];

        // Save corners
        corners[0] = cubeState[row][col];         // top-left
        corners[1] = cubeState[row][col + 2];     // top-right
        corners[2] = cubeState[row + 2][col + 2]; // bottom-right
        corners[3] = cubeState[row + 2][col];     // bottom-left

        // Save edges
        edges[0] = cubeState[row][col + 1];       // top
        edges[1] = cubeState[row + 1][col + 2];   // right
        edges[2] = cubeState[row + 2][col + 1];   // bottom
        edges[3] = cubeState[row + 1][col];       // left

        // Rotate corners clockwise
        cubeState[row][col + 2] = corners[0];     // top-left -> top-right bc its clockwise
        cubeState[row + 2][col + 2] = corners[1]; // top-right -> bottom-right
        cubeState[row + 2][col] = corners[2];     // bottom-right -> bottom-left
        cubeState[row][col] = corners[3];         // bottom-left -> top-left

        // Rotate edges clockwise
        cubeState[row + 1][col + 2] = edges[0];   // top -> right
        cubeState[row + 2][col + 1] = edges[1];   // right -> bottom
        cubeState[row + 1][col] = edges[2];       // bottom -> left
        cubeState[row][col + 1] = edges[3];       // left -> top

    }

    /**
     * returns true if the current state of the Cube is solved,
     * i.e., it is in this state:
     * OOO
     * OOO
     * OOO
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     * GGGWWWBBBYYY
     * RRR
     * RRR
     * RRR
     */
    public boolean isSolved() {
        // Check Top face (Orange) - rows 0-2, cols 3-5
        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < 6; col++) {
                if (cubeState[row][col] != 'O') return false;
            }
        }

        // Check Left face (Green) - rows 3-5, cols 0-2
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col < 3; col++) {
                if (cubeState[row][col] != 'G') return false;
            }
        }

        // Check Front face (White) - rows 3-5, cols 3-5
        for (int row = 3; row < 6; row++) {
            for (int col = 3; col < 6; col++) {
                if (cubeState[row][col] != 'W') return false;
            }
        }

        // Check Right face (Blue) - rows 3-5, cols 6-8
        for (int row = 3; row < 6; row++) {
            for (int col = 6; col < 9; col++) {
                if (cubeState[row][col] != 'B') return false;
            }
        }

        // Check Back face (Yellow) - rows 3-5, cols 9-11
        for (int row = 3; row < 6; row++) {
            for (int col = 9; col < 12; col++) {
                if (cubeState[row][col] != 'Y') return false;
            }
        }

        // Check Bottom face (Red) - rows 6-8, cols 3-5
        for (int row = 6; row < 9; row++) {
            for (int col = 3; col < 6; col++) {
                if (cubeState[row][col] != 'R') return false;
            }
        }

        return true;
    }

    /**
     * @return cube_state as a string instead of a 2D Array
     */

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

    /**
     *
     * @param moves Applied moves to Cube
     * @return the order of the sequence of moves
     */
    public static int order(String moves) {
        // Precondition: Must have moves
        if(moves.isEmpty()){
            return -1;
        }

        RubiksCube cube = new RubiksCube();
        int counter = 0;
        boolean isInit = false;

        while(!isInit){
            cube.applyMoves(moves);
            counter++; // count # of times it takes to return to init state
            if(cube.isSolved()){
                isInit = true;
            }
        }
        return counter;
    }

}

