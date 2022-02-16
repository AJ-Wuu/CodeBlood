/**
 * @author AJWuu
 */

package search;

public class BinarySearch {

	/*
	 * Key of Binary Search:
	 * 1. Find mid <- Search by Index or Search by Range
	 * 2. Update start or end with mid
	 * 
	 * Complexity: O(logN)
	 */
	
	public static int findRotationIndex(int[] nums) {
		int i = 0, j = nums.length-1;
		while (j - i > 1) { //mid != i && mid != j
			int mid = (i + j) / 2;
			if (nums[mid] < nums[j]) { //put this condition first, so when the array is in ascending order, we update j
				j = mid;
			}
			else if (nums[i] < nums[mid]) {
				i = mid;
			}
		}
		if (j - i == 1) {
			if (nums[j] < nums[i]) {
				return j;
			}
			else {
				return i;
			}
		}
		else { //only one element in the array
			return j;
		}
	}

	public static int searchFirst(int[] nums, int target) {
		int i = 0, j = nums.length-1;
		int result = -1;
		while (i <= j) {
			int mid = (i+j)/2;
			if (nums[mid] < target) {
				i = mid + 1;
			}
			else if (nums[mid] > target) {
				j = mid - 1;
			}
			else {
				result = mid;
				j = mid - 1;
			}
		}
		return result;
	}

	public static int searchLast(int[] nums, int target) {
		int i = 0, j = nums.length-1;
		int result = -1;
		while (i <= j) {
			int mid = (i+j)/2;
			if (nums[mid] < target) {
				i = mid + 1;
			}
			else if (nums[mid] > target) {
				j = mid - 1;
			}
			else {
				result = mid;
				i = mid + 1;
			}
		}
		return result;
	}

	public static int[] searchRange(int[] nums, int target) {
		int[] result = new int[2];
		result[0] = searchFirst(nums, target);
		result[1] = searchLast(nums, target);
		return result;
	}
	
	public static int kthSmallest(int[][] matrix, int k) {
		int low = matrix[0][0], high = matrix[matrix.length - 1][matrix[0].length - 1] + 1; //[low, high] is our initial search range
		while (low < high) {
			int mid = low + (high - low) / 2;
			int count = 0, j = matrix[0].length - 1;
			for (int i=0; i<matrix.length; i++) {
				while(j >= 0 && matrix[i][j] > mid) {
					j--;
				}
				count += (j + 1); //nums <= mid in matrix
			}
			if (count < k) {
				low = mid + 1;
			}
			else {
				high = mid;
			}
		}
		return low;
	}
	
	//#287 - Find the Duplicate Number
	//NO modification to the original array and CONSTANT space
	public static int findDuplicate(int[] nums) {
        	// 'low' and 'high' represent the range of values of the target        
        	int low = 1, high = nums.length - 1;
        	int duplicate = -1;
        
        	while (low <= high) {
        		int cur = (low + high) / 2;

            		// Count how many numbers in 'nums' are less than or equal to 'cur'
            		int count = 0;
            		for (int num : nums) {
                		if (num <= cur) {
                    			count++;
				}
            		}
            
            		if (count > cur) {
                		duplicate = cur;
                		high = cur - 1;
            		}
			else {
                		low = cur + 1;
            		}
        	}
        	return duplicate;
	}

	public static void main(String[] args) {
		//arr[] is already in ascending order
		//search by index -> linear sorted array
		int[] arr = new int[] {5,6,8,8,8,8,8,8,8,8,8,10};
		int[] result = searchRange(arr, 8);
		System.out.println(result[0] + " - " + result[1]);
		
		int[] rotation = new int[]{3,4,5,6,7,0,1,2};
		System.out.println(findRotationIndex(rotation));
		
		//search by range -> sorted in two directions and cannot find a linear index
		int[] matrix = new int[]{{1,5,9},{10,11,13},{12,13,15}};
		System.out.println(kthSmallest(matrix, 8));
		
		int[] duplicate = new int[]{1,2,3,2};
		System.out.println(findDuplicate(duplicate));
	}

}
