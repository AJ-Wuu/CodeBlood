/**
 * @author AJWuu
 */

package mst;

import java.util.Arrays;

class Edge implements Comparable<Edge> {
	int src, dest, weight;

	public int compareTo(Edge compareEdge) {
		return (this.weight - compareEdge.weight);
	}
};

class subset {
	int parent, rank;
};

class Graph {
	int V, E;
	Edge[] edge;

	public Graph(int v, int e) {
		this.V = v;
		this.E = e;
		this.edge = new Edge[E];
		for (int i=0; i<e; i++) {
			edge[i] = new Edge();
		}
	}
}

public class Kruskal {
	
	//Works for weighted, connected, undirected graphs
	//Step1: Sorted all the edges from smallest to largest
	//Step2: At each iteration -- Add edge with lowest cost that does not create a cycle

	private static int find(subset subsets[], int i) {
		// find root and make root as parent of i
		// (path compression)
		if (subsets[i].parent != i)
			subsets[i].parent
			= find(subsets, subsets[i].parent);

		return subsets[i].parent;
	}

	// A function that does union of two sets of x and y (uses union by rank)
	private static void Union(subset subsets[], int x, int y) {
		int xroot = find(subsets, x);
		int yroot = find(subsets, y);

		// Attach smaller rank tree under root
		// of high rank tree (Union by Rank)
		if (subsets[xroot].rank
				< subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		else if (subsets[xroot].rank
				> subsets[yroot].rank)
			subsets[yroot].parent = xroot;

		// If ranks are same, then make one as
		// root and increment its rank by one
		else {
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
	}

	private static void printKruskal(Edge[] result, int e) {
		System.out.println("Following are the edges in " + "the constructed MST");
		int minimumCost = 0;
		for (int i=0; i<e; i++) {
			System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
			minimumCost += result[i].weight;
		}
		System.out.println("Minimum Cost Spanning Tree " + minimumCost);
	}

	public static void kruskalMST(Graph graph, int V, int E) {
		Edge[] result = new Edge[V];
		int e = 0;

		for (int i=0; i<V; i++) {
			result[i] = new Edge();
		}

		//Step 1: Sort all the edges in non-decreasing order of their weight
		Arrays.sort(graph.edge);

		subset subsets[] = new subset[V];
		for (int i=0; i<V; i++) {
			subsets[i] = new subset();
		}

		for (int v=0; v<V; v++) {
			subsets[v].parent = v;
			subsets[v].rank = 0;
		}

		int index = 0; //index used to pick next edge
		while (e < V-1) {
			//Step 2: Pick the smallest edge
			Edge next_edge = graph.edge[index++];
			int x = find(subsets, next_edge.src);
			int y = find(subsets, next_edge.dest);

			//Doesn't cause a cycle
			if (x != y) {
				result[e++] = next_edge;
				Union(subsets, x, y);
			}
		}

		printKruskal(result, e);
	}

}
