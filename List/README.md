# ArrayList & LinkedList
|            | Internal structure  |  Manipulation                              |  Interface       |  Better at                   |  Performance |
|:----------:|--------------------:|-------------------------------------------:|-----------------:|-----------------------------:|-------------:|
| ArrayList  |  dynamic array      |  slow (array needs shift for removal)   |  list only       |  storing and accessing data  |  O(1)        |
| LinkedList | doubly linked list  |  fast (DLL needs no shift for removal)  |  list and deque  |  manipulating data           |  O(n)        |

## Tips
* Always remember to use two pointers moving along the list / queue if needed for elements comparasion / selection / etc.
* Build an array of LinkedList: ```LinkedList<Integer>[] array = new LinkedList[n];```
* Build empty List<List<Integer>>: ```List<List<Integer>> result = new LinkedList<>(); OR List<List<Integer>> result = new LinkedList<List<Integer>>();```
* Build list using Arrays: ```Arrays.asList(x,y,z);```
* Convert LinkedList<int[]> to int[][]: ```list.toArray(new int[list.size()][]);```
* Convert int[] to LinkedList<Integer>: ```Arrays.asList(array);```
* Initialize list: ```List<Double> temp = new LinkedList<Double>(Arrays.asList(1.0, 2.0));```
* Get the index of the max value in a list: ```IntStream.range(0, list.size()).reduce(0, (a, b) -> list.get(a) < list.get(b) ? b : a);```
* Reverse the list in itself: ```Collections.reverse(list); //return void```
