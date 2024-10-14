// Best and Safe Practice
boolean checkNullAndMatch = "key".equalsIgnoreCase(sortBy); // instead of `sortBy != null && sortBy.equalsIgnoreCase("key")`

// String
String s = "";
s = s.concat("s"); // s.concat() has a return value, and it needs this return value to update the original string
s += "00"; // directly using "+" could also work

// String Builder
StringBuilder str = new StringBuilder();
str.append("GFG"); // str.append() does not have a return value, but directly update the original string
StringBuilder str1 = new StringBuilder("AAAABBBCCCC");
StringBuilder str2 = new StringBuilder(10);
StringBuilder str3 = new StringBuilder(str1.toString());

// Return the string representation of the passed argument
str.valueOf(int i); // all data types
str.valueOf(char[] cc); // only for char arrays
str.valueOf(char[] data, int offset, int count); // representation of a specific subarray of the char array argument

// Commpare -- Notice that .compareTo() returns int, not boolean
// str1.equals(str2) tells the equality of two strings
// str1.compareTo(str2) tells how strings are compared lexicographically -> ATTENTION: uppercase are "smaller" than lowercase
int a = ("AEC").compareTo("DBC"); // a = -3 = A - D
int b = ("Z").compareTo("a"); // b = -7

// X-digit after the decimal
String.format("%.6f", m/n); // Note, at least one of m and n should be float or double.

// Substring
str.startsWith(prefix); // return true or false
str.endsWith(suffix); // return true or false

// Eliminate leading and trailing spaces (but NOT middle spaces)
String s1 = "   Geeks For Geeks   ";
String s2 = s1.trim(); // "Geeks For Geeks"

public void stringMatch() {
    // `pattern.matcher` - test if the string contains-a pattern
    // `pattern.matches` - test if the string is-a pattern
    String str = "hello+";
    Pattern pattern = Pattern.compile("\\+");
    Matcher matcher = pattern.matcher(str);
    
    while (matcher.find()) {
        System.out.println("I found the text " + matcher.group() + " starting at index " + matcher.start() + " and ending at index " + matcher.end());
    }
    System.out.println(java.util.regex.Pattern.matches(".*\\+", str));
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #567 - Permutation in String
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;

    int[] s1Count = new int[26];
    int[] s2Count = new int[26];

    // Count the frequency of characters in s1 and the first window of s2
    for (int i = 0; i < s1.length(); i++) {
        s1Count[s1.charAt(i) - 'a']++;
        s2Count[s2.charAt(i) - 'a']++;
    }

    // Slide the window over s2
    for (int i = 0; i < s2.length() - s1.length(); i++) {
        if (matches(s1Count, s2Count)) return true;
        // Update the window
        s2Count[s2.charAt(i) - 'a']--;
        s2Count[s2.charAt(i + s1.length()) - 'a']++;
    }

    // Check the last window
    return matches(s1Count, s2Count);
}

