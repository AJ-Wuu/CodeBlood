/**
 * @author AJWuu
 */

package dp;

public class EditDistance {
	
	/*
   * #72 - Edit Distance
	 * Think as Induction
	 * (cost[i][j] is the cost of changing the first i characters of word1 into the first j characters of word2)
	 * Base Case: cost[k][0] = cost[0][k] = k
	 * Induction:
	 *     if (word1[i] == word2[j]), then cost[i][j] = cost[i-1][j-1]
	 *     else, cost[i][j] = 1 + min(cost[i][j-1], cost[i-1][j], cost[i-1][j-1]),
	 *         where insert -> cost[i][j-1]; delete -> cost[i-1][j]; replace -> cost[i-1][j-1]
	 */

	public static int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[][] cost = new int[m+1][n+1]; //need one extra space each row / column to count the cost
        for (int i=0; i<=m; i++) {
            cost[i][0] = i;
        }
        for (int i=1; i<=n; i++) {
            cost[0][i] = i;
        }
        
        for (int i=1; i<=m; i++) {
            for (int j=1; j<=n; j++) {
                if (word1.charAt(i-1) == word2.charAt(j-1)) {
                    cost[i][j] = cost[i-1][j-1];
                }
                else {
                    int a = cost[i-1][j-1]; //replace
                    int b = cost[i-1][j]; //delete
                    int c = cost[i][j-1]; //insert
                    cost[i][j] = a < b ? (a < c ? a : c) : (b < c ? b : c);
                    cost[i][j]++;
                }
            }
        }
        return cost[m][n];
    }
	
	public static void main(String[] args) {
		String word1 = "horse";
		String word2 = "ros";
		System.out.println(minDistance(word1, word2));
	}

}
