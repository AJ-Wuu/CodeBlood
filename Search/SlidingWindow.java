/**
 * @author AJWuu
 */

package slidingWindow;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SlidingWindow {

	//Find the maximum sum of a subarray of size k
	//Time: O(n*k)

	private static List<Integer> slidingWindowTemplate(String s, String t) {
		//initialize a result container
		List<Integer> result = new LinkedList<>();
		if (t.length() > s.length()) {
			return result;
		}

		Map<Character, Integer> map = new HashMap<>(); //save the target string, (K, V) = (Character, Frequency of the Characters)
		for (char c : t.toCharArray()) {
			map.put(c, map.getOrDefault(c, 0) + 1);
		}
		int counter = map.size(); //check if matched the target substring
		//Note: this must be the map size, NOT the string size because string may contains duplicate characters.
		int begin = 0, end = 0; //two pointers

		while (end < s.length()) {
			char c = s.charAt(end);            
			if (map.containsKey(c)) {
				map.put(c, map.get(c) - 1);
				if (map.get(c) == 0) {
					counter--;
				}
			}
			end++;

			//change begin pointer to make it invalid/valid again
			while (counter == 0) {
				char temp = s.charAt(begin);
				if (map.containsKey(temp)) {
					map.put(temp, map.get(temp) + 1);
					if (map.get(temp) > 0) {
						counter++;
					}
				}
				if (end-begin == t.length()) {
					result.add(begin);
				}
				begin++;
			}
		}

		return result;
	}

	public static int maxSum(int arr[], int n, int k) {
		int max_sum = Integer.MIN_VALUE;

		//consider all blocks starting with i
		for (int i=0; i<n-k+1; i++) {
			int current_sum = 0;
			for (int j=0; j<k; j++) {
				current_sum = current_sum + arr[i + j];
			}
			max_sum = Math.max(current_sum, max_sum);
		}

		return max_sum;
	}

	public static void main(String[] args) {
		int array[] = {1,4,2,10,2,3,1,0,20};
		int k = 4;
		int n = array.length;
		System.out.println(maxSum(array, n, k));
	}
	
}
