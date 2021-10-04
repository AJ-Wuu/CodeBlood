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
//2. Keep the digits before i as it is, numList.get(j) should be in position i, and numList.get(i) should be in position j
//3. All elements after current index = i should be sorted from small to large
//4. Check if the new integer overflow in 32-bit
//   4.1 this could be done by checking each step (i.e. if (Integer.MAX_VALUE - result > currDigit) { return -1; }), both addition and multiplication
//   4.2 or, we could use a long variable (resultInLong) to store it, and check if (resultInLong > Integer.MAX_VALUE) { return -1; } else { return (int)resultInLong; } 
