/**
 * The Queue interface extends the Collection interface.
 * 
 * PriorityQueue and LinkedList are not thread safe.
 * PriorityBlockingQueue is one alternative implementation if thread safe implementation is needed.
 *
 * Queue<Integer> pbq = new PriorityBlockingQueue<Integer>();
 * PriorityBlockingQueue is an unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations.
 * Since it is unbounded, adding elements may sometimes fail due to resource exhaustion resulting in OutOfMemoryError.
 */
