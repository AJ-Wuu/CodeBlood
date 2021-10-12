/**
 * @author AJWuu
 */

package counting;

import java.util.Arrays;

public class CountingSort {
	
	//Get the min, max and length of the integer array
	//Form a new array with min:1:max
	//Count the number of each place (i.e. min = 0, max = 9, we have three 2s and two 8s, so count[2] = 3 and count[8] = 2)
	//  we could also add another step here to convert the count[] into index[] (i.e. count[0] = 1, count[1] = 0, count[2] = 2, count[3] = 1 -> index[] = {0,0,1,3})
	//Generate the output array accordingly (i.e. count[0] = 3, count[1] = 0, count[2] = 2, count[3] = 1 -> {0,0,0,2,2,3})
	//Time: O(n+k)
	//Space: O(k)
	
	//Trick: Entering the elements from back to front using the index-array (process: original-array -> counting-array -> index-array -> sorted-array)

	public static void countingSort(int[] array) {
		int max = Arrays.stream(array).max().getAsInt();
		int min = Arrays.stream(array).min().getAsInt();
		int range = max - min + 1;
		int count[] = new int[range];
		int output[] = new int[array.length];
		
		for (int i=0; i<array.length; i++) {
			count[array[i] - min]++;
		}

		for (int i=1; i<count.length; i++) {
			count[i] += count[i - 1];
		}

		for (int i=array.length-1; i>=0; i--) {
			output[count[array[i] - min] - 1] = array[i];
			count[array[i] - min]--;
		}

		for (int i=0; i<array.length; i++) {
			array[i] = output[i];
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
		countingSort(array);
		print(array);
	}
	
}
