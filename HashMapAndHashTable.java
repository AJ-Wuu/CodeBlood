/**
 * Bucket == LinkedList
 * Load Factor: 16 initial buckets, doubles the existing capacity every time it reaches more than 75% of its capacity
 * Key: Not Primitive, but Object -> we need the Key.hashCode() to calculate the bucket index = Key.hashCode() & (length - 1)
 * map.put(K, V): K's bucket is empty (no collision) -> put it in;
 *                           not empty (collision) -> K.equals(K_existed) == true, replace (K_exisited, V_existed) with (K, V) -> no duplicate;
 *                                                                           false, add (K, V) next to (K_exisited, V_existed)
 *
 * Java 8 enhancements:
 * 1. map.get(Key): find Key.hashCode() -> get Key's bucket index -> go to the bucket and go through the LinkedList elements one by one until find Key
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
 * Why HashMap?
 * 1. Using key makes it very efficient: map.add() has O(1) time complexity, while map.search() and map.delete() has nearly O(1).
 * 2. Fail-fast iterator.
 * 3. Non-synchronised implementation that multiple threads accessing the hashmap do not synchroise its value among each other:
 *             -> it can be externally synchronised using Collections.synchronizedmap(),
 *             -> also internally synchronised by ConcurrentHashMap (more efficient in comparision to externally synchronising the HashMap).
 *
 * Why HashTable?
 * 1. Efficiency same as HashMap.
 * 2. No null key or value (while HashMap could have).
 * 3. (Disadvantage, maybe) Synchronized that Hashtable is thread-safe and can be shared between multiple threads (Synchronization HashTable is much slower than HashMap).
 */

//Initialization
HashMap<Integer, Integer> map = new HashMap<Integer, Integer>(); //<K, V>
Hashtable<Integer, Integer> table = new Hashtable<Integer, Integer>();

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#3 - Longest Substring Without Repeating Characters
//This is an easy way to: 1. check if c is in the map & 2. move the starting index to the latter possible position
start = Math.max(start, map.getOrDefault(c, 0)); //map.getOrDefault(c, 0) -> if find c in the map, return its value; else, return 0

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#17 - Letter Combinations of a Phone Number
//Convert char to int
int a = Integer.parseInt(String.valueOf(digits.charAt(i)));
int b = digits.charAt(i) - '0';

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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#30 - Substring with Concatenation of All Words
//Copy a whole HashMap
map1.putAll(map2);

//This method takes advantage of the no-duplicate property of HashMap, and map1.equals(map2) is faster than array comparison
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

//Method 2:
public static ArrayList<Integer> findSubstring(String s, String[] words) {
	ArrayList<Integer> res = new ArrayList<Integer>();
	int n = s.length(), m = words.length, k;
	if (n == 0 || m == 0 || (k = words[0].length()) == 0) {
		//assign value to k in if() -> avoid the possibility that words[] == null
		return res;
	}

	HashMap<String, Integer> wordDict = new HashMap<String, Integer>();
	for (String word : words) {
		if (wordDict.containsKey(word))
			wordDict.put(word, wordDict.get(word) + 1);
		else
			wordDict.put(word, 1); //make sure that if word exists, its value > 0; distinguish from non-existing word
	}

	int start, x, wordsLen = m * k;
	HashMap<String, Integer> currDict = new HashMap<String, Integer>();
	String test, temp;
	for (int i=0; i<k; i++) {
		currDict.clear();
		start = i;
		if (start + wordsLen > n) {
			return res;
		}
		
		for (int j=i; j+k<=n; j+=k) {
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
	//if found a substring, return its starting index
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
	if (x == 1)
		currDict.remove(key);
	else
		currDict.put(key, x - 1);
}
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
