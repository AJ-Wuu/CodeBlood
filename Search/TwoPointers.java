/**
 * @author AJWuu
 */

package pointers;

class ListNode {
	int val;
	ListNode next;
	ListNode() {}
	ListNode(int val) { this.val = val; }
	ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class TwoPointers {

	public static ListNode deleteDuplicates(ListNode head) {
		ListNode sentinel = new ListNode(0, head); //create virtual sentinel
		ListNode pred = sentinel; //the last node before the sublist of duplicates
		while (head != null) {
			if (head.next != null && head.val == head.next.val) {
				//if it's a beginning of duplicates sublist， skip all duplicates
				while (head.next != null && head.val == head.next.val) {
					head = head.next;    
				}
				pred.next = head.next;
			}
			else { //move predecessor
				pred = head;
			}
			head = head.next;
		}  
		return sentinel.next;
	}

	public static void printer(ListNode head) {
		System.out.print(head.val + " ");
		while (head.next != null) {
			head = head.next;
			System.out.print(head.val + " ");
		}
	}

	public static void main(String[] args) {
		ListNode head = new ListNode(0,null);
		int[] arr = new int[] {0,0,1,2,3,3,4,4,5,5,5,6,7,7,8};
		ListNode temp = head;
		for (int i=1; i<arr.length; i++) {
			ListNode newNode = new ListNode(arr[i], null);
			temp.next = newNode;
			temp = newNode;
		}
		printer(deleteDuplicates(head));
	}

}
