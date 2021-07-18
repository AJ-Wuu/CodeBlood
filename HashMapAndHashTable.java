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
Map<Integer, Integer> map = new HashMap();
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

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------
