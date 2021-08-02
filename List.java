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
        for (int i=1; i<k; i++) { //Eg. 12345 with k = 4
            ListNode next = tail.next.next; //3 -- 4
            tail.next.next = prev.next; //12121 -- 
            prev.next = tail.next; //21212 -- 
            tail.next = next; //21345 -- 
        }
        prev = tail;
        tail = tail.next;
    }
    return init.next;
}
