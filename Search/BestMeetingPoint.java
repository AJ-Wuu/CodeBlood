 * @author AJWuu
 */

package meeting

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BestMeetingPoint {

	//#296 - Best Meeting Point
	//One-Dimensional: --A-----B-- -> the meeting point could be anywhere between AB
	//                 --C---A-----B---D-- -> the meeting point should be anywhere between AB
	//Two-Dimensional: Two One-Dimensional
	public static int minTotalDistance(int[][] grid) {
		List<Integer> ipos = new ArrayList<>();
		List<Integer> jpos = new ArrayList<>();

		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[0].length; j++) {
				if (grid[i][j] == 1) {
					ipos.add(i);
					jpos.add(j);
				}
			}
		}

		int sum = 0;
		int iMid = ipos.get(ipos.size() / 2); //ipos is got by order, so no need to sort it
		for (Integer pos : ipos){
			sum += Math.abs(pos - iMid);
		}

		Collections.sort(jpos); //jpos could be out-of-order, so we need to sort it first
		int jMid = jpos.size() / 2;
		for (int j=0; j<jpos.size(); j++) {
			sum += Math.abs(jpos.get(jMid) - jpos.get(j));
		}

		return sum;
	}


	public static void main(String[] args) {
		System.out.println(minTotalDistance(new int[][] { {1,0,0,0,1},{0,0,0,0,0},{0,0,1,0,0} }));
	}

}