private boolean matches(int[] s1Count, int[] s2Count) {
    for (int i = 0; i < 26; i++) {
        if (s1Count[i] != s2Count[i]) return false;
    }
    return true;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #482 - License Key Formatting
public String licenseKeyFormatting(String s, int k) {
    s = s.replaceAll("-","").toUpperCase();   // remove all '-' first and format from raw
    StringBuilder sb = new StringBuilder(s);
    int i = sb.length() - k; // format from back to front
    while (i > 0) {
        sb.insert(i, '-');
        i -= k;
    }
    return sb.toString();
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #76 - Minimum Window Substring
// Template for substring problems (with optimization of using array instead of HashMap):
// 1. Shortest Substring w/o repeating characters************************************************************************
// Algorithms: 1. Use two pointers: start and end to represent a window.
//             2. Move end to find a valid window.
//             3. When a valid window is found, move start to find a smaller window.
public String minWindow(String s, String t) {
    int[] map = new int[128]; // ASCII
    for (char c : t.toCharArray()) {
        map[c]++; // initialize the map array
    }
    int start = 0, end = 0; // start & end -> pointers of the head and the tail for each substring
    int minStart = 0; // minStart -> pointer of the head of the shortest substring
    int minLen = Integer.MAX_VALUE; // minLen -> the shortest length of the valid substring
    int counter = t.length(); // counter -> check whether the substring is valid
    
    while (end < s.length()) { // move the end pointer
        char c1 = s.charAt(end);
        if (map[c1] > 0) {
            counter--;
        }
        map[c1]--;
        end++;
        while (counter == 0) { // move the start pointer and find the minLen
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

// 2. Longest Substring with At Most K Distinct Characters************************************************************
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
                counter--; // no this character (c2) anymore, so counter decreases 1
            }
            map[c2]--;
            start++;
        }
        maxLen = Math.max(maxLen, end - start); // maxLen and minLen use different places to update
    }
    return maxLen;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #151 - Reverse Words in a String
public static String cleanSpaces(char[] a) {
    int i = 0, j = 0, n = a.length;
    while (j < n) { // one word for a loop
        while (j < n && a[j] == ' ') { j++; } // skip spaces
        while (j < n && a[j] != ' ') { a[i++] = a[j++]; } // keep non-spaces
        while (j < n && a[j] == ' ') { j++; } // skip spaces
        if (j < n) { a[i++] = ' '; } // keep only one space}
    }
    return new String(a).substring(0, i);
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1525 - Number of Good Ways to Split a String
// Key: get unique_from_start[] and unique_from_end[] using HashMap, and a split is good if unique_from_start[i] == unique_from_end[i+1] -> O(n)

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1209 - Remove All Adjacent Duplicates in String II
// Solution 1: Two Pointers
public String removeDuplicates(String s, int k) {
    int i = 0; // i refers to the index to set next character in the output string
    int n = s.length();
    int count[] = new int[n];
    char[] stack = s.toCharArray();
    for (int j=0; j<n; i++, j++) { // j refers to the index of current iteration in the input string
        stack[i] = stack[j];
        count[i] = (i > 0 && stack[i-1] == stack[j]) ? count[i-1] + 1 : 1; // add count[i] if s[j] == s[i-1]
        if (count[i] == k) {
            i -= k;
        }
    }
    return new String(stack, 0, i);
}

// Solution 2: Stack
public String removeDuplicates(String s, int k) {
    int[] count = new int[s.length()];
    StringBuilder sb = new StringBuilder(); // use as a stack
    for (char c: s.toCharArray()) {
        sb.append(c);
        int last = sb.length()-1;
        count[last] = 1 + ((last > 0 && sb.charAt(last) == sb.charAt(last-1)) ? count[last-1] : 0); // push (c, oldValue + 1) if there is oldValue; otherwise, (c, 1)
        if (count[last] >= k) {
            sb.delete(sb.length()-k, sb.length());
        }
    }
    return sb.toString();
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #49 - Group Anagrams
// transform anagram strings to the same key (eg. "eat", "tea", "ate")
char[] ca = new char[26];
for (char c : s.toCharArray()) {
    ca[c - 'a']++;
}
String keyStr = String.valueOf(ca);

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #43 - Multiply Strings
// Key: num1[i] * num2[j] will be placed at indices [i + j, i + j + 1]
//      1  2  3
//   x     4  5
//         1  5
//      1  0
//   0  5
//      0  8
//  ...
public String multiply(String num1, String num2) {
    int m = num1.length(), n = num2.length();
    int[] pos = new int[m + n];
   
    for(int i = m - 1; i >= 0; i--) {
        for(int j = n - 1; j >= 0; j--) {
            int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); 
            int p1 = i + j, p2 = i + j + 1;
            int sum = mul + pos[p2];

            pos[p1] += sum / 10;
            pos[p2] = (sum) % 10;
        }
    }  
    
    StringBuilder sb = new StringBuilder();
    for(int p : pos) if(!(sb.length() == 0 && p == 0)) sb.append(p);
    return sb.length() == 0 ? "0" : sb.toString();
}
