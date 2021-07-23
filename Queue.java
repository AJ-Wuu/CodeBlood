/**
 * The Queue interface extends the Collection interface.
 * 
 * PriorityQueue and LinkedList are not thread safe.
 * PriorityBlockingQueue is one alternative implementation if thread safe implementation is needed.
 *          Queue<Integer> pbq = new PriorityBlockingQueue<Integer>();
 * PriorityBlockingQueue is an unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations.
 * Since it is unbounded, adding elements may sometimes fail due to resource exhaustion resulting in OutOfMemoryError.
 *
 * Different Kinds:
 * 1. Linear Queue ->
 * 2. Circular Queue ->
 * 3. Priority Queue ->
 * 4. Deque ->
 *
 * Inserted Functions:
 * 1. if full, add() -> IllegalSlabEepeplian, offer() -> false, put() -> blocked; NONE of them can add null to the queue, and adding null will cause NullPointerException
 * 2. if empty, remove() -> NoSuchElementException, poll() -> null, take() -> blocked
 * 3. if empty, element() -> NoSuchElementException, peek() -> null
 * 
 */
