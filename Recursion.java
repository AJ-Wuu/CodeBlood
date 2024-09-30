/**
 * Special Note: Java is always pass by value, but when using Collections or Arrays, they are pass by reference.
 * Advantages:
 *     1. Only need to define the base case and recursive case for the recursive funtion, so the code is simpler and shorter than an iterative one.
 *     2. Some problems are inherently recursive, such as Graph and Tree Traversal.
 * Disadvantages: 
 *     1. Greater space requirements than an iterative program as each function call will remain in the stack until the base case is reached.
 *     2. Greater time requirements because each time the function is called, the stack grows and the final answer is returned when the stack is popped completely.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #2 - Add Two Numbers
// Do not add the numbers together in an int or a long, as it's easily get overflowed.
// Store the adding result for every bit, and use an int as carry digit
int x = (p != null) ? p.val : 0; // save the trouble of if (p != null && q != null) AND if (p != null && q == null) AND if (p == null && q != null)
int y = (q != null) ? q.val : 0;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #24 - Swap Nodes in Pairs
// Essence: every two adjacent nodes become a pair, and this pair can "ignore" what happens before and after.
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) {
        return head;
    }

    ListNode newHead = head.next; // record the second node
    head.next = swapPairs(head.next.next); // move the first node to the second place
    newHead.next = head; // add the second node to the first place
    return newHead;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #143 - Reorder List
public void reorderList(ListNode head) {
    if (head == null || head.next == null) {
        return;
    }

    // Find the middle of the list
    ListNode p1 = head;
    ListNode p2 = head;
    while (p2.next != null && p2.next.next != null) {
        p1 = p1.next;
        p2 = p2.next.next;
    }

    // Reverse the half after middle  1->2->3->4->5->6 to 1->2->3->6->5->4
    ListNode preMiddle = p1;
    ListNode preCurrent = p1.next;
    while (preCurrent.next != null) {
        ListNode current = preCurrent.next;
        preCurrent.next = current.next;
        current.next = preMiddle.next;
        preMiddle.next = current;
    }

    // Start reorder one by one  1->2->3->6->5->4 to 1->6->2->5->3->4
    p1 = head;
    p2 = preMiddle.next;
    while (p1 != preMiddle) {
        preMiddle.next = p2.next;
        p2.next = p1.next;
        p1.next = p2;
        p1 = p2.next;
        p2 = preMiddle.next;
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// BackTracking
// Recursion over every possible way and then find the best route.
// Time Complexity: O(2^n)
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new LinkedList<>();
    Arrays.sort(nums);
    backtrackSubset(list, new LinkedList<>(), nums, 0);
    return list;
}

// #78, #90 - Subsets I, II
private static void backtrackSubset(List<List<Integer>> list , LinkedList<Integer> tempList, int[] nums, int start) {
    list.add(new LinkedList<>(tempList));
    for (int i=start; i<nums.length; i++) {
        tempList.add(nums[i]);
        backtrackSubset(list, tempList, nums, i+1);
        tempList.removeLast(); // tempList is of LinkedList type
    }
}
private void backtrackSubsetWithDuplicates(List<List<Integer>> list, LinkedList<Integer> tempList, int[] nums, int start) {
    list.add(new LinkedList<>(tempList));
    for (int i=start; i<nums.length; i++) {
        if (i>start && nums[i]==nums[i-1]) {
        	continue; // skip duplicates (already existed)
        }
        tempList.add(nums[i]);
        backtrackSubsetWithDuplicates(list, tempList, nums, i+1);
        tempList.removeLast();
    }
}

// #47 - Permutations II
private void backtrackPermutation(List<List<Integer>> list, List<Integer> tempList, int[] nums, boolean[] used) {
    if (tempList.size() == nums.length) {
        list.add(new LinkedList<>(tempList));
    }
    else {
        for (int i=0; i<nums.length; i++) {
            if (used[i]) {
            	continue;
            }
            used[i] = true; 
            tempList.add(nums[i]);
            backtrackPermutation(list, tempList, nums, used);
            used[i] = false; 
            tempList.remove(tempList.size() - 1); // tempList is of List type
            while (i+1<nums.length && nums[i]==nums[i+1]) {
            	i++; // skip duplicates
            }
        }
    }
}

// #39 - Combination Sum
private void backtrackAllowReuse(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start) {
    if (remain < 0) {
    	return ;
    }
    else if (remain == 0) {
    	list.add(new LinkedList<>(tempList));
    }
    else { 
        for (int i=start; i<nums.length; i++) {
            tempList.add(nums[i]);
            backtrackAllowReuse(list, tempList, nums, remain - nums[i], i); // NOT i+1 because we can reuse the same elements
            tempList.remove(tempList.size() - 1);
        }
    }
}
