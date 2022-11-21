// #542 - 01 Matrix
// Approach 1: BFS on 0 cells first (see: https://assets.leetcode.com/users/images/0de8711a-bdd9-4da3-a094-012abe995508_1627576450.2849615.png)
/*
 * Key: 1. in-place update
 *      2. ArrayDeque: a special kind of array that grows and allows users to add or remove an element from both sides of the queue
 *         2.1. no capacity restrictions -- grow as necessary
 *         2.2. not thread-safe, not support concurrent access by multiple threads
 *         2.3. Null elements are prohibited
 *         2.4. likely to be faster than Stack when used as a stack
 *         2.5. likely to be faster than LinkedList when used as a queue
 */
int[][] direction = new int[][]{{-1,0}, {1,0}, {0,-1}, {0,1}};
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length;
        
    Queue<int[]> queue = new ArrayDeque<int[]>();
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) {
                queue.offer(new int[]{i,j});
            }
            else {
                mat[i][j] = -1; // mark as unprocessed
            }
        }
    }
        
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int i = curr[0], j = curr[1];
        for (int k = 0; k < 4; k++) { // up, down, left, right
            int i_new = i + direction[k][0];
            int j_new = j + direction[k][1];
            if (i_new < 0 || i_new >= m || j_new < 0 || j_new >= n || mat[i_new][j_new] != -1) {
                continue;
            }
            mat[i_new][j_new] = mat[i][j] + 1;
            queue.offer(new int[]{i_new,j_new});
        }
    }
        
    return mat;
}

// Approach 2: DP (see: https://assets.leetcode.com/users/images/8da7965f-6cf4-4ac2-a307-33f661fea5ca_1627604534.7922251.png)
public int[][] updateMatrix(int[][] mat) {
    int m = mat.length, n = mat[0].length, INF = m + n; // distance is up to m + n
        
    // compute based on top and left neighbors
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (mat[i][j] == 0) {
                continue;
            }
            int top = (i >= 1) ? mat[i-1][j] : INF;
            int left = (j >= 1) ? mat[i][j-1] : INF;
            mat[i][j] = Math.min(top, left) + 1;
        }
    }
        
    // compute based on bottom and right neighbors
    for (int i = m-1; i >= 0; i--) {
        for (int j = n-1; j >= 0; j--) {
            if (mat[i][j] == 0) {
                continue;
            }
            int bottom = (i < m-1) ? mat[i+1][j] : INF;
            int right = (j < n-1) ? mat[i][j+1] : INF;
            mat[i][j] = Math.min(mat[i][j], Math.min(bottom, right) + 1);
        }
    }
        
    return mat;
}
