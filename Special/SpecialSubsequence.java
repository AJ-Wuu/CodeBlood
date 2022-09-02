//Credit: https://leetcode.com/discuss/interview-question/algorithms/125520/adobe-oa-2016-special-subsequence#:~:text=Consider%20a%20string%2C%20s%20%3D%20%22abc%22.%20An%20alphabetically-ordered,with%20a%20consonant%2C%20we%27re%20left%20with%20%7B%22ab%22%2C%20%22abc%22%7D.
//Solved errors: 1. java.lang.OutOfMemoryError: Java heap space; 2. Time Limit Exceeded

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class test {

    public static List<String> findSubstrings(String str) {
        if (str == null || str.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        Map<Character, List<Integer>> map = new HashMap<>();

        int n = str.length();
        char[] chars = str.toCharArray();
        boolean consonants = false;

        for (int i = 0; i < n; i++) {
            char c = chars[i];

            if (isVowel(c)) {
                if (!map.containsKey(c)) {
                    map.put(c, new ArrayList<>());
                }

                if (!map.get(c).isEmpty()) {
                    int last = map.get(c).get(map.get(c).size() - 1);
                    if (i > last && i != last + 1) {
                        map.get(c).add(i);
                    }
                }
                else {
                    map.get(c).add(i);
                }
            }
            else {
                consonants = true;
            }
        }

        /**
         * if there is no consonants, then we can't form the substring
         */
        if (!consonants) {
            return Collections.EMPTY_LIST;
        }

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};
        List<String> subStrings = new ArrayList<>();

        int v = 0;
        String[] solution = null;
        for (; v < vowels.length; v++) {
            char vowel = vowels[v];
            if (map.containsKey(vowel)) {
                solution = subString(chars, map.get(vowel));
                break;
            }
        }

        if (v == map.size() - 1) {
            return Arrays.asList(solution);
        }
        else {
            subStrings.add(solution[0]);
            int x = vowels.length - 1;

            for (; x > v; x--) {
                char vowel = vowels[x];
                if (map.containsKey(vowel)) {
                    solution = subString(chars, map.get(vowel));
                    break;
                }
            }

            subStrings.add(solution[1]);
        }

        return subStrings;
    }

    private static String[] subString(char[] str, List<Integer> indexes) {
        String first = null;
        String last = null;

        for (int i : indexes) {
            StringBuilder builder = new StringBuilder();
            int x = i;
            while (x < str.length) {
                builder.append(str[x++]);

                if (!isVowel(builder.charAt(builder.length() - 1))) {
                    String temp = builder.toString();
                    if (first != null) {
                        first = first.compareTo(temp) < 0 ? first : temp;
                    }
                    else {
                        first = temp;
                    }

                    if (last != null) {
                        last = last.compareTo(temp) > 0 ? last : temp;
                    }
                    else {
                        last = temp;
                    }
                }
            }
        }

        return new String[]{first, last};
    }

    private static boolean isVowel(char c) {
        if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Path fileName = Path.of("SpecialSubsequenceInput.txt");
        String str = Files.readString(fileName);
        List<String> result = findSubstrings(str);
        System.out.println(result.get(0));
        System.out.println(result.get(1));
    }

}
