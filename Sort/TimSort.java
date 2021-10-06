/**
 * @author AJWuu
 */

package tim;

import java.util.Arrays;

public class TimSort {
	
	//Hybrid of Merge and Binary Insertion Sort
	//Designed to perform well on many kinds of real world data
	//Adaptive algorithm that handles input data differently based on various factors
	//    Small sized input (<64 elements) sorted using simple binary linear search (reduce overhead)
	//    Natural runs (elements that are already sorted in the unsorted set) are identified and boosted to sufficient size via binary insertion sort
	//    Natural runs are merged using "one pair at a time" mode by default
	//        When a particular natural run wins the tie breaker more often than a certain threshold, galloping mode is employed instead where 
	//        binary search is used to identify the first element where the winning run loses out and then the entire chunk is copied in one go
	//Used in Java’s Arrays.sort() as well as Python’s sorted() and sort()
	//Stable
	//Time: O(nlogn)
	//Space: O(n)
	
	static int MIN_MERGE = 8; //This should be larger in real-work situation

//	public static int minRunLength(int n) {
//		assert n >= 0;
//
//		// Becomes 1 if any 1 bits are shifted off
//		int r = 0;
//		while (n >= MIN_MERGE) {
//			r |= (n & 1);
//			n >>= 1;
//		}
//		return n + r;
//	}
//
//	// This function sorts array from left index to
//	// to right index which is of size atmost MERGE
//	public static void insertionSort(int[] array, int left, int right) {
//		for (int i = left + 1; i <= right; i++) {
//			int temp = array[i];
//			int j = i - 1;
//			while (j >= left && array[j] > temp) {
//				array[j + 1] = array[j];
//				j--;
//			}
//			array[j + 1] = temp;
//		}
//	}
//
//	// Merge function merges the sorted runs
//	public static void merge(int[] array, int l, int m, int r) {
//		// Original array is broken in two parts
//		// left and right array
//		int len1 = m - l + 1, len2 = r - m;
//		int[] left = new int[len1];
//		int[] right = new int[len2];
//		for (int x = 0; x < len1; x++) {
//			left[x] = array[l + x];
//		}
//		for (int x = 0; x < len2; x++) {
//			right[x] = array[m + 1 + x];
//		}
//
//		int i = 0;
//		int j = 0;
//		int k = l;
//
//		// After comparing, we merge those two array
//		// in larger sub array
//		while (i < len1 && j < len2) {
//			if (left[i] <= right[j]) {
//				array[k] = left[i];
//				i++;
//			}
//			else {
//				array[k] = right[j];
//				j++;
//			}
//			k++;
//		}
//
//		// Copy remaining elements
//		// of left, if any
//		while (i < len1) {
//			array[k] = left[i];
//			k++;
//			i++;
//		}
//
//		// Copy remaining element
//		// of right, if any
//		while (j < len2) {
//			array[k] = right[j];
//			k++;
//			j++;
//		}
//	}
//
//	// Iterative Timsort function to sort the
//	// array[0...n-1] (similar to merge sort)
//	public static void timSort(int[] array, int n) {
//		int minRun = minRunLength(MIN_MERGE);
//
//		// Sort individual subarrays of size MERGE
//		for (int i = 0; i < n; i += minRun) {
//			insertionSort(array, i,
//					Math.min((i + MIN_MERGE - 1), (n - 1)));
//		}
//
//		// Start merging from size
//		// MERGE (or 32). It will
//		// merge to form size 64,
//		// then 128, 256 and so on
//		// ....
//		for (int size = minRun; size < n; size = 2 * size) {
//
//			// Pick starting point
//			// of left sub array. We
//			// are going to merge
//			// array[left..left+size-1]
//			// and array[left+size, left+2*size-1]
//			// After every merge, we
//			// increase left by 2*size
//			for (int left = 0; left < n; left += 2 * size) {
//
//				// Find ending point of left sub array
//				// mid+1 is starting point of right sub
//				// array
//				int mid = left + size - 1;
//				int right = Math.min((left + 2 * size - 1),
//						(n - 1));
//
//				// Merge sub array array[left.....mid] &
//				// array[mid+1....right]
//				if(mid < right)
//					merge(array, left, mid, right);
//			}
//		}
//	}
	
	static int MERGE = 8; //This should be larger in real-work situation
	
	//input_size <= MERGE
    public static void binaryInsertionSort(int[] array, int left, int right) {
    	for (int i=1; i<array.length; i++) {
            int x = array[i];
            int j = Math.abs(Arrays.binarySearch(array, 0, i, x) + 1);
            System.arraycopy(array, j, array, j + 1, i - j);
            array[j] = x;
        }
    }

    public static void merge(int[] array, int left, int mid, int right) { 
    	int size1 = mid - left + 1;
		int size2 = right - mid;
		int[] leftHalf = new int[size1];
		int[] rightHalf = new int[size2];
		for (int i=0; i<size1; i++) {
			leftHalf[i] = array[i+left];
		}
		for (int i=0; i<size2; i++) {
			rightHalf[i] = array[i+mid+1];
		}
		
		int i = 0, j = 0;
		int k = left;
		while (i < size1 && j < size2) {
			if (leftHalf[i] < rightHalf[j]) {
				array[k] = leftHalf[i];
				i++;
			}
			else {
				array[k] = rightHalf[j];
				j++;
			}
			k++;
		}
		while (i < size1) {
			array[k] = leftHalf[i];
			i++;
			k++;
		}
		while (j < size2) {
			array[k] = rightHalf[j];
			j++;
			k++;
		}
    }
  
    //Iteratively to sort the array[0...n-1]
    //Similar to merge sort
    public static void timSort(int[] array, int n) {
        //Sort sub-arrays of size MERGE
        for (int i=0; i<n; i+=MERGE) {
            binaryInsertionSort(array, i, Math.min((i + MERGE - 1), (n - 1)));
        }

        //Start merging from size MERGE
        for (int size=MERGE; size<n; size=2*size) {
            //Pick starting point of the left sub-array ([left, left+size-1] & [left+size, left+2*size-1])
        	//After every merge, increase left by 2*size
            for (int left=0; left<n; left+=2*size) { 
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1)); 
                merge(array, left, mid, right);
            }
        }
    }

	public static void print(int[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
		System.out.println("");
	}

	public static void main(String[] args) {
		int[] array = {12,22,9,4,32,23,2,24,1,21,3,42};
		timSort(array, array.length);
		print(array);
	}

}
