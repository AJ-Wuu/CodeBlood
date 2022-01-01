/**
 * @author AJWuu
 */

package practice;

class UnionFind {

	/*
	 * Skeleton:
	 * Initialize with parent[i] = i
	 * function find(x):
	 * 	if (parent[x] != x):
	 * 		return find(parent[x])
	 * 	else:
	 * 		return x
	 * function union(x,y):
	 * 	parent[find(y)] = find(x)
	 */

	private int count = 0;
	private int[] parent, rank; //parent is the representative of its group

	public UnionFind(int n) {
		count = n;
		parent = new int[n];
		rank = new int[n];
		for (int i=0; i<n; i++) {
			parent[i] = i; //initially, every element's root is itself
		}
	}

	public int find(int p) { //path compression: two elements belong to the same group if and only if they have the same representative
		while (p != parent[p]) {
			parent[p] = parent[parent[p]];
			p = parent[p];
		}
		return p;
	}

	public void union(int p, int q) { //union by rank/size: set the root of one tree to be the child of the other
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) return;
		if (rank[rootQ] > rank[rootP]) {
			parent[rootP] = rootQ;
		}
		else {
			parent[rootQ] = rootP;
			if (rank[rootP] == rank[rootQ]) {
				rank[rootP]++;
			}
		}
		count--;
	}

	public int count() {
		return count;
	}
}

public class UnionFindAlgorithm {

	public static int findCircleNum(int[][] M) {
		int n = M.length;
		UnionFind uf = new UnionFind(n);
		for (int i=0; i<n-1; i++) {
			for (int j=i + 1; j<n; j++) {
				if (M[i][j] == 1) {
					uf.union(i, j);
				}
			}
		}
		return uf.count();
	}

	public static void main(String args[]) {
		System.out.println(findCircleNum(new int[][] {{1,1,0},{1,1,0},{0,0,1}}));
	}

}
