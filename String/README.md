# String
## String, StringBuffer and StringBuilder
### Customizaion
```java
String reversedString = new StringBuilder(s).reverse().toString();
```

### Mutability
* String is immutable, if you try to alter their values, another object gets created
* StringBuffer and StringBuilder are mutable so they can change their values

### Thread-Safety
* StringBuffer is thread-safe, so when the application needs to be run only in a single thread then it is better to use StringBuilder
* StringBuilder is more efficient than StringBuffer, NOT thread-safe

### Performance Analysis
* **StringBuilder** is preferrable
  * uses LinkedList as data structure underneath
  * in general, insertion has amortized complexity to be O(1)
  * when resize is not needed, insertion costs O(1)
  * when resize is needed, the size is always doubled, so it costs O(n) ones

![image](https://github.com/Gnaku-18519/CodeBlood/assets/84046974/dc8d7d65-5cc8-4bdf-b1f5-a441b2ef6745)

## `.charAt()` vs `.toCharArray()`
* In a very large amount of operations, the validation will make `.charAt()` slower than `.toCharArray()`
* `.charAt()` performs an index validation, then takes O(1) to fetch
```java
public char charAt(int index) {
    if ((index < 0) || (index >= value.length)) {
        throw new StringIndexOutOfBoundsException(index);
    }
    return value[index];
}
```
* `.toCharArray()` has no validation, and takes O(n) to finish

## Get All Distinct Characters from a String
```java
// slower with set
Set<Character> set = new HashSet<>();
for (char c : allowed.toCharArray()) {
    set.add(c);
}

// faster with array
boolean[] s = new boolean[26];
for (char c : allowed.toCharArray()) {
    s[c - 'a'] = true;
}
```

## Add Delimiter to the Middle of a String
```java
String.join(", ", privileges);
```

## Split String by Space and Convert to a List
```java
List<String> list1 = Arrays.asList(s1.split(" ", -1));       // faster but only covers ` ` itself
List<String> list2 = Arrays.asList(s2.split("\\s+", -1));    // slower but more robust, `\s` covers more white space chars than just space and tab
```

## Prefix with Trie
```java
// #2416 - Sum of Prefix Scores of Strings
// #3043 - Find the Length of the Longest Common Prefix
class TrieNode {
    TrieNode[] children = new TrieNode[26]; // each node has up to 26 possible children (letter a to z)
    int score = 0; // the score of a string `term` = the number of strings `words[i]` such that `term` is a prefix of `words[i]`
}

class Trie {
    TrieNode root = new TrieNode();

    void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (node.children[c - 'a'] == null) {
                node.children[c - 'a'] = new TrieNode();
            }
            node.children[c - 'a'].score++;
            node = node.children[c - 'a'];
        }
    }

    int count(String s) {
        TrieNode node = root;
        int ans = 0; // the sum of scores
        for (char c : s.toCharArray()) {
            ans += node.children[c - 'a'].score;
            node = node.children[c - 'a'];
        }
        return ans;
    }
}
```
