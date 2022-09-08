/*
 * LinkedHashMap = HashMap + a Doubly-Linked List running through all of its entries
 * Advantages:
 *     compared to HashMap and Hashtable -- predictable iteration order
 *                                          normally based on keys' insertion order, and not affected by re-insertion
 *     compared to TreeMap -- a lower time complexity for operations like put(), containsKey()
 *                            (TreeMap requires logarithmic time to add / locate elements in the tree)
 */

import java.util.LinkedHashMap;

public static void play() {
    LinkedHashMap<String, Integer> lmap = new LinkedHashMap<String, Integer>();
    lmap.put("A", 1);
    lmap.put("B", 2);
    lmap.put("C", 3);
    lmap.put("D", 4);
    lmap.put("a", 11);
    lmap.put("b", 22);
    lmap.put("c", 33);
    lmap.put("d", 44);

    Iterator<String> iter = lmap.keySet().iterator();
    while (iter.hasNext()) {
        System.out.print(iter.next() + " "); //output = "A B C D a b c d "
    }
}
