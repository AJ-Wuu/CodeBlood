/**
 * @author AJWuu
 */

package heap;

public class HeapSort {
	
	//Create max heap, remove the max with the last node, and place it in sorted partition
	//Time: O(nlogn)
	//Space: O(1)
	
	public static void swap(int[] array, int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp; 
	}
	
	public static void buildMaxHeap(int[] array, int n) {
		//rearrange the array
		for (int i=n/2-1; i>=0; i--) {
			heapify(array, n, i);
		}
	}
	
	public static void heapify(int[] array, int n, int i) {
		//node i is the root of the heap
		int left = 2 * i;
		int right = 2 * i + 1;
		int max = i;
		if (left < n && array[left] > array[max]) { //left child > root
			max = left;
		}		
		if (right < n && array[right] > array[max]) { //right child > root
			max = right;
		}
		if (max != i) { //max is not the root
			swap(array, i, max);
			heapify(array, n, max); //recursion on the affected subtree
		}
	}
	
	public static void heapSort(int[] array) {
		int n = array.length;
		buildMaxHeap(array, n);
		for (int i=n-1; i>=0; i--) { //extract the element from the heap one by one
			swap(array, 0, i); //move current root to the end
			heapify(array, i, 0); //act on the reduced array
		}
	}
	
	public static void print(int[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		int[] array = {12,22,9,4,32,23,2,24,1,21,3,42};
		heapSort(array);
		print(array);
	}

}
