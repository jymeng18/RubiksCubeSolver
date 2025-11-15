package rubikscube;

import java.util.ArrayList;
import java.util.List;

public class IDAStarSolver {
    private List<Character> solution; // Ex: ['F', 'B', 'R', 'B', ...]
    private boolean foundSolution;
    private static final char[] MOVES = {'F', 'B', 'R', 'L', 'U', 'D'};
    private static int MAX_TIME = 9700; // 9.7s
    private static long startTime;
    private Heuristic heuristic;

    public IDAStarSolver(){
        this.solution = new ArrayList<>();
        this.foundSolution = false;
        this.heuristic = new MisplacedTilesHeuristic();
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
        int currentThreshold = heuristic.calculate(cube);
        System.out.println("Init Heuristic: " + currentThreshold);

        // '35' is an arbitrary value, our threshold could be way higher
        while(currentThreshold <= 35 && !foundSolution){
            if(isTime()){ // Check if we are under 10s runtime
                System.out.println("Time is up! Exiting");
                return null;
            }
            solution.clear();

            // Perform our DFS with A*
            int val = IDASearch(cube, 0, currentThreshold, ' ');

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
    int IDASearch(RubiksCube cube, int g, int limit, char prevMove){
        assert cube != null;

        if(isTime()){ return Integer.MAX_VALUE; }
        if(cube.isSolved()){ return -1; }

        // Obtain heuristic of our current cube state
        int h = heuristic.calculate(cube);
        int f = h + g;

        // Check that f <= lim
        if(f > limit){
            return f; // f becomes newest heuristic
        }
        int min = Integer.MAX_VALUE;

        // TODO: Change to successors later for performance??
        for(char move: MOVES){
            String cubeStateBefore = cube.toString();
            if(isOpposite(prevMove, move)){
                continue; // skip this move
            }
            // Store our moves to solution list
            cube.applyMoves(String.valueOf(move));
            solution.add(move);
            int temp = IDASearch(cube, g+1, limit, move);

            if(temp == -1){ return -1; }
            if(temp < min){
                min = temp;
            }

            // Undo moves
            solution.removeLast();
            for(int i = 0; i < 3; i++){
                cube.applyMoves(String.valueOf(move));
            }
        }
        return min;
    }

    /**
     * Avoid opposite moves as they are not search friendly
     * Do not affect each other much, redundant behaviour
     */
    private boolean isOpposite(char lastMove, char curMove){
        if(lastMove == ' '){ return false; }
        if((lastMove == 'F' && curMove == 'B') || (lastMove == 'B' && curMove == 'F')){ return true; }
        if((lastMove == 'L' && curMove == 'R') || (lastMove == 'R' && curMove == 'L')){ return true; }
        return (lastMove == 'U' && curMove == 'D') || (lastMove == 'D' && curMove == 'U');
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

}
