/**
 * @author AJWuu
 */

package backtracking;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BackTracking {
    
	//#78, #90 - Subsets I, II
	public static List<List<Integer>> subsets(int[] nums) {
	    List<List<Integer>> list = new LinkedList<>();
	    Arrays.sort(nums);
	    backtrackSubset(list, new LinkedList<>(), nums, 0);
	    return list;
	}

	private static void backtrackSubset(List<List<Integer>> list , LinkedList<Integer> tempList, int[] nums, int start){
	    list.add(new LinkedList<>(tempList));
	    for (int i=start; i<nums.length; i++) {
	        tempList.add(nums[i]);
	        backtrackSubset(list, tempList, nums, i+1);
	        tempList.removeLast(); //tempList is of LinkedList type
	    }
	}
	
	private void backtrackSubsetWithDuplicates(List<List<Integer>> list, LinkedList<Integer> tempList, int [] nums, int start){
	    list.add(new LinkedList<>(tempList));
	    for (int i=start; i<nums.length; i++){
	        if (i>start && nums[i]==nums[i-1]) {
	        	continue; //skip duplicates (already existed)
	        }
	        tempList.add(nums[i]);
	        backtrackSubsetWithDuplicates(list, tempList, nums, i+1);
	        tempList.removeLast();
	    }
	}
	
	//#47 - Permutations II
	private void backtrackPermutation(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
	    if (tempList.size() == nums.length){
	        list.add(new LinkedList<>(tempList));
	    }
	    else{
	        for (int i=0; i<nums.length; i++){
	            if (used[i]) {
	            	continue;
	            }
	            used[i] = true; 
	            tempList.add(nums[i]);
	            backtrackPermutation(list, tempList, nums, used);
	            used[i] = false; 
	            tempList.remove(tempList.size() - 1); //tempList is of List type
	            while (i+1<nums.length && nums[i]==nums[i+1]) {
	            	i++; //skip duplicates
	            }
	        }
	    }
	}
	
	//#39 - Combination Sum
	private void backtrackAllowReuse(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start){
	    if (remain < 0) {
	    	return ;
	    }
	    else if (remain == 0) {
	    	list.add(new LinkedList<>(tempList));
	    }
	    else { 
	        for(int i=start; i<nums.length; i++){
	            tempList.add(nums[i]);
	            backtrackAllowReuse(list, tempList, nums, remain - nums[i], i); //NOT i+1 because we can reuse the same elements
	            tempList.remove(tempList.size() - 1);
	        }
	    }
	}

	public static void main(String args[]) {
		subsets(new int[] {1,1,2,2,3});
	}

}
