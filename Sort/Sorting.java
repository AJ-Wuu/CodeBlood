//https://www.geeksforgeeks.org/sorting-algorithms/

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#21 - Merge Two Sorted Lists
//Approach A: Iterative
//A new ListNode for convenient storage?
// -> Sometimes, in industrial projects, it's not trivial to create a ListNode which might require many resource allocations or inaccessible dependencies.
// -> So ideally, we'd pick up either the head of l1 or l2 as the head rather than creating a new one, which makes the initialization step tedious (TBH).
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode head = new ListNode(0);
    ListNode handler = head;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            handler.next = l1;
            l1 = l1.next;
        }
        else {
            handler.next = l2;
            l2 = l2.next;
        }
        handler = handler.next;
    }
    
    if (l1 != null) {
        handler.next = l1;
    }
    else if (l2 != null) {
        handler.next = l2;
    }

    return head.next;
}

//Approach B: Recursive
//Is this a practical approach?
// -> In real life, the length of a linked list could be much longer than we expected, in which case the recursive approach is likely to introduce a stack overflow.
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) {
        return l2;
    }
    if (l2 == null) {
        return l1;
    }
        
    if (l1.val < l2.val) {
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
    }
    else {
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

