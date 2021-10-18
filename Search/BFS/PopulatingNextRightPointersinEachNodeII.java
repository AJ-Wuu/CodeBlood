package practice;

class Node {
	public int val;
	public Node left;
	public Node right;
	public Node next;

	public Node() {}

	public Node(int _val) {
		val = _val;
	}

	public Node(int _val, Node _left, Node _right, Node _next) {
		val = _val;
		left = _left;
		right = _right;
		next = _next;
	}
}

public class Test {

	public static Node getNext(Node root) {
		if (root == null) {
			return null;
		}

		if (root.left != null) {
			return root.left;
		}
		else if (root.right != null) {
			return root.right;
		}
		else if (root.next != null) {
				return getNext(root.next);
			}
		else {
			return null;
		}
	}

	public static Node connect(Node root) {
		if (root == null) {
			return null;
		}

		if (root.left != null && root.right != null) {
			root.left.next = root.right;
			root.right.next = getNext(root.next);
		}
		else if (root.left != null) {
			root.left.next = getNext(root.next);
		}
		else if (root.right != null) {
			root.right.next = getNext(root.next);
		}

		//Do right first, as to make sure if root.next is null or not
		connect(root.right);
		connect(root.left);
		return root;
	}

	private static void printHelper(Node root) {
		if (root == null) {
			System.out.print("# ");
		}
		else {
			System.out.print(root.val + " ");
			printHelper(root.next);
		}
	}
	
	public static void print(Node root, boolean flag) {
		if (flag) {
			printHelper(root);
		}
		if (root.left != null) {
			print(root.left, true);
		}
		else if (root.right != null) {
			print(root.right, true);
		}
		else {
			if (root.next != null) {
				print(root.next, false);
			}
		}
	}

	public static void main(String[] args) {
		Node root = new Node(2);
		root.left = new Node(1);
		root.right = new Node(3);
		root.left.left = new Node(0);
		root.left.right = new Node(7);
		root.right.left = new Node(9);
		root.right.right = new Node(1);
		root.left.left.left = new Node(2);
		root.left.right.left = new Node(1);
		root.left.right.right = new Node(0);
		root.right.right.left = new Node(8);
		root.right.right.right = new Node(8);
		root.left.right.right.left = new Node(7);
		print(connect(root), true);
	}

}
