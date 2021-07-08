//#566 - Reshape the Matrix
int row = mat.length;
int col = mat[0].length;
result[i/c][i%c] = mat[i/col][i%col];

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#189 - Rotate Array
//Best way: Reverse (whole and partial array)
//Original List                   : 1 2 3 4 5 6 7
//After reversing all numbers     : 7 6 5 4 3 2 1
//After reversing first k numbers : 5 6 7 4 3 2 1
//After revering last n-k numbers : 5 6 7 1 2 3 4 --> Result
class Solution {
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
}

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#
//Count Sort: Put positive integers into countP[] with (valueOfInteger == index), and put the absolute value of negative integers into countN[].
//            Then combining elements of reverse countN[] and plain countP[] to get the sorted array.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#525 - Contiguous Array
//This is to count how many 0-1 are there, including 0-1 & 00-11 & 000-111, but they don't need to be joined.
//Use a HashMap mapmap to store the entries in the form of (index, count).
//If the nums[i] == 0, counts -1; else, counts 1.
public class Solution {
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
}

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
