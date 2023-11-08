/**
 * @author AJWuu
 */

package stringPatternSearch;

public class BoyerMoore {
	
	private static final int SIZE = 256;
	private static int lastOccurrence[] = new int[SIZE];
	
	private static void buildIndex(String pattern) {
		int length = pattern.length();
		for (int i = 0; i < SIZE; i++) {
			lastOccurrence[i] = -1;
		}
		for (int i = 0; i < length; i++) {
			lastOccurrence[pattern.charAt(i)] = i;
		}
	}
	
	private static int findLast(char aChar) {
		return lastOccurrence[aChar];
	}
	
	public static int search(String content, String pattern) {
		//validate, null or empty string is not allowed
		if (content == null || content.length() == 0) {
			return -1;
		}
		
		if (pattern == null || pattern.length() == 0) {
			return -1;
		}
		
		// search pattern
		if (content.length() < pattern.length()) {
			// impossible match
			return -1;
		}
		
		// build last occurrence index
		buildIndex(pattern);
		
		// searching
		int start = pattern.length() - 1;
		int end = content.length();
		int position, j;
		
		// search from left to right
		while (start < end) {
			position = start;
			
			for (j = pattern.length() - 1; j >= 0; j--) {
				// if not match a character
				if (pattern.charAt(j) != content.charAt(position)) {
					// check the last occurrence index
					if (findLast(content.charAt(position)) != -1) {
						if (j - findLast(content.charAt(position)) > 0) {
							// case 1
							start += j - findLast(content.charAt(position));
						}
						else {
							// case 2
							start += 1;
						}
					}
					else {
						// case 3
						start += j + 1;
					}
					break;
				}
				
				if (j == 0) {
					// found pattern
					return position;
				}
				
				position--;
			}
		}
		
		// not found
		return -1;
	}
	
	public static void main(String[] args) {
		String text = "ABAAAABAACD";
		String pat = "ABA";
		search(text, pat);
	}

}
