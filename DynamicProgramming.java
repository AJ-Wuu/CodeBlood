/**
 * Recursion VS Memoization:
 * Recursion is for solving the subproblem / optimal substructure property.
 * Memoization is for those overlapping substructures.
 * Sometimes memoization takes up too much space, and you can save memory using recursion.
 *
 * How to design a DP?
 * The core idea is to first design an intuitive recursive function that solves the problem, and then applied memoization to the recursive function.
 * For many problems, recursive function + memoization have nearly the same "theoretical" time complexity as DP tabularization.
 * If needed, we can convert the recursive solution to DP by using the recursive function parameters as DP index.
 * 
 * To identify problems that can be solved by DP or recursion + memoization, get familiar with some frequently seen DP problems, 
 *     such as Fibonacci Sequence, 0-1 Knapsack and its many variants, Unbounded Knapsack (#518), 
 *     Longest Common Sub-string (#718) or Longest Common Subsequence (#1143), Pseudopolynomial MultiWay number partitioning, etc.
 * 
 * There are still a lot of problems that are still unable to be solved by DP or Recursion + Memoization even in pseudopolynomial time,
 *     for example, Strong NP-completeness problems.
 */

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#10 - Regular Expression Matching
//Similar to Longest Common Sub-string (#718) or Longest Common Subsequence (#1143) as they all match string from left to right.
//Time 100%, Space 64% 
enum Result {
    TRUE, FALSE
}

class Solution {
    Result[][] memo;

    public boolean isMatch(String text, String pattern) {
        memo = new Result[text.length() + 1][pattern.length() + 1];
        return dp(0, 0, text, pattern);
    }

    public boolean dp(int i, int j, String text, String pattern) {
        if (memo[i][j] != null) {
            return memo[i][j] == Result.TRUE;
        }
        boolean ans;
        if (j == pattern.length()){
            ans = i == text.length();
        } else{
            boolean first_match = (i < text.length() &&
                                   (pattern.charAt(j) == text.charAt(i) ||
                                    pattern.charAt(j) == '.'));

            if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                ans = (dp(i, j+2, text, pattern) ||
                       first_match && dp(i+1, j, text, pattern));
            } else {
                ans = first_match && dp(i+1, j+1, text, pattern);
            }
        }
        memo[i][j] = ans ? Result.TRUE : Result.FALSE;
        return ans;
    }
}

//Time 86%, Space 81%
//Idea: 1. If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
3, If p.charAt(j) == '*': 
   here are two sub conditions:
               1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
               2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
                              dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a 
                           or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
                           or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
        return false;
    }
    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
    dp[0][0] = true;
    for (int i = 0; i < p.length(); i++) {
        if (p.charAt(i) == '*' && dp[0][i-1]) {
            dp[0][i+1] = true;
        }
    }
    for (int i = 0 ; i < s.length(); i++) {
        for (int j = 0; j < p.length(); j++) {
            if (p.charAt(j) == '.') {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == s.charAt(i)) {
                dp[i+1][j+1] = dp[i][j];
            }
            if (p.charAt(j) == '*') {
                if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                    dp[i+1][j+1] = dp[i+1][j-1];
                } else {
                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                }
            }
        }
    }
    return dp[s.length()][p.length()];
    }
