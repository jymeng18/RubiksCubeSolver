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
        System.out.println("Init Heuristic: " + currentThreshold);

        // '35' is an arbitrary value, our threshold could be way higher
        while(currentThreshold <= 35 && !foundSolution){
            if(isTime()){ // Check if we are under 10s runtime
                System.out.println("Time is up! Exiting");
                return null;
            }
            solution.clear();

            // Perform our DFS with A*
            int val = IDASearch(cube, 0, currentThreshold);

            // Solution found or out of time
            if(val == -1){
                foundSolution = true;
                System.out.println("Found sol");
                break;
            }

            // Solution was not found
            if(val == Integer.MAX_VALUE){
                break;
            }
            currentThreshold = val;
        }
        // Give user back the solution array as a str
        return solutionToString();
    }

    /**
     * Iterative Deepening DFS + A*
     * @param cube cube state
     * @param g actual cost
     * @param limit currentThreshold
     * @return -1 for fail, Integer.MAX_VALUE for success
     */
    int IDASearch(RubiksCube cube, int g, int limit){
        assert cube != null;

        if(isTime()){ return Integer.MAX_VALUE; }
        if(cube.isSolved()){ return -1; }

        // Obtain heuristic of our current cube state
        int h = getHeuristic(cube);
        int f = h + g;

        // Check that f <= lim
        if(f > limit){
            return f; // f becomes newest heuristic
        }
        int min = Integer.MAX_VALUE;

        // TODO: Change to successors later for performance
        for(char move: MOVES){
            String cubeStateBefore = cube.toString();

            // Store our moves to solution list
            cube.applyMoves(String.valueOf(move));
            solution.add(move);
            int temp = IDASearch(cube, g+1, limit);

            if(temp == -1){ return -1; }
            if(temp < min){
                min = temp;
            }

            // Undo moves
            solution.remove(solution.size() - 1);
            for(int i = 0; i < 3; i++){
                cube.applyMoves(String.valueOf(move));
            }
        }
        return min;
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

        // Underestimate guess, may need to change later
        return Math.max(1, misplacedCounter / 4);
    }

    /**
     * Helper method for getHeuristic(..)
     */
    private int countMisplaced(String[] line, int rowStart, int rowEnd, int colStart, int colEnd, char target){
        if(line == null){ return -1; }

        int count = 0;
        for(int i = rowStart; i <= rowEnd; i++){
            // Bounds checking
            if(i < line.length && line[i].length() >= colEnd) {
                for (int j = colStart; j < colEnd; j++) {
                    // Checking for mismatches
                    if(j < line[i].length() && line[i].charAt(j) != target && line[i].charAt(j) != ' '){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private boolean isTime(){
        return (System.currentTimeMillis() - startTime) > MAX_TIME;
    }

    private String solutionToString() {
        StringBuilder sb = new StringBuilder();
        for (char move : solution) {
            sb.append(move);
        }
        return sb.toString();
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
            String sol = solver.IDAStarSolve(cube);
            System.out.println("Sol: " + sol);
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
