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
