# Sort
## Doubly-Linked List with Node
```java
// #729 - My Calendar I
class MyCalendar {
    class Node {
        int start;
        int end;
        Node left, right;

        public Node(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    Node root;

    public MyCalendar() {
        root = null;
    }

    public boolean book(int start, int end) {
        if (root == null) {
            root = new Node(start, end);
            return true;
        }
        Node curr = root;
        while (curr != null) {
            if (end <= curr.start) {
                if (curr.left == null) {
                    curr.left = new Node(start, end);
                    return true;
                }
                curr = curr.left;
            } else if (start >= curr.end) {
                if (curr.right == null) {
                    curr.right = new Node(start, end);
                    return true;
                }
                curr = curr.right;
            } else
                return false;
        }
        return false;
    }
}
```

## TreeMap
```java
// #731 - My Calendar II
private TreeMap<Integer, Integer> map;

public MyCalendarTwo() {
    map = new TreeMap<>();
}

public boolean book(int start, int end) {
    map.put(start, map.getOrDefault(start, 0) + 1); // start is 1
    map.put(end, map.getOrDefault(end, 0) - 1);     // end is -1
    int count = 0;
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        count += entry.getValue(); // when any existing interval is done, count will be offset by the +1 and -1
        if (count > 2) {
            map.put(start, map.get(start) - 1);
            if (map.get(start) == 0) {
                map.remove(start);
            }
            map.put(end, map.get(end) + 1);
            if(map.get(end) == 0) {
                map.remove(end);
            }
            return false;
        }
    }
    return true;
}
```
