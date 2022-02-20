/**
 * @author AJWuu
 */

package randomPointer;

import java.util.HashMap;
import java.util.Map;

class Node {
	int val;
	Node next;
	Node random;

	public Node(int val) {
		this.val = val;
		this.next = null;
		this.random = null;
	}
}

public class FastSlowPointers {

	//#138 - Copy List with Random Pointer
	public static Node copyRandomList(Node head) {
		if (head == null) {
			return null;
		}

		Map<Node, Node> map = new HashMap<Node, Node>();

		//copy all the nodes
		Node node = head;
		while (node != null) {
			map.put(node, new Node(node.val));
			node = node.next;
		}

		//assign next and random pointers
		node = head;
		while (node != null) {
			map.get(node).next = map.get(node.next);
			map.get(node).random = map.get(node.random);
			node = node.next;
		}

		return map.get(head);
	}

	private static String getVal(Node node) {
		if (node == null) {
			return "null";
		}
		else {
			return String.valueOf(node.val);
		}
	}

	public static void printNodeList(Node head) {
		while (head != null) {
			System.out.println(head.val + ": [" + getVal(head.next) + ", " + getVal(head.random) + "]");
			head = head.next;
		}
	}

	public static void main(String[] args) {
		Node head = new Node(7);
		Node node13 = new Node(13);
		Node node11 = new Node(11);
		Node node10 = new Node(10);
		Node node1 = new Node(1);
		head.next = node13;
		head.random = null;
		node13.next = node11;
		node13.random = head;
		node11.next = node10;
		node11.random = node1;
		node10.next = node1;
		node10.random = node11;
		node1.next = null;
		node1.random = head;

		System.out.println("The initial node list is:");
		printNodeList(head);
		System.out.println();
		Node newHead = copyRandomList(head);
		System.out.println("The new node list is:");
		printNodeList(newHead);
	}

}
