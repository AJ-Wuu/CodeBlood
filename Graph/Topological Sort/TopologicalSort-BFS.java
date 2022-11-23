// AKA Kahn's Algorithm
import java.util.*;

class Graph {
	int V;
	List<Integer> adj[];

	public Graph(int V) {
		this.V = V;
		adj = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			adj[i] = new ArrayList<Integer>();
        }
	}

	public void addEdge(int u, int v) {
		adj[u].add(v);
	}

	public void topologicalSort() {
		// initialize all indegrees as 0
		int indegree[] = new int[V];

		// traverse adjacency lists to fill indegrees of vertices -- O(V+E)
		for (int i = 0; i < V; i++) {
			ArrayList<Integer> temp = (ArrayList<Integer>)adj[i];
			for (int node : temp) {
				indegree[node]++;
			}
		}

		// create a queue and enqueue all vertices with indegree 0
		Queue<Integer> q = new LinkedList<Integer>();
		for (int i = 0; i < V; i++) {
			if (indegree[i] == 0) {
				q.add(i);
            }
		}

		int cnt = 0; // count of visited vertices
		Vector<Integer> topOrder = new Vector<Integer>();
		while (!q.isEmpty()) {
			// extract front of queue (or perform dequeue), and add it to topological order
			int u = q.poll();
			topOrder.add(u);

			// iterate through all its neighbouring nodes of dequeued node u and decrease their in-degree by 1
			for (int node : adj[u]) {
				// if in-degree becomes zero, add it to queue
				if (--indegree[node] == 0) {
					q.add(node);
                }
			}
			cnt++;
		}

		// check if there was a cycle
		if (cnt != V) {
			System.out.println("There exists a cycle in the graph");
			return ;
		}

		for (int i : topOrder) {
			System.out.print(i + " ");
		}
	}

	public static void main(String args[]) {
		Graph g = new Graph(6);
		g.addEdge(5, 2);
		g.addEdge(5, 0);
		g.addEdge(4, 0);
		g.addEdge(4, 1);
		g.addEdge(2, 3);
		g.addEdge(3, 1);
		System.out.print("Following is a Topological sort of the given graph: ");
		g.topologicalSort();
		System.out.println();
	}
}
