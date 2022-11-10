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

//Commpare -- Notice that .compareTo() returns int, not boolean
//str1.equals(str2) tells the equality of two strings
//str1.compareTo(str2) tells how strings are compared lexicographically -> ATTENTION: uppercase are "smaller" than lowercase
int a = ("AEC").compareTo("DBC"); //a = -3 = A - D
int b = ("Z").compareTo("a"); //b = -7

//X-digit after the decimal
String.format("%.6f", m/n); //Note, at least one of m and n should be float or double.

//Substring
str.startsWith(prefix); //return true or false
str.endsWith(suffix); //return true or false

/*
 * String, StringBuffer and StringBuilder
 * Mutability Difference:
 * String is immutable, if you try to alter their values, another object gets created;
 * StringBuffer and StringBuilder are mutable so they can change their values.
 * 
 * Thread-Safety Difference:
 * StringBuffer is thread-safe, so when the application needs to be run only in a single thread then it is better to use StringBuilder.
 * StringBuilder is more efficient than StringBuffer, NOT thread-safe.
 * 
 * Situations:
 * If your string is not going to change use a String class because a String object is immutable.
 * If your string can change, and will only be accessed from a single thread, using a StringBuilder is good enough.
 *     (example: lots of logic and operations in the construction of the string)
 * If your string can change, and will be accessed from multiple threads, use a StringBuffer because StringBuffer is synchronous so you have thread-safety.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#482 - License Key Formatting
public String licenseKeyFormatting(String s, int k) {
    s = s.replaceAll("-","").toUpperCase(); //remove all '-' first and format from raw
    StringBuilder sb = new StringBuilder(s);
    int i = sb.length() - k; //format from back to front
    while (i > 0) {
        sb.insert(i, '-');
        i -= k;
    }
    return sb.toString();
}

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

//#151 - Reverse Words in a String
public static String cleanSpaces(char[] a) {
    int i = 0, j = 0, n = a.length;
    while (j < n) { //one word for a loop
        while (j < n && a[j] == ' ') { j++; } //skip spaces
        while (j < n && a[j] != ' ') { a[i++] = a[j++]; } //keep non-spaces
        while (j < n && a[j] == ' ') { j++; } //skip spaces
        if (j < n) { a[i++] = ' '; } //keep only one space}
    }
    return new String(a).substring(0, i);
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1525 - Number of Good Ways to Split a String
//Key: get unique_from_start[] and unique_from_end[] using HashMap, and a split is good if unique_from_start[i] == unique_from_end[i+1] -> O(n)

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1209 - Remove All Adjacent Duplicates in String II
//Solution 1: Two Pointers
public String removeDuplicates(String s, int k) {
    int i = 0; //i refers to the index to set next character in the output string
    int n = s.length();
    int count[] = new int[n];
    char[] stack = s.toCharArray();
    for (int j=0; j<n; i++, j++) { //j refers to the index of current iteration in the input string
        stack[i] = stack[j];
        count[i] = (i > 0 && stack[i-1] == stack[j]) ? count[i-1] + 1 : 1; //add count[i] if s[j] == s[i-1]
        if (count[i] == k) {
            i -= k;
        }
    }
    return new String(stack, 0, i);
}

//Solution 2: Stack
public String removeDuplicates(String s, int k) {
    int[] count = new int[s.length()];
    StringBuilder sb = new StringBuilder(); //use as a stack
    for (char c: s.toCharArray()) {
        sb.append(c);
        int last = sb.length()-1;
        count[last] = 1 + ((last > 0 && sb.charAt(last) == sb.charAt(last-1)) ? count[last-1] : 0); //push (c, oldValue + 1) if there is oldValue; otherwise, (c, 1)
        if (count[last] >= k) {
            sb.delete(sb.length()-k, sb.length());
        }
    }
    return sb.toString();
}
