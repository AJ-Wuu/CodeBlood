import java.util.*;

class Graph {
	int V;
	List<Integer> adjListArray[];

	public Graph(int V) {
		this.V = V;
	        adjListArray = new LinkedList[V];
		this.adjListArray = adjListArray;
		for (int i = 0; i < V; i++) {
			adjListArray[i] = new LinkedList<>();
		}
	}

        public void addEdge(int src, int dest) {
		this.adjListArray[src].add(dest);
	}
	
	private void allTopologicalSortsUtil(boolean[] visited, int[] indegree, ArrayList<Integer> stack) {
		boolean flag = false; // indicate whether all topological are found or not

		for (int i = 0; i < this.V; i++) {
			// if indegree is 0 and not yet visited then only choose that vertex
			if (!visited[i] && indegree[i] == 0) {
				visited[i] = true;
				stack.add(i);
				for (int adjacent : this.adjListArray[i]) {
					indegree[adjacent]--;
				}
				allTopologicalSortsUtil(visited, indegree, stack);
				
				// resetting visited, res and indegree for backtracking
				visited[i] = false;
				stack.remove(stack.size() - 1);
				for (int adjacent : this.adjListArray[i]) {
					indegree[adjacent]++;
				}

				flag = true;
			}
		}
                
		// reach here if all vertices are visited
		if (!flag) {
			stack.forEach(i -> System.out.print(i + " "));
			System.out.println();
		}
	}
	
	public void allTopologicalSorts() {
		boolean[] visited = new boolean[this.V];
		int[] indegree = new int[this.V];
		for (int i = 0; i < this.V; i++) {
			for (int var : this.adjListArray[i]) {
				indegree[var]++;
			}
		}
		ArrayList<Integer> stack = new ArrayList<>();
		allTopologicalSortsUtil(visited, indegree, stack);
	}

	public static void main(String[] args) {
		Graph graph = new Graph(6);
		graph.addEdge(5, 2);
		graph.addEdge(5, 0);
		graph.addEdge(4, 0);
		graph.addEdge(4, 1);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1);
		System.out.println("All Topological sorts: ");
		graph.allTopologicalSorts();
	}
}
