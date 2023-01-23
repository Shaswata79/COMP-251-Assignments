import java.util.*;

/**
 * Author : Shaswata Bhattacharyya
 * No collaborators
 */
public class Q1 {

	public static void main(String[] args) {

		String[][][] jail =	{
				{
						{"S",".",".",".","."},
						{".","#","#","#","."},
						{".","#","#",".","."},
						{"#","#","#",".","#"}
				},

				{
						{"#","#","#","#","#"},
						{"#","#","#","#","#"},
						{"#","#",".","#","#"},
						{"#","#",".",".","."}
				},

				{
						{"#","#","#","#","#"},
						{"#","#","#","#","#"},
						{"#",".","#","#","#"},
						{"#","#","#","#","E"}
				}

		};

		System.out.println(find_exit(jail));

	}



    private static class Vertex{
        int x;
        int y;
        int z;
		String value;

        public Vertex(int x, int y, int z, String s) {
            this.x = x;
            this.y = y;
            this.z = z;
			this.value = s;
        }



		@Override
		public int hashCode(){
			String s = "x" + this.x + "y" + this.y + "z" + this.z;
			int h = s.hashCode();
			return h;
		}

		@Override
		public boolean equals(Object v){
			Vertex other = (Vertex) v;
			if(this.x==other.x && this.y== other.y && this.z==other.z){
				return true;
			}
			return false;
		}


    }



    private static Hashtable<Vertex, ArrayList<Vertex>> buildGraph(String[][][] jail){
        Hashtable<Vertex, ArrayList<Vertex>> graph = new Hashtable<>();

        for(int z = 0; z < jail.length; z++){
            for(int y = 0; y < jail[0].length; y++){
                for (int x = 0; x < jail[0][0].length; x++){
					if(!jail[z][y][x].equals("#")){
						Vertex v = new Vertex(x,y,z,jail[z][y][x]);
						ArrayList<Vertex> adjList = new ArrayList<>();
						if(canMoveUp(jail, x, y, z)){
							adjList.add(new Vertex(x, y, z+1, jail[z+1][y][x]));
						}
						if(canMoveDown(jail, x, y, z)){
							adjList.add(new Vertex(x, y, z-1, jail[z-1][y][x]));
						}
						if(canMoveNorth(jail, x, y, z)){
							adjList.add(new Vertex(x, y-1, z, jail[z][y-1][x]));
						}
						if(canMoveSouth(jail, x, y, z)){
							adjList.add(new Vertex(x, y+1, z, jail[z][y+1][x]));
						}
						if(canMoveWest(jail, x, y, z)){
							adjList.add(new Vertex(x+1, y, z, jail[z][y][x+1]));
						}
						if(canMoveEast(jail, x, y, z)){
							adjList.add(new Vertex(x-1, y, z, jail[z][y][x-1]));
						}

						graph.put(v, adjList);
					}
                }
            }
        }

		return graph;
    }





	private enum Colour{
		White,
		Grey,
		Black
	}




	private static HashMap<Vertex, Integer> breadthFirstSearch(Hashtable<Vertex, ArrayList<Vertex>> graph, Vertex s){
		//initialize colour and distances
		HashMap<Vertex, Integer> distances = new HashMap<>();
		for(Vertex v : graph.keySet()){
			distances.put(v, -1);
		}
		Hashtable<Vertex, Colour> colours = new Hashtable<>();
		for(Vertex v : graph.keySet()){
			colours.put(v, Colour.White);
		}
		colours.put(s, Colour.Grey);
		distances.put(s, 0);

		//initialize queue
		LinkedList<Vertex> queue = new LinkedList<>();
		queue.addLast(s);

		//traverse graph
		while (!queue.isEmpty()){
			Vertex u = queue.removeFirst();
			for(Vertex v : graph.get(u)){
				if(colours.get(v).equals(Colour.White)){
					colours.put(v, Colour.Grey);
					int d = distances.get(u) + 1;
					distances.put(v, d);
					queue.addLast(v);
				}
			}
			colours.put(u, Colour.Black);
		}

		return distances;
	}





	public static int find_exit(String[][][] jail) {
		Hashtable<Vertex, ArrayList<Vertex>> graph = buildGraph(jail);

		Vertex s = new Vertex(0, 0, 0, jail[0][0][0]);
		for(Vertex v : graph.keySet()){
			if(v.value.equals("S")){
				s = v;
			}
		}

		HashMap<Vertex, Integer> distances = breadthFirstSearch(graph, s);
		for(Vertex v : distances.keySet()){
			if(v.value.equals("E")){
				return distances.get(v);
			}
		}

		return -1;
	}



	private static boolean canMoveUp(String[][][] jail, int x, int y, int z){
		if((z+1 < jail.length) && (jail[z+1][y][x].equals(".")||jail[z+1][y][x].equals("E")||jail[z+1][y][x].equals("S"))){
			return true;
		}
		return false;
	}

	private static boolean canMoveDown(String[][][] jail, int x, int y, int z){
		if((z-1 >= 0) && (jail[z-1][y][x].equals(".")||jail[z-1][y][x].equals("E")||jail[z-1][y][x].equals("S"))){
			return true;
		}
		return false;
	}

	private static boolean canMoveWest(String[][][] jail, int x, int y, int z){
		if((x+1 < jail[z][y].length) && (jail[z][y][x+1].equals(".")||jail[z][y][x+1].equals("E")||jail[z][y][x+1].equals("S"))){
			return true;
		}
		return false;
	}

	private static boolean canMoveEast(String[][][] jail, int x, int y, int z){
		if((x-1 >= 0) && (jail[z][y][x-1].equals(".")||jail[z][y][x-1].equals("E")||jail[z][y][x-1].equals("S"))){
			return true;
		}
		return false;
	}

	private static boolean canMoveSouth(String[][][] jail, int x, int y, int z){
		if((y+1 < jail[z].length) && (jail[z][y+1][x].equals(".")||jail[z][y+1][x].equals("E")||jail[z][y+1][x].equals("S"))){
			return true;
		}
		return false;
	}

	private static boolean canMoveNorth(String[][][] jail, int x, int y, int z){
		if((y-1 >= 0) && (jail[z][y-1][x].equals(".")||jail[z][y-1][x].equals("E")||jail[z][y-1][x].equals("S"))){
			return true;
		}
		return false;
	}


}

