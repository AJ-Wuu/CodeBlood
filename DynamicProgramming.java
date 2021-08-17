/**
 * Dynamic Programming shares the core of induction that it's guaranteed to find the solution for larger order since all the minimal order subproblems are already solved.
 * It's always finding the shortest path in a DAG.
 * 
 * Recursion VS Memoization:
 *     Recursion is for solving the subproblem / optimal substructure property.
 *     Memoization is for those overlapping substructures.
 *     Sometimes memoization takes up too much space, and you can save memory using recursion.
 *
 * Memoization:
 *     1. Identify a subproblem of the same nature
 *     2. For a problem of size n, check if i have already solved for subproblem of size k where k=f(n)
 *        2.1. If I have the answer, use that answer in the solution step
 *        2.2. Else go for the solution of subproblem (recursive, stack based algorithm, etc.)
 *
 * State: A number of necessary variables at a particular instant that are required to calculate the optimal result
 *        Basically, it is a combination of variables that will keep changing over different instants.
 *        More the number of states, more is the depth of the recursive solution and more is the memory required to cache the result of the states to avoid re-computing.
 *        Therefore, it very well makes sense to preserve the results of the state to save time.
 *        As a general rule, index is a required state in nearly all dynamic programming problems, except for shortest paths which is row and column (two indexes).
 *
 * Top-Down VS Bottom-UP:
 *     Top-Down -> start solving in a natural manner and store the solutions of the subproblems along the way
 *                 use memoization (as memo)
 *                 Advantages -> coding is easier for complex problems & don't need solutions for all subproblems
 *                 Disadvantages -> a lot of recursion, possibly out of memory, slower
 *     Bottom-Up -> start from the bottom (the 2nd, 3rd and so on), and finally calculate the higher terms on the top of these by using these values
 *                  use tabulation (like filling up a table from the start)
 *                  Advantages -> faster, might require less memory, go over all subproblem values
 *                  Disadvantages -> harder to code
 * 
 * https://leetcode.com/problems/target-sum/discuss/455024/DP-IS-EASY!-5-Steps-to-Think-Through-DP-Questions.
 * Categories -> most DP questions can be boiled down to a few categories:
 *     1. 0/1 Knapsack (and its many variants)
 *     2. Unbounded Knapsack (eg. #518)
 *     3. Shortest Path (eg: Unique Paths I/II)
 *     4. Fibonacci Sequence (eg: House Thief, Jump Game)
 *     5. Longest Common Substring / Subsequeunce (#718 / #1143)
 *    [6. Pseudopolynomial MultiWay Number Partitioning]
 *
 * How to design a DP?
 *     The core idea is to first design an intuitive recursive function that solves the problem, and then applied memoization to the recursive function.
 *     For many problems, recursive function + memoization have nearly the same "theoretical" time complexity as DP tabularization.
 *     If needed, we can convert the recursive solution to DP by using the recursive function parameters as DP index.
 *     Problems like Strong NP-completeness still cannot be solved by DP or Recursion + Memoization in pseudopolynomial time.
 *     5 Steps:
 *         1. define subproblems (might have multiple subproblems)
 *         2. guess (part of solution)
 *         3. relate subproblem solutions
 *         4. recurse & memoize || build DP table
 *         5. solve the original problem
 *
 * Solving sequence problems (words, cards, etc.):
 *     suffix (x[i:]) \
 *     prefix (x[:i]) -> Θ(|x|) -> suffix and prefix are cheaper, if possible to be used
 *     substring (x[i:j]) -> Θ(x^2)
 *
 * Running Time:
 *     1. Polynomial: polynomial in input size -> Θ(n), if number S fits in a word; O(n*lg(S)) in general (S is exponential in lg(S), not polynomial)
 *     2. Pseudopolynomial: polynomial in the problem size AND the numbers (S, Si, Vi) in input -> Θ(n*S) is pseudopolynomial
 */

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#68 - Text Justification -> See Projects/Text Justification/DP.java

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
        if (j == pattern.length()) {
            ans = i == text.length();
        }
        else {
            boolean first_match = (i < text.length() && (pattern.charAt(j) == text.charAt(i) || pattern.charAt(j) == '.'));

            if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*') {
                ans = (dp(i, j+2, text, pattern) ||
                       first_match && dp(i+1, j, text, pattern));
            }
            else {
                ans = first_match && dp(i+1, j+1, text, pattern);
            }
        }
        memo[i][j] = ans ? Result.TRUE : Result.FALSE;
        return ans;
    }
}

//Time 86%, Space 81%
//Idea: 1. If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
//      2. If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
//      3. If p.charAt(j) == '*': 
//         3.1. if (p.charAt(j-1) != s.charAt(i)): dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
//         3.2. if (p.charAt(i-1) == s.charAt(i) || p.charAt(i-1) == '.'):
//                 dp[i][j] = dp[i-1][j] //where a* counts as multiple a 
//              or dp[i][j] = dp[i][j-1] //where a* counts as single a
//              or dp[i][j] = dp[i][j-2] //where a* counts as empty
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
                }
                else {
                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                }
            }
        }
    }
    return dp[s.length()][p.length()];
}
