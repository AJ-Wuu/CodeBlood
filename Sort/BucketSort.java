/**
 * @author AJWuu
 */

package bucket;

import java.util.Collections;
import java.util.LinkedList;

public class BucketSort {
	
	//Create buckets, put elements into buckets, sort within each bucket, and put them back to array
	//Put each element into the bucket with index of (int)(array[i] * n)
	//Time: O(n)
	//Space: O(n)

	@SuppressWarnings("unchecked")
	public static void bucketSort(double[] array) {
		int n = array.length;
		//Create buckets
		LinkedList<Double>[] buckets = new LinkedList[n]; //Could use Vector<Double> instead of LinkedList<Double> here
		for (int i=0; i<n; i++) {
			buckets[i] = new LinkedList<Double>();
		}
 
		//Assign elements into buckets
		for (int i=0; i<n; i++) {
			buckets[(int)(array[i] * n)].add(array[i]);
		}
 
		//Sort within each bucket
		for (int i=0; i<n; i++) {
			Collections.sort(buckets[i]);
		}
 
		//Put back into the array
		int index = 0;
		for (int i=0; i<n; i++) {
			for (int j=0; j<buckets[i].size(); j++) {
				array[index++] = buckets[i].get(j);
			}
		}
	}
	
	public static void print(double[] array) {
		for (int i=0; i<array.length; i++) {
			System.out.printf("%.4f ", array[i]);
		}
		System.out.println("");
	}
	
	public static void main(String[] args) {
		double[] array = {0.1212,0.2211,0.94,0.4321,0.2332,0.3223,0.2,0.2413,0.1234,0.2112,0.233,0.4231};
		bucketSort(array);
		print(array);
	}

}
