import java.util.ArrayList;
import java.util.Scanner;

public class Frontend {
	public static Backend back = new Backend();
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		printIntro();
		Character in = 'z';
		while(in != 'x') {
			in = getInput(console);
			serveMode(in, console);
		}
	}
	
	public static void printIntro() {
		System.out.println("Welcome to the Campus Mapper!");
		System.out.println("You will have 6 modes to choose form!");
		System.out.println("[B] Base Mode: Give Two Locations and get the shortest distance between the two.");
		System.out.println("[O] One Point Mode: Give One location and find the shortest to other locations.");
		System.out.println("[C] Construction Mode: Limit Roads between two locations.");
		System.out.println("[S] Scheduler Mode: Find the path between more that two locations.");
		System.out.println("[P] Prominent Locations: Give any prominenent buildings in between two locations.");
		System.out.println("[A] Overview: List all prominenet locations and intersections.");
		System.out.println("Type Letter Corresponding to the Mode");
		System.out.println("[X] Press X to Exit");
	}
	
	public static void printModes() {
		System.out.println("[B] Base Mode");
		System.out.println("[O] One Point Mode");
		System.out.println("[C] Construction Mode");
		System.out.println("[S] Scheduler Mode");
		System.out.println("[P] Prominent Locations");
		System.out.println("[A] Overview");
		System.out.println("[X] Press X to Exit");
		System.out.println("Type Letter Corresponding to the Mode");
	}
	
	public static Character getInput(Scanner console) {
		while(!console.hasNext("[BbOoCcSsPpAaXx]")) {
			System.out.println("Incorrect Input. Please enter a valid character!");
			console.next();
		}
		Character in = Character.toLowerCase(console.next().charAt(0));
		return in;
	}
	
	public static void serveMode(char in, Scanner console) {
		switch(in) {
		case 'b':
			baseMode(console);
		case 'o':
			oneMode(console);
		case 'c':
			constructionMode(console);
		case 's':
			schedulerMode(console);
		case 'p':
			listProminentLocations(console);
		case 'a':
			overview();
		case 'x':
			return;
		}
	}
	
	public static String inputValidation(Scanner console) {
		boolean correctInput = false;
		String place = "";
		while(!correctInput) {
			place = console.nextLine();
			if(place.matches(".*[\\s\\S]&[\\s\\S].*")) {
				String[] parts = place.split("[\\s*]&[\\s*]");
				
			}
			else {
				System.out.println("Incorrect Input, Try Again");
			}
		}
		return place;
	}
	public static void baseMode(Scanner console) {
		System.out.println("Input your starting point.");
		String start = console.nextLine();
		System.out.println("Input your ending point.");
		String end = console.nextLine();
		Path route = back.getRoute(start, end);
		System.out.println(route.toString());
	}
	
	public static void oneMode(Scanner console) {
		
	}
	
	public static void constructionMode(Scanner console) {
		
	}
	
	 // Ask about the return type on getRoute
	public static void schedulerMode(Scanner console) {
		System.out.println("Input your starting point.");
		String start = console.nextLine();
		System.out.println("Input your first stopping point.");
		String first = console.nextLine();
		System.out.println("Input your second stopping point.");
		String second = console.nextLine();
		ArrayList <Path> paths = back.getRoute(start, first, second);
		 // Format String
		System.out.println(paths.toString());
	}
	 // Ask about what backend doing
	public static void listProminentLocations(Scanner console) {
		
	}
	
	public static void overview() {
		System.out.println("Here is a list of all significant land marks and all "
				+ "intersections");
		System.out.println(back.getPoints().toString());
	}
}





