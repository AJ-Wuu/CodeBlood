 * @author AJWuu
 */

package backtracking;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BackTracking {
    
	public static List<List<Integer>> subsets(int[] nums) {
	    List<List<Integer>> list = new LinkedList<>();
	    Arrays.sort(nums);
	    backtrack(list, new LinkedList<>(), nums, 0);
	    return list;
	}

	private static void backtrack(List<List<Integer>> list , List<Integer> tempList, int[] nums, int start){
	    list.add(new LinkedList<>(tempList));
	    for (int i=start; i<nums.length; i++) {
	        tempList.add(nums[i]);
	        backtrack(list, tempList, nums, i+1);
	        tempList.remove(tempList.size()-1);
	    }
	}

	public static void main(String args[]) {
		subsets(new int[] {1,1,2,2,3});
	}

}
