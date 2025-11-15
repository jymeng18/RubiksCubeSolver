package rubikscube;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class Solver {
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

            IDAStarSolver solver = new IDAStarSolver();
            String solution = solver.IDAStarSolve(cube);
            System.out.println("Sol: " + solution);
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
