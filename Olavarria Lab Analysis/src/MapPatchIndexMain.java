// The main method to run the entire analysis.
// Client can input the data txt file needed to be analysis, and the calculation will
// be displayed in the console window.
// By Baihan Lin, May 2014

import java.io.*;     // for File, FileNotFoundException
import java.util.*;   // for Scanner, List, Set, Collections

public class MapPatchIndexMain {

	private static final int SMOOTH_FACTOR = 10;

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Welcome to the PatchIndex smoother and generator!");
		System.out.println();

		// open data file
		System.out.print("What is the name of the data file? ");
		Scanner console = new Scanner(System.in);
		String fileName = console.nextLine();

		List<String> lines = readLines(fileName);
		Map<Integer, Double> data = list2Map(lines);

		// construct Patch Index solver and begin user input loop
		MapPatchIndexSolver solver = new MapPatchIndexSolver(Collections.unmodifiableMap(data));

		// do the calculations on patch index solver: Patch Index = 1 - (SM / SI)
		solver.smooth(SMOOTH_FACTOR);  // 10 per Moving Average
		solver.doPICalculation();
	}

	// Reads text from the file with the given name and returns as a List.
	// Strips empty lines and trims leading/trailing whitespace from each line.
	// pre: a file with the given name exists, throws FileNotFoundException otherwise
	private static List<String> readLines(String fileName) throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		Scanner input = new Scanner(new File(fileName));
		while (input.hasNextLine()) {
			String line = input.nextLine().trim();
			if (line.length() > 0) {
				lines.add(line);
			}
		}
		return lines;
	}

	// construct a Map of X-Y correlations
	// pre: a list of string, throws an IllegalArgumentException null
	private static Map<Integer, Double> list2Map(List<String> data) {
		if (data == null) {  
			throw new IllegalArgumentException();
		}
		Map<Integer, Double> grayValue = new TreeMap<Integer, Double>();
		for (String element : data) {
			int key = Integer.parseInt(element.split("[ \t]+")[0]);
			double value = Double.parseDouble(element.split("[ \t]+")[1]);
			grayValue.put(key, value);	
		}
		return grayValue; 
	}

}
