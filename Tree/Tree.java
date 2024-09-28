// #114 - Flatten Binary Tree to Linked List
// Reversed flattened tree = Reversed pre-order -> (right, left, root)
private TreeNode prev = null;
public void flatten(TreeNode root) {
    //Traverse the original tree in reversed pre-order
    if (root == null) {
        return ;
    }
    flatten(root.right);
    flatten(root.left);
    
    //Set each node's right pointer as the previous one and left pointer as null
    root.right = prev;
    root.left = null;
    prev = root;
}
