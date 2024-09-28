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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #440 - K-th Smallest in Lexicographical Order
public int findKthNumber(int n, int k) {
    int current = 1;
    k--; // remove the step for current number

    while (k > 0) {
        int step = countSteps(current, current + 1, n);
        if (step <= k) { // go to the next parent layer
            current++;
            k -= step;
        }
        else { // stay in the same parent layer, but go to the next child layer
            current *= 10; // remove the step for current number
            k--;
        }
    }

    return current;
}

private int countSteps(long currentLayer, long nextLayer, int n) {
    int step = 0;
    while (currentLayer <= n) {
        step += Math.min(n + 1, nextLayer) - currentLayer; // either the current layer contains n or need to go to the next layer
        currentLayer *= 10;
        nextLayer *= 10;
    }
    return step;
}
