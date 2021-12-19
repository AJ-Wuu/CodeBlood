/**
 * @author AJWuu
 */

package redBlackTree;

class Node {
	int data;
	Node leftChild;
	Node rightChild;
	Node parent;
	char color;

	public Node(int data, char color) {
		this.data = data;
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
		this.color = color;
	}
}

public class RedBlackTree{

	static Node root;

	public static Node newNode(int data) {
		Node node = new Node(data,'R');
		node.leftChild = new Node(-1,'B');
		node.rightChild = new Node(-1,'B');
		return node;
	}

	public static void insert(int data) {
		System.out.println("Inserting data : " + data);
		Node node = newNode(data);
		if (root == null) {
			root = node;
			root.color = 'B';
			return;
		}

		Node temp = root;
		while (temp != null) {
			if (temp.data > data) {
				if (temp.leftChild.data == -1) {
					temp.leftChild = node;
					node.parent = temp;
					insertBalance(node);
					return;
				}
				temp = temp.leftChild;
				continue;
			}
			if (temp.data < data) {
				if (temp.rightChild.data == -1) {
					temp.rightChild = node;
					node.parent = temp;
					insertBalance(node);
					return;
				}
				temp = temp.rightChild;
			}
		}
	}

	public static void delete(int data) {
		System.out.println("Remove data : " + data);
		if (root == null) {
			return;
		}

		//Search for the given element
		Node temp = root;
		while (temp.data != -1) {
			if (temp.data == data) {
				break;
			}
			if (temp.data > data) {
				temp = temp.leftChild;
				continue;
			}
			if (temp.data < data) {
				temp = temp.rightChild;	
				continue;
			}
		}
		
		//Not found
		if (temp.data == -1) {
			return;
		}
		
		//Find the next greater number than the given data
		Node next = findNext(temp);
		
		//Swap the data values of given node and next greater number
		int t = temp.data;
		temp.data = next.data;
		next.data = t;
		
		//Delete the next node.
		Node parent = next.parent;
		if (parent == null) {
			if (next.leftChild.data == -1) {
				root = null;
			}
			else {
				root = next.leftChild;
				next.parent = null;
				root.color = 'B';
			}
			return;
		}

		if (parent.rightChild == next) {
			parent.rightChild = next.leftChild;
		}
		else {
			parent.leftChild = next.leftChild;
		}
		next.leftChild.parent = parent;
		String color = Character.toString(next.leftChild.color) +  Character.toString(next.color);
		deleteBalance(next.leftChild, color);
	}

	private static void deleteBalance(Node node, String color) {
		System.out.println("Balancing Node : " + node.data + " Color : " + color);

		//node is Red-Black.
		if (node.parent == null || color.equals("BR") || color.equals("RB")) {
			node.color = 'B';
			return;
		}

		Node parent = node.parent;

		//get sibling of the given node
		Node sibling = null;
		if (parent.leftChild == node) {
			sibling = parent.rightChild;
		}
		else {
			sibling = parent.leftChild;
		}


		Node sibleft = sibling.leftChild;
		Node sibright = sibling.rightChild;

		//sibling's leftChild and rightChild all are black
		if (sibling.color == 'B' && sibleft.color == 'B' && sibright.color == 'B') {
			sibling.color = 'R';
			node.color = 'B';
			String c = Character.toString(parent.color) + Character.toString('B');
			deleteBalance(parent,c);
			return;
		}

		//sibling is red
		if (sibling.color == 'R') {
			if (parent.rightChild == sibling) {
				leftRotate(sibling);
			}
			else {
				rightRotate(sibling);
			}
			deleteBalance(node,color);
			return;
		}

		//sibling is red and one of its children is red
		if (parent.leftChild == sibling) {
			if (sibleft.color == 'R') {
				rightRotate(sibling);
				sibleft.color = 'B';
			}
			else {
				leftRotate(sibright);
				rightRotate(sibright);
				sibright.leftChild.color = 'B';
			}
			return;
		}
		else {
			if (sibright.color == 'R') {
				leftRotate(sibling);
				sibright.color = 'B';
			}
			else {
				rightRotate(sibleft);
				leftRotate(sibleft);
				sibleft.rightChild.color= 'B';
			}
			return;
		}
	}

