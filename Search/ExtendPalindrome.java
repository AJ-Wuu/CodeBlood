 * @author AJWuu
 */

package palindrome;

public class ExtendPalindrome{

  //#647 - Palindromic Substrings
	private static int extendPalindrome(String s, int start, int end) {
		int count = 0;
		while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
			count++;
			start--;
			end++;
		}
		return count;
	}

	public static int countSubstrings(String s) {
		int result = 0;
		if (s == null || s.length() <= 0) {
			return result;
		}

		for (int i=0; i<s.length(); i++) {
			result += extendPalindrome(s, i, i);
			result += extendPalindrome(s, i, i+1);
		}
		return result;
	}

	public static void main(String[] args) {
		String str = "abc";
		System.out.println(countSubstrings(str));
	}

}
