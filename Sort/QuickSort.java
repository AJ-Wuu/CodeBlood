/**
 * @author AJWuu
 */

package quick;

public class QuickSort {
	
	//Divide and Conquer
	//Sort based on the chosen pivot to {[smaller than pivot], pivot, [larger than pivot]}
	//Two pointers moving towards each other on the same array
	//Time: Best = O(nlogn), Worst = O(n^2) -> However, QuickSort is better in practice, as choosing the pivot wisely (instead of randomly) would be very helpful
	//Space: O(1)

	public static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	public static int partition(int[] array, int left, int right) {
		int pivot = array[left]; //take the first element as pivot (any random pivot could do)
		
		//put elements smaller than (or equal to) pivot in the left and larger than pivot in the right
		int i = left + 1, j = right; //pointers
		while (j >= i) {
			if (array[i] > pivot && array[j] < pivot) { //ready to swap
				swap(array, i, j);
				i++;
				j--;
			}
			else if (array[i] > pivot) { //check the next position from back
				j--;
			}
			else if (array[j] < pivot) { //check the next position from front
				i++;
			}
			else { //this pair is good to go
				i++;
				j--;
			}
		}
		
		//put the pivot in the middle and return the separating point index
		//Notice that the two pointers may be the same or next to each other
		if (i == j) {
			swap(array, i, left);
			return i;
		}
		else {
			swap(array, i-1, left);
			return i-1;
		}
	}
	
	public static void quickSort(int[] array, int left, int right) {
		if (left < right) {
			int pivotIndex = partition(array, left, right);
			quickSort(array, left, pivotIndex-1);
			quickSort(array, pivotIndex+1, right);
		}
	}
	
	public static void print(int[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int[] array = {12,22,9,4,32,23,2,24,1,21,3,42};
		quickSort(array, 0, array.length-1);
		print(array);
	}

}
