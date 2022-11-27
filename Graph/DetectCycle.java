/*
 * @author AJWuu
 */

import java.util.*;

class Edge {
    int src, dest;
}

class Graph_Array {
    int V, E;
    Edge[] edge;

    Graph_Array(int V, int E) {
        this.V = V;
        this.E = E;
        edge = new Edge[E];
        for (int i = 0; i < E; ++i) {
            edge[i] = new Edge();
        }
    }

    int find(int parent[], int i) {
        if (parent[i] == i)
            return i;
        return find(parent, parent[i]);
    }

    void union(int parent[], int x, int y) {
        parent[x] = y;
    }
}

class Graph {
    int V;
    LinkedList<Integer> adj[];

    Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];
        for (int i = 0; i < V; ++i) {
            adj[i] = new LinkedList();
        }
    }

    void addUndirectedEdge(int a, int b) {
        adj[a].add(b);
        adj[b].add(a);
    }

    void addDirectedEdge(int a, int b) {
        adj[a].add(b);
    }
}

public class cycle {

    public static boolean detectCyclesInUndirectedGraph_UnionFind(Graph_Array graph) {
        int parent[] = new int[graph.V];
        for (int i = 0; i < graph.V; ++i) {
            parent[i] = i;
        }

        // find subset of both vertices of every edge
        // if both subsets are same, then there is cycle in graph
        for (int i = 0; i < graph.E; ++i) {
            int x = graph.find(parent, graph.edge[i].src);
            int y = graph.find(parent, graph.edge[i].dest);
            if (x == y) {
                return true;
            }

            graph.union(parent, x, y);
        }
        return false;
    }

    public static boolean isCyclicUtil(Graph graph, int v, boolean visited[], int parent) {
        visited[v] = true;

        Iterator<Integer> it = graph.adj[v].iterator();
        while (it.hasNext()) {
            int i = it.next();
            if (!visited[i]) {
                if (isCyclicUtil(graph, i, visited, v)) {
                    return true;
                }
            }
            else if (i != parent) {
                return true;
            }
        }
        return false;
    }

    public static boolean detectCyclesInGraph_DFS(Graph graph, int V) {
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i) {
            if (!visited[i]) {
                if (isCyclicUtil(graph, i, visited, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    // #2360 - Longest Cycle in a Graph
    // each node has **at most one** outgoing edge -> at most one cycle exist
    public static int longestCycleInDirectedGraph(Graph_Array graph, int V) {
        int result = -1;
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; ++i){
            if (visited[i]) {
                continue;
            }

            // local visited, count the size of the current cycle (if there is one)
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int idx = i, dist = 0; idx != -1; idx = graph.edge[idx].dest){
                if (map.containsKey(idx)) { // find a cycle
                    result = Math.max(result, dist - map.get(idx));
                    break;
                }
                if (visited[idx]) { // already counted this cycle in previous rounds
                    break;
                }
                visited[idx] = true;
                map.put(idx, dist++);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int V = 3, E = 3;
        Graph_Array undirectedGraph1 = new Graph_Array(V, E);
        undirectedGraph1.edge[0].src = 0;
        undirectedGraph1.edge[0].dest = 1;
        undirectedGraph1.edge[1].src = 1;
        undirectedGraph1.edge[1].dest = 2;
        undirectedGraph1.edge[2].src = 0;
        undirectedGraph1.edge[2].dest = 2;
        if (detectCyclesInUndirectedGraph_UnionFind(undirectedGraph1)) {
            System.out.println("Cycle!");
        }
        else {
            System.out.println("No cycle");
        }

        V = 5;
        Graph undirectedGraph2 = new Graph(V);
        undirectedGraph2.addUndirectedEdge(1, 0);
        undirectedGraph2.addUndirectedEdge(0, 2);
        undirectedGraph2.addUndirectedEdge(2, 1);
        undirectedGraph2.addUndirectedEdge(0, 3);
        undirectedGraph2.addUndirectedEdge(3, 4);
        if (detectCyclesInGraph_DFS(undirectedGraph2, V)) {
            System.out.println("Cycle!");
        }
        else {
            System.out.println("No cycle");
        }

        V = 4;
        Graph directedGraph1 = new Graph(V);
        directedGraph1.addDirectedEdge(0, 1);
        directedGraph1.addDirectedEdge(0, 2);
        directedGraph1.addDirectedEdge(1, 2);
        directedGraph1.addDirectedEdge(2, 0);
        directedGraph1.addDirectedEdge(2, 3);
        directedGraph1.addDirectedEdge(3, 3);
        if (detectCyclesInGraph_DFS(directedGraph1, V)) {
            System.out.println("Cycle!");
        }
        else {
            System.out.println("No cycle");
        }
        
        V = E = 5;
        Graph_Array directedGraph2 = new Graph_Array(V, E);
        directedGraph2.edge[0].src = 0;
        directedGraph2.edge[0].dest = 3;
        directedGraph2.edge[1].src = 1;
        directedGraph2.edge[1].dest = 3;
        directedGraph2.edge[2].src = 2;
        directedGraph2.edge[2].dest = 4;
        directedGraph2.edge[3].src = 3;
        directedGraph2.edge[3].dest = 2;
        directedGraph2.edge[4].src = 4;
        directedGraph2.edge[4].dest = 3;
        System.out.println(longestCycleInDirectedGraph(directedGraph2, V));

        V = E = 4;
        Graph_Array directedGraph3 = new Graph_Array(V, E);
        directedGraph3.edge[0].src = 0;
        directedGraph3.edge[0].dest = 2;
        directedGraph3.edge[1].src = 1;
        directedGraph3.edge[1].dest = -1;
        directedGraph3.edge[2].src = 2;
        directedGraph3.edge[2].dest = 3;
        directedGraph3.edge[3].src = 3;
        directedGraph3.edge[3].dest = 1;
        System.out.println(longestCycleInDirectedGraph(directedGraph3, V));
    }
}
