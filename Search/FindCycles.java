/**
 * @author AJWuu
 */

package findCycles;

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) {
		val = x;
		next = null;
	}
}

public class FastSlowPointers {

	/*
	 * Logic:
	 *        x            y
	 * p1 --------- p2 --------- p3 --------- p4
	 *               \------------------------/
	 * initial state: head, fast and slow all at p1
	 * round 1: v(fast) = 2*v(slow), so l(fast) = 2*l(slow)
	 *          both pointers move in the cycle and meet in the cycle (p3)
	 * fast & slow met: slow has not covered one round yet, and fast has covered more than one round
	 *                  l(slow) = x+y -> l(fast) = 2*(x+y)
	 *                  l(fast) - l(slow) = x+y = n*cycle -> fast covers n cycles more than slow
	 *                      -> it could be more than one, as fast may "jump over" slow
	 * middle state: head at p1, fast and slow at p3
	 * round 2: we are certain if slow moves x, then slow will be back to p2
	 *          we also realize that the distance between head and p2 is x
	 *          so, if slow and head move together in the same speed, they should meet at p2
	 *          NOTICE that this is why we cannot have any other v(fast) = k*v(slow), as we don't want head to come into the cycle, but right at the beginning
	 * head & slow met: exactly at p2, which is the beginning of the cycle
	 * final state: head and slow at p2, fast at p3
	 */

	public static ListNode detectCycle(ListNode head) {
		ListNode fast = head, slow = head;
		while (fast != null && fast.next != null) {
			fast = fast.next.next;
			slow = slow.next;
			if (fast == slow) {
				break;
			}
		}

		if (fast == null || fast.next == null) { //check if there is actually a cycle
			return null;
		}
		while (head != slow) {
			head = head.next;
			slow = slow.next;
		}
		return head;
	}

	public static void main(String[] args) {
		ListNode head = new ListNode(3);
		head.next = new ListNode(2);
		head.next.next = new ListNode(1);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = head.next;
		System.out.println("The starting point of the cycle is: " + detectCycle(head).val);
	}

}
