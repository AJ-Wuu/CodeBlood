/**
 *             Internal structure  |  Manipulation                              |  Interface       |  Better at                   |  Performance
 * ArrayList:  dynamic array       |  slow (array needs shifting for removal)   |  list only       |  storing and accessing data  |  O(1)
 * LinkedList: doubly linked list  |  fast (DLL needs no shifting for removal)  |  list and deque  |  manipulating data           |  O(n)
 *
 * Always remember to use two pointers moving along the list / queue if needed for elements comparasion / selection / etc.
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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#61 - Rotate List
//This method is slow but interesting. It uses two pointers to track the nodes, and it avoids counting the size of the list (which is always used in k % n when k > n)
public static ListNode rotateRight(ListNode head, int k) {
    if (head == null || head.next == null || k == 0) {
        return head;
    }
    ListNode fast = head, slow = head, newHead;
    for (int i=0; i<k; i++) {
        if (fast.next == null) {
            fast = head; //go back to head when k > n
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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#82 - Remove Duplicates from Sorted List II
//Method: Sentinel Head + Predecessor
//The standard way to handle this use case is to use the so-called Sentinel Node.
//Sentinel nodes are widely used for trees and linked lists as pseudo-heads, pseudo-tails, etc.
//    They are purely functional and usually don't hold any data.
//    Their primary purpose is to standardize the situation to avoid edge case handling.
public static ListNode deleteDuplicates(ListNode head) {
    //sentinel, "virtual head", make sure there is a new head and keep track of the new head (newHead = sentinel.next)
    ListNode sentinel = new ListNode(0, head);
    //predecessor = the last node before the sublist of duplicates
    ListNode pred = sentinel;
        
    while (head != null) {
    //skip all duplicates at the beginning of any duplicates sublist
        if (head.next != null && head.val == head.next.val) {
            //move till the end of duplicates sublist
            while (head.next != null && head.val == head.next.val) {
                head = head.next;    
            }
            //skip the entire duplicate sublist and make predecessor to point to the node after the sublist
            pred.next = head.next;   
        }
        else { //otherwise, move predecessor
            pred = pred.next;    
        }
      
        //move on
        head = head.next;    
    }  
    return sentinel.next;
}
