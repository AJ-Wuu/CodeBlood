 * @author AJWuu
 */

package practice;

import java.util.Arrays;
import java.util.LinkedList;

public class LongestIncreasingSubsequence {

  //#673 - Number of Longest Increasing Subsequence
	//Time Complexity: O(NlogN)
	
	public static LinkedList<int[]> LIS(int[] array) {
		LinkedList<int[]> list = new LinkedList<int[]>();
		list.add(new int[] {array[0]}); //Case 1: No list. Create one.
		for (int i=1; i<array.length; i++) {
			if (array[i] > list.getLast()[list.size()-1]) { //Case 2: Extend
				int[] newSubsequence = Arrays.copyOf(list.getLast(), list.size()+1);
				newSubsequence[newSubsequence.length-1] = array[i];
				list.add(newSubsequence);
			}
			for (int j=list.size()-1; j>0; j--) { //Case 3: Extend and Discard
				if (array[i] < list.get(j)[j] && array[i] > list.get(j-1)[j-1]) {
					int[] updateSubsequence = Arrays.copyOf(list.get(j-1), j+1);
					updateSubsequence[j] = array[i];
					list.set(j, updateSubsequence);
					break;
				}
			}
		}
		//eg. {0, 8, 4, 12, 2, 10}
		//Case 1: {0}
		//Case 2: {0}, {0,8}
		//Case 3: {0}, {0,4} (discard {0,8})
		//Case 2: {0}, {0,4}, {0,4,12}
		//Case 3: {0}, {0,2}, {0,4,12} (discard {0,4})
		//Case 3: {0}, {0,2}, {0,2,10} (discard {0,4,12})
		return list;
	}
	
	public static void main(String args[]) {
		int[] array = new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
		int[] result = LIS(array).getLast();
		for (int i=0; i<result.length; i++) {
			System.out.print(result[i] + " ");
		}
	}

}
