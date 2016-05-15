
public class Edge {

	private int source, destination;
	private int weight;

	public Edge(int source, int destination, int weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}

	public int getDestination() {
		return destination;
	}

	public int getWeight() {
		return weight;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + source;
        hash = hash * 31 + destination;
        
        return hash;
	}

	/*
	 * Equals method is needed to make the hashCode function proper
	 */
	@Override
	public boolean equals(Object other) {
		if (this.source == ((Edge) other).source 
				&& this.destination == ((Edge) other).destination) {

			return true;
		}

		return false;
	}

}
