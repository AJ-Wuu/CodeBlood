//Use long (as 64-bit integer) to avoid overflow
//Build an array of LinkedList: LinkedList<Integer>[] array = new LinkedList[n];

//Circular array: moving forward from the last element -> the first element, and vice versa
//                int nextIndex = (currentIndex + arr[currentIndex]) % arr.length;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Modify Arrays.sort()
//Note: compare() -> return 1, increasing; return 0, unchanged; return -1, decreasing
//Lambda:
Arrays.sort(array, (o1,o2)->(o1[0]-o2[0]));
//Comparator:
Arrays.sort(array, new Comparator<int[]>() {
    public int compare(int[] a, int[] b) {
        return (a[0] - b[0]);
    }
});

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Count all sub-arrays having sum divisible by k
public static int subCount(int arr[], int k) {
    //create auxiliary hash array to count frequency of remainders
    int mod[] = new int[k];
    Arrays.fill(mod, 0);

    //traverse original array and compute cumulative sum take remainder of this current cumulative sum and increase count by 1 for this remainder in mod[] array
    int currSum = 0;
    for (int i = 0; i < n; i++) {
        currSum += arr[i];
        mod[((currSum % k) + k) % k]++; //as the sum can be negative, taking modulo twice
    }

    int result = 0;
    for (int i = 0; i < k; i++) {
        //if there are more than one prefix sub-array with a particular mod value
        if (mod[i] > 1) {
            result += (mod[i] * (mod[i] - 1)) / 2;
        }
    }
    //add the elements which are divisible by k itself, i.e., the elements whose sum = 0
    result += mod[0];

    return result;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//#48 - Rotate Image
//rotate = transpose + reverse OR transfer between i, j, n-1-i, n-1-j

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Traffic Map
//Always remember to check the index first before using it (especially when moving through the array within the code)
if (directions[i][j] == 0 || directions[i][j] == 3 || directions[i][j] == 4) { //move right
    if (j + 1 < n && Tube[directions[i][j]].contains(directions[i][j+1])) { //remember to check j+1
        return canPass(directions, i, j+1, Tube, m, n);
    }
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#566 - Reshape the Matrix
int row = mat.length;
int col = mat[0].length;
result[i/c][i%c] = mat[i/col][i%col];

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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
    for (int i=0; i<length/2; i++) {
        //as swap moves between 2 elements and temp never changes back to initial setting, length/2 is enough to get all possible permutations
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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#912 - Sort an Array
//Count Sort: Put positive integers into countP[] with (valueOfInteger == index), and put the absolute value of negative integers into countN[].
//            Then combining elements of reverse countN[] and plain countP[] to get the sorted array.

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#392 - Beautiful Array
//For questions with new concepts like this ("for every i < j, there is no k with i < k < j such that nums[k] * 2 = nums[i] + nums[j]"),
//we should try to analyze its basic properties first, then deduce some underlined properties, and use those properties in the algorithm.
    
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#896 - Monotonic Array
Integer.compare(A[i], A[i+1]); //equals .compareTo() with previous declarations of A[i] and A[i+1] being Integer (not int)
//One Pass (Simple Variant) -> set two boolean variables increasing and decreasing
//                             if A[i] > A[i+1], increasing = false; if A[i] < A[i+1], decreasing = false
//                             return (increasing || decreasing)

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1095 - Find in Mountain Array
//Binary Search is enough, no need for Trinary
//1. Find peak; 2. Try to find target on the left side of the peak; 3. Try to find on the right side

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#73 - Set Matrix Zeroes
//If the problem can be solved in-place for an array, there is no need to involve a new data structure
//Method: use the first row and the first column as the marker
        //-> Check (and record as a flag) if the first row has 0.
        //-> If (matrix[i][j] == 0), matrix[i][0] = matrix[0][j] = 0. Then if (matrix[i][0] == 0 || matrix[0][j] == 0), matrix[i][j] = 0.
        //-> If the first row has 0, Arrays.fill(matrix[0], 0).

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#264 - Ugly Number II
//How to prevent adding numbers that have other prime factors? -> Remember that an ugly number must be multiplied by either 2, 3, or 5 from a smaller ugly number.
public static int nthUglyNumber(int n) {
    int[] ugly = new int[n];
    ugly[0] = 1;
    int index2 = 0, index3 = 0, index5 = 0;
    int factor2 = 2, factor3 = 3, factor5 = 5;
    for (int i=1; i<n; i++) {
        int min = Math.min(Math.min(factor2,factor3),factor5); //Min(factor2, factor3, factor5), shares the same idea of merging three lists
        ugly[i] = min;
        //Use three parallel if() to make sure the same elements from different factors are removed together (eg. 12 for 2 and 3)
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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#313 -Super Ugly Number
//Key: New ugly number is generated by multiplying a prime with previous generated ugly number
//Approach 1 (same idea as #264): Use index[] -> 
//                                this approach can be better if we use value[] to store all (primes[j] * ugly[index[j]]) to save time of duplicate mutiplication
    for (int j=0; j<primes.length; j++) {
        ugly[i] = Math.min(ugly[i], primes[j] * ugly[index[j]]);
    }
    for (int k=0; k<primes.length; k++) {
        if (ugly[i] == primes[k] * ugly[index[k]]) {
            index[k]++;
        }
    }
//Approach 2: Use PriorityQueue (possible but bad efficiency). Though we hope it would be O(k * n * log(k)) (k == primes.length), the ratio may not scale in terms of k,
//            but there's a m in front of the time complexity anyway, so it becomes O(m * n * log(k)). Therefore, the efficiency is worse, not better.

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1201 - Ugly Number III
//Calculate how many numbers from 1 to num are divisible by either a, b or c by using the formula:
//    (num / a) + (num / b) + (num / c) – (num / lcm(a, b)) – (num / lcm(b, c)) – (num / lcm(a, c)) + (num / lcm(a, b, c))

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#4 - Median of Two Sorted Arrays
public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int m = nums1.length;
    int n = nums2.length;
		
    if (m > n) {
        return findMedianSortedArrays(nums2, nums1);
    }
		
    int i = 0, j = 0, imin = 0, imax = m, half = (m + n + 1) / 2;
    double maxLeft = 0, minRight = 0;
    while (imin <= imax) {
        i = (imin + imax) / 2;
        j = half - i;
        if (j > 0 && i < m && nums2[j - 1] > nums1[i]) {
            imin = i + 1;
        }
        else if (i > 0 && j < n && nums1[i - 1] > nums2[j]) {
            imax = i - 1;
        }
        else{
            if (i == 0) {
                maxLeft = (double)nums2[j - 1];
            }
            else if (j == 0) {
                maxLeft = (double)nums1[i - 1];
            }
            else {
                maxLeft = (double)Math.max(nums1[i - 1], nums2[j - 1]);
            }
            break;
        }
    }
    if ((m + n) % 2 == 1) {
        return maxLeft;
    }
    if (i == m) {
        minRight = (double)nums2[j];
    }
    else if (j == n) {
        minRight = (double)nums1[i];
    }
    else {
        minRight = (double)Math.min(nums1[i], nums2[j]);
    }
		
    return (double)(maxLeft + minRight) / 2;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#53 - Maximum Subarray
//Key: MaxSoFar = Math.max(nums[i], MaxSoFar + nums[i])

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1509 - Minimum Difference Between Largest and Smallest Value in Three Moves
//Key: All moves must happen in the largest three elements and the smallest three elements, eg. [A,B,C,D,...,E,F,G,H] -> min(E-A, F-B, G-C, H-D)

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#904 - Fruit Into Baskets
//Translate: Longest subarray with at most two distinct elements
public int totalFruit(int[] fruits) {
    int start = 0;
    int maxLength = 0;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
    for (int end=0; end<fruits.length; end++) {
        map.put(fruits[end], end); //store the last index of the appearance
            
        if (map.size() > 2) {               
            start = Collections.min(map.values()) + 1;
            map.remove(fruits[start-1]);
        }
           
        maxLength = Math.max(maxLength, end - start + 1);
    }
        
    return maxLength;
}
