/**
 *             Internal structure  |  Manipulation                              |  Interface       |  Better at                   |  Performance
 * ArrayList:  dynamic array       |  slow (array needs shifting for removal)   |  list only       |  storing and accessing data  |  O(1)
 * LinkedList: doubly linked list  |  fast (DLL needs no shifting for removal)  |  list and deque  |  manipulating data           |  O(n)
 */

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#25 - Reverse Nodes in k-Group
public static ListNode reverseKGroup(ListNode head, int k) {
    int n = 0;
    for (ListNode i=head; i!=null; i=i.next) {
        n++;
    }
    ListNode init = new ListNode(0); //init is the node before head
    init.next = head;
    for (ListNode prev=init, tail=head; n>=k; n-=k) {
        for (int i=1; i<k; i++) { //Eg. 12345 with k = 4
            ListNode next = tail.next.next; //3 -- 4 -- 5
            tail.next.next = prev.next; //12121 -- 21321 -- 32143
            prev.next = tail.next; //21212 -- 32132 -- 43214
            tail.next = next; //21345 -- 32145 -- 43215
        }
        prev = tail;
        tail = tail.next; //Node(5).next == Node(5), not null, so it's needed to control the process with n
    }
    return init.next;
}

public static ListNode rotateRight(ListNode head, int k) {
//		if (head == null || head.next == null) {
//            return head;
//        }
//		int n = 0;
//		ListNode temp = head;
//		for (; temp != null; n++) {
//			temp = temp.next;
//		}
//		for (int i=0; i<k%n; i++) {
//			head = rotateHelper(head, n);
//		}
//		return head;
		if (head == null || head.next == null || k == 0) {
	         return head;
	    }
	    ListNode fast = head;
	    ListNode slow = head;
	    ListNode newHead;
	    for (int i = 0; i < k; i++) {
	        if (fast.next == null) {
	            fast = head; //go back to head when k > list's size
	        }
	        else {
	            fast = fast.next;
	        }
	    }
	    while (fast.next != null) { //when fast reaches the last node, slow reaches the last node of the new list
	        fast = fast.next;
	        slow = slow.next;
	    }
	    fast.next = head;
	    newHead = slow.next;
	    slow.next = null;
	    return newHead;
	}
