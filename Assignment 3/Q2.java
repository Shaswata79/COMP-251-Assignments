import java.util.*;

/**
 * Author : Shaswata Bhattacharyya
 * No collaborators
 */
public class Q2 {

	public static int rings(Hashtable<Integer, ArrayList<Integer>> graph, int[]location) {
		graph = completeGraph(graph);
		int travels = 0;
		ArrayList<Integer> planets = new ArrayList<>();
		HashMap<Integer, Integer> inDegrees = computeInDegree(graph);

		Integer source = getFirstSource(inDegrees);
		while(!graph.isEmpty()){
			planets.add(source);
			source = getNextSource(inDegrees, graph, source, location);
		}

		if(planets.size() == 1){
			return 0;
		}

		int currentPlanet = location[planets.get(0)];
		for(int i = 1; i < planets.size(); i++){
			int nextPlanet = location[planets.get(i)];
			if(nextPlanet != currentPlanet){
				travels++;
			}
			currentPlanet = nextPlanet;
		}

		return travels;
	}





	private static HashMap<Integer, Integer> computeInDegree(Hashtable<Integer, ArrayList<Integer>> graph){
		HashMap<Integer, Integer> inDegrees = new HashMap<>();

		//first add all nodes to inDegree map
		for(Integer node : graph.keySet()){
			inDegrees.put(node, 0);
		}

		//then compute inDegrees for each node
		for(Integer node : graph.keySet()){
			for(Integer value : graph.get(node)){
				int count = inDegrees.get(value);
				count++;
				inDegrees.put(value, count);
			}
		}

		return inDegrees;
	}

	private static int getFirstSource(HashMap<Integer, Integer> inDegrees){
		for(Integer node : inDegrees.keySet()){
			if(inDegrees.get(node) == 0){
				return node;
			}
		}
		return 0;
	}

	private static int getNextSource(HashMap<Integer, Integer> inDegrees, Hashtable<Integer, ArrayList<Integer>> graph, int source, int[] location) {
		//if only one node left
		if(graph.size() == 1){
			graph.remove(source);
			return -1;	//no more nodes
		}

		//get in-degrees for graph without node 'source'
		for(Integer node : graph.get(source)){	//for all nodes in adjacency list of node 'source'
			int degree = inDegrees.get(node);
			degree--;			//decrement degree
			inDegrees.put(node, degree);
		}
		inDegrees.remove(source);

		//then get the possible sources of the new graph
		ArrayList<Integer> possibleSources = new ArrayList<>();
		for(Integer node : inDegrees.keySet()){
			if(inDegrees.get(node) == 0){
				possibleSources.add(node);
			}
		}

		if(possibleSources.size() == 1){
			graph.remove(source);
			return possibleSources.get(0);
		}

		int currentPlanet = location[source];
		for(Integer newSource : possibleSources){
			if(currentPlanet == location[newSource]){
				graph.remove(source);
				return newSource;
			}
		}

		graph.remove(source);
		return possibleSources.get(0);
	}

	private static Hashtable<Integer, ArrayList<Integer>> completeGraph(Hashtable<Integer, ArrayList<Integer>> graph) {
		Hashtable<Integer, ArrayList<Integer>> newGraph = new Hashtable<>();
		for(Integer node : graph.keySet()){
			newGraph.put(node, graph.get(node));
			for(Integer connectedNode : graph.get(node)){
				if(!graph.containsKey(connectedNode)){
					newGraph.put(connectedNode, new ArrayList<>());
				}
			}
		}
		return newGraph;
	}



	public static void main(String[] args) {
		int[] location = {1, 2, 1, 2, 1};
		Hashtable<Integer, ArrayList<Integer>> graph = new Hashtable<>();
		graph.put(0, new ArrayList<>(Arrays.asList(1, 2)));
		graph.put(1, new ArrayList<>(Arrays.asList(3, 4)));
		graph.put(2, new ArrayList<>(Arrays.asList(3, 4)));

		System.out.println(rings(graph, location));
	}

}
