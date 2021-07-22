//String
String s = "";
s = s.concat("s"); //s.concat() has a return value, and it needs this return value to update the original string

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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#76 - Minimum Window Substring
//Template for substring problems (with optimization of using array instead of HashMap):
//1. Shortest Substring w/o repeating characters************************************************************************
//Algorithms: 1. Use two pointers: start and end to represent a window.
//            2. Move end to find a valid window.
//            3. When a valid window is found, move start to find a smaller window.
public String minWindow(String s, String t) {
    //Initialization
    int[] map = new int[128];
    for (char c : t.toCharArray()) {
        map[c]++; //initialize the map array
    }
    int start = 0, end = 0; //start & end -> point to the head and the tail for each substring
    int minStart = 0; //minStart -> point to the head of the shortest substring
    int minLen = Integer.MAX_VALUE; //minLen -> the shortest length of the valid substring
    int counter = t.length(); //counter -> check whether the substring is valid
    
    while (end < s.length()) {
        char c1 = s.charAt(end);
        if (map[c1] > 0) {
            counter--;
        }
        map[c1]--;
        end++;
        while (counter == 0) {
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

int findSubstring(String string){
    HashMap<Object, Integer> map = new HashMap<Object, Integer>();
    int counter; // check whether the substring is valid
    int begin = 0, end = 0; //two pointers, one point to head and one to tail
    int length; //the length of substring
    for () { /* initialize the map */ }
    while (end < string.length) {
        if (map[string[end++]]-- ?) { /* modify counter */ }
        while (/* counter condition to see if valid */) { 
            /* update length here if finding minimum*/
            //increase begin to make it invalid/valid again
            if (map[s[begin++]]++ ?) { /* modify counter */ }
        }  
        /* update d here if finding maximum*/
    }
    return d;
}

//2. Longest Substring with At Most K Distinct Characters************************************************************
public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] map = new int[256];
    int start = 0, end = 0, maxLen = Integer.MIN_VALUE, counter = 0;

    while (end < s.length()) {
      final char c1 = s.charAt(end);
      if (map[c1] == 0) counter++;
      map[c1]++;
      end++;

      while (counter > k) {
        final char c2 = s.charAt(start);
        if (map[c2] == 1) counter--;
        map[c2]--;
        start++;
      }

      maxLen = Math.max(maxLen, end - start);
    }

    return maxLen;
  }
int lengthOfLongestSubstringTwoDistinct(string s) {
        vector<int> map(128, 0);
        int counter=0, begin=0, end=0, d=0; 
        while(end<s.size()){
            if(map[s[end++]]++==0) counter++;
            while(counter>2) if(map[s[begin++]]--==1) counter--;
            d=max(d, end-begin);
        }
        return d;
    }

//3. Longest Substring Without Repeating Characters********************************************************************
  public int lengthOfLongestSubstring2(String s) {
    int[] map = new int[128];
    int start = 0, end = 0, maxLen = 0, counter = 0;

    while (end < s.length()) {
      final char c1 = s.charAt(end);
      if (map[c1] > 0) counter++;
      map[c1]++;
      end++;

      while (counter > 0) {
        final char c2 = s.charAt(start);
        if (map[c2] > 1) counter--;
        map[c2]--;
        start++;
      }

      maxLen = Math.max(maxLen, end - start);
    }

    return maxLen;
  }
int lengthOfLongestSubstring(string s) {
        vector<int> map(128,0);
        int counter=0, begin=0, end=0, d=0; 
        while(end<s.size()){
            if(map[s[end++]]++>0) counter++; 
            while(counter>0) if(map[s[begin++]]-->1) counter--;
            d=max(d, end-begin); //while valid, update d
        }
        return d;
    }
