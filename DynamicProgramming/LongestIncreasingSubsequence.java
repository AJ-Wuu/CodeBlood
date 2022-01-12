/**
 * @author AJWuu
 */

package practice;

import java.util.Arrays;
import java.util.LinkedList;

public class LongestIncreasingSubsequence {

	//#673 - Number of Longest Increasing Subsequence
	public static int findNumberOfLIS(int[] array) {
		int max = 0; 
		int[] size = new int[array.length];
		int[] count = new int[array.length];

		for (int i=0; i<array.length; i++) {             
			for (int j=i-1; j>=0; j--) {
				if (array[j] < array[i]) {
					if (size[j] + 1 > size[i]) {
						size[i] = size[j] + 1;
						count[i] = count[j];
					}
					else if (size[j] + 1 == size[i]) {
						count[i] += count[j]; //KEY
					}                                        
				}
			}
			if (size[i] == 0) {
				size[i] = 1;
				count[i] = 1;
			}
			max = Math.max(max, size[i]);            
		}

		int result = 0;
		for (int i=0; i<array.length; i++) {
			if (size[i] == max) {
				result += count[i];
			}
		}
		return result;                
	}

	//#300 - Number of Longest Increasing Subsequence
	//Time Complexity: O(NlogN)
	public static int lengthOfLIS(int[] array) {
		int[] tails = new int[array.length]; //the last element of each subsequence, tails[] is a possible LIS
		int size = 0;
		for (int x : array) {
			int i = 0, j = size;
			while (i != j) {
				int m = (i + j) / 2; //binary search, as tails[] is ascending
				if (tails[m] < x) {
					i = m + 1;
				}
				else {
					j = m;
				}	
			}
			tails[i] = x;
			if (i == size) {
				size++;
			}
		}
		return size;
	}

	//eg. {0, 8, 4, 12, 2, 10}
	//Case 1: {0}
	//Case 2: {0}, {0,8}
	//Case 3: {0}, {0,4} (discard {0,8})
	//Case 2: {0}, {0,4}, {0,4,12}
	//Case 3: {0}, {0,2}, {0,4,12} (discard {0,4})
	//Case 3: {0}, {0,2}, {0,2,10} (discard {0,4,12})
	public static LinkedList<int[]> LIS(int[] array) {
		LinkedList<int[]> list = new LinkedList<int[]>();
		list.add(new int[] {array[0]}); //Case 1: No list. Create one.
		for (int i=1; i<array.length; i++) {
			if (array[i] > list.getLast()[list.size()-1]) { //Case 2: Extend
				int[] newSubsequence = Arrays.copyOf(list.getLast(), list.size()+1);
				newSubsequence[newSubsequence.length-1] = array[i];
				list.add(newSubsequence);
			}
			if (array[i] < list.getFirst()[0]) { //Case 3: Extend and Discard
				list.set(0, new int[] {array[i]});
			}
			else {
				for (int j=list.size()-1; j>0; j--) {
					if (array[i] < list.get(j)[j] && array[i] > list.get(j-1)[j-1]) {
						int[] updateSubsequence = Arrays.copyOf(list.get(j-1), j+1);
						updateSubsequence[j] = array[i];
						list.set(j, updateSubsequence);
						break;
					}
				}
			}
		}
		return list;
	}

	public static void main(String args[]) {
		int[] array = new int[] {10,9,2,5,3,7,101,18};
		System.out.println("The length of LIS is: " + lengthOfLIS(array));

		System.out.print("One possible LIS is: ");
		int[] result = LIS(array).getLast();
		for (int i=0; i<result.length; i++) {
			System.out.print(result[i] + " ");
		}

		System.out.println("\nThere are " + findNumberOfLIS(array) + " different possible LIS");
	}

}