	private static Node findNext(Node node){
		Node next = node.rightChild;
		if (next.data == -1) {
			return node;
		}
		while (next.leftChild.data != -1) {
			next = next.leftChild;
		}
		return next;
	}

	public static void insertBalance(Node node){
		System.out.println("Balancing Node : " + node.data);

		//root
		if (node.parent == null) {
			root = node;
			root.color = 'B';
			return;
		}

		//node's parent is black
		if (node.parent.color == 'B') {
			return;
		}	

		//get the node's parent's sibling node
		Node sibling = null;
		if (node.parent.parent.leftChild == node.parent) {
			sibling = node.parent.parent.rightChild;
		}
		else {
			sibling = node.parent.parent.leftChild;
		}

		if (sibling.color == 'R') {
			node.parent.color = 'B';
			sibling.color = 'B';
			node.parent.parent.color = 'R';
			insertBalance(node.parent.parent);
			return;
		}
		else {
			if (node.parent.leftChild == node && node.parent.parent.leftChild == node.parent) {
				rightRotate(node.parent);			
				insertBalance(node.parent);
				return;
			}
			if (node.parent.rightChild == node && node.parent.parent.rightChild == node.parent) {
				leftRotate(node.parent);
				insertBalance(node.parent);
				return;
			}
			if (node.parent.rightChild == node && node.parent.parent.leftChild == node.parent) {
				leftRotate(node);
				rightRotate(node);
				insertBalance(node);
				return;
			}
			if (node.parent.leftChild == node && node.parent.parent.rightChild == node.parent) {
				rightRotate(node);
				leftRotate(node);
				insertBalance(node);
				return;
			}
		}
	}

	private static void leftRotate(Node node){
		System.out.println("Rotating leftChild  : "  + node.data);
		Node parent = node.parent;
		Node leftChild = node.leftChild;
		node.leftChild = parent;
		parent.rightChild = leftChild;
		if (leftChild != null) {
			leftChild.parent = parent;
		}
		char c = parent.color;
		parent.color = node.color;
		node.color = c;
		Node grandParent = parent.parent;
		parent.parent = node;
		node.parent = grandParent;

		if (grandParent == null) {
			root = node;
			return;
		}
		else {
			if (grandParent.leftChild == parent) {
				grandParent.leftChild = node;
			}	
			else {
				grandParent.rightChild = node;
			}
		}
	}

	private static void rightRotate(Node node) {
		System.out.println("Rotating rightChild : " + node.data);
		Node parent = node.parent;
		Node rightChild = node.rightChild;
		node.rightChild = parent;
		parent.leftChild = rightChild;
		if (rightChild != null) {
			rightChild.parent = parent;
		}
		char c = parent.color;
		parent.color = node.color;
		node.color = c;
		Node grandParent = parent.parent;
		parent.parent = node;
		node.parent = grandParent;

		if (grandParent == null) {
			root = node;
			return;
		}
		else {
			if (grandParent.leftChild == parent) {
				grandParent.leftChild = node;
			}
			else {
				grandParent.rightChild = node;
			}
		}	
	}

	private static void preOrderTraversal(Node node) {
		if (node.data == -1) {
			return;
		}
		System.out.print(node.data + "-" + node.color + " ");
		preOrderTraversal(node.leftChild);
		preOrderTraversal(node.rightChild);
	}

	public static void printTree() {
		if (root == null) {
			System.out.println("Empty Tree");
			return;
		}

		System.out.print("Tree's PreOrder Traversal : ");
		preOrderTraversal(root);
		System.out.println();
	}

	public static void main(String [] args) {
		int[] array = new int[] {3, 7, 6, 5, 4, 2};
		for (int i=0; i<array.length; i++) {
			insert(array[i]);
		}
		printTree();
		delete(6);
		printTree();
	}
  
}
