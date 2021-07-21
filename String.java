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

//************UNFINISHED***********************

//#76 - Minimum Window Substring https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
//Template for substring problems
//Shortest Substring w/o repeating characters
//Algorithms: 1. Use two pointers: start and end to represent a window.
//            2. Move end to find a valid window.
//            3. When a valid window is found, move start to find a smaller window.
int findSubstring(string s){
        vector<int> map(128,0);
        int counter; // check whether the substring is valid
        int begin=0, end=0; //two pointers, one point to tail and one  head
        int d; //the length of substring

        for() { /* initialize the hash map here */ }

        while(end<s.size()){

            if(map[s[end++]]-- ?){  /* modify counter here */ }

            while(/* counter condition */){ 
                 
                 /* update d here if finding minimum*/

                //increase begin to make it invalid/valid again
                
                if(map[s[begin++]]++ ?){ /*modify counter here*/ }
            }  

            /* update d here if finding maximum*/
        }
        return d;
  }

//Longest Substring with At Most Two Distinct Characters
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

//Longest Substring Without Repeating Characters
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
