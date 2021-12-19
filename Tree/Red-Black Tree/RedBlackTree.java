/**
 * @author AJWuu
 */

package redBlackTree;

/*
 * Red-Black Tree: BST that stay balanced.
 * Complexity: rotate, search, insert, delete: O(logn) -- worst BST could be O(n)
 * 
 * Nodes & Height -> N <= 2^H - 1
 * Shortest BST (Red-Black Tree):
 * 	size <= 2^height - 1
 * 	height = log(size) - 1
 * Longest BST:
 * 	height <= 2*log(size/2) - 1
 * 
 * Properties:
 * 	1. every node is either red or black (every new node is red)
 * 	2. root node is black <- check this at the end of each insertion
 * 	3. null children are black -> every leaf (NIL) is black
 * 	4. red nodes can only have 0 or 2 black node children, and no red child (if a node is red, then both its children are black)
 * 	   4.1. a red node cannot only have one child (if the only child is red, violate the color rule; if the only child is black, violate the black-height rule
 * 	   4.2. a black node can only have one red child (and it's a red leaf), but cannot only have one black child
 * 	5. for EACH node, all simple paths from the node to descendant leaves contain the same number of black nodes (black height)
 * 
 * Insertion Process (1&2 are the same as for any BST):
 * 	1. follow the binary search algorithm to null child
 * 	2. add the leaf node
 * 	3. color the new node red
 * 	4. restore root-red-black properties
 * 
 * Deletion Process (1&2 are the same as for any BST):
 * 	1. follow the binary search to find the node to be deleted
 * 	2. check the cases:
 * 	   2.1. if node has no children, replace with null
 * 	   2.2. if node has one child, replace with child
 * 	   2.3. if node has two children, replace VALUE in node with replacement and delete the node with replacement key (back to 2.1 or 2.2)
 * 	3. restore root-red-black properties
 * 
 * Balance for Red-Black Trees is defined through black node heights, which completely ignore the distribution of red nodes throughout the tree.
 * 	This is OK to do because that distribution is limited by the red-black tree property/rule: no red node with red child.
 * 
 * Cascading Fix: multiple layers
 * 	eg. (from top level to bottom) 
 * 	    initial tree: 14(B) -- 7(B), 20(R) -- 1(R), 11(R), 18(B), 25(B) -- 23(R), 29(R)
 * 	    insert 27: 14(B) -- 7(B), 20(R) -- 1(R), 11(R), 18(B), 25(B) -- 23(R), 29(R) -- 27(R)
 * 	    re-color: 14(B) -- 7(B), 20(R) -- 1(R), 11(R), 18(B), 25(R) -- 23(B), 29(B) -- 27(R) -> 20(R)-25(R) has red property violation
 * 	    left rotation: 20(R) -- 14(B), 25(R) -- 7(B), 18(B), 23(B), 29(B) -- 1(R), 11(R), 27(R) -> root color violation
 * 	    re-color: 20(B) -- 14(R), 25(R) -- 7(B), 18(B), 23(B), 29(B) -- 1(R), 11(R), 27(R)
 * 
 * Double Black: when a black node is deleted and replaced by a black child, the child is marked as double black.
 * 
 * Different cases of insertion is determined by: 1. the color of the uncle; 2. the node is the left / right child of its parent
 * Different cases of deletion is determined by: 1. the color of the sibling; 2. the color of sibling's children
 * 
 * Standard BST insert algorithm is sufficient (ie. there is no need to resolve any red-black tree property violations) for: 
 * 	inserting a new red node -- when the sibling's position is non-null
 * 	removing a node -- when removing a red leaf or
 *                              removing a black node with two children (copy one child value up to the parent, then delete the child -> no color change)
 */

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
