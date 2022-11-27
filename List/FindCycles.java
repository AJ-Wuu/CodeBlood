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
	 *  {     x     }{           y            }
	 * p1 --------- p2 --------- p3 --------- p4
	 *               \------------------------/
	 * initial state: head, fast and slow all at p1
	 * round 1: v(fast) = 2*v(slow), so l(fast) = 2*l(slow)
	 *          both pointers move in the cycle and meet in the cycle (p4)
	 * fast & slow met: slow has not covered one round yet, and fast has covered more than one round
	 *                  l(slow) = x+y -> l(fast) = 2*(x+y)
	 *                  l(fast) - l(slow) = x+y = n*cycle -> fast covers n cycles more than slow
	 *                                                    -> it could be more than one, as fast may "jump over" slow
	 * middle state: head at p1, fast and slow at p4
	 * round 2: we are certain if slow moves x, then slow will be back to p2 (see from fast, p1 -- x -- p2 -- y -- p4 -- ? -- p2 -- y -- p4 -> p4 to p2 = x)
	 *          we also realize that the distance between head and p2 is x
	 *          so, if slow and head move together in the same speed, they should meet at p2
	 * head & slow met: exactly at p2, which is the beginning of the cycle
	 * final state: head and slow at p2, fast at p4
	 */

	//#142 - Linked List Cycle II
	public static ListNode detectCycle(ListNode head) {
		ListNode fast = head, slow = head;
		while (fast != null && fast.next != null) {
			fast = fast.next.next.next;
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
	
	//#287 - Find the Duplicate Number
	//The Key for this Array:
	//Forming the cycle by nums[nums[...]]
	//Also called Floyd's Tortoise and Hare
	public static int findDuplicate(int[] nums) {
		int fast = 0, slow = 0;
		while (fast < nums.length) {
			fast = nums[nums[fast]];
			slow = nums[slow];
			if (fast == slow) {
				break;
			}
		}
		
		int index = 0;
		while (nums[index] != nums[slow]) {
			index = nums[index];
			slow = nums[slow];
		}
		return nums[index];
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
