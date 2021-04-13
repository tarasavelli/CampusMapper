// --== CS400 File Header Information ==--
// Name: Yash Agrawal
// Email: yagrawal3@wisc.edu
// Team: red
// Role: Frontend Developer
// TA: Mu Cai
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The Frontend to the Campus Project Mapper
 * @author Yash Agrawal
 *
 */
public class Frontend {
	 // Initializes the backend to pass input to
	public static Backend back = new Backend();
	 /**
	  * Driver method for class
	  * @param args command line argument
	  */
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		printIntro();
		Character in = 'z';
		 // Continues to check if the input for mode is changed to X or exit mode
		 // so that program can stop
		while(in != 'x') {
			in = getInput(console);
			serveMode(in, console);
			if(in != 'x') {
				printModes();
			}
		}
	}
	
	 /**
	  * Prints the intro to the program along with details on what each mode does
	  */
	private static void printIntro() {
		System.out.println("Welcome to the Campus Mapper!");
		System.out.println("You will have 6 modes to choose from!");
		System.out.println("[B] Base Mode: Give Two Locations and get the shortest path and distance between the two.");
		System.out.println("[O] One Point Mode: Give One location and find the nearest Points of Interest"
				+ " around one intersection away.");
		System.out.println("[C] Construction Mode: Limit Roads.");
		System.out.println("[S] Scheduler Mode: Find the path between more that two locations.");
		System.out.println("[P] Prominent Locations: Give any prominenent buildings in between two locations.");
		System.out.println("[A] Overview: List all prominenet locations and intersections.");
		System.out.println("[X] Press X to Exit");
		System.out.println("Type Letter Corresponding to the Mode");
	}
	
	/**
	 * Prints the different modes available
	 */
	private static void printModes() {
		System.out.println("[B] Base Mode");
		System.out.println("[O] One Point Mode");
		System.out.println("[C] Construction Mode");
		System.out.println("[S] Scheduler Mode");
		System.out.println("[P] Prominent Locations");
		System.out.println("[A] Overview");
		System.out.println("[X] Press X to Exit");
		System.out.println("Type Letter Corresponding to the Mode");
	}
	
	/**
	 * Checks the input validity for the modes requested
	 * @param console Scanner object for input
	 * @return a character that refers to one of the modes 
	 */
	private static Character getInput(Scanner console) {
		 // Regex for acceptable mode letters (case insensitive)
		while(!console.hasNext("[BbOoCcSsPpAaXx]")) {
			System.out.println("Incorrect Input. Please enter a valid character!");
			console.nextLine();
		}
		 // Correct input read, changed to lower case and returned
		Character in = Character.toLowerCase(console.nextLine().charAt(0));
		return in;
	}
	
	/**
	 * Checks to see what mode is request and serves the mode appropriately
	 * @param in character representing the mode requested
	 * @param console Scanner object to pass for additional input needed
	 */
	private static void serveMode(char in, Scanner console) {
		switch(in) {
		case 'b':
			baseMode(console);
			return;
		case 'o':
			oneMode(console);
			return;
		case 'c':
			constructionMode(console);
			return;
		case 's':
			schedulerMode(console);
			return;
		case 'p':
			listProminentLocations(console);
			return;
		case 'a':
			overview();
			return;
		case 'x':
			return;
		}
	}
	
	/**
	 * Validates the input of user to see if correct format of location name
	 * @param console Scanner object to take in input through
	 * @return the formatted string name of location inputted
	 */
	private static String inputValidation(Scanner console) {
		boolean correctInput = false;
		String formattedPath = "";
		while(!correctInput) {
			String place = console.nextLine();
			 //  As long as location is spelled correctly and has the & symbol correctly placed input
			 // will be taken correctly
			if(place.matches(".*&.*")) {
				 // Formats string for backend
				place = place.replaceAll("\\s", "");
				place = place.toLowerCase();
				String[] parts = place.split("&");
				parts[0] = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);
				parts[1] = parts[1].substring(0, 1).toUpperCase() + parts[1].substring(1);
				formattedPath = parts[0] + " & " + parts[1];
				correctInput = true;
			}
			else {
				System.out.println("Incorrect Input, Try Again");
			}
		}
		return formattedPath;
	}
	
	/**
	 * Serves the base mode: given 2 locations it will get the shortest path to get
	 * from one place to the other
	 * @param console Scanner object for input
	 */
	public static void baseMode(Scanner console) {
		System.out.println("Input your starting intersection (Location & Location).");
		String start = inputValidation(console);
		System.out.println("Input your ending intersection (Location & Location).");
		String end = inputValidation(console);
		try {
			Path route = back.getRoute(start, end);
			System.out.print("The Shortest Path: ");
			printPathsIntersection(route);
			System.out.println("The Distance: " + route.getDistance() + "ft");
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
		}
	}
	
	/**
	 * Gives all the Points of Interest in a one intersection radius
	 * @param console Scanner object for input
	 */
	public static void oneMode(Scanner console) {
		System.out.println("Input your starting intersection (Location & Location).");
		String start = inputValidation(console);
		try {
			Path route = back.getPoints(start);
			printPathsPOI(route);
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
		}
	}
	
	/**
	 * Removes a path from being used
	 * @param console Scanner object for input
	 */
	public static void constructionMode(Scanner console) {
		System.out.println("Input Intersection to toggle.");
		String intersection = inputValidation(console);
		try {
			if(!back.toggleIntersection(intersection)) {
				System.out.println("Intersection toggled to not be used.");
			}
			else {
				System.out.println("Intersection toggled to be used.");
			}
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
		}
	}
	
	/**
	 * Gets the path between 3 locations (StartPoint, Stopover, Endpoint)
	 * @param console 
	 */
	public static void schedulerMode(Scanner console) {
		System.out.println("Input your starting intersection (Location & Location).");
		String start = inputValidation(console);
		System.out.println("Input your stopping intersection (Location & Location).");
		String first = inputValidation(console);
		System.out.println("Input your ending intersection (Location & Location).");
		String second = inputValidation(console);
		try {
			ArrayList <Path> paths = back.getRoute(start, first, second);
			System.out.print("Starting Point to Stop Over: ");
			printPathsIntersection(paths.get(0));
			System.out.println("The Distance from Starting Point to Stop Over: " + paths.get(0).getDistance() + "ft");
			System.out.print("Stop Over to Ending Point: ");
			printPathsIntersection(paths.get(1));
			System.out.println("The Distance from Stop Over to Ending Point: " + paths.get(1).getDistance() + "ft");
			int total = paths.get(0).getDistance() + paths.get(1).getDistance();
			System.out.println("Total Distance: " + total + "ft");
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
		}
	}
	
	/**
	 * Get the Points of Interest between two locations
	 * @param console Scanner object for input
	 */
	public static void listProminentLocations(Scanner console) {
		System.out.println("Input your starting intersection (Location & Location).");
		String start = inputValidation(console);
		System.out.println("Input your ending intersection (Location & Location).");
		String end = inputValidation(console);
		try {
			Path route = back.getRoute(start, end);
			printPathsPOI(route);
		} catch(NoSuchElementException nsee) {
			System.out.println(nsee.getMessage());
		}
	}
	
	/**
	 * Prints all the Intersections and Points of Interest Available to Campus Mapper
	 */
	public static void overview() {
		System.out.println("Here is a list of all significant land marks and all "
				+ "intersections.");
		printPathsPOI(back.getPoints());
		System.out.print("List of Intersections: ");
		printPathsIntersection(back.getPoints());
	}
	
	/**
	 * Helper method for printing path's points of interest
	 * @param path Path object whose POI's are to be printed
	 */
	private static void printPathsPOI(Path path) {
		System.out.print("Points of Interest include: " + path.getPOI().get(0));
		for(int i = 1; i < path.getPOI().size(); i ++) {
			System.out.print(", " + path.getPOI().get(i));
		}
		System.out.println();
	}
	
	/**
	 * Helper method for printing path's Intersections
	 * @param path Path object whose intersections are to be printed
	 */
	private static void printPathsIntersection(Path path) {
		System.out.print(path.getPath().get(0).getName());
		for(int i = 1; i < path.getPath().size(); i ++) {
			System.out.print(", " + path.getPath().get(i).getName());
		}
		System.out.println();
	}
}
