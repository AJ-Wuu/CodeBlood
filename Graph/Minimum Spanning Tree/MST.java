/**
 * @author AJWuu
 */

package mst;

public class MST {

	/*
	 * Spanning Tree: Connectd & Undirected
	 * 
	 * The properties of a MST:
	 * Possible multiplicity: n vertices in the graph -> each spanning tree has n − 1 edges
	 * Uniqueness: each edge has a distinct weight -> only one, unique minimum spanning tree
	 * Minimum-cost subgraph: the weights are positive -> a minimum-cost subgraph connecting all vertices
	 * Cycle property: in Cycle, the weight of an edge e > any other the individual edge -> e cannot belong to a MST
	 * Cut property: in Cut, the weight of an edge e < any other individual edge -> e belongs to all MSTs
	 * Minimum-cost edge: unique minimum cost edge -> this edge is included in any MST
	 * Contraction: If T is a tree of MST edges, then we can contract T into a single vertex while maintaining the invariant 
	 *              that the MST of the contracted graph plus T gives the MST for the graph before contraction
	 *
	 * Every possible minimal spanning tree of the same graph has an identical number of edges.
	 * For one graph, Prim's and Kruskal's algorithm may return different minimal spanning tree, but the total path costs returned will be the same.
	 */
	
	//Complexities
	//Prim (faster when number of edges is high): O(V^2)
	//      improve to O((V+E)*logV) by
	//          V*logV -> use a priority queue for the vertices with edge weights connecting vertex to current tree as priority values
	//          E*logV -> update priority values after tree is updated
	//Kruskal (faster when number of edges is low): O(E*logE) -> sorting edges
	
	//Starting Node
	//Prim: influence the order of visiting each edge
	//      NO influence on the overall MST
	//Kruskal: No influence at all
	
	public static void main(String[] args) {
		int graphMatrix[][] = new int[][] { { 0, 2, 0, 6, 0 },
									{ 2, 0, 3, 8, 5 },
									{ 0, 3, 0, 0, 7 },
									{ 6, 8, 0, 0, 9 },
									{ 0, 5, 7, 9, 0 } };
		Prim.primMST(1, graphMatrix);
		
		int V = 4;
		int E = 5;
		Graph graph = new Graph(V, E);
		graph.edge[0].src = 0;
		graph.edge[0].dest = 1;
		graph.edge[0].weight = 10;
		graph.edge[1].src = 0;
		graph.edge[1].dest = 2;
		graph.edge[1].weight = 6;
		graph.edge[2].src = 0;
		graph.edge[2].dest = 3;
		graph.edge[2].weight = 5;
		graph.edge[3].src = 1;
		graph.edge[3].dest = 3;
		graph.edge[3].weight = 15;
		graph.edge[4].src = 2;
		graph.edge[4].dest = 3;
		graph.edge[4].weight = 4;
		Kruskal.kruskalMST(graph, V, E);
	}
	
}
