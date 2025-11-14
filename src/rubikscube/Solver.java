package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private List<Character> solution; // Ex: ['F', 'B', 'R', 'B', ...]
    private boolean foundSolution;
    private static final char[] MOVES = {'F', 'B', 'R', 'L', 'U', 'D'};
    private static int MAX_TIME = 9700; // 9.7s
    private static long startTime;

    public Solver(){
        this.solution = new ArrayList<>();
        this.foundSolution = false;
    }

    /**
     * Solves our cube using IDA* algorithm
     * @param cube Scrambled code we solve
     * @return a string of moves that solves our cube
     */
    public String IDAStarSolve(RubiksCube cube){
        if(cube == null){ return null; }

        // Start timer
        startTime = System.currentTimeMillis();
        if(cube.isSolved()){
            return "";
        }
        int currentThreshold = getHeuristic(cube);

        return null;
    }

    /**
     * Returns the heuristic of the current cube state
     * Utilizes a Misplaced Tile Piece idea to estimate
     * our heuristics. Note: should not overestimate
     */
    private int getHeuristic(RubiksCube cube){
        String cubeState = cube.toString();
        String[] lines = cubeState.split("\n"); // Splitting our cube[9][12] line by line

        int misplacedCounter = 0;

        // Count # of misplaced tiles on each face
        // Top face (Orange)
        misplacedCounter += countMisplaced(lines, 0, 2, 3, 6, 'O');

        // Left face (Green)
        misplacedCounter += countMisplaced(lines, 3, 5, 0, 3, 'G');

        // Front face (White)
        misplacedCounter += countMisplaced(lines, 3, 5, 3, 6, 'W');

        // Right face (Blue)
        misplacedCounter += countMisplaced(lines, 3, 5, 6, 9, 'B');

        // Back face (Yellow)
        misplacedCounter += countMisplaced(lines, 3, 5, 9, 12, 'Y');

        // Bottom face (Red)
        misplacedCounter += countMisplaced(lines, 6, 8, 3, 6, 'R');

        return Math.max(1, misplacedCounter / 4);
    }
    private int countMisplaced(String[] line, int rowStart, int rowEnd, int colStart, int colEnd, char target){
        return -1;
    }


	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

		File input = new File(args[0]);
		File output = new File(args[1]);

        try {
            // Pass in our scrambled.txt file
            RubiksCube cube = new RubiksCube(args[0]);

            // Debugging information
            System.out.println("Reading scrambled cube from: " + input);
            System.out.println("Cube init state: ");
            System.out.println(cube.toString());

            // If cube is already solved, no need to continue
            if(cube.isSolved()){
                System.out.println("Cube is already solved, Exiting");
                return;
            }

            Solver solver = new Solver();

        }
        // Error reading/writing file
        catch (IOException e){
            System.err.println("Error reading input file " + e.getMessage());
        }
        // Not a file at all - Bad format
        catch (IncorrectFormatException e){
            System.err.println("Incorrect format! " + e.getMessage());
        }
	}
}
