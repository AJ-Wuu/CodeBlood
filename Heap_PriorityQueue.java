/**
 * Heap is generally preferred for priority queue implementation because heaps provide better performance compared arrays or linked list
 * 
 * Why Binary Heap?
 * 1. Efficiently add new values after initially constructing it.
 * 2. Only takes O(logn) to insert, which is exponentially faster than a Priority Queue.
 *
 * Why Priority Queue?
 * 1. Easy to implement (no need to worry about manually sorting).
 * 2. Elements are automatically sorted in desired order (which also results in Θ(n) for the worst adding case).
 * 3. Every item has a priority associated with it.
 * 4. An element with high priority is dequeued before an element with low priority.
 * 5. If two elements have the same priority, they are served according to their order in the queue.
 */

//Comparator
//Ascending Order -- Default
//Descending Order --
//Approach A:
PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
//Approach B:
PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2){
        return o2 < o1 ? -1 : 1; //OR return o2.compare(o1);
    }
});

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#23 - Merge k Sorted Lists
//Attempt 1: Compare one by one (Time: O(n * k), Space: O(n))
//1. Compare every k nodes (head of every linked list) and get the node with the smallest value.
//2. Extend the final sorted LinkedList with the selected Nodes.

//Attempt 2: Merge lists one by one (Time: O(n * k), Space: O(1))
//-> See Sorting.java (link)

//Attempt 3: Use Priority Queue (Time: O(n * log(k)), Space: O(n))
//1. Add every node into the Priority Queue.
//2. Convert the queue to the LinedList of Nodes.

//Attempt 4: Merge with Divide and Conquer (Time: O(n * log(k)), Space: O(1))
//List0 → List0 →→ List0 →→→→→→ result
//List1 ↗         ↗             ↗
//List2 → List2 ↗             ↗
//List3 ↗                  ↗
//List4 → List4 →→ List4 ↗
//List5 ↗
//1. Pair up k lists and merge each pair.
//2. After the first pairing, k lists are merged into k/2 lists with average 2N/k length, then k/4, k/8, and so on.
//3. Repeat this procedure until we get the final sorted linked list.
