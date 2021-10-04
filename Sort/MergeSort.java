/**
 * @author AJWuu
 */

package merge;

public class MergeSort {

	//Divide and Conquer
	//Separate to pieces and then merge
	//Two pointers of two subarrays
	//Time: Best = Median = Worst = O(nlogn)
	//Space: O(n)
	
	public static void merge(int[] array, int left, int mid, int right) {
		int size1 = mid - left + 1;
		int size2 = right - mid;
		int[] leftHalf = new int[size1];
		int[] rightHalf = new int[size2];
		
		//copy data into two temporary arrays
		for (int i=0; i<size1; i++) {
			leftHalf[i] = array[i+left];
		}
		for (int i=0; i<size2; i++) {
			rightHalf[i] = array[i+mid+1];
		}
		
		//pointers
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
	
	public static void divide(int[] array, int left, int right) {
		if (left < right) {
			int mid = (left + right) / 2;
			divide(array, left, mid);
			divide(array, mid+1, right);
			merge(array, left, mid, right);
		}
	}
	
	public static void print(int[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int[] array = {12,22,9,4,32,23,2,24,1,21,3,42};
		divide(array, 0, array.length-1);
		print(array);
	}

}
