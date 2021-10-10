/**
 * Bucket == LinkedList
 * Load Factor: 16 initial buckets, doubles the existing capacity every time it reaches more than 75% of its capacity
 * Key: Not Primitive, but Object -> we need the Key.hashCode() to calculate the bucket index = Key.hashCode() & (length - 1)
 * map.put(K, V): K's bucket is empty (no collision) -> put it in;
 *                           not empty (collision) -> K.equals(K_existed) == true, replace (K_exisited, V_existed) with (K, V) -> no duplicate;
 *                                                                           false, add (K, V) next to (K_exisited, V_existed)
 *
 * Java 8 enhancements:
 * 1. map.get(Key) process: find Key.hashCode() -> get Key's bucket index -> go to the bucket and go through the LinkedList elements one by one until find Key
 * 2. if there are too many elements in one bucket, they are not stored in linearity, but in a Treeify Threshold (go up/right when larger, down/left when smaller)
 *    -> use compareTo() to check the order -> it's a BST, a Self-Balancing Tree, also a Red-Black Tree
 *
 * Fail-Fast and Fail-Safe Iterator:
 * Fail-Fast -> 1. throw ConcurrentModificationException if a collection is modified while iterating over it
 *              2. use original collection to traverse over the elements of the collection
 *              3. don’t require extra memory
 *              Eg. ArrayList, Vector, HashMap
 * Fail-Safe -> 1. allow modifications of a collection while iterating over it
 *              2. don’t throw any Exception if a collection is modified while iterating over it
 *              3. use copy of original collection to traverse over the elements of the collection
 *              4. require extra memory for cloning of collection
 *              Eg. ConcurrentHashMap, CopyOnWriteArrayList
 *
 * The following data structures all use HashCodes for keys/objects inside to improve performance.
 * Why HashMap?
 * 1. Implement the Map interface.
 * 2. Using key makes it very efficient: map.add() has O(1) time complexity, while map.search() and map.delete() has nearly O(1).
 * 3. Fail-fast iterator.
 * 4. Non-synchronized -- multiple threads accessing the hashmap do not synchroize its value among each other:
 *             -> it can be externally synchronized using Collections.synchronizedmap(),
 *             -> also internally synchronized by ConcurrentHashMap (more efficient in comparision to externally synchronizing the HashMap).
 *
 * Why HashTable?
 * 1. Implement the Map interface.
 * 2. Efficiency same as HashMap.
 * 3. No null key or value (while HashMap could have).
 * 4. (Disadvantage, maybe) Synchronized -- Hashtable is thread-safe and can be shared between multiple threads (Synchronization HashTable is much slower than HashMap).
 *
 * Why HashSet?
 * 1. Implement the Set interface, backed by a HashTable (actually a HashMap instance).
 * 2. No Value needed, only Key.
 * 3. No guarantees as to the iteration order of the set (in particular, it does not guarantee that the order will remain constant over time).
 *
 * Collection Types:
 * Set -> a collection of distinct (non-equal) objects, with no other structure.
 * Map -> a map from a set of objects (the distinct keys) to a collection of objects (the values).
 *
 * Handle Collision:
 * 1. Chaining: LinkedList<ElementType>[]
 * 2. Open-Addressing -> if the target basket is filled, find the next empty basket by one of the following methods
 *    2.1. Linear Probing: hash(x)%S -> (hash(x)+1)%S -> (hash(x)+2)%S -> (hash(x)+3)%S -> ...
 *    2.2. Quadraic Probing: hash(x)%S -> (hash(x)+1^2)%S -> (hash(x)+2^2)%S -> (hash(x)+3^2)%S -> ...
 *    2.3. Double Hashing: hash(x)%S -> (hash(x)+1*hash2(x))%S -> (hash(x)+2*hash2(x))%S -> (hash(x)+3*hash2(x))%S -> ...
 */

//Initialization
HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); //<K, V>
Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();

//computeIfAbsent()
public V computeIfAbsent(K key, Function<? super K, ? extends V> remappingFunction);
targets.computeIfAbsent(ticket.get(0), k -> new PriorityQueue()).add(ticket.get(1)); //See LeetCode/Graph.java #332

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#954 - Array of Doubled Pairs ????????????????????????????????????????????????????????
map.put(x, map.getOrDefault(x, 0)); //map.getOrDefault(x, 0) -> hashmap.get(Object key, V defaultValue)

