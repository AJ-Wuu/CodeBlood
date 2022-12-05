// Collections = List + Set + Map + Stack + Queue
Collections.addAll(Collection<? extends E> c);
Collections.addAll(int index, Collection<? extends E> c);

// List = ArrayList + LinkedList
List<data_type> list_name = new ArrayList<>(Arrays.asList(Object o1, Object o2, …, Object on));
List<data_type> list_name = new ArrayList<>(map.values());
List<data_type> list_name = new ArrayList<>(Collection c);

// Set = HashSet (unordered) + LinkedHashSet (unordered) + TreeSet (ordered)
Set<data_type> set_name = new HashSet<data_type>(Arrays.asList(Object o1, Object o2, …, Object on));
Set<data_type> set_name = new HashSet<data_type>(map.values());
Set<data_type> set_name = new HashSet<data_type>(Collection c);

// Map = HashMap + Hashtable + IdentityHashMap + WeakHashMap + LinkedHashMap + TreeMap
// DOES NOT inherit the Collection interface (map cannot be directly passed to extension, but map.keys() and map.values() can)
// HashMap: not thread-safe, no method is synchronized, Null is allowed for key and value, judge key by equivalence, strong reference, unordered
// Hashtable: thread-safe, every method is synchronized, Null is not allowed for key or value
// IdentityHashMap: judge key by identity (eg. int key1 = 256, key2 = 256 -- key1 and key2 are regarded as different keys)
// WeakHashMap: weak reference, Garbage Collector dominates (eg. map.put(d,"Hi"); d = null; System.gc(); -- size = 0, 1, 0)
// TreeMap: ordered, manage keys with Red-Black Tree
Map<key_type, value_type> map_name = new HashMap<key_type, value_type>(Map.of(Key k1, Value v1, Key k2, Value v2, ..., Key kn, Value vn)); 

// Stack
Stack<data_type> stack_name = new Stack<data_type>();

// Queue = PriorityQueue + ArrayDeque + LinkedList
// ArrayDeque == Array Double Ended Queue == Array Deck:
//            can add or remove an element from both sides of the queue, thread-safe, Null prohibited
//            faster than Stack when used as a stack, faster than LinkedList when used as a queue
Queue<data_type> queue_name = new PriorityQueue<data_type>(Arrays.asList(Object o1, Object o2, …, Object on));
Queue<data_type> queue_name = new PriorityQueue<data_type>(map.values());
Queue<data_type> queue_name = new PriorityQueue<data_type>(Collection c);
