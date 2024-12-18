/**
 * @author AJWuu
 */

package shortestPath;

public class Dijkstra {
    // Find shortest path from start vertex to all other vertices in a graph
    // Fastest, single start, shortest path algorithm for directed and undirected graphs with unbounded, non-negative edge weights

    // Step1: Pick an unvisited vertex u with a minimum distance value. 
    // Step2: Mark u as visited.
    // Step3: Update distance value of all adjacent vertices of u.
    //        To update the distance values, iterate through all adjacent vertices.
    //        For every adjacent vertex v, 
    //            if the sum of distance value of u (from source) and weight of edge u-v is less than the distance value of v, 
    //            then update the distance value of v. 

    // Complexity: O((V+E) * logV)
    // 1. remove vertex from PriorityQueue: logV (once per vertex)
    // 2. update priority value: logV (once per edge)

    static int V;

    int minDistance(int distance[], boolean visitedSet[]) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < V; v++) {
            if (visitedSet[v] == false && distance[v] <= min) {
                min = distance[v];
                min_index = v;
            }
        }
        return min_index;
    }

    void printSolution(int distance[]) {
        System.out.println("Vertex \t Distance from Source");
        for (int i = 0; i < V; i++)
            System.out.println(i + " \t " + distance[i]);
    }

    void shortestPath(int graph[][], int src) {
        int distance[] = new int[V];
        boolean visitedSet[] = new boolean[V];
        for (int i=0; i<V; i++) {
            distance[i] = Integer.MAX_VALUE;
            visitedSet[i] = false;
        }

        distance[src] = 0; // distance of source vertex from itself is always 0
        for (int count=0; count<V-1; count++) {
            int u = minDistance(distance, visitedSet);
            visitedSet[u] = true;
            for (int v=0; v<V; v++) {
                if (!visitedSet[v] && graph[u][v] != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        printSolution(distance);
    }

    public static void main(String[] args) {
        V = 6;
        int graph[][] = new int[][] { 
            { 0,6,2,5,0,0 },
            { 0,0,2,0,1,0 },
            { 0,3,0,0,0,1 },
            { 0,0,0,0,3,0 },
            { 4,0,0,0,0,0 },
            { 1,0,0,1,0,0 } 
        };
        Dijkstra t = new Dijkstra();
        t.shortestPath(graph, 1);
    }
}
