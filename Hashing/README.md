# Hashing
## Design a HashMap
* Goal: speed up the lookup time of data to a hopefully O(1) time
* Key:
  1. uses Array as it comes naturally with index lookups
     * size to be at least equal to the number of entries
  2. needs to translate the index from other non-index value, like a string
  3. has a hasing function that converts data into a randomized but reproduceable integer version of themselves
     * randomize enough to limit collisions as much as possible to keep down the average lookup time complexity
  4. deals with collisions by making each element of the array as a linked list
* Implementation
```java
class ListNode {
    int key, val;
    ListNode next;

    public ListNode(int key, int val, ListNode next) {
        this.key = key;
        this.val = val;
        this.next = next;
    }
}

class MyHashMap {
    static final int size = 19997;
    static final int mult = 12582917;
    ListNode[] data;
    
    public MyHashMap() {
        this.data = new ListNode[size];
    }

    private int hash(int key) {
        return (int)((long)key * mult % size);
    }

    public void put(int key, int val) {
        remove(key);
        int h = hash(key);
        ListNode node = new ListNode(key, val, data[h]);
        data[h] = node;
    }

    public int get(int key) {
        int h = hash(key);
        ListNode node = data[h];
        while (node != null) {
            if (node.key == key) {
                return node.val;
            }
            node = node.next;
        }
        return -1;
    }

    public void remove(int key) {
        int h = hash(key);
        ListNode node = data[h];
        if (node == null) {
            return;
        }
        if (node.key == key) {
            data[h] = node.next;
        }
        else {
            while (node != null) {
                if (node.next.key == key) {
                    node.next = node.next.next;
                    return;
                }
                node = node.next;
            }
        }
    }
}
```
