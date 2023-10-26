# Regular Expression
## Examples
* exact sequence of characters: `abc`
* escape characters with special meaning: adding `\`, eg. `\.`, `\+`, `\$`, etc.
* any digit: `\d`
* any digit or letter: `\w`
* any space: `\s`
* any single character: `.`, which will match one digit, letter, space, special character, etc.
* any digit, any space, or any word character: `[\d\s\w]`
* any combination of possible characters: `[abc]`
* a range of possible characters: `[a-c]`
* exclude possible characters from set: `[^72]` or `[^abcd]` or `[^0-9a-z]`
* either of two different patterns: `(|)`, eg, `([a-z]{2}|[0-9]{3})`, which will match two lowercase letters or three digits
* both patterns (at the same time): `.*`, eg. `ii.*g$` will match the pattern containing the consecutive letters `ii` and ending with g
* an optional part of pattern: `@(cs\.)?study\.edu`, which will match `@study.edu` and `@cs.study.edu`
* pattern appears one or more times: `[0-9]+@study.\edu`, which will match `4@study.edu`, `789@study.edu`, etc.
* pattern appears zero or more times: `[0-9]*@study.\edu`, which will match `@study.edu`, `4@study.edu`, `789@study.edu`, etc.
* pattern appears a specific number of times: `[a-z]{m}` where m = 1, 2, ..., which will match two lowercase letters
* pattern at the beginning of a line: `^`, eg. `^a`
* pattern at the end of a line: `$`, eg. `g$`
* same letter (out of `[xy]`) at the beginning and the end: `^([xy])[xy]*\1$|^[xy]$`
* Meta Escape = `\W` = match any non-word character = `[^a-zA-Z0-9_]`

## Regular Expressions in Makefile
* Note that for ending pattern, instead of `a$`, it should be written as `a$$`

## Regular Expressions in Java
* Packages needed:
```
import java.util.regex.Matcher
import java.util.regex.Pattern
```
* An example of a String method that makes use of regex:
```
String regex = "\\S*@(cs\\.)?study\\.edu)";
text.split(regex); //use regex to split the whole text
```
* Static method to create a new Pattern object: `Pattern pattern = Pattern.compile(regex);`
* Instance method to create a new Matcher object: `Matcher matcher = pattern.matcher(text);`
* Useful methods to call on a Matcher object:
```
matcher.find(); //return true or false of found the pattern or not
matcher.group(0); //group(0) is always the entire match
```
* Capture groups are defined within regular expressions by using: `()`
```txt
EXAMPLE FOR GROUP:
    String str = "start123456end"; // Your input String
    //                                 Group#1 Group#2
    //                                   ↑      ↑  
    Pattern p = Pattern.compile("start([0-9]*)(end)");
    //                           |<--- Group#0 --->|
    Matcher m = p.matcher(str); // Create a matcher for regex and input
    while(m.find()){ // as long as your regex matches something
        System.out.println("group#0:\t" + m.group()); // Or: m.group(0)
        System.out.println("group#1:\t" + m.group(1));
        System.out.println("group#2:\t" + m.group(2));
    }
    /*
     * Output:
     * group#0:    start123456end
     * group#1:    123456
     * group#2:    end
     */
```

## Regular Expressions in Bash
1. Put double quotes around strings to avoid the special bash characters
2. Put the `-E` in front as an argument to make use of the additional regular expression pattern matching notation
```bash
grep -E PATTERN -> (explain) interpret PATTERN as an extended regular expression
grep -n PATTERN -> (line-number) prefix each line of output with the line number within its input file
grep -c PATTERN -> (count) suppress normal output; instead print a count of matching lines for each input file
grep -o PATTERN -> (only-matching) show only the part of a matching line that matches PATTERN
```