public boolean canReorderDoubled(int[] arr) {
    var intsLeft = new HashMap();
    for (int i : arr) {
        intsLeft.put(i, intsLeft.getOrDefault(i, 0) + 1);
    }
    for (int i : arr) {
        if (!checkMatch(intsLeft, i)) {
            return false;
        }
    }
    return true;
}
    
public boolean checkMatch(Map intsLeft, int n) {
    if (intsLeft.getOrDefault(n, 0) == 0) {
        return true;
    }
    if (n != 0 && n % 2 == 0 && !checkMatch(intsLeft, n / 2)) {
        return false;
    }
    int nCount = intsLeft.get(n);
    int matchCount = intsLeft.getOrDefault(n * 2, 0);
    intsLeft.put(n * 2, matchCount - nCount);
    intsLeft.put(n, 0);
    return matchCount >= nCount;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#3 - Longest Substring Without Repeating Characters
//This is an easy way to: 1. check if c is in the map & 2. move the starting index to the latter possible position
start = Math.max(start, map.getOrDefault(c, 0)); //map.getOrDefault(c, 0) -> if find c in the map, return its value; else, return 0

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#17 - Letter Combinations of a Phone Number
//Convert char to int
int a = Integer.parseInt(String.valueOf(digits.charAt(i)));
int b = digits.charAt(i) - '0';
int c = Character.getNumericValue(i);

//Method 1:
result.add(""); //so that the add() can work
for (int i=0; i<digits.length(); i++) {
    result = combine(digitletter[digits.charAt(i)-'0'], result);
}

public static List<String> combine(String digit, List<String> l) {
    List<String> result = new ArrayList<String>();
    for (int i=0; i<digit.length(); i++) {
        for (String x : l) {
            result.add(x + digit.charAt(i));
        }
    }
    return result;
}

//Method 2 - recursive:
backtrack(combos, digits.toCharArray(), "", dict);

public void backtrack(List<String> combos, char[] digits, String s, String[] dict) {
    if (s.length() == digits.length) {
        combos.add(s);
        return;
    }
    int i = s.length();
    int digit = digits[i] - '0';
    for (char letter : dict[digit].toCharArray()) {
        backtrack(combos, digits, s + Character.toString(letter), dict);
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#30 - Substring with Concatenation of All Words
//Shallow copy a whole HashMap
map1.putAll(map2);

//Method 1: take advantage of the no-duplicate property of HashMap, and map1.equals(map2) is faster than array comparisons
public static ArrayList<Integer> findSubstring(String s, String[] words) {
    if (s == null || words == null || s.length() == 0 || words.length == 0) {
        return new ArrayList<>();
    }
    HashMap<String, Integer> counts = new HashMap<>();
    for (String word : words) {
        counts.put(word, counts.getOrDefault(word, 0) + 1); //this makes sure that if word exists in count, it has value > 0 -> to distinguish from non-existing
    }
    
    ArrayList<Integer> r = new ArrayList<>();
    int sLen = s.length();
    int num = words.length;
    int wordLen = words[0].length();
    
    for (int i = 0; i < sLen - num * wordLen + 1; i++) {
        String sub = s.substring(i, i + num * wordLen);
        if (isConcat(sub, counts, wordLen)) {
            r.add(i);
        }
    }
    return r;
}

private static boolean isConcat(String sub, HashMap<String, Integer> counts, int wordLen) {
    HashMap<String, Integer> seen = new HashMap<>();
    for (int i = 0; i < sub.length(); i += wordLen) {
        String sWord = sub.substring(i, i + wordLen);
        seen.put(sWord, seen.getOrDefault(sWord, 0) + 1);
    }
    return seen.equals(counts);
}

//Method 2: inner loop matches a word, and uses checkFound() to decide whether all words are found; outer loop makes sure all possible divisions have been traversed
public static ArrayList<Integer> findSubstring(String s, String[] words) {
    ArrayList<Integer> res = new ArrayList<Integer>();
    int n = s.length(), m = words.length, k;
    if (n == 0 || m == 0 || (k = words[0].length()) == 0) {
        //assign value to k in if() -> avoid the possibility that words[] == null
        return res;
    }

    HashMap<String, Integer> wordDict = new HashMap<String, Integer>();
    for (String word : words) {
        if (wordDict.containsKey(word)) {
            wordDict.put(word, wordDict.get(word) + 1);
	}
        else {
            wordDict.put(word, 1); //make sure that if word exists, its value > 0; distinguish from non-existing word
	}
    }

    int start, x, wordsLen = m * k;
    HashMap<String, Integer> currDict = new HashMap<String, Integer>();
    String test, temp;
    for (int i=0; i<k; i++) { //outer loop jump from 0 to word's length
        currDict.clear();
        start = i;
        if (start + wordsLen > n) {
            return res;
        }
		
        for (int j=i; j+k<=n; j+=k) { //inner loop skip by word's length
            test = s.substring(j, j+k);

            if (wordDict.containsKey(test)) {
                x = currDict.getOrDefault(test, 0);
                if (x < wordDict.get(test)) {
                    //currDict does not contain test OR currDict contains less times of test than wordDict
                    currDict.put(test, x + 1);
                    start = checkFound(res, start, wordsLen, j, k, currDict, s);
                    continue;
                }

                //currDict.get(test) == wordDict.get(test), 
                //slide start to the next word of the first same word as test
                while (!(temp = s.substring(start, start + k)).equals(test)) {
                    decreaseCount(currDict, temp);
                    start += k;
                }
                start += k;
                if (start + wordsLen > n) {
                    break;
                }
                continue;
            }

            //totally failed with index j+k, slide start and reset all
            start = j + k;
            if (start + wordsLen > n) {
                break;
            }
            currDict.clear();
        }
    }
    return res;
}

public static int checkFound(ArrayList<Integer> res, int start, int wordsLen, int j, int k, HashMap<String, Integer> currDict, String s) {
    //if found a substring, return its starting index; else, return the start unchanged
    if (start + wordsLen == j + k) {
        res.add(start);
        //slide start to the next word
        decreaseCount(currDict, s.substring(start, start + k));
        return start + k;
    }
    return start;
}

public static void decreaseCount(HashMap<String, Integer> currDict, String key) {
    //remove key if currDict.get(key)==1, otherwise decrease it by 1
    int x = currDict.get(key);
    if (x == 1) {
        currDict.remove(key);
    }
    else {
        currDict.put(key, x - 1);
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#36 - Valid Sudoku
//Using some signal words to store a value in different strings in HashSet can save loops.
public static boolean isValidSudoku(char[][] board) {
    Set<String> seen = new HashSet<String>();
        for (int i=0; i<9; ++i) {
            for (int j=0; j<9; ++j) {
                char number = board[i][j];
                if (number != '.') {
                    if (!seen.add(number + " in row " + i) ||
                        !seen.add(number + " in column " + j) ||
                        !seen.add(number + " in block " + i/3 + '-' + j/3)) {
                            return false;
		    }
		}
            }
        }
    return true;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#49 - Group Anagrams
//Key points:
    //1. Make the value of the HashMap in a special data structure
    //2. Sort the characters in the string -> this is super helpful for this kind of sharing similarities question
public List<List<String>> groupAnagrams(String[] strs) {
    if (strs == null || strs.length == 0) {
        return new ArrayList<>();
    }
    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
    for (String s : strs) {
	//Sort the characters in s in alphabetical order
        char[] charArray = new char[26];
        for (char c : s.toCharArray()) {
            charArray[c-'a'] = c;
        }
	
	//Combine the charArray[] together as a new String, which has all the characters in s and is written in order
        String keyStr = String.valueOf(charArray); //return the string representation of the char array argument.
        if (!map.containsKey(keyStr)) {
            map.put(keyStr, new ArrayList<>());
        }
        map.get(keyStr).add(s);
    }
    return new ArrayList<>(map.values()); //Convert Map to ArrayList (object could be omitted)
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#347 - Top K Frequent Elements
//Sort HashMap by Values
@SuppressWarnings({ "rawtypes", "unchecked" })
private static HashMap sortByValues(HashMap<Integer, Integer> map) { 
    LinkedList list = new LinkedList(map.entrySet());
    Collections.sort(list, new Comparator() {
        public int compare(Object obj1, Object obj2) { //from the largest to the smallest
            return ((Comparable) ((Map.Entry) (obj2)).getValue()).compareTo(((Map.Entry) (obj1)).getValue());
        }
    });
	
    //Here, the sorted list is copied in HashMap using LinkedHashMap to safeguard the insertion order
    HashMap sortedHashMap = new LinkedHashMap();
    Iterator iter = list.iterator(); //from java.util.Iterator, not javax.swing.text.html.HTMLDocument.Iterator
    for ( ;iter.hasNext(); ) {
        Map.Entry entry = (Map.Entry)iter.next();
        sortedHashMap.put(entry.getKey(), entry.getValue());
    } 
    return sortedHashMap;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
