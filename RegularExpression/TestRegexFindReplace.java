package regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TestRegexFindReplace {
	
	public static void main(String[] args) {
		String inputStr = "This is an apple. These are 33 (Thirty-three) apples";
		String regexStr = "apple";         // pattern to be matched
		String replacementStr = "orange";  // replacement pattern

		// Step 1: Allocate a Pattern object to compile a regex
		Pattern pattern = Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE);

		// Step 2: Allocate a Matcher object from the pattern, and provide the input
		Matcher matcher = pattern.matcher(inputStr);

		// Step 3: Perform the matching and process the matching result
		//String outputStr = matcher.replaceAll(replacementStr);     // all matches
		String outputStr = matcher.replaceFirst(replacementStr); // first match only
		System.out.println(outputStr);
	}
	
}