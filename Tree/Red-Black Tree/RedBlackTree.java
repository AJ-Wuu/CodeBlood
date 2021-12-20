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
 * 	   2.2. if node has one child, replace with child (with the child's color)
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
 * Double Black: when a black node is deleted and replaced by a black child, the child is marked as double black 
 * 	         to solve this, we will push the double black property up recursively towards the root
 *               eg. (from top level to bottom)
 *                   initial tree: 20(B) -- 10(B), 30(B) -- 2(B), 14(B), 22(B), 45(B) -- 1(B), 5(B), 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 *                   remove 5: 20(B) -- 10(B), 30(B) -- 2(B), 14(B), 22(B), 45(B) -- 1(B), NULL【DB】, 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 *                   move up:  20(B) -- 10(B), 30(B) -- 2【DB】, 14(B), 22(B), 45(B) -- 1(R), NULL(B), 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 *                   move up:  20(B) -- 10【DB】, 30(B) -- 2(B), 14(R), 22(B), 45(B) -- 1(R), NULL(B), 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 *                   move up:  20【DB】 -- 10(B), 30(R) -- 2(B), 14(R), 22(B), 45(B) -- 1(R), NULL(B), 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 *                   resolve the root: 20(B) -- 10(B), 30(R) -- 2(B), 14(R), 22(B), 45(B) -- 1(R), NULL(B), 11(B), 16(B), 21(B), 24(B), 40(B), 58(B)
 * 
 * Different cases of insertion is determined by: 1. parent's color; 2. uncle's color
 * Different cases of deletion is determined by: 1. sibling's color; 2. sibling's children's color
 * 
 * Standard BST insert algorithm is sufficient (ie. there is no need to resolve any red-black tree property violations) for: 
 * 	inserting a new red node -- when the sibling's position is non-null
 * 	removing a node -- when removing a red leaf or
 *                              removing a black node with two children (copy one child value up to the parent, then delete the child -> no color change)
 *
 * The sibling of a null child reference in a red-black tree is either another null child reference or a red node.
 * The subtree of the root of a red-black tree may not be a red-black tree (the root of the subtree might be red).
 */

class Node {
	int data;
	Node parent;
	Node left;
	Node right;
	int color; //red == 1, black == 0
}

public class RedBlackTree {
	private Node root;
	private Node TNULL; //Null Node
	
	public Node getRoot(){
		return this.root;
	}

	private void preOrderHelper(Node node) {
		if (node != TNULL) {
			System.out.print(node.data + " ");
			preOrderHelper(node.left);
			preOrderHelper(node.right);
		} 
	}

	private void inOrderHelper(Node node) {
		if (node != TNULL) {
			inOrderHelper(node.left);
			System.out.print(node.data + " ");
			inOrderHelper(node.right);
		} 
	}

	private void postOrderHelper(Node node) {
		if (node != TNULL) {
			postOrderHelper(node.left);
			postOrderHelper(node.right);
			System.out.print(node.data + " ");
		} 
	}

	private Node searchTreeHelper(Node node, int key) {
		if (node == TNULL || key == node.data) {
			return node;
		}

		if (key < node.data) {
			return searchTreeHelper(node.left, key);
		} 
		return searchTreeHelper(node.right, key);
	}

	private void rbTransplant(Node u, Node v){
		if (u.parent == null) {
			root = v;
		}
		else if (u == u.parent.left){
			u.parent.left = v;
		}
		else {
			u.parent.right = v;
		}
		v.parent = u.parent;
	}

	private void fixDelete(Node x) {
		Node s;
		while (x != root && x.color == 0) {
			if (x == x.parent.left) {
				s = x.parent.right;
				if (s.color == 1) {
					// case 3.1: x's sibling S is red
					s.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);
					s = x.parent.right;
				}

				if (s.left.color == 0 && s.right.color == 0) {
					// case 3.2: x's sibling S is black, and both of S's children are black
					s.color = 1;
					x = x.parent;
				}
				else {
					if (s.right.color == 0) {
						// case 3.3: x's sibling S is black, S's left child is red, and S's right child is black
						s.left.color = 0;
						s.color = 1;
						rightRotate(s);
						s = x.parent.right;
					} 

					// case 3.4: x's sibling S is black, and S's right child is red
					s.color = x.parent.color;
					x.parent.color = 0;
					s.right.color = 0;
					leftRotate(x.parent);
					x = root;
				}
			}
			else {
				s = x.parent.left;
				if (s.color == 1) {
					// case 3.1
					s.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					s = x.parent.left;
				}

				if (s.right.color == 0 && s.right.color == 0) {
					// case 3.2
					s.color = 1;
					x = x.parent;
				}
				else {
					if (s.left.color == 0) {
						// case 3.3
						s.right.color = 0;
						s.color = 1;
						leftRotate(s);
						s = x.parent.left;
					} 

					// case 3.4
					s.color = x.parent.color;
					x.parent.color = 0;
					s.left.color = 0;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = 0;
	}
	
	private void deleteNodeHelper(Node node, int key) {
		//search for the node containing key
		Node z = TNULL;
		Node x, y;
		while (node != TNULL){
			if (node.data == key) {
				z = node;
			}

			if (node.data <= key) {
				node = node.right;
			}
			else {
				node = node.left;
			}
		}

		if (z == TNULL) {
			System.out.println("Couldn't find key in the tree");
			return;
		} 

		y = z;
		int yOriginalColor = y.color;
		if (z.left == TNULL) {
			x = z.right;
			rbTransplant(z, z.right);
		}
		else if (z.right == TNULL) {
			x = z.left;
			rbTransplant(z, z.left);
		}
		else {
			y = minimum(z.right);
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			}
			else {
				rbTransplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}

			rbTransplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		if (yOriginalColor == 0){
			fixDelete(x);
		}
	}

	private void fixInsert(Node k){
		Node u;
		while (k.parent.color == 1) {
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // uncle
				if (u.color == 1) {
					// case 3.1: P is red and U is red, too
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				}
				else {
					// case 3.2: P is red and U is black (or NULL)
					if (k == k.parent.left) {
						// case 3.2.2: P is right child of G and K is left child of P
						k = k.parent;
						rightRotate(k);
					}
					// case 3.2.1: P is right child of G and K is right child of P
					k.parent.color = 0;
					k.parent.parent.color = 1;
					leftRotate(k.parent.parent);
				}
			}
			else {
				u = k.parent.parent.right; // uncle

				if (u.color == 1) {
					// mirror case 3.1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				}
				else {
					if (k == k.parent.right) {
						// mirror case 3.2.2: P is left child of G and K is right child of P
						k = k.parent;
						leftRotate(k);
					}
					// mirror case 3.2.1: P is left child of G and K is left child of P
					k.parent.color = 0;
					k.parent.parent.color = 1;
					rightRotate(k.parent.parent);
				}
			}
			if (k == root) {
				break;
			}
		}
		root.color = 0;
	}

	private void printHelper(Node root, String indent, boolean last) {
		if (root != TNULL) {
			System.out.print(indent);
			if (last) {
				System.out.print("R----");
				indent += "     ";
			}
			else {
				System.out.print("L----");
				indent += "|    ";
			}

			String sColor = root.color == 1?"RED":"BLACK";
			System.out.println(root.data + "(" + sColor + ")");
			printHelper(root.left, indent, false);
			printHelper(root.right, indent, true);
		}
	}

	public RedBlackTree() {
		TNULL = new Node();
		TNULL.color = 0;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
	}

	public void preorder() {
		preOrderHelper(this.root);
	}

	public void inorder() {
		inOrderHelper(this.root);
	}

	public void postorder() {
		postOrderHelper(this.root);
	}

	public Node searchTree(int k) {
		return searchTreeHelper(this.root, k);
	}

	public Node minimum(Node node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}

	public Node maximum(Node node) {
		while (node.right != TNULL) {
			node = node.right;
		}
		return node;
	}

	public Node successor(Node x) {
		// the right subtree is not null, the successor is the leftmost node in the right subtree
		if (x.right != TNULL) {
			return minimum(x.right);
		}

		// the successor is the lowest ancestor of x whose left child is also an ancestor of x.
		Node y = x.parent;
		while (y != TNULL && x == y.right) {
			x = y;
			y = y.parent;
		}
		return y;
	}

	public Node predecessor(Node x) {
		// the left subtree is not null, the predecessor is the rightmost node in the left subtree
		if (x.left != TNULL) {
			return maximum(x.left);
		}

		Node y = x.parent;
		while (y != TNULL && x == y.left) {
			x = y;
			y = y.parent;
		}

		return y;
	}

	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
			y.left.parent = x;
		}
		
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		}
		else if (x == x.parent.left) {
			x.parent.left = y;
		}
		else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}

	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
			y.right.parent = x;
		}
		
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		}
		else if (x == x.parent.right) {
			x.parent.right = y;
		}
		else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}

	public void insert(int key) {
		// Ordinary Binary Search Insertion
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; // new node must be red

		Node y = null;
		Node x = this.root;

		while (x != TNULL) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			}
			else {
				x = x.right;
			}
		}

		// y is parent of x
		node.parent = y;
		if (y == null) {
			root = node;
		}
		else if (node.data < y.data) {
			y.left = node;
		}
		else {
			y.right = node;
		}

		// if new node is a root node, simply return
		if (node.parent == null){
			node.color = 0;
			return;
		}

		// if the grandparent is null, simply return
		if (node.parent.parent == null) {
			return;
		}

		fixInsert(node);
	}

	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
	}

	public void prettyPrint() {
		if (this.root == TNULL) {
			System.out.println("Tree is empty!");
		}
		printHelper(this.root, "", true);
	}

	public static void main(String [] args){
		RedBlackTree bst = new RedBlackTree();
		int[] array = new int[] {62,48,51,13,99,23,40,78,14,35,74,79,77,24,43,91,1,84,10,36,38};
		for (int i=0; i<array.length; i++) {
			bst.insert(array[i]);
		}
		bst.prettyPrint();
		for (int i=0; i<array.length; i++) {
			bst.deleteNode(array[i]);
		}
		bst.prettyPrint();
	}
	
}
