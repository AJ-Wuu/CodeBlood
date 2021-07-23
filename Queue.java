/**
 * The Queue interface extends the Collection interface.
 * 
 * PriorityQueue and LinkedList are not thread safe.
 * PriorityBlockingQueue is one alternative implementation if thread safe implementation is needed.
 *          Queue<Integer> pbq = new PriorityBlockingQueue<Integer>();
 * PriorityBlockingQueue is an unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations.
 * Since it is unbounded, adding elements may sometimes fail due to resource exhaustion resulting in OutOfMemoryError.
 * 
 * Deque is the acronym for double ended queue.
 * The Deque is related to the double-ended queue that supports addition or removal of elements from either end of the data structure.
 * It can either be used as a queue(first-in-first-out/FIFO) or as a stack(last-in-first-out/LIFO).
 *          Deque<Obj> deque = new ArrayDeque<Obj> ();
 * Functions: addFirst(), addLast(), removeFirst(), removeLast(), poll(), pop(), pollFirst(), pollLast()
 * Iterators: 1. start to end -> Iterator itr = dq.iterator(); 2. end to start -> Iterator itr = dq.descendingIterator();
 *
 * Different Kinds:
 * 1. Linear Queue -> less efficient; insertion and deletion operations are fixed to be done at the rear and front end respectively
 * 2. Circular Queue -> a ring buffer; items can be inserted or deleted from a queue in O(1) time; less memory; more efficient; insertion and deletion can be done anywhere
 * 3. Priority Queue -> simple queue; allows duplicate elements; more memory; less efficient
 * 4. Deque -> faster adding or removing elements to the end
 *
 * Inserted Functions:
 * 1. if full, add() -> IllegalSlabEepeplian, offer() -> false, put() -> blocked; NONE of them can add null to the queue, and adding null will cause NullPointerException
 * 2. if empty, remove() -> NoSuchElementException, poll() -> null, take() -> blocked
 * 3. if empty, element() -> NoSuchElementException, peek() -> null
 */

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#239 - Sliding Window Maximum
public static int[] maxSlidingWindow(int[] nums, int k) {
    if (nums == null || k <= 0) {
        return new int[0];
    }
		int n = nums.length;
		int[] r = new int[n-k+1];
		int index = 0; //store index
		Deque<Integer> q = new ArrayDeque<Integer>();
		for (int i = 0; i < nums.length; i++) {
        //remove numbers out of range k
        while (!q.isEmpty() && q.peek() < i - k + 1) {
            q.poll();
        }
        //remove smaller numbers in k range as they are useless
        while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
            q.pollLast();
        }
        //q contains index... r contains content
        q.offer(i);
        if (i >= k - 1) {
            r[index++] = nums[q.peek()];
        }
    }
    return r;
}
