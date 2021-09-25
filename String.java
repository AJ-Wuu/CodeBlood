//String
String s = "";
s = s.concat("s"); //s.concat() has a return value, and it needs this return value to update the original string
s += "00"; //directly using "+" could also work

//String Builder
StringBuilder str = new StringBuilder();
str.append("GFG"); //str.append() does not have a return value, but directly update the original string
StringBuilder str1 = new StringBuilder("AAAABBBCCCC");
StringBuilder str2 = new StringBuilder(10);
StringBuilder str3 = new StringBuilder(str1.toString());

//Return the string representation of the passed argument
str.valueOf(int i); //all data types
str.valueOf(char[] cc); //only for char arrays
str.valueOf(char[] data, int offset, int count); //representation of a specific subarray of the char array argument

//Commpare
//str1.equals(str2) tells the equality of two strings
//str1.compareTo(str2) tells how strings are compared lexicographically -> ATTENTION: uppercase are "smaller" than lowercase
int a = ("AEC").compareTo("DBC"); //a = -3 = A - D
int b = ("Z").compareTo("a"); //b = -7

//X-digit after the decimal
String.format("%.6f", m/n); //Note, at least one of m and n should be float or double.

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#76 - Minimum Window Substring
//Template for substring problems (with optimization of using array instead of HashMap):
//1. Shortest Substring w/o repeating characters************************************************************************
//Algorithms: 1. Use two pointers: start and end to represent a window.
//            2. Move end to find a valid window.
//            3. When a valid window is found, move start to find a smaller window.
public String minWindow(String s, String t) {
    int[] map = new int[128]; //ASCII
    for (char c : t.toCharArray()) {
        map[c]++; //initialize the map array
    }
    int start = 0, end = 0; //start & end -> pointers of the head and the tail for each substring
    int minStart = 0; //minStart -> pointer of the head of the shortest substring
    int minLen = Integer.MAX_VALUE; //minLen -> the shortest length of the valid substring
    int counter = t.length(); //counter -> check whether the substring is valid
    
    while (end < s.length()) { //move the end pointer
        char c1 = s.charAt(end);
        if (map[c1] > 0) {
            counter--;
        }
        map[c1]--;
        end++;
        while (counter == 0) { //move the start pointer and find the minLen
            if (minLen > end - start) {
                minLen = end - start;
                minStart = start;
            }
            char c2 = s.charAt(start);
            map[c2]++;
            if (map[c2] > 0) {
                counter++;
            }
            start++;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
}

//2. Longest Substring with At Most K Distinct Characters************************************************************
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] map = new int[128];
    int start = 0, end = 0, maxLen = Integer.MIN_VALUE, counter = 0;

    while (end < s.length()) {
        char c1 = s.charAt(end);
        if (map[c1] == 0) {
            counter++;
        }
        map[c1]++;
        end++;
        while (counter > k) {
            char c2 = s.charAt(start);
            if (map[c2] == 1) {
                counter--; //no this character (c2) anymore, so counter decreases 1
            }
            map[c2]--;
            start++;
        }
        maxLen = Math.max(maxLen, end - start); //maxLen and minLen use different places to update
    }
    return maxLen;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
