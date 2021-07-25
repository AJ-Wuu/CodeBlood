//#566 - Reshape the Matrix
int row = mat.length;
int col = mat[0].length;
result[i/c][i%c] = mat[i/col][i%col];

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1146 - Snapshot Array
class SnapshotArray {
    HashMap<Integer,Integer>[] maps;
    int snapID;
    HashMap<Integer,Integer> tempMap;
    
    public SnapshotArray(int length) {
        snapID = -1;
        maps = new HashMap[length];
        for(int i=0; i<length; i++) {
            maps[i]=new HashMap<>();
        }
        tempMap = new HashMap<>();
    }
    
    public void set(int index, int val) {
        tempMap.put(index,val);
    }
    
    public int snap() {
        snapID++;
        for (Map.Entry<Integer,Integer> m : tempMap.entrySet()) {
            maps[m.getKey()].put(snapID,m.getValue());
        }
        tempMap = new HashMap<>();
        return snapID;
    }
    
    public int get(int index, int snap_id) {
        while(snap_id >= 0){
            if(maps[index].containsKey(snap_id))
                 return maps[index].get(snap_id);
            snap_id--;
        }
        return 0;
    }
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#384 - Shuffle an Array
Random rand = new Random(); //the seed goes in the parentheses
                            //when the seed is specified, every time we stop and re-run the code it will get the same output;
                            //but if we don't stop and just run multiple times, the results will be different
for (int j=0; j<8; j++) {
    System.out.print(" " + rand.nextInt(100) + ", "); //rand.nextInt(size) generates an integer in [0, size)
}

//how to copy arrays without linking their addresses
object = Arrays.copyOf(nums,nums.length); //used for reset()
temp = Arrays.copyOf(nums,nums.length); //used for shuffle()
//if we do 
//   object = num;
//   temp = num;
//then object, temp and num share the same storing address, which means changing any one of them will change the other two simutaneously

public int[] shuffle() {
    Random rand = new Random();
    int index;
    for (int i=0; i<length/2; i++) { //as swap moves between 2 elements and temp never changes back to initial setting, length/2 is enough to get all possible permutations
        index = rand.nextInt(length);
        if (i != index) {
            temp = swap(temp, 0, index);
        }
    }
    return temp;
}

int i = (int) (12/2 + 0.5); // 6
int ii = (int) ((double)12/2 + 0.5); // 6
int j = (int) (21/2 + 0.5); // 10
int jj = (int) ((double)21/2 + 0.5); // 11

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#189 - Rotate Array
//Best way: Reverse (whole and partial array)
//Original List                   : 1 2 3 4 5 6 7
//After reversing all numbers     : 7 6 5 4 3 2 1
//After reversing first k numbers : 5 6 7 4 3 2 1
//After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
public void rotate(int[] nums, int k) {
    k %= nums.length;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
}

public void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#912 - Sort an Array
//Count Sort: Put positive integers into countP[] with (valueOfInteger == index), and put the absolute value of negative integers into countN[].
//            Then combining elements of reverse countN[] and plain countP[] to get the sorted array.

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#525 - Contiguous Array
//This is to count how many 0-1 are there, including 0-1 & 00-11 & 000-111 & etc., but they don't need to be joined nor sorted.
//Eg. 0100101 -> 6; 0110 -> 4
//Use a HashMap mapmap to store the entries in the form of (count, index).
//If the nums[i] == 0, counts -1; else, counts 1.
public int findMaxLength(int[] nums) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);
    int maxlen = 0, count = 0;
    for (int i = 0; i < nums.length; i++) {
        count = count + (nums[i] == 1 ? 1 : -1);
        if (map.containsKey(count)) { //already contains this key, meaning that an opposite number (a 1 after a list of 0 or vise-versa) is just caught.
            maxlen = Math.max(maxlen, i - map.get(count)); //(i - map.get(count)) == (i - index) -> the sequence from index to i forms an array with equal number of 0-1
        }
        else {
            map.put(count, i);
        }
    }
    return maxlen;
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#392 - Beautiful Array
//For questions with new concepts like this ("for every i < j, there is no k with i < k < j such that nums[k] * 2 = nums[i] + nums[j]"),
//we should try to analyze its basic properties first, then deduce some underlined properties, and use those properties in the algorithm.
    
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#896 - Monotonic Array
Integer.compare(A[i], A[i+1]); //equals .compareTo() with previous declarations of A[i] and A[i+1] being Integer (not int)
//One Pass (Simple Variant) -> set two boolean variables increasing and decreasing
//                             if A[i] > A[i+1], increasing = false; if A[i] < A[i+1], decreasing = false
//                             return (increasing || decreasing)

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#565 - Array Nesting
//For questions concerning inner recycling (eg. "s[k] = {nums[k], nums[nums[k]], nums[nums[nums[k]]], ... }"), we first need to think about using boolean visited[].
//Another method is to combine the original array with visited[] -> for example, marking the visited element as Integer.MAX_VALUE
public int arrayNesting(int[] nums) {
    int res = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != Integer.MAX_VALUE) {
            int start = nums[i], count = 0;
            while (nums[start] != Integer.MAX_VALUE) {
                int temp = start;
                start = nums[start];
                count++;
                nums[temp] = Integer.MAX_VALUE;
            }
            res = Math.max(res, count);
        }
    }
    return res;
}

