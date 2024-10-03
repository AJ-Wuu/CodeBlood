# ArrayList & LinkedList
|            | Internal structure  |  Manipulation                              |  Interface       |  Better at                   |
|:----------:|--------------------:|-------------------------------------------:|-----------------:|-----------------------------:|
| ArrayList  |  dynamic array      |  slow (array needs shift for removal)   |  list only       |  storing and accessing data  |
| LinkedList | doubly linked list  |  fast (DLL needs no shift for removal)  |  list and deque  |  manipulating data           |

|            | Get by index | Search by value | Insert at last index | Insert at given index | Remove by index | Remove by value |
|:----------:|-------------:|----------------:|---------------------:|----------------------:|----------------:|----------------:|
| ArrayList  | O(1)         | O(N)            | O(N) with array copy | O(N)                  | O(N)            | O(N)            |
| LinkedList | O(N)         | O(N)            | O(1)                 | O(N)                  | O(N)            | O(N)            |
| Preference | ArrayList    | ArrayList       | LinkedList           | LinkedList            | LinkedList      | LinkedList      |

## Tips
* Always remember to use two pointers moving along the list / queue if needed for elements comparasion / selection / etc.
* Build an array of LinkedList: ```LinkedList<Integer>[] array = new LinkedList[n];```
* Build empty List<List<Integer>>: ```List<List<Integer>> result = new LinkedList<>(); OR List<List<Integer>> result = new LinkedList<List<Integer>>();```
* Build list using Arrays: ```Arrays.asList(x,y,z);```
  * `List.of()` is immutable, meaning its size and elements cannot be modified after creation, and it doesn't allow Null
  * `Arrays.asList()` is fix-sized but modifiable, meaning its element can be changes (and it is **backed by the original array, so any changes to elements of the list will affect the underlying array and vice versa**), and it allows Null
* Convert LinkedList<int[]> to int[][]: ```list.toArray(new int[list.size()][]);```
* Convert int[] to LinkedList<Integer>: ```Arrays.asList(array);```
* Initialize list: ```List<Double> temp = new LinkedList<Double>(Arrays.asList(1.0, 2.0));```
* Get the index of the max value in a list: ```IntStream.range(0, list.size()).reduce(0, (a, b) -> list.get(a) < list.get(b) ? b : a);```
* Reverse the list in itself: ```Collections.reverse(list); // return void```
