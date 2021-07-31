/**
 * Shallow Copy: If the field value is a reference to an object (e.g., a memory address) it copies the reference.
 *               The referenced objects are thus shared, so if one of these objects is modified (from A or B), the change is visible in the other.
 *               Shallow copies are simple and typically cheap, as they can be usually implemented by simply copying the bits exactly.
 * Deep Copy: Fields are dereferenced.
 *            Rather than references to objects being copied, new copy objects are created for any referenced objects, and references to these placed in B.
 *            The objects referenced by the copy B are distinct from those referenced by A, and independent.
 *            Deep copies are more expensive, due to needing to create additional objects, 
 *                and can be substantially more complicated, due to references possibly forming a complicated graph.
 */

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#133 - Clone Graph
//DFS, using recursion and visited map
//Concerning duplicate labels situations (two nodes with the same label)
public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
    return clone(node, new HashMap<UndirectedGraphNode, UndirectedGraphNode>());
}
    
public UndirectedGraphNode clone(UndirectedGraphNode src, HashMap<UndirectedGraphNode, UndirectedGraphNode> visitedBag) {
    if (src == null) {
        return null;
    }
    if (visitedBag.containsKey(src)) {
        return visitedBag.get(src);
    }
        
    UndirectedGraphNode n = new UndirectedGraphNode(src.label);
    visitedBag.put(src, n);
    for (UndirectedGraphNode child : src.neighbors) {
        if (visitedBag.containsKey(child)) {
            n.neighbors.add(visitedBag.get(child));
        }
        else {
            UndirectedGraphNode childCopy = clone(child, visitedBag);    
            n.neighbors.add(childCopy);    
        }
    }
    return n;
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//207 - Course Schedule
//prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai
//Approach 1: Topological Sort (worse efficiency) -> See #210 or CLRS/Chapter22-Elementary Graph Algorithms/Directed Graph/TopologicalSort.java for details
//Approach 2: DFS (Time Limit Exceeded)
//Approach 3: BFS (better efficiency)
public static boolean canFinish(int numCourses, int[][] prerequisites) {
    ArrayList[] graph = new ArrayList[numCourses]; //prerequisite-requisite relationship
    int[] degree = new int[numCourses]; //courses that are prerequisites (Eg. degree[1] = 2 means that course 1 is the prerequisite of two other courses)
    Queue<Integer> queue = new LinkedList<Integer>(); //courses that are not prerequisites to any other courses
    int count = 0; //how many courses have ever been added to queue
    
    //Initialize
    for (int i=0; i<numCourses; i++) {
        graph[i] = new ArrayList<Integer>();
    }
    
    //Assign Values
    for (int i=0; i<prerequisites.length; i++) {
        degree[prerequisites[i][1]]++;
        graph[prerequisites[i][0]].add(prerequisites[i][1]);
    }
    for (int i=0; i<degree.length; i++) {
        if (degree[i] == 0) {
            queue.add(i);
            count++;
        }
    }
    
    //Check if there are cycles
    //If cycle exists, then the two courses will never be added to queue, as: 1. courses in the queue could never point to them; OR 2. their degrees will never reach 0
    while (queue.size() != 0) {
        int course = (int)queue.poll();
        for (int i=0; i<graph[course].size(); i++) {
            int pointer = (int)graph[course].get(i);
            degree[pointer]--;
            if (degree[pointer] == 0) {
                queue.add(pointer);
                count++;
            }
        }
    }
    if (count == numCourses) {
        return true;
    }
    else {
        return false;
    }
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#210 - Course Schedule II
//Topological Sort Approach
static boolean hasCycle(ArrayList<Integer>[] list, boolean[] visited, boolean[] restack, Queue<Integer> queue, int i){
    if (restack[i]) { //deal with cycle, like [5,5] or [[1,2], [2,1]]
        return true;
    }
    if (visited[i]) { //stop recursion
        return false;
    }
        
    visited[i] = true;
    restack[i] = true;
    ArrayList<Integer> adj = list[i];
    for (int c : adj) {
        if (hasCycle(list, visited, restack, queue, c)) {
            return true;
        }
    }
    restack[i] = false;
    queue.add(i);
    return false;
}

public static int[] findOrder(int numCourses, int[][] prerequisites) {
    ArrayList<Integer>[] list = new ArrayList[numCourses];
    for (int i=0; i<numCourses; i++) {
        list[i] = new ArrayList<Integer>();
    }
        
    for (int i=0; i<prerequisites.length; i++) {
        int u = prerequisites[i][1], v = prerequisites[i][0];
        list[u].add(v);
    }
        
    boolean[] visited = new boolean[numCourses];
    boolean[] restack = new boolean[numCourses];
    Queue<Integer> queue = new LinkedList<>();
        
    for (int i=0; i<numCourses; i++) {
        if (hasCycle(list, visited, restack, queue, i)) {
            return (new int[0]);
        }
    }
        
    int[] ans = new int[numCourses];
    int k = numCourses - 1;
    while (queue.size() != 0) {
        ans[k--] = queue.poll();
    }
    return ans;
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#310 - Minimum Height Trees
//Key: 1. For a tree-alike graph, the number of centroids is no more than 2
//     2. Center is always the middle node in the longest path(s) along the tree
//Process: 1. Once we trim out the first layer of the leaf nodes (nodes that have only one connection), some of the non-leaf nodes would become leaf nodes.
//         2. The trimming process continues until there are only two nodes left in the graph, which are the centroids that we are looking for.
public ArrayList<Integer> findMinHeightTrees(int n, int[][] edges) {
    //Base cases
    if (n < 2) {
        ArrayList<Integer> centroids = new ArrayList<Integer>();
        for (int i=0; i<n; i++) {
            centroids.add(i);
        }
        return centroids;
    }
    
    //Build the graph
    int[] degree = new int[n];
    HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
    for (int i=0; i<n; i++) {
        map.put(i, new ArrayList<Integer>());
    }
    for (int[] edge : edges) {
        map.get(edge[0]).add(edge[1]);
        map.get(edge[1]).add(edge[0]);
        degree[edge[0]]++;
        degree[edge[1]]++;
    }
    
    //Trim the leaves until reaching the centroids
    ArrayList<Integer> leaves = new ArrayList<>();
    for (int i=0; i<n; i++) {
        if (degree[i] <= 1) {
            leaves.add(i);
            degree[i] = 0;
        }
    }
        
    int c = leaves.size();
    while (c < n) {
        ArrayList<Integer> newLeaves = new ArrayList<Integer>();
        for (int leaf : leaves) { //remove the current leaves along with the edges
            for (int neighbor : map.get(leaf)) {
                degree[neighbor]--; //picking off the leaf, so decrease degree for each of its neighbors
                if (degree[neighbor] == 1) {
                    newLeaves.add(neighbor);
                }
            }
            degree[leaf] = 0;
        }
        c += newLeaves.size(); //all nodes added to leaves (tracking using c since we need to replace leaves with newLeaves)
        leaves = newLeaves;
    }
    
    //The remaining 1 (or 2) node(s) in the leaves would be the centroid(s) of the tree which considered as minimum height tree's root node
    return leaves;
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#329 - Longest Increasing Path in a Matrix
//Key: 1. Use matrix[x][y] <= matrix[i][j] so we don't need a visited[m][n] array
//     2. Cache the distance because it's highly possible to revisit a cell
final int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
public int longestIncreasingPath(int[][] matrix) {
    //Base Case
    if (matrix.length == 0) {
        return 0;
    }

    //Traverse all points in matrix, use every point as starting point to do dfs traversal
    //DFS function returns max increasing path after comparing four max return distance from four directions
    int result = 0;
    int n = matrix.length, m = matrix[0].length;
    int[][] cache = new int[n][m]; //represent the longest increasing path starts from point matrix[i][j]
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            int curLen = dfs(matrix, cache, i, j, matrix[i][j]); //do DFS for every cell
            result = Math.max(result, curLen);
        }
    }
    return result;
}

public int dfs(int[][] matrix, int[][] cache, int x, int y, int curPoint) {
    if (cache[x][y] != 0) {
        return cache[x][y];
    }

    //Initialize max distance as 1, since the path includes starting point itself
    int max = 1;
    for (int[] dir : dirs) {
        int dx = x + dir[0];
        int dy = y + dir[1];
        
        //Compare every 4 direction and skip cells that are out of boundary or smaller
        if (dx < 0 || dx > matrix.length - 1 || dy < 0 || dy > matrix[0].length - 1 || curPoint >= matrix[dx][dy]) { //next point is invalid (out of bound or GE next point)
            continue;
        }
        int curLen = 1 + dfs(matrix, cache, dx, dy, matrix[dx][dy]); //next point is valid
        max = Math.max(max, curLen); //matrix max (getting from every cell's max)
    }
    
    //Update max increasing path value starting from current point in cache
    cache[x][y] = max;
    return max;
}


//In Eulerian paths, there must exist a start node(which is JFK in this problem) and a end node.
End node can be start node or another node.
end node is start node iff all nodes has even degree.
end node is another node iff there is another odd degree node and start node has an odd degree.
So, the algorithm is to find the end node first and delete the path to this node(backtrack), meanwhile using PriorityQueue to guarantee lexical order.
public static List<String> findItinerary(List<List<String>> tickets) {
	Map<String, PriorityQueue<String>> targets = new HashMap<>();
	for (List<String> ticket : tickets)
	    targets.computeIfAbsent(ticket.get(0), k -> new PriorityQueue()).add(ticket.get(1));
	List<String> route = new LinkedList();
	Stack<String> stack = new Stack<>();
	stack.push("JFK");
	while (!stack.empty()) {
	    while (targets.containsKey(stack.peek()) && !targets.get(stack.peek()).isEmpty())
	        stack.push(targets.get(stack.peek()).poll());
	    route.add(0, stack.pop());
	}
	return route;
}
