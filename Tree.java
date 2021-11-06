/**
 * Nodes & Height -> N <= 2^H - 1
 * 
 * Red-Black Tree: BST that stay balanced.
 * H <= 2*logN
 * rotate search, insert, delete: O(logn)
 * Properties:
 *     1. root node is black
 *     2. rednodes must have black children
 *     3. every path from root to a null childe must have the same number of black nodes (black height)
 *     4. null children are black
 *     5. every new node is red
 * Cascading Fix: multiple layers
 */
