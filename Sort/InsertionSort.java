/**
 * @author AJWuu
 */

package insertion;

public class InsertionSort {

	//Binary Insertion uses binary search to find the proper location to insert the selected item at each iteration (O(n) -> O(logn))
	//Time: O(n^2)
	//Space: O(1)
	
	public static int binarySearch(int[] array, int target, int left, int right) {
		while (left <= right) {
			int mid = (left + right) / 2;
			if (target == array[mid]) {
				return (mid + 1);
			}
			else if (target > array[mid]) {
				left = mid + 1;
			}
			else {
				right = mid - 1;
			}
		}
		return left;
	}
	
	public static void insertionSort(int[] array) {
		for (int i=1; i<array.length; i++) { //i is the separating bar -> sortedArray | unsortedArray
			int j = i - 1; //last sorted element
			int target = array[i]; //first unsorted element
			int location = binarySearch(array, target, 0, j);
			
			while (j >= location) { //move every element in [location, j] one place to the right
				array[j+1] = array[j];
				j--;
			}
			array[location] = target;
		}
	}
	
	public static void print(int[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int[] array = {12,22,9,4,32,23,2,24,1,21,3,42};
		insertionSort(array);
		print(array);
	}

}
