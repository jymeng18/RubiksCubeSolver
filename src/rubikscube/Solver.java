package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("File names are not specified");
			System.out.println("usage: java " + MethodHandles.lookup().lookupClass().getName() + " input_file output_file");
			return;
		}

		File input = new File(args[0]);

        try {
            // Pass in our scrambled.txt file
            RubiksCube cube = new RubiksCube(args[0]);

            // Debugging information
            System.out.println("Reading scrambled cube from: " + input);

            // TODO: If cube is already solved, no need to continue

            // Print output
            System.out.println("//======Cubie Testing=====//");
            testCubieMapping(cube);

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

    public static void testCubieMapping(RubiksCube cube){
        Corner[] corners;
        Edge[] edges;

        System.out.println("====== INITIAL SOLVED STATE (from file) ======");
        System.out.println(cube.toString());
        corners = cube.getCorners();
        edges = cube.getEdges();

        System.out.println();
        System.out.println();

        System.out.println("Init");
        corners = cube.getCorners();
        for(int i = 0; i < 8; i ++) {
            System.out.println("Pos: " + i + ": " + corners[i]);
        }

        cube.applyMoves("F");
        corners = cube.getCorners();
        for(int i = 0; i < 8; i ++) {
            System.out.println("Pos: " + i + ": " + corners[i]);
        }

        System.out.println();
        cube.applyMoves("B");
        corners = cube.getCorners();
        for(int i = 0; i < 8; i ++) {
            System.out.println("Pos: " + i + ": " + corners[i]);
        }

        System.out.println();
        cube.applyMoves("B");
        corners = cube.getCorners();
        for(int i = 0; i < 8; i ++) {
            System.out.println("Pos: " + i + ": " + corners[i]);
        }

        System.out.println();
        cube.applyMoves("B");
        corners = cube.getCorners();
        for(int i = 0; i < 8; i ++) {
            System.out.println("Pos: " + i + ": " + corners[i]);
        }

        System.out.println("Solved: " + cube.isSolved());
    }
}
