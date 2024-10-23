// #2641 - Cousins in Binary Tree II
// key: replaceValue = levelSum - siblingSum

/**
 * @author AJWuu
 */

package tree;

import java.util.ArrayList;
import java.util.List;

class TreeNode {
	int val;
	TreeNode left;
	TreeNode right;
	TreeNode() {}
	TreeNode(int val) { this.val = val; }
	TreeNode(int val, TreeNode left, TreeNode right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}

public class LevelOrderTraversal {

	public static List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		computeLevelOrder(ans, root, 0);
		return ans;
	}

	//#102 - Binary Tree Level Order Traversal
	private static void computeLevelOrder(List<List<Integer>> ans, TreeNode curr, int height) {
		if (curr == null) {
			return ;
		}
		if (ans.size() == height) {
			ans.add(new ArrayList<Integer>());
		}
		ans.get(height).add(curr.val);
		computeLevelOrder(ans, curr.left, height+1);
		computeLevelOrder(ans, curr.right, height+1);
	}

	//#107 - Binary Tree Level Order Traversal II
	private static void computeLevelOrderBottom(List<List<Integer>> ans, TreeNode curr, int height) {
		if (curr == null) {
			return;
		}
		if (ans.size() == height) {
			ans.add(0, new ArrayList<Integer>()); //void java.util.List.add(int index, List<Integer> element)
		}
		ans.get(ans.size()-height-1).add(curr.val);
		computeLevelOrderBottom(ans, curr.left, height+1);
		computeLevelOrderBottom(ans, curr.right, height+1);
	}

	//#103 - Binary Tree Zigzag Level Order Traversal
	private static void computeZigZag(List<List<Integer>> ans, TreeNode curr, int height) {
		if (curr == null) {
			return;
		}
		if (ans.size() == height) {
			ans.add(new ArrayList<Integer>());
		}
		if (height % 2 == 0) {
			ans.get(height).add(curr.val);
		}
		else {
			ans.get(height).add(0, curr.val);
		}
		computeZigZag(ans, curr.left, height+1);
		computeZigZag(ans, curr.right, height+1);
	}

	public static void main(String[] args) {
		TreeNode root = new TreeNode(2);
		root.left = new TreeNode(9, null, null);
		root.right = new TreeNode(21);
		root.right.left = new TreeNode(12, null, null);
		root.right.right = new TreeNode(32, null, null);
		List<List<Integer>> traversal = levelOrder(root);
		for (int i=0; i<traversal.size(); i++) {
			for (int j=0; j<traversal.get(i).size(); j++) {
				System.out.print(traversal.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}

}
