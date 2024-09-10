/**
 * Heap is generally preferred for priority queue implementation because heaps provide better performance compared arrays or linked list.
 *
 * PriorityQueue can contain repetitive elements.
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
 *
 * Priority Queue add() vs offer()
 * 1. when there is no max capacity limit, no difference
 * 2. when there is a max capacity limit, add() throws an exception if the queue is full, whereas offer() only returns false
 *
 * 🔴 Special Note about Priority Queue
 * PriorityQueue does NOT return elements in particular order when we iterate it because it's implemented as a priority heap rather than sorted list.
 * From javadoc: "The Iterator provided in method iterator() is not guaranteed to traverse the elements of the priority queue in any particular order.
 *                If you need ordered traversal, consider using Arrays.sort(pq.toArray())."
 * The only guarantee provided by PriorityQueue is that poll(), peek(), etc return the least element.
 * If you need ordered iteration of elements, use some other collection such as TreeSet.
 * Eg. PriorityQueue<Integer> queue = new PriorityQueue<Integer>; queue.add(-1); queue.add(-2); queue.add(-3);
 *     Object[] array = queue.toArray(); -> array = [-3, -1, -2];
 *     System.out.println("Elements from smallest to largest: " + queue.poll() + " " + queue.poll() + " " + queue.poll()); -> Elements from smallest to largest: -3 -2 -1
 */

// Comparator
// Ascending Order -> Default
// Descending Order ->
// Approach A (Use Collections / Comparator):
PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
    // Comparator.reverseOrder() also works, as:
        public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
            return Collections.reverseOrder();
        }
// Approach B (Override Comparator):
PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
    @Override
    public int compare(Integer o1, Integer o2){
        return o2 < o1 ? -1 : 1; // OR return o2.compare(o1); OR return (o2 - o1);
    }
});
// Approach C (Lambda Expression):
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #23 - Merge k Sorted Lists
// Attempt 1: Compare one by one (Time: O(n * k), Space: O(n))
// 1. Compare every k nodes (head of every linked list) and get the node with the smallest value.
// 2. Extend the final sorted LinkedList with the selected Nodes.

// Attempt 2: Merge lists one by one (Time: O(n * k), Space: O(1))
// -> See Sorting.java (link)

// Attempt 3: Use Priority Queue (Time: O(n * log(k)), Space: O(n))
// 1. Add every node into the Priority Queue.
// 2. Convert the queue to the LinedList of Nodes.

// Attempt 4: Merge with Divide and Conquer (Time: O(n * log(k)), Space: O(1))
// List0 → List0 →→ List0 →→→→→→ result
// List1 ↗         ↗             ↗
// List2 → List2 ↗             ↗
// List3 ↗                  ↗
// List4 → List4 →→ List4 ↗
// List5 ↗
// 1. Pair up k lists and merge each pair.
// 2. After the first pairing, k lists are merged into k/2 lists with average 2N/k length, then k/4, k/8, and so on.
// 3. Repeat this procedure until we get the final sorted linked list.

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #218 - The Skyline Problem
// Key: 1. The use of queue, queue.compute() and the conversion between queue.compute() and queue.getOrDefault()
//      2. TreeMap makes it faster
public static List<List<Integer>> getSkyline(int[][] buildings) {
    int[][] buildingPoints = new int[buildings.length * 2][2];
    // Split the original input with size 1x3 into two vectors of size 1x2: e.g. [2,9,10] -> [2,-10] and [9,10], -10 shows that 2 is the start
    int i = 0;
    for (int[] building : buildings) {
        // Negative height for the start
        buildingPoints[i][0] = building[0];
        buildingPoints[i][1] = -building[2];
        // Positive height for the end
        buildingPoints[i + 1][0] = building[1];
        buildingPoints[i + 1][1] = building[2];
        i += 2;
    }
    // Sort from smallest to largest based on first column elements 
    Arrays.sort(buildingPoints, new Comparator<int[]>() {
        public int compare(int[] o1, int[] o2) {
            // If the first elements are identical, use the second elements to compare
            return o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]; 
        }
    });

    List<List<Integer>> res = new ArrayList<>();
    TreeMap<Integer, Integer> queue = new TreeMap<>();  // Ascending order by default
    // Since the height of the building is likely to be repeated, let the key record the height, and the value record the number of times the same height appears
    queue.put(0, 1);
    int prevMaxHeight = 0;
    for (int j = 0; j < buildingPoints.length; j++) {
        int[] buildingPoint = buildingPoints[j];
        if (buildingPoint[1] < 0) { // isStart == true
            queue.compute(-buildingPoint[1], (key, value) -> {
                if (value != null) {
                    return value + 1;
                }
                return 1;
            });
            // OR queue.put(-buildingPoint[1], queue.getOrDefault(-buildingPoint[1], 0) + 1);
        }
        else { // isStart == false
            queue.compute(buildingPoint[1], (key, value) -> {
                if (value == 1) {
                    return null;
                }
                return value - 1;
            });
        }

        int currMaxHeight = queue.lastKey();
        // Since queue is in ascending order by default,
        // if height changes from previous height, then this building x becomes critical x,
        // so add it to the result
        if (currMaxHeight != prevMaxHeight) {
            res.add(Arrays.asList(buildingPoint[0], currMaxHeight));
            prevMaxHeight = currMaxHeight;
        }
    }
    return res;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #295 - Find Median from Data Stream
// Use two PriorityQueues to store the values
PriorityQueue<Integer> lowerHalf;
PriorityQueue<Integer> upperHalf;

if (lowerHalf.isEmpty() || num <= lowerHalf.peek()) {
    lowerHalf.add(num);
}
else {
    upperHalf.add(num);
}
if (upperHalf.size() > lowerHalf.size()) {
    lowerHalf.add(upperHalf.poll());
}
else if (lowerHalf.size() > upperHalf.size() + 1) {
    upperHalf.add(lowerHalf.poll());
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #347 - Top K Frequent Elements
// Approach 1: Using heap
// initialize heap 'the less frequent element first'
Queue<Integer> heap = new PriorityQueue<>((n1, n2) -> count.get(n1) - count.get(n2));
// keep k top frequent elements in the heap for O(N * log(k)) < O(N * log(N)) time
for (int n : count.keySet()) {
    heap.add(n);
    if (heap.size() > k) {
        heap.poll();   
    }
}
// build an output array with O(k * log(k)) time
int[] top = new int[k];
for(int i=k-1; i>=0; --i) {
    top[i] = heap.poll();
}

// Approach 2: Quickselect (Hoare's selection algorithm) -> See CLRS/Chapter07-Quick Sort/QuickSelect.java
