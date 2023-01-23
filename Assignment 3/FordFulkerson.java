import java.util.*;
import java.io.File;


/**
 * Author : Shaswata Bhattacharyya
 * No collaborators
 */
public class FordFulkerson {

	private enum Colour{
		White,
		Grey,
		Black
	}

	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> path = new ArrayList<Integer>();
		HashMap<Integer, Colour> colours = new HashMap<>();

		for(Integer node : graph.getNodes()){
			colours.put(node, Colour.White);
		}

		path.add(source);
		recursiveDFS(destination, source, graph, colours, path);
		return path;
	}

	private static boolean recursiveDFS(Integer destination, Integer u, WGraph graph, HashMap<Integer, Colour> colours, ArrayList<Integer> path) {

		colours.put(u, Colour.Grey);

		boolean movedForward = false;
		for(Edge edge : graph.getEdges()){
			if(edge.nodes[0]==u && colours.get(edge.nodes[1]).equals(Colour.White)){
				movedForward = true;
				Integer v = edge.nodes[1];
				path.add(v);

				if(v==destination){
					return true;
				}else {
					boolean reached = recursiveDFS(destination, v, graph, colours, path);
					if(reached){
						return true;
					}
				}
			}
		}

		if(!movedForward){
			path.remove(path.size()-1);
		}
		colours.put(u, Colour.Black);
		return false;
	}



	public static String fordfulkerson(WGraph graph){
		String answer="";
		int maxFlow = 0;

		WGraph flowGraph = new WGraph(graph);
		WGraph residualGraph = new WGraph(graph);
		for(Edge edge: flowGraph.getEdges()){
			flowGraph.setEdge(edge.nodes[0], edge.nodes[1], 0);
		}


		ArrayList<Integer> path = pathDFS(graph.getSource(), graph.getDestination(), residualGraph);
		while (path.size() > 2){
			augment(path, flowGraph, residualGraph);		//flows updated
			residualGraph = createResidualGraph(residualGraph, flowGraph, path);
			path = pathDFS(graph.getSource(), graph.getDestination(), residualGraph);
		}


		for(Edge e : flowGraph.getEdges()){
			if(e.weight >= maxFlow){
				maxFlow = e.weight;
			}
		}
		answer += maxFlow + "\n" + flowGraph.toString();
		return answer;
	}



	public static WGraph createResidualGraph(WGraph graph, WGraph flowGraph, ArrayList<Integer> path){
		WGraph residualGraph = new WGraph();

		for(int i = 0; i < path.size()-1; i++){
			int u = path.get(i);
			int v = path.get(i+1);
			Edge cap = graph.getEdge(u, v);
			Edge flow = flowGraph.getEdge(u, v);

			if(flow.weight < cap.weight){
				residualGraph.setEdge(u, v, cap.weight- flow.weight);
			}
			if(flow.weight > 0){
				residualGraph.setEdge(v, u, flow.weight);
			}
		}
		return residualGraph;
	}


	public static void augment(ArrayList<Integer> path, WGraph flowGraph, WGraph residualGraph){
		int beta = Integer.MAX_VALUE;

		//find min beta
		for(int i = 0; i < path.size()-1; i++){
			Edge edge = residualGraph.getEdge(path.get(i), path.get(i+1));
			if(edge.weight < beta){
				beta = edge.weight;
			}
		}

		//augment
		for(int i = 0; i < path.size() - 1; i++){
			Integer u = path.get(i);
			Integer v = path.get(i + 1);
			Edge flow = flowGraph.getEdge(u, v);

			if(flowGraph.getEdges().contains(flow)){
				flowGraph.setEdge(u, v, flow.weight + beta);
			}
			else{
				flowGraph.setEdge(u, v, flow.weight - beta);
			}
		}

	}



	public static void main(String[] args){
		 WGraph g = new WGraph();
		 g.setSource(0);
		 g.setDestination(9);
		 Edge[] edges = new Edge[] {
				 new Edge(0, 1, 10),
				 new Edge(0, 2, 5),
				 new Edge(2, 3, 5),
				 new Edge(1, 3, 10),
				 new Edge(3, 4, 5),
				 new Edge(4, 5, 10),
				 new Edge(4, 6, 5),
				 new Edge(6, 7, 5),
				 new Edge(6, 8, 10),
				 new Edge(8, 9, 10),
		 };
		 Arrays.stream(edges).forEach(e->g.addEdge(e));
		 String result = FordFulkerson.fordfulkerson(g);
		 System.out.println(result);
	 }
}

