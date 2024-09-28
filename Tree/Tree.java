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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #386 - Lexicographical Numbers
// 0    -- (Virtual Starting Point)
// ↓                                  ↘                                                               ↘
// 1                                   2                            ...                                9    -- (main layer)
// ↓             ↘                     ↓        ↘                                                      ↓        ↘
// 10             11        ...        20        21        ...        ...        ...        ...        90        91        ...
// ↓   ↘          ↓                    ↓         ↓                                                     ↓         ↓
// 100  101  ...  110  ...             200  ...  210  ...                                              900  ...  910  ...
// ↓
// ...
public List<Integer> lexicalOrder(int n) {
    List<Integer> result = new ArrayList<>();
    for (int i = 1; i < 10; i++) { // loop through the main layer, starting with 1
        dfs(i, n, result);
    }
    return result;
}

private void dfs(int current, int n, List<Integer> result) {
    if (current > n) {
        return;
    }

    result.add(current); // add the number one by one, instead of layer by layer
    for (int i = 0; i < 10; ++i) { // include the starting point of the next layer, so start from 0
        if (10 * current + i > n) {
            return;
        }
        dfs(10 * current + i, n, result);
    }
}
