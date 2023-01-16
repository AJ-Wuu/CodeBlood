/**
 * Dynamic Programming vs Greedy Approach.
 * Both Greedy and DP construct an optimal solution of a subproblem based on optimal solutions of smaller subproblems.
 * Both require that an optimal solution of current subproblem is based on optimal solutions of dependent subproblems referred to as optimal substructure property.
 * However, the main difference is that Greedy have a local choice of the subproblem that will lead to the optimal answer.
 * On the other hand, DP would solve all dependent subproblems and then select one that would lead to optimal solution.
 * 
 * DP can not be applied over the structures following cyclic property, whereas, greedy can be applied over cyclic structures.
 * DP is slower on average, but it ensures the optimality of the answer.
 * Greedy is hard in proving that it is optimal, but it has much simpler implementation.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#253 - Meeting Rooms II
//Key: arrival time will always be earlier than departure time, we only need to get the peek number of people in the room
//Note: treat the "==" on double carefully due to precision differences (could be avoided with java.math.BigDecimal)
public static int countChairs(int[] arrive, int[] depart) {
    double[] process = new double[arrive.length + depart.length];
    for (int i=0; i<arrive.length; i++) {
        process[i] = arrive[i] + 0.1; //to distinguish arrive or depart
    }
    for (int i=0; i<depart.length; i++) {
        process[i+arrive.length] = depart[i] + 0.2;
    }

    int count = 0;
    int[] chairs = new int[arrive.length + depart.length];
    Arrays.sort(process);
    for (int i=0; i<process.length; i++) {
        if (process[i] % 1 < 0.15) { //precision requirement
            count++;
        }
        else {
            count--;
        }
        chairs[i] = count;
    }
    Arrays.sort(chairs);
    return chairs[arrive.length + depart.length - 1];
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#68 - Text Justification -> See Projects/Text Justification/Greedy.java

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#503 - Next Greater Element II
//Notion here: get E1, stack empty, store E1
//             if E1 > E2, store E2; else, assign the return value for E1 (let's concern the situation when E1 > E2 now) and then store E2
//             if E2 > E3, store E3; else, E2 has its return value, so assign it and compare E1 with E3
//                                         if E1 < E3, return the value for E1; else, store E3
//             ...
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    for (int i=0; i<n; i++) {
        result[i] = -1;
    }
    Stack<Integer> key = new Stack<Integer>();
    Stack<Integer> index = new Stack<Integer>();
    for (int i=0; i<2*n-1; i++) {
        if (i < n) {
            while (!key.isEmpty() && key.peek() < nums[i]) {
                key.pop();
                result[index.pop()] = nums[i];
            }
            key.push(nums[i]);
            index.push(i);
        }
        else {
            int j = i - n;
            while (!key.isEmpty() && key.peek() < nums[j]) {
                key.pop();
                result[index.pop()] = nums[j];
            }
        }
    }
    return result;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//556 - Next Greater Element III
//Steps after separating the number into digits:
//1. From back to front, find the first pair that makes numList.get(j) < numList.get(i) (i < j)
//2. Keep the digits before i as it is, numList.get(j) should be in position i, and numList.get(i) should be in position j (indices be like {x,x,x,j,y,y,y,i,z,z,z})
//3. All elements AFTER current index = i should be sorted from small to large (directly using Arrays.sort(currArray) could do)
//4. Check if the new integer overflow in 32-bit
//   4.1 this could be done by checking each step (i.e. if (Integer.MAX_VALUE - result > currDigit) { return -1; }), both addition and multiplication
//   4.2 or, we could use a long variable (resultInLong) to store it, and check if (resultInLong > Integer.MAX_VALUE) { return -1; } else { return (int)resultInLong; } 

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#435 - Non-overlapping Intervals
class myComparator implements Comparator<Interval> {
    public int compare(Interval a, Interval b) {
        return a.end - b.end;
    }
}

public int eraseOverlapIntervals(Interval[] intervals) {
    if (intervals.length == 0)  {
        return 0;
    }

    Arrays.sort(intervals, new myComparator());
    int end = intervals[0].end;
    int count = 1;        
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i].start >= end) {
            end = intervals[i].end;
            count++;
        }
    }
    return intervals.length - count;
}
