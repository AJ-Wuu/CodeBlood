// Immutable: Map.of(...) vs Dynamic: new HashMap<>(...)
// try to modify an immutable collection will get java.lang.UnsupportedOperationException

// Check if (myCollection == null || myCollection.size() == 0)
CollectionUtils.isEmpty(myCollection);

// Combine two collections of the same type using flatMap()
Set<String> combined = Stream.of(newStringSet, oldStringSet).flatMap(Set::stream).collect(toSet());

/**
 * Sorting
 */
// List - accept index, accept duplicate
List<Integer> sortedList = new ArrayList<>();
Collections.sort(sortedList, newComparator);

list.sort((x, y) -> x.length() - y.length());

public class OverrideListSort {
    static class Employee{
        int id;
        String name;
        int salary;

        public Employee(String name, int salary) {
            this.name = name;
            this.salary = salary;
            id = temp++;
        }
    }

    public void getEmployeesAlphabetical(List<Employee> list) {
        // Sort the list by employee name
        Collections.sort(list, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.name.compareTo(o2.name);
            }
        });
    }

    public void getEmployeeWithHighestSalary(List<Employee> list) {
        // Sort the list as per employee name
        Collections.sort(list, new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o2.salary-o1.salary;
            }
        });
    }

    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Carl", 10000));
        employeeList.add(new Employee("Andy", 20000));
        employeeList.add(new Employee("Bob", 30000));
        employeeList.add(new Employee("Drake", 5000));

        OverrideListSort o = new OverrideListSort(); // Important!!!
        o.getEmployeesAlphabetical(employeeList);
        o.getEmployeeWithHighestSalary(employeeList);
    }
}

// Map - index == key, no duplicate
// Loop Performance Analysis
// 1. only need keys or values: use `keySet()` or `values()`
// 2. otherwise: use `entrySet()`
//    * do NOT use key then fetch value with `get(key)` -- based on the implementation of the map, can be 20% - 200% slower
//    * as each `get(key)` requires that the hashCode() and equals() methods of the key object be evaluated
class CustomizedHashMap implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
        return -o1.getValue().compareTo(o2.getValue());
    }
}

public class OrderedLinkedHashMap {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        
        map.put("a", 11);
        map.put("B", 12);
        map.put("c", 3);
        map.put("d", 4);
        map.put("e", 5);
        map.put("f", 6);
        map.put("g", 7);
        map.put("h", 8);
        map.put("i", 9);
        map.put("j", 3);
        map.put("k", 2);
        map.put("l", 1);

        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entries,new CustomizedHashMap());

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
            System.out.print( sortedMap.put(entry.getKey(), entry.getValue())+" ");
        }
    }
}

// Set - no index, no duplicate
Comparator<Employee> newComparator = new Comparator<Employee>() {
    @Override
    public int compare(Employee o1, Employee o2) {
        return o2.salary-o1.salary;
    }
};
SortedSet<Employee> employeeSet = new TreeSet<Employee>(newComparator);

// Queue - no index, accept duplicate
// NOT sorted inside but stored all values in a heap
// output in order with `poll()` and each `poll()` takes O(logN)
PriorityQueue<Employee> employeeQueue = new PriorityQueue<Employee>(newComparator);

/**
 * Transform
 */
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
Deque<data_type> deque_name = new ArrayDeque<data_type>(Arrays.asList(Object o1, Object o2, …, Object on));
Deque<data_type> deque_name = new ArrayDeque<data_type>(map.values());
Deque<data_type> deque_name = new ArrayDeque<data_type>(Collection c);
