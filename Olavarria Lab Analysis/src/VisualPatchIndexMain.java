// The main method to run the entire analysis.
// Client can input the data txt file needed to be analysis, and the calculation will
// be displayed in the console window.
// By Baihan Lin, May 2014

import java.io.*;     // for File, FileNotFoundException
import java.util.*;   // for Scanner, List, Set, Collections
import java.util.List;

import acm.graphics.*;
import acm.gui.IntField;
import acm.program.*; // for ConsoleProgram etc

import java.awt.*; // for listeners etc
import java.awt.event.*;

import javax.swing.*; // for JComponents

public class VisualPatchIndexMain extends ConsoleProgram {

	private static final int DEFAULT_SMOOTH_FACTOR = 10;
	private final String IMAGE1
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\PatchProcessorLogo.jpg";
	private final String IMAGE2
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\imagej.jpg";
	private final String IMAGE3
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\P10ME P21DEV PAT-6 I WGA-HRP s5 line.jpg";
	private final String IMAGE4
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\P10ME P21DEV PAT-6 C WGA-HRP s4 plot.jpg";
	private final String IMAGE5
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\trend.jpg";
	private final String IMAGE6
	= "D:\\Java Eclipse\\eclipse\\PatchProcessor\\file.jpg";

	private JButton back;
	private JButton patchProcessor;
	private JButton smoothFinder;
	private JButton imageJComponent;
	private JButton about;
	private JLabel instruction1;
	private JLabel instruction2;
	private JLabel instruction3;
	private JLabel instruction4;
	private JLabel instruction5;
	private JLabel instruction6;
	private JLabel input;
	private JLabel sf;
	private JLabel head;
	private JButton start;
	private JTextField path;
	private IntField smoothFactor;
	private GCanvas outCanvas;
	private GImage logo;
	private GImage imagej1;
	private GImage imagej2;
	private GImage imagej3;
	private GImage xlsx;
	private GImage filePI;

