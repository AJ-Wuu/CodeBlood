//#566 - Reshape the Matrix
//1. int row = mat.length; int col = mat[0].length;
//2. result[i/c][i%c] = mat[i/col][i%col];

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#1146 - Snapshot Array
class SnapshotArray {
    HashMap<Integer,Integer>[] maps;
    int snapID;
    HashMap<Integer,Integer> tempMap;
    
    public SnapshotArray(int length) {
        snapID = -1;
        maps = new HashMap[length];
        for(int i=0; i<length; i++){
            maps[i]=new HashMap<>();
        }
      tempMap = new HashMap<>();
    }
    
    public void set(int index, int val) {
        tempMap.put(index,val);
    }
    
    public int snap() {
        snapId++;
        for(Map.Entry<Integer,Integer>m:tempMap.entrySet()){
            maps[m.getKey()].put(snapId,m.getValue());
        }
        tempMap=new HashMap<>();
        return snapId;
    }
    
    public int get(int index, int snap_id) {
        while(snap_id>=0){
             if(maps[index].containsKey(snap_id))
                 return maps[index].get(snap_id);
            snap_id--;
        }
        return 0;
    }
}

