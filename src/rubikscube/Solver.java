package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

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
            System.out.println("Cube init state: ");
            System.out.println(cube.toString());

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
        Corner[] corners = cube.getCorners();
        Edge[] edges = cube.getEdges();

        System.out.println("\nCorners");
        for(int i = 0; i < 8 && i < corners.length; i++){
            System.out.println(" Position "+ i + ": " + corners[i]);
        }

        System.out.println("\nEdges");
        for(int i = 0; i < 12 && i < edges.length; i++){
            System.out.println(" Position " + i + ": " + edges[i]);
        }

    }
}