	public void init() {

		this.setSize(1000, 500);
		setFont("Courier-15");
		setLayout(new GridLayout(1, 2));

		about = new JButton("About");
		patchProcessor = new JButton("Patch Processor");
		smoothFinder = new JButton("Smooth Factor Finder");
		imageJComponent = new JButton("ImageJ Component");

		outCanvas = new GCanvas();

		instruction1 = new JLabel("Instruction: \n");
		instruction2 = new JLabel("First step is to save all your data file ending with "
				+ "\"data.txt\", enter path of the folder");
		instruction3 = new JLabel("with all files to be analyzed. Press enter or click "
				+ "\"analyze\" will start the analyze. ");
		instruction4 = new JLabel("Result will be shown on the console window on the left. "
				+ "A \"result.pi\" file will also be ");
		instruction5 = new JLabel("created in your folder with more concise summary of all"
				+ " the results analyzed.");
		instruction6 = new JLabel("(Note: Smooth factor (SF) if not entered will be treated"
				+ " as default 10)");

		logo = new GImage(IMAGE1);
		imagej1 = new GImage(IMAGE2);
		imagej2 = new GImage(IMAGE3);
		imagej3 = new GImage(IMAGE4);
		xlsx = new GImage(IMAGE5);
		filePI = new GImage(IMAGE6);
		logo.scale(0.5);
		imagej1.scale(0.83);
		imagej2.scale(0.33);
		imagej3.scale(0.25);
		xlsx.scale(0.4);

		outCanvas.add(instruction1, 20, 10);
		outCanvas.add(instruction2, 20, 40);
		outCanvas.add(instruction3, 20, 60);
		outCanvas.add(instruction4, 20, 80);
		outCanvas.add(instruction5, 20, 100);
		outCanvas.add(instruction6, 20, 120);
		outCanvas.add(logo);
		outCanvas.add(imagej1, 10, 10);
		outCanvas.add(imagej2, 10, 140);
		outCanvas.add(imagej3, 160, 180);
		outCanvas.add(xlsx, 10, 50);
		outCanvas.add(filePI, 280, 130);

		head = new JLabel("Version 1.0. Designed by Baihan Lin, Mentored by "
				+ "Dr. Robyn Liang and Prof. Jaime Olavarria. "
				+ "Send all complaints and suggestions to sunnylin@uw.edu.");
		head.setFont(new java.awt.Font("Arial", 0, 12));
		input = new JLabel("File or Folder to analyze: ");
		back = new JButton("back to menu");
		path = new JTextField(10);
		start = new JButton("Analyze");
		smoothFactor = new IntField(DEFAULT_SMOOTH_FACTOR);
		sf = new JLabel("SF:");

		add(head, NORTH);
		add(outCanvas);
		add(about, SOUTH);
		add(patchProcessor, SOUTH);
		add(smoothFinder, SOUTH);
		add(imageJComponent, SOUTH);
		add(back, NORTH);
		add(sf, SOUTH);
		add(smoothFactor, SOUTH);
		add(input, SOUTH);
		add(path, SOUTH);
		add(start, SOUTH);

		addActionListeners();
		path.addActionListener(this);
		smoothFactor.addActionListener(this);

		restore();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == patchProcessor) {
			doAnalysis();
		} else if (e.getSource() == smoothFinder) {
			doSmooth();
		} else if (e.getSource() == imageJComponent) {
			doImageJ();
		} else if (e.getSource() == back) {
			println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			restore();
		} else if (e.getSource() == path || e.getSource() == start) {
			try {
				piMain();
			} catch (FileNotFoundException e1) {
				println("File or folder not found. Please reenter.");
				e1.printStackTrace();
			} catch (IOException e1) {
				println("File input not valid. Please change it.");
				e1.printStackTrace();
			}
		} else if (e.getSource() == about) {
			doIntroduction();
		}
	}


	public void doAnalysis() {
		restore();
		println("\n\n\n\n\n\n\n\n\n\n\n\n");
		println("*******************************************");
		println("--\"Patch Processor\": click on which will lead you to enter path of "
				+ "the folder with all the files to be analyzed. Press "
				+ "enter or click \"analyze\" will start the analyze. Result will be shown"
				+ " on the console window on the left. A \"result.pi\" file will also "
				+ "be created in your folder with more concise summary of all the "
				+ "results analyzed. (Note: Smooth factor (SF) if not entered will be treated"
				+ " as default 10)");
		setFont("Courier-25");	
		println("Important Note: please save your data file ending with \"data.txt\".");
		setFont("Courier-15");

		about.setVisible(false);
		logo.setVisible(false);;
		patchProcessor.setVisible(false);
		smoothFinder.setVisible(false);
		imageJComponent.setVisible(false);

		filePI.setVisible(true);

		back.setVisible(true);
		path.setVisible(true);
		start.setVisible(true);
		sf.setVisible(true);
		smoothFactor.setVisible(true);
		instruction1.setVisible(true);
		instruction2.setVisible(true);
		instruction3.setVisible(true);
		instruction4.setVisible(true);
		instruction5.setVisible(true);
		instruction6.setVisible(true);
		input.setVisible(true);

		path.addActionListener(this);
		smoothFactor.addActionListener(this);
		addActionListeners();
	}

	private void doSmooth() {
		restore();
		xlsx.setVisible(true);
		logo.setVisible(false);
		back.setVisible(true);
		println("\n\n\n\n\n\n\n\n\n\n\n\n");
		println("*******************************************");
		setFont("Courier-20");
		println("Still under development. :)");
		setFont("Courier-15");
		println("*******************************************");
		println("--\"Smooth Factor Finder\": still Under development. click which will leads"
				+ " to an interface where the testing of proper smooth factor is performed on "
				+ "the first file in the given path. I coined smooth factor to indict the "
				+ "period of cycle in our chosen smoothing method--Moving Average. Thus smooth "
				+ "factor is x meaning x per moving average. You can slide the sliding bar "
				+ "ranging from 0 to the max, and observe the dynamic changes in patch index"
				+ "and the simulation of the graph itself on the canvas on the right.");
	}


	private void doImageJ() {
		restore();
		logo.setVisible(false);
		imagej1.setVisible(true);
		imagej2.setVisible(true);
		imagej3.setVisible(true);
		back.setVisible(true);
		println("\n\n\n\n\n\n\n\n\n\n\n\n");
		println("*******************************************");
		setFont("Courier-20");
		println("Still under development. :)");
		setFont("Courier-15");
		println("*******************************************");
		println("--\"ImageJ Component\": still Under development. click which will leads to"
				+ "an interface where we can import our tiff file and draw our line on the "
				+ "interface to be analyzed all together. This includes a plugin of ImageJ"
				+ " into our software but will facilitate the entire process of analyzing "
				+ "patchness of ODC.");
	}

	private void restore() {
		imagej1.setVisible(false);
		imagej2.setVisible(false);
		imagej3.setVisible(false);
		about.setVisible(true);
		logo.setVisible(true);;
		patchProcessor.setVisible(true);
		smoothFinder.setVisible(true);
		imageJComponent.setVisible(true);
		back.setVisible(false);
		path.setVisible(false);
		instruction1.setVisible(false);
		instruction2.setVisible(false);
		instruction3.setVisible(false);
		instruction4.setVisible(false);
		instruction5.setVisible(false);
		instruction6.setVisible(false);
		input.setVisible(false);
		start.setVisible(false);
		sf.setVisible(false);
		smoothFactor.setVisible(false);
		filePI.setVisible(false);
		xlsx.setVisible(false);

		doIntroduction();
	}

	private void doIntroduction() {
		println("******************************************************************");
		println();
		println("About");
		println();
		println("                                                                          "
				+ "                                           This software is developed by "
				+ "Olavarria Lab, Department of Psychology, University of Washington. Main"
				+ " purpose of this software is to determine the patchness of ocular"
				+ "dominance column in V1 on ipsi and contra cortices. Other "
				+ "supplemental softwares includes Photoshop and ImageJ which"
				+ "serve as data sources. ");
		println();
		println("To use this program, you need to prepare the data generated from"
				+ "ImageJ's grayValue plot profile. Please save the data file ending"
				+ "with \"data.txt\" to be identified as target files. ");
		println();
		println("--\"Patch Processor\": click on which will lead you to enter paths of"
				+ " the folder with all the files (ending with \"data.txt\") to be analyzed. "
				+ "Press enter or click \"analyze\" will start the analyze. Result will be "
				+ "shown on the console window on the left. A \"result.pi\" file will also "
				+ "be created in your folder with more concise summary of all the "
				+ "results analyzed. (Note: Smooth factor if not entered will be treated as "
				+ "default 10)");
		println();
		println("--\"Smooth Factor Finder\": still Under development. click which will leads"
				+ " to an interface where the testing of proper smooth factor is performed on "
				+ "the first file in the given path. I coined smooth factor to indict the "
				+ "period of cycle in our chosen smoothing method--Moving Average. Thus smooth "
				+ "factor is x meaning x per moving average. You can slide the sliding bar "
				+ "ranging from 0 to the max, and observe the dynamic changes in patch index"
				+ "and the simulation of the graph itself on the canvas on the right.");
		println();
		println("--\"ImageJ Component\": still Under development. click which will leads to"
				+ "an interface where we can import our tiff file and draw our line on the "
				+ "interface to be analyzed all together. This includes a plugin of ImageJ"
				+ " into our software but will facilitate the entire process of analyzing "
				+ "patchness of ODC.");
		println();
		println("Version 1.0 was designed by Baihan Lin, Mentored by Dr. Robyn Liang"
				+ "and Prof. Jaime Olavarria. As stated above, more functions are still"
				+ "under development. All complaints and suggestions please be sent to "
				+ "sunnylin@uw.edu, and I am more than willing to improve the user "
				+ "experience and functionality of this software.");
	}


	public void piMain() throws FileNotFoundException, IOException {

		println("************************************");
		String fileName = path.getText();
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
		output.close();
	}

	private String generate(File file) throws FileNotFoundException {
		String fileName = file.getName();
		String output = "File doesn't exist.";
		if (fileName.toLowerCase().endsWith("data.txt")) {
			println("-----------------------------");
			println(fileName);

			List<String> lines = readLines(file);
			List<Double> data = list2ArrayList(lines);
			// construct Patch Index solver and begin user input loop
			VisualPatchIndexSolver solver 
			= new VisualPatchIndexSolver(Collections.unmodifiableList(data));

			// do the calculations on patch index solver: Patch Index = 1 - (SM / SI)
			solver.smooth(smoothFactor.getValue());  // n per Moving Average
			String sm = String.valueOf(solver.getSM());
			String si = Double.toString(solver.getSI());
			String pi = Double.toString(solver.doPICalculation());

			output = fileName + "	" + sm + "	" + si  + "	" + pi;
			println("SM: " + sm);
			println("SI: " + si);
			println("PI: " + pi);		
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
