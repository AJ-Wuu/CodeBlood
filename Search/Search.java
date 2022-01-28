//Sink Method (saves nxn space of storing visited[][]): eg. isConnected = 1, notConnected = 0 -> when visited graph[i][j] == 1, change to graph[i][j] = 0

//#32 - Longest Valid Parentheses
//Two directions search (from front to back or from back to front)
public int longestValidParentheses(String s) {
    int left = 0, right = 0, maxlength = 0;
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            left++;
        }
        else {
            right++;
        }
        if (left == right) {
            maxlength = Math.max(maxlength, 2 * right);
        }
        else if (right >= left) {
            left = right = 0;
        }
    }
    left = right = 0;
    for (int i = s.length() - 1; i >= 0; i--) {
        if (s.charAt(i) == '(') {
            left++;
        }
        else {
            right++;
        }
        if (left == right) {
            maxlength = Math.max(maxlength, 2 * left);
        }
        else if (left >= right) {
            left = right = 0;
        }
    }
    return maxlength;
}
