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
//Key: For a tree-alike graph, the number of centroids is no more than 2.
//Process: 1. Once we trim out the first layer of the leaf nodes (nodes that have only one connection), some of the non-leaf nodes would become leaf nodes.
//         2. The trimming process continues until there are only two nodes left in the graph, which are the centroids that we are looking for.
public ArrayList<Integer> findMinHeightTrees(int n, int[][] edges) {
        int[] degree = new int[n];
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, new ArrayList<>());
        }
        for (int[] edge : edges) {
            map.get(edge[0]).add(edge[1]);
            map.get(edge[1]).add(edge[0]);
            degree[edge[0]]++;
            degree[edge[1]]++;
        }
        ArrayList<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] <= 1) {
                leaves.add(i);
                degree[i] = 0;
            }
        }
        
        int c = leaves.size();
        
        while (c < n) {
            ArrayList<Integer> newLeaves = new ArrayList<>();
            for (int leaf : leaves) {
                for (int neighbor : map.get(leaf)) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 1) {
                        newLeaves.add(neighbor);
                    }
                }
                degree[leaf] = 0;
            }
            c += newLeaves.size();
            leaves = newLeaves;
        }
        
        return leaves;
    }