Arrays.sort(a); //this sorts array "a" in an increasing order -> a[0] is the minimum and a[a.length-1] is the maximum

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#845 - Longest Mountain in Array
//The order in if() / while() sentence is crucial. Eg:
if (end + 1 < arr.length && arr[end] < arr[end + 1]) {;} //this line will first evaluate (end + 1 < n), and if it's false, it will go on without problem
if (arr[end] < arr[end + 1] && end + 1 < arr.length) {;} //however, this line will come up with an error of arr[] index out of bounds,
                                                         //as it goes for (arr[end] < arr[end+1]) first, an (end + 1) might be larger than (arr.length - 1)
while (base < N) {
    int end = base; //update the end
    if (end + 1 < N && A[end] < A[end + 1]) { //find left boundary
        while (end + 1 < N && A[end] < A[end + 1]) { //get to the peak
            end++;
        }
        if (end + 1 < N && A[end] > A[end + 1]) { //check if end is the peak now
            while (end + 1 < N && A[end] > A[end + 1]) { //go through the right boundary
                end++;
            }
            ans = Math.max(ans, end - base + 1); //after a mountain, record the current longest one
        }
    }
    base = Math.max(end, base + 1); //update the base
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1095 - Find in Mountain Array
//Binary Search is enough, no need for Trinary
//1. Find peak; 2. Try to find target on the left side of the peak; 3. Try to find on the right side

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#954 - Array of Doubled Pairs
//Greedy
//1. Delete the latter index element before the former one, as a dynamic data structure's index will change if a former element is removed.
//   Eg. ArrayList -> (j=i+1; j<N; j++) -> remove(j) before remove(i)
//2. Always choose "*" (multiplication) over "/" (division), as division will cause rounding-down results in several data type (-5/2 == -2)
//3. Pay attention to negative-number situations

//Sorting implementary Methods
//Comparable (Arrays only):
public class Employee implements Comparable<Employee> {
    @Override
    public int compareTo(Employee employee) {
        return this.salary - employee.salary;
    }
}
Employee[] employees = new Employee[4];
...;
Arrays.sort(employees);

//Comparator (Arrays & ArrayList):
public class EmployeeSalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee emp1, Employee emp2) {
        return emp1.getSalary() - emp2.getSalary();
    }
}
//Arrays ->
Employee[] newEmployees = new Employee[4];
...;
Arrays.sort(newEmployees, new EmployeeSalaryComparator());
Arrays.sort(B, Comparator.comparingInt(Math::abs)); //// B as Integer[], sorted by absolute value
//ArrayList ->
ArrayList<Integer> list = new ArrayList<Integer>();
...;
Collections.sort(list, new EmployeeSalaryComparator());
list.sort(Comparator.naturalOrder());
list.sort(Comparator.reverseOrder());

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#41 - First Missing Positive
//We don't care negative and duplicate numbers, so we try to make the original A[] as a positive standard array like [1,2,3,...] (Key Point: A[i] = i + 1):
    //1. judge if A[i] is positive, ignore negative ones;
    //2. judge if A[i] is within its "capacity", ignore the outsiding ones;
    //3. judge if A[i] = i + 1, ignore the ones at the right place.
//After swapping A[] to standard, we go over A[] to find the first index where A[i] != i + 1, and (i+1) is the final result.
if (A[i] >= 1 && A[i] <= A.length && A[A[i]-1] != A[i]) {
    //Why (A[A[i]-1] != A[i]), not (A[i]-1 != i)?
    //We need to swap elements, so we need to make sure their indexes.
    swap(A, i, A[i]-1); //make it A[i] = i + 1
}
else {
    i++;
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#73 - Set Matrix Zeroes
//If the problem can be solved in-place for an array, there is no need to involve a new data structure
//Method: use the first row and the first column as the marker
        //-> Check (and record as a flag) if the first row has 0.
        //-> If (matrix[i][j] == 0), matrix[i][0] = matrix[0][j] = 0. Then if (matrix[i][0] == 0 || matrix[0][j] == 0), matrix[i][j] = 0.
        //-> If the first row has 0, Arrays.fill(matrix[0], 0).

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#264 - Ugly Number II
//How to prevent adding numbers that have other prime factors? -> Remember that an ugly number must be multiplied by either 2, 3, or 5 from a smaller ugly number.
public static int nthUglyNumber(int n) {
    int[] ugly = new int[n];
    ugly[0] = 1;
    int index2 = 0, index3 = 0, index5 = 0;
    int factor2 = 2, factor3 = 3, factor5 = 5;
    for (int i=1; i<n; i++) {
        int min = Math.min(Math.min(factor2,factor3),factor5);
        ugly[i] = min;
        if (factor2 == min) {
            factor2 = 2 * ugly[++index2]; //find the smallest unused ugly number for this factor
        }
        if (factor3 == min) {
            factor3 = 3 * ugly[++index3];
        }
        if (factor5 == min) {
            factor5 = 5 * ugly[++index5];
        }
    }
    return ugly[n-1];
}
