/* java.util.set<E>
 * boolean add(E e): true if e is not in the set yet 
 * boolean remove(Object o): true if o is in the set and is removed
 * Iterator<E> iterator(): iterator over elements by hasNext(), next()
 * 
 * data structure  |  add / insert  |  contains / search  |  delete / remove  |  iterator                 |  note
 * array           |  O(N)          |  O(N)               |  O(N)             |  ordered, but not sorted  |
 * sorted array    |  O(N)          |  O(logN)            |  O(N)             |  sorted                   |  elements need to be comparable
 * linkedlist      |  O(1)          |  O(N)               |  O(N)             |  ordered, but not sorted  |
 * BST / RBT       |  O(logN)       |  O(logN)            |  O(logN)          |  sorted (inorder)         |  elements need to be comparable
 * hash table      |  O(1)          |  O(1)               |  O(1)             |  NOT ordered              |  need good hash function for element type
 * trie            |  O(length)     |  O(length)          |  O(length)        |  sorted                   |  represent data as a sequence of characters
 */
