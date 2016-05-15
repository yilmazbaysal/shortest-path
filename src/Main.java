
public class Main {
	
	public static void main(String[] args) {
		
		// Control the number of arguments
		if(args.length != 2) {
			System.err.println("Error: Number of command line arguments must be two!");
		}
		
		Manager manager = new Manager(args);
		
		manager.readInputs();
		manager.createGraph();
		manager.findShortestPath();
		manager.findConstrainedShortestPath();
		manager.closeFiles();
	}
}
