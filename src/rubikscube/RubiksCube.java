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

    }

    public void moveBack(){

    }

    public void moveRight(){

    }

    public void moveLeft(){

    }

    public void moveUp(){

    }

    public void moveDown(){

    }

    // TODO: Might be a little slow, may need to optimize
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

    public Corner[] getCorners(){
        return this.corners;
    }

    public Edge[] getEdges(){
        return this.edges;
    }
}

