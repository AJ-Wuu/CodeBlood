/**
 * The Queue interface extends the Collection interface.
 * Queue can be implemented by LinkedList and Array (and Two Stacks).
 * 
 * PriorityQueue and LinkedList are not thread safe.
 * PriorityBlockingQueue is one alternative implementation if thread safe implementation is needed.
 *          Queue<Integer> pbq = new PriorityBlockingQueue<Integer>();
 * PriorityBlockingQueue is an unbounded blocking queue that uses the same ordering rules as class PriorityQueue and supplies blocking retrieval operations.
 * Since it is unbounded, adding elements may sometimes fail due to resource exhaustion resulting in OutOfMemoryError.
 * 
 * Deque is the acronym for double ended queue.
 * The Deque is related to the double-ended queue that supports addition or removal of elements from either end of the data structure.
 * It can either be used as a queue (first-in-first-out/FIFO) or as a stack (last-in-first-out/LIFO).
 *          Deque<Obj> deque = new ArrayDeque<Obj> ();
 * Functions: addFirst(), addLast(), removeFirst(), removeLast(), poll(), pop(), pollFirst(), pollLast(), etc.
 * Iterators: 1. start to end -> Iterator itr = dq.iterator(); 2. end to start -> Iterator itr = dq.descendingIterator();
 * Advantages: dynamic size, O(1) for insertion and deletion, versatile as queue or stack, no reallocation needed, thread-safe, and cache-friendly
 * Disadvantages: less memory efficient, not suitable for sorting, and may cause synchronization issue in multi-threaded applications
 *
 * Different Kinds:
 * 1. Linear Queue -> less efficient; insertion and deletion operations are fixed to be done at the rear and front end respectively
 * 2. Circular Queue -> a ring buffer; items can be inserted or deleted from a queue in O(1) time; 
 *                      less memory; more efficient; insertion and deletion can be done anywhere
 * 3. Priority Queue -> simple queue; allows duplicate elements; more memory; less efficient
 * 4. Deque -> faster adding or removing elements to the end
 *
 * Inserted Functions:
 * 1. if full, add() -> IllegalSlabEepeplian, offer() -> false, put() -> blocked;
 *             NONE of them can add null to the queue, and adding null will cause NullPointerException
 * 2. if empty, remove() -> NoSuchElementException, poll() -> null, take() -> blocked
 * 3. if empty, element() -> NoSuchElementException, peek() -> null
 */

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

// Queue using Two Stacks
// Key: enqueue -> put into Stack1
//      dequeue -> pop out of Stack1 to Stack2, then pop out of Stack2
//      print -> pop out of Stack1 to Stack2, then peep Stack2
// The reason of using two stacks is because there exists a possibility of printing without dequeue, and the current Stack should be the combination of Stack2-Stack1
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int N = sc.nextInt();
        
    Stack<Integer> s1 = new Stack<Integer>();
    Stack<Integer> s2 = new Stack<Integer>();
        
    while (N-- > 0) {
        int read = sc.nextInt();
        if (read == 1) {
            int num = sc.nextInt();
            s1.push(num);
        }
        else if (read == 2) {
            if (s2.empty()) {
                while (!s1.empty()) {
                    s2.push(s1.pop());
                }
            }
            s2.pop();
        }
        else {
            if (s2.empty()) {
                while (!s1.empty()) {
                    s2.push(s1.pop());
                }
            }
            System.out.println(s2.peek());
        }
    }
    sc.close();
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #239 - Sliding Window Maximum
public static int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n-k+1];
    int index = 0; // store index
    Deque<Integer> q = new ArrayDeque<Integer>();
    for (int i = 0; i < nums.length; i++) {
        // remove numbers out of range k (only keep those in [i-(k-1), i])
        while (!q.isEmpty() && q.peek() < i - k + 1) {
            q.poll();
        }
        // discard the elements in k range smaller than a[i] from the tail, as they are useless for the future
        // if a[x] < a[i] and x < i, then a[x] has no chance to be the "max" in [i-(k-1),i] or any other subsequent window
        while (!q.isEmpty() && nums[q.peekLast()] < nums[i]) {
            q.pollLast();
        }
        // q contains index and result contains content
        q.offer(i);
        if (i >= k - 1) {
            result[index++] = nums[q.peek()];
        }
    }
    return result;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// Max Even Sum of a Subsequence of Length K
public static int MaxEvenSumKLengthSubsequence(int[] arr, int k) {
    PriorityQueue<Integer> odd = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> even = new PriorityQueue<>(Collections.reverseOrder());
    if (even.size() == 0 && odd.size() == 1) {
        return -1;
    }

    int result = 0;
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] % 2 == 1) {
            odd.add(arr[i]);
        }
        else {
            even.add(arr[i]);
        }
    }

    while (k > 1) {
        if (even.peek() > odd.peek()) { // the largest even number > the largest odd number
            k--;
            result += even.poll();
        }
        else { // choose the larger one out of the sum of even top two and the sum of odd top two
            k -= 2;
            int[] temp = new int[4];
            temp[0] = even.poll();
            temp[1] = even.peek();
            temp[2] = odd.poll();
            temp[3] = odd.peek();
            if (temp[0] + temp[1] >= temp[2] + temp[3]) {
                result += temp[0] + temp[1];
                even.poll();
                odd.add(temp[2]);
            }
            else {
                result += temp[2] + temp[3];
                odd.poll();
                even.add(temp[0]);
            }
        }
    }
    if (k == 1) { // still need one more number
        result += even.poll();
    }

    return result;
}
