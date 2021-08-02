//As seen in performance comparison, ArrayList is better for storing and accessing data. LinkedList is better for manipulating data.

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
        for (int i=1; i<k; i++) { //21345 -> 32145 -> 43215
            ListNode next = tail.next.next;
            tail.next.next = prev.next;
            prev.next = tail.next;
            tail.next = next;
        }
        prev = tail;
        tail = tail.next;
    }
    return init.next;
}
