//#856 - Score of Parentheses
//Approach 1: Divide and Conquer
//Approach 2: Stack
public static int scoreOfParentheses(String S) {
    Stack<Integer> stack = new Stack<Integer>();
    stack.push(0); //base score
    for (char c : S.toCharArray()) {
        if (c == '(') {
            stack.push(0);
        }
        else {
            int v = stack.pop(); //the score of the current frame
            int w = stack.pop();
            stack.push(w + Math.max(2 * v, 1)); //base score is 0, so 2 * v = 0
        }
    }
    return stack.pop();
}

//Approach 3: Count Cores
//    Keep track of the balance of the string.
//    For every ) that immediately follows a (, the answer is 1 << balance, as balance is the number of exterior set of parentheses that contains this core.
public static int scoreOfParentheses(String S) {
    int ans = 0, balance = 0;
    for (int i = 0; i < S.length(); ++i) {
        if (S.charAt(i) == '(') {
            balance++;
        }
        else {
            balance--;
            if (S.charAt(i-1) == '(') {
                ans += 1 << balance;
            }
        }
    }
    return ans;
}
