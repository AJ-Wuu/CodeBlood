/**
 * @author AJWuu
 */

package nDArray;

import java.util.Arrays;
import java.util.Comparator;

public class NDimensionalArray {

	public static void sortByColumn(int[][] matrix, int columnIndex) {
		Arrays.sort(matrix, new Comparator<int[]>() {
			public int compare(final int[] entry1, final int[] entry2) {
				//ascending order
				if (entry1[columnIndex] > entry2[columnIndex]) {
					return 1;
				}
				else {
					return -1;
				}
			}
		});
	}
	
	public static void print(int[][] matrix) {
		for (int i=0; i<matrix.length; i++) {
			for (int j=0; j<matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		int[][] matrix = {{41,42,43,44},
				  {11,12,13,14},
				  {31,32,33,34},
				  {21,22,23,24}};
		sortByColumn(matrix, 2);
		print(matrix);
	}

}
