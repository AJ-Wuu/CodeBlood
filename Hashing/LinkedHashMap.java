/*
 * LinkedHashMap = HashMap + a Doubly-Linked List running through all of its entries
 * Advantages:
 *     compared to HashMap and Hashtable -- predictable iteration order
 *                                          normally based on keys' insertion order, and not affected by re-insertion
 *     compared to TreeMap -- a lower time complexity for operations like put(), containsKey()
 *                            (TreeMap requires logarithmic time to add / locate elements in the tree)
 */

import java.util.LinkedHashMap;

