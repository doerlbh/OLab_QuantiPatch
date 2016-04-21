// The main method to run the entire analysis.
// Client can input the data txt file needed to be analysis, and the calculation will
// be displayed in the console window.

import java.io.*;     // for File, FileNotFoundException
import java.text.*;   // for Calendar formating
import java.util.*;   // for Scanner, List, Set, Collections

public class AutoPatchIndexMain {

	private static final int SMOOTH_FACTOR = 10;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("Welcome to the PatchIndex smoother and generator!");
		System.out.println();

		// open data file
		System.out.print("Please input the folder or file with all the data: ");
		Scanner console = new Scanner(System.in);
		String fileName = console.nextLine();
		File files = new File(fileName);
		File result = new File(fileName, "result.pi");
		result.createNewFile(); // create output file
		PrintStream output = new PrintStream(result);

		if (files.isDirectory()) {
			output.println("result for directory: " + fileName);
			output.println("FileName	SM	SI	PI=1-SM/SI");
			output.println("-----------------------------------");
			File allFiles[] = files.listFiles();
			for (File file : allFiles) {
				if (!generate(file).equals("File doesn't exist.")) {
					output.println(generate(file));
				}
			}
		} else {
			output.println("result for file: " + fileName);
			output.println("FileName	SM	SI	PI=1-SM/SI");
			output.println("-----------------------------------");
			output.println(generate(files));
		}
	}

	private static String generate(File file) throws FileNotFoundException {
		String fileName = file.getName().toLowerCase();
		String output = "File doesn't exist.";
		if (fileName.endsWith("data.txt")) {
			System.out.println("-----------------------------");
			System.out.println(fileName);
			
			List<String> lines = readLines(file);
			List<Double> data = list2ArrayList(lines);
			// construct Patch Index solver and begin user input loop
			PatchIndexSolver solver = new PatchIndexSolver(Collections.unmodifiableList(data));

			// do the calculations on patch index solver: Patch Index = 1 - (SM / SI)
			solver.smooth(SMOOTH_FACTOR);  // 10 per Moving Average
			String sm = String.valueOf(solver.getSM());
			String si = Double.toString(solver.getSI());
			String pi = Double.toString(solver.doPICalculation());

			output = fileName + "	" + sm + "	" + si  + "	" + pi;
		}
		return output;
	}


	// Reads text from the file with the given name and returns as a List.
	// Strips empty lines and trims leading/trailing whitespace from each line.
	// pre: a file with the given name exists, throws FileNotFoundException otherwise
	private static List<String> readLines(File file) throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();
		Scanner input = new Scanner(file);
		while (input.hasNextLine()) {
			String line = input.nextLine().trim();
			if (line.length() > 0) {
				lines.add(line);
			}
		}
		return lines;
	}

	// construct a list of X-Y correlations
	// pre: a list of string, throws an IllegalArgumentException null
	private static List<Double> list2ArrayList(List<String> data) {
		if (data == null) {  
			throw new IllegalArgumentException();
		}
		List<Double> grayValue = new ArrayList<Double>();
		for (String element : data) {
			int index = Integer.parseInt(element.split("[ \t]+")[0]);
			double value = Double.parseDouble(element.split("[ \t]+")[1]);
			grayValue.add(index, value);	
		}
		return grayValue; 
	}

}
