/**
 * @author AJWuu
 */

package mst;

public class Prim {
	
	//THIS PRIM IS PROBABLY WRONG, NEED FURTHER CHECK!!!
	
	//Works for weighted, connected, undirected graphs
	//Step1: Pick start vertex s
	//Step2: Add s as root of T
	//Step3: At each iteration -- Choose the lowest weight edge that connects a vertex to T without creating a cycle

	private static int V;
	
	private static int minKey(int key[], Boolean mstSet[]) {
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < V; v++) {
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}
		}
		
		return min_index;
	}
	
	private static void printPrim(int parent[], int graph[][]) {
		System.out.println("Edge \tWeight");
		for (int i = 1; i < V; i++)
			System.out.println(parent[i] + " - " + i + "\t" + graph[i][parent[i]]);
	}

	public static void primMST(int graph[][]) {
		V = graph.length;
		int parent[] = new int[V];
		int key[] = new int[V];
		Boolean mstSet[] = new Boolean[V];
		for (int i = 0; i < V; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		//Insert the first vertex in MST as root
		key[0] = 0;
		parent[0] = -1;
		for (int count=0; count<V-1; count++) {
			int u = minKey(key, mstSet);
			mstSet[u] = true;

			for (int v=0; v<V; v++) {
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
			}
		}

		printPrim(parent, graph);
	}

}
