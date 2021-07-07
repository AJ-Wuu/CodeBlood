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


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
