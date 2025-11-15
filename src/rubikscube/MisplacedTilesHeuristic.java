package rubikscube;

public class MisplacedTilesHeuristic implements Heuristic {

    @Override
    public int calculate(RubiksCube cube) {
        String cubeState = cube.toString();
        String[] lines = cubeState.split("\n");

        int misplacedCounter = 0;

        misplacedCounter += countMisplaced(lines, 0, 2, 3, 6, 'O'); // Top face (Orange)
        misplacedCounter += countMisplaced(lines, 3, 5, 0, 3, 'G'); // Left face (Green)
        misplacedCounter += countMisplaced(lines, 3, 5, 3, 6, 'W'); // Front face (White)
        misplacedCounter += countMisplaced(lines, 3, 5, 6, 9, 'B'); // Right face (Blue)
        misplacedCounter += countMisplaced(lines, 3, 5, 9, 12, 'Y'); // Back face (Yellow)
        misplacedCounter += countMisplaced(lines, 6, 8, 3, 6, 'R'); // Bottom face (Red)

        return Math.max(1, misplacedCounter / 4);
    }

    private int countMisplaced(String[] lines, int rowStart, int rowEnd, int colStart, int colEnd, char target) {
        if (lines == null) return -1;

        int count = 0;
        for (int i = rowStart; i <= rowEnd; i++) {
            if (i < lines.length && lines[i].length() >= colEnd) {
                for (int j = colStart; j < colEnd; j++) {
                    if (j < lines[i].length() && lines[i].charAt(j) != target && lines[i].charAt(j) != ' ') {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}