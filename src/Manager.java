import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Manager {

	private WeightedDirectedGraph graph;
	private ArrayList<String> lines;
	private List<Integer> mustpass;
	private InputOutput io;
	private int source, destination;

	public Manager(String[] filePaths) {
		mustpass = new ArrayList<Integer>();
		io = new InputOutput(filePaths);

		io.write("21327694\n");
	}

	// Reads inputs and adds them to a list
	public void readInputs() {
		lines = new ArrayList<String>();
		String line;

		while ((line = io.readInputLine()) != null) {
			lines.add(line);
		}
	}

	public void createGraph() {
		// Create the graph with the given size
		graph = new WeightedDirectedGraph(lines.size() - 1);

		source = Integer.valueOf(lines.get(0).substring(2, lines.get(0).indexOf(",")));
		destination = Integer.valueOf(lines.get(0).substring(lines.get(0).indexOf(",") + 3));

		for (int i = 1; i < lines.size(); i++) {

			String[] tokens = lines.get(i).split("[\\s.,()]+");

			// Add adjacencies to the graph
			for (int j = 1; j < tokens.length - 1; j += 2) {
				graph.addAdjacency(new Integer(tokens[0]), new Integer(tokens[j]), new Integer(tokens[j + 1]));
			}

			// Control the vertex if it is a mustpass or not
			if (tokens[tokens.length - 1].equals("mustpass")) {
				mustpass.add(new Integer(tokens[0]));
			}
		}
	}

	public void findShortestPath() {
		List<Integer> resultantList = graph.findShortestPathBetween(source, destination);

		if (resultantList == null) {
			io.write("\nShortest Path:  Connot find a path\n");
			return;
		}

		// Print results
		io.write("\nShortest Path:  Distance=" + graph.getDistanceTo(destination) + "     Path=(");

		for (int i = 0; i < resultantList.size(); i++) {
			io.write(resultantList.get(i) + " "); // Print the path
		}

		io.write(")\n");
	}

	public void findConstrainedShortestPath() {

		if (!mustpass.contains(source)) {
			mustpass.add(0, source);
		}
		if (!mustpass.contains(destination)) {
			mustpass.add(destination);
		}

		Hashtable<Edge, List<Integer>> mustpassPaths = new Hashtable<Edge, List<Integer>>();

		// Create new graph that contains only the source, destination and
		// mustpass vertices
		WeightedDirectedGraph mustpassGraph = new WeightedDirectedGraph(mustpass.size());

		// Find the shortest distances between each mustpass and add them to the
		// new graph
		for (int i = 0; i < mustpass.size(); i++) {
			for (int j = 0; j < mustpass.size(); j++) {

				if (i != j && mustpass.get(i) != destination && mustpass.get(j) != source) {

					List<Integer> path = graph.findShortestPathBetween(mustpass.get(i), mustpass.get(j));

					if (path != null) {

						mustpassPaths.put(new Edge(mustpass.get(i), mustpass.get(j), 0), path);
						mustpassGraph.addAdjacency(i + 1, j + 1, graph.getDistanceTo(mustpass.get(j)));
					}
				}
			}
		}

		// Find hamiltonian path of the reduced graph
		List<Integer> hamiltonian = mustpassGraph.shortestHamiltonianPath(1, mustpass.size());

		if (hamiltonian == null) {
			io.write("\nConstrained Shortest Path:  Connot find a path\n");
			return;
		}

		
		// Build shortest path considering the hamiltonian path of mustpass vertices
		List<Integer> resultantPath = new ArrayList<Integer>();
		
		for (int i = 1; i < hamiltonian.size(); i++) {
			Edge tempEdge = new Edge(mustpass.get(hamiltonian.get(i - 1) - 1), mustpass.get(hamiltonian.get(i) - 1), 0);

			resultantPath.addAll(mustpassPaths.get(tempEdge));

			if (i != hamiltonian.size() - 1) {
				resultantPath.remove(resultantPath.size() - 1);
			}
		}

		// Print results
		io.write("\nConstrained Shortest Path:  Distance=" + mustpassGraph.getShortestHamiltonianWeight()
				+ "     Path=(");

		for (int i = 0; i < resultantPath.size(); i++) {
			int temp = resultantPath.get(i);

			if (temp != source && temp != destination && mustpass.contains(temp)) {
				io.write(temp + "(mustpass) ");
			} else {
				io.write(temp + " ");
			}
		}
		
		io.write(")");
	}

	// Closes the input-output files
	public void closeFiles() {
		io.closeFiles();
	}
}
