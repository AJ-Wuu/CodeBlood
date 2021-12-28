/**
 * @author AJWuu
 */

package search;

public class BinarySearch {

	/*
	 * Key of Binary Search:
	 * 1. Find mid
	 * 2. Update start or end with mid
	 * 
	 * Complexity: O(logN)
	 */

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

	public static void main(String[] args) {
		int[] arr = new int[] {5,6,8,8,8,8,8,8,8,8,8,10};
		int[] result = searchRange(arr, 8);
		System.out.println(result[0] + " - " + result[1]);
	}

}
