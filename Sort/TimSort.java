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
