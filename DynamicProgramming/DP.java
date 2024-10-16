/**
 * Dynamic Programming shares the core of induction that it guarantees to find the solution for larger order since all the minimal order subproblems are already solved.
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
 *     FAST:
 *         Find the recursive solution
 *         Analyze the solution (to look for overlapping problems)
 *         Save the results for future (use n-dimensional array for caching purpose)
 *         Tweak the solution to make it more powerful by eliminating recursion overhead (known as Bottom-Up)
 *
 * Solving sequence problems (words, cards, etc.):
 *     suffix (x[i:]) \
 *     prefix (x[:i]) -> Θ(|x|) -> suffix and prefix are cheaper, if possible to be used
 *     substring (x[i:j]) -> Θ(x^2)
 *
 * Running Time:
 *     1. Polynomial: polynomial in input size -> Θ(n), if number S fits in a word; O(n*lg(S)) in general (S is exponential in lg(S), not polynomial)
 *     2. Pseudopolynomial: polynomial in the problem size AND the numbers (S, Si, Vi) in input -> Θ(n*S) is pseudopolynomial
 *     Generally, Polynomial is good, Pseudopolynomial is okay, Exponential is bad.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// Find the number of ways in which we can decode the string into upper case English alphabets. Assume 'A' stands for 1 and 'B' stands for 2 …..'Z' stands for 26.
public static int countDecoding(char[] digits, int n) {
    // base cases
    if (n == 0 || n == 1) {
        return 1;
    }
    if (digits[0] == '0') {
        return 0;
    }
    
    int count = 0;

    // the last digit is not 0
    if (digits[n - 1] > '0') {
        count = countDecoding(digits, n-1);
    }

    // if the last two digits form a number smaller than or equal to 26, then consider last two digits and recur
    if (digits[n - 2] == '1' || (digits[n - 2] == '2' && digits[n - 1] < '7')) {
        count += countDecoding(digits, n - 2);
    }
    
    return count;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #68 - Text Justification -> See Projects/Text Justification/DP.java

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #10 - Regular Expression Matching
// Similar to Longest Common Sub-string (#718) or Longest Common Subsequence (#1143) as they all match string from left to right.
// Time 100%, Space 64% 
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

// Time 86%, Space 81%
// Idea: 1. If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
//       2. If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
//       3. If p.charAt(j) == '*': 
//          3.1. if (p.charAt(j-1) != s.charAt(i)): dp[i][j] = dp[i][j-2]    // in this case, a* only counts as empty
//          3.2. if (p.charAt(i-1) == s.charAt(i) || p.charAt(i-1) == '.'):
//                  dp[i][j] = dp[i-1][j]    // where a* counts as multiple a 
//               or dp[i][j] = dp[i][j-1]    // where a* counts as single a
//               or dp[i][j] = dp[i][j-2]    // where a* counts as empty
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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #5 - Longest Palindromic Substring
// Note: A palindrome is a string which reads the same in both directions.
// Key: The substring of a palindrome without the left-most and right-most chars is a palindrome, too (Eg. "ababa" & its substring "bab")
// Conditions: A string is a palindrome when it's
//             1. a single char
//             2. "XY" where 'X' == 'Y'
//             3. string[i] == string[j] and substring at [i-1][j+1] is palindrome

// Approach 1: "DP"
// The following solution is a special one for this question, not technically a DP
// Time: O(n ^ 2)
public static String longestPalindrome(String s) {
    int low, maxLen;
    int len = s.length();
    if (len < 2) {
        return s;
    }
    for (int i=0; i<len-1; i++) {
        extendPalindrome(s, i, i);      // Conditions 1
        extendPalindrome(s, i, i+1);    // Conditions 2
    }
    return s.substring(low, low + maxLen);
}

private static void extendPalindrome(String s, int j, int k) {
    while (j>=0 && k<s.length() && s.charAt(j) == s.charAt(k)) {
    // find if the base is a palindrome (in other words, if conditions 1 OR 2 is TRUE)
    // if TRUE, then goes to the left and right, to see if the palindrome can be extended (see Key above)
        j--;
        k++;
    }
    if (maxLen < k - j - 1) {
        lo = j + 1;
        maxLen = k - j - 1;
    }
}

// Approach 2: Choose Center
// Key: All pairs (i,i) and (i,i+1), where i is from 0 to s.length()-1, can form a center ((i,i) -> odd-length substring, (i,i+1) -> even-length substring)
private int expandAroundCenter(String s, int L, int R) {
    while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
        L--;
        R++;
    }
    return R - L - 1;
}

// Approach 3: Refer to Projects/ManacherAlgorithm.java for a special solution with Time: O(n)

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #118 - Pascal's Triangle
// TakeAways: 1. define List<List<>>,
//            2. currow can be reused after adding to rows,
//            3. eg. numRows = 4, it goes as [1,3,3,1] -> [1,1,3,3,1] -> [1,4,3,3,1] -> [1,4,6,3,1] -> [1,4,6,4,1], so there is no need to access the previous row
public static List<List<Integer>> generate(int numRows) {
    List<List<Integer>> rows = new LinkedList<List<Integer>>();
    LinkedList<Integer> currrow = new LinkedList<Integer>();
    for (int i=0; i<numRows; i++) {
        currrow.add(0, 1);
        for (int j=1; j<currrow.size()-1; j++) {
            currrow.set(j, currrow.get(j) + currrow.get(j+1));
        }
        rows.add(new LinkedList<Integer>(currrow));
    }
    return rows;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #119 - Pascal's Triangle II
// Use the mathematical property of this triangle
public static List<Integer> getRow(int rowIndex) {
    List<Integer> row = new LinkedList<Integer>();
    row.add(1);
    if (rowIndex == 0) {
        return row;
    }
    int t = rowIndex, b = 1;
    long cur = 1; // this must be long, otherwise it will go out-of-bound
    for (int i=1; i<rowIndex+1; i++) {
        cur = cur * t;
        cur = cur / b;
        row.add((int)cur);
        t--;
        b++;
    }
    return row;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #22 - Generate Parentheses
// Brute Force: Generate all 2^(2n) sequences of '(' and ')' characters, then check if each one is valid.
public static void generateAll(char[] current, int pos, List<String> result) {
    if (pos == current.length) {
        if (valid(current)) {
            result.add(new String(current));
        }
    }
    else {
        current[pos] = '(';
        generateAll(current, pos+1, result);
        current[pos] = ')';
        generateAll(current, pos+1, result);
    }
}

public static boolean valid(char[] current) {
    int balance = 0;
    for (char c : current) {
        if (c == '(') {
            balance++;
        }
        else {
            balance--;
        }
        if (balance < 0) {
            return false;
        }
    }
    return (balance == 0);
}

// Back Track: Add '(' or ')' only when we know it will remain a valid sequence.
public void backtrack(List<String> list, String str, int open, int close, int max){
    if (str.length() == max*2){
        list.add(str);
        return;
    }

    if (open < max) {
        backtrack(list, str+"(", open+1, close, max);
    }
    if (close < open) {
	    backtrack(list, str+")", open, close+1, max);
    }
}

// Closure Number: For each closure number c, we know the starting and ending brackets must be at index 0 and 2*c + 1.
//                 Then, the 2*c elements between must be a valid sequence, plus the rest of the elements must be a valid sequence.
public static List<String> generateParenthesis(int n) {
    List<String> ans = new ArrayList<String>();
    if (n == 0) {
        ans.add("");
    }
    else {
        for (int c = 0; c < n; ++c) {
            for (String left : generateParenthesis(c)) {
                for (String right : generateParenthesis(n-1-c)) {
                    ans.add("(" + left + ")" + right);
                }
            }
        }
    }
    return ans;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1306 - Jump Game III
// DFS
public boolean jumpGame(int[] arr, int start, boolean[] visited) {
    if (start>=arr.length || start<0 || visited[start]) {
        return false;
    }    
    if (arr[start] == 0) {
        return true;
    }       
    visited[start] = true;       
    return jumpGame(arr, start+arr[start], visited) || jumpGame(arr, start-arr[start], visited);
    // Use "OR" to combine the two possibilities (moving left or right)
    // Do NOT use if-else in this case
}
// Optimization: instead of spending O(n) to store visited[], we could directly "flip" the checked number to negative arr[i] = -arr[i]

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1871 - Jump Game VII
public boolean canReach(String s, int minJ, int maxJ) {
    int n = s.length();
    int pre = 0; // the number of previous position that we can jump from
    boolean[] dp = new boolean[n]; // whether this position is reachable from start
    dp[0] = true;
    for (int i=1; i<n; i++) {
	// Sliding Window Problem, so there is no need to calculate everytime, but remove the effect from dp[i-maxJ-1] and add the effect from dp[i-minJ]
	// To determine the state of dp[i], we need to check the states in window dp[i-maxJ : i-minJ], since any one of them can reach i if it's labeled true.
        if (i>=minJ && dp[i-minJ]) {
            pre++; // add the effect from dp[i-minJ]
	}
        if (i>maxJ && dp[i-maxJ-1]) {
            pre--; // remove the effect from dp[i-maxJ-1]
	}
        dp[i] = (pre>0 && s.charAt(i)=='0');
    }
    return dp[n - 1];
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #279 - Perfect Squares
public int numSquares(int n) {
    int[] dp = new int[n+1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int i=1; i<n+1; i++) {
        for (int j=1; j*j<=i; j++) { // "<="
            dp[i] = Math.min(dp[i], dp[i-j*j]+1);
        }
    }
    return dp[n];
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1049 - Last Stone Weight II
// Key: S1 + S2 = S, S1 - S2 = diff >= 0 -> diff = S - 2 * S2 -> finding minimum diff is the same as finding the maximum S2
public static int lastStoneWeightII(int[] stones) {
    int n = stones.length;
    int total = 0, S2 = 0; // S2 must be less than total/2 as S2 <= S1
    for (int i = 0; i < stones.length; i++) {
        total += stones[i];
    }
    
    boolean[] dp = new boolean[total+1];  // "true" means this value can be achieved by a combination of part of previous stones
    dp[0] = true;
    for (int s: stones) {
        boolean[] cur = dp.clone(); // two arrays so that this stone won't be "used twice"
        for (int i = s; i <= total/2; i++) { // start from the current stone's weight and end before S2 exceeds total/2
            if (dp[i - s]) {
                cur[i] = true; // can achieve this value
                S2 = Math.max(S2, i);
            }
        }
        dp = cur;
    }
    return total - 2 * S2;
}
