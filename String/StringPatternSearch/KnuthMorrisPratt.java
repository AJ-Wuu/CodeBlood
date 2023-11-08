/**
 * @author AJWuu
 */

package stringPatternSearch;

import java.util.ArrayList;

public class KnuthMorrisPratt {
	
	/*
	 * General Idea:
	 * Find the longest prefix of the pattern, then us it to easily match pattern in text
	 * 
	 * Time Complexity: O(m+n)
	 */

	public static int[] longestPrefix(String pattern) {
		int[] calculate = new int[pattern.length()+1];
		calculate[0] = -1; //initialize
		calculate[1] = 0;
		
		int prefixLength = 0;
		int i = 1; //always start finding from the first character
		while (i < pattern.length()) {
			if (pattern.charAt(prefixLength) == pattern.charAt(i)) {
				prefixLength++;
				i++;
				calculate[i] = prefixLength;
			}
			else if (prefixLength > 0) {
				//found mismatch, then search for substring before the mismatched character
				//eg. before mismatch, we have "acbac" -> substring prefix "a", "ac", "acb", "acba", suffix "c", "ac", "bac", "cbac"
				prefixLength = calculate[prefixLength]; //do not increment i
			}
			else {
				i++; //current character cannot be matched, store 0 and move forward
				calculate[i] = 0;
			}
		}
		return calculate;
	}
	
	public static ArrayList<Integer> matchPattern(String text, String pattern) {
		int t = 0, p = 0; //the current character position of text, pattern
		ArrayList<Integer> match = new ArrayList<Integer>();
		int[] prefixCalculate = longestPrefix(pattern);
		
		while (t < text.length()) {
			if (pattern.charAt(p) == text.charAt(t)) {
				p++;
				t++;
				if (p == pattern.length()) {
					match.add(t-p);
					p = prefixCalculate[p];
				}
			}
			else {
				p = prefixCalculate[p]; //jump to the next possible position
				if (p < 0) {
					t++;
					p++;
				}
			}
		}
		
		return match;
	}
	
	public static void main(String[] args) {
		String text = "AABAACAAACAAAAACAADAABAAACAAAAACAABA";
		String pattern = "ABCCAABCC";
		ArrayList<Integer> list = matchPattern(text, pattern);
		
		if (list.size() > 0) {
			System.out.print("Matched at position: ");
			for (int i=0; i<list.size(); i++) {
				System.out.print(list.get(i) + " ");
			}
		}
		else {
			System.out.println("No matched pattern");
		}
	}

}
