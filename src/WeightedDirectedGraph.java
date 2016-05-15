import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class WeightedDirectedGraph {

	private ArrayList<Edge>[] adjacency; // Graph
	private int size;

	private ArrayList<Integer> shortestHamiltonianPath; // To find the
	private int shortestHamiltonianWeight; /////////////// Hamiltonian path

	private int[] edgeTo; //////////////////////////////////////
	private boolean[] isVisited; /// For Dijkstra's algorithm //
	private int[] distTo; //////////////////////////////////////

	@SuppressWarnings("unchecked")
	public WeightedDirectedGraph(int size) {
		
		this.size = size;
		adjacency = new ArrayList[size + 1];
		distTo = new int[size + 1];
		isVisited = new boolean[size + 1];

		for (int i = 1; i <= size; i++) { // Skip zero '0' vertex
			adjacency[i] = new ArrayList<Edge>();
		}
	}

	// Adds new adjacency
	public void addAdjacency(int source, int destination, int weight) {
		adjacency[source].add(new Edge(source, destination, weight));
	}

	// Returns the minimum distance from source to destination
	// (It should be called after Dijkstra method)
	public int getDistanceTo(int destination) {
		return distTo[destination];
	}

	// Returns the distance of shortest hamiltonian path
	// (It should be called after HamiltonianPath method)
	public int getShortestHamiltonianWeight() {
		return shortestHamiltonianWeight;
	}

	// Returns a list of vertices which is the shortest path between source and
	// destination vertices
	public List<Integer> findShortestPathBetween(int source, int destination) {

		// Check the source and destination
		if (source < 1 || source > size || destination < 1 || destination > size) {
			return null;
		}

		if (source == destination) { // Check if they are equal
			distTo[destination] = 0;
			return new LinkedList<Integer>();
		}

		Dijkstra(source); // Run Dijkstra's algorithm

		// Create the shortest path between two vertices
		LinkedList<Integer> shortestPath = new LinkedList<Integer>();

		int temp = destination;
		shortestPath.addFirst(temp);

		for (int i = 0; i < size; i++) {
			temp = edgeTo[temp];
			shortestPath.addFirst(temp);

			if (temp == source) {
				return shortestPath; // Return the path
			}
		}

		return null; // If a path could not found
	}

	// Finds shortest path from source vertex to all other vertices
	private void Dijkstra(int source) {

		Arrays.fill(isVisited, false);
		Arrays.fill(distTo, Integer.MAX_VALUE);
		edgeTo = new int[adjacency.length];

		distTo[source] = 0;
		int minimum;

		// while there is unvisited vertex
		while ((minimum = getMinimumVertex()) != 0) {
			isVisited[minimum] = true; // The vertex is visited

			for (int i = 0; i < adjacency[minimum].size(); i++) {
				Edge edge = adjacency[minimum].get(i);

				// Relax the edge
				if (distTo[minimum] + edge.getWeight() < distTo[edge.getDestination()]) {
					distTo[edge.getDestination()] = distTo[minimum] + edge.getWeight();
					edgeTo[edge.getDestination()] = minimum;
				}
			}
		}
	}

	// Returns a list of vertices which is the shortest Hamiltonian path of this
	// graph
	public List<Integer> shortestHamiltonianPath(int source, int destination) {
		shortestHamiltonianWeight = Integer.MAX_VALUE;
		Arrays.fill(isVisited, false);

		shortestHamiltonPath(source, 0, destination, new ArrayList<Integer>());

		return shortestHamiltonianPath;
	}

	// Finds the shortest Hamiltonian path recursively
	private void shortestHamiltonPath(int current, int weight, int destination, ArrayList<Integer> path) {
		path.add(current);
		isVisited[current] = true;

		if (current == destination && path.size() == size) {

			if (weight < shortestHamiltonianWeight) {
				shortestHamiltonianPath = new ArrayList<Integer>(path);
				shortestHamiltonianWeight = weight;
			}
		} else if (weight < shortestHamiltonianWeight) {

			for (int i = 0; i < adjacency[current].size(); i++) {
				Edge temp = adjacency[current].get(i);

				if (!isVisited[temp.getDestination()]) {
					shortestHamiltonPath(temp.getDestination(), weight + temp.getWeight(), destination, path);
				}
			}
		}

		isVisited[current] = false;
		path.remove(path.size() - 1);
	}

	// Returns the vertex with minimum distance. If all vertices are visited
	// returns 0 (zero).
	private int getMinimumVertex() {
		int minimum = 0;
		int minimumWeight = Integer.MAX_VALUE;

		for (int i = 1; i < distTo.length; i++) {

			if (isVisited[i] == false && distTo[i] < minimumWeight) {
				minimum = i;
				minimumWeight = distTo[i];
			}
		}

		return minimum;
	}
}
