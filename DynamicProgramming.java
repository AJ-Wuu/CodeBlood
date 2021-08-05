/**
The core idea is to first design an intuitive recursive function that solve the problem, and then applied memoization to the recursive function. For many problems, recursive function+memoization have nearly the same "theoretical" time complexity as DP tabularization. If needed, we can convert the recursive solution to DP by using the recursive function parameters as DP index. To learn memoization, please consult this LeetCode Explore Card.

Generate example test cases. In this problem, such as, "ab", "a*", ".*", etc.
Play with the test case, and match characters of text and pattern from left to right.
Observe from the test cases that this problem is similar to Longest Common Sub-string (#LC 718) or Longest Common Subsequence (#LC 1143) because these problems all match string from left to right.
Recall the recursive solution to the similar problem, (e.g., Longest Common Subsequence Recursive Solution).
Based on the recursive solution to the similar problem, try to re-design a recursive solution for this problem. For many cases, this will work.
If unable to come up with a recursive solution, then think about the DP solution to the similar problem, (e.g., Longest Common Subsequence DP Solution). Try to re-design the DP solution to solve this problem.
[Postscript]

In my (unproven) opinion, many DP problems have a recursive solution. Consult this quora discussion. https://www.quora.com/Can-every-dynamic-programming-problem-be-solved-using-recursion-with-memoization
To identify problems that can be solved by DP or recursion+memoization, get familiar with some frequently seen DP problems, such as Fibonacci Sequence, 0-1 Knapsack and its many variants, Unbounded Knapsack (#LC 518), Longest Common Sub-string (#LC 718) or Longest Common Subsequence (#LC 1143), Pseudopolynomial MultiWay number partitioning, etc. There is a good LeetCode Discussion article DP IS EASY! 5 Steps to Think Through DP Questions written by @teampark.
There are lots of problems out there that are still unable to be solved by DP or recursion+memoization even in pseudopolynomial time, for example, Strong NP-completeness problems.
 */

//
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
