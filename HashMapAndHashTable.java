/**
 * Bucket == LinkedList
 * Load Factor: 16 initial buckets, doubles the existing capacity every time it reaches more than 75% of its capacity
 * Key: Not Primitive, but Object -> we need the Key.hashCode() to calculate the bucket index = Key.hashCode() & (length - 1)
 * map.put(K, V): K's bucket is empty -> put it in;
 *                           not empty -> K.equals(K_existed) == true, replace (K_exisited, V_existed) with (K, V); 
 *                                                               false, add (K, V) next to (K_exisited, V_existed)
 *
 * Why HashMap?
 * Its process of getting the value using key from HashMap is very very efficient & fast. The get(“key”) method of HashMap has time complexity of O(1) in general.
 * 
 */

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

