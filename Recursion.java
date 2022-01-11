/**
 * Advantages:
 *     1. Only need to define the base case and recursive case for the recursive funtion, so the code is simpler and shorter than an iterative one.
 *     2. Some problems are inherently recursive, such as Graph and Tree Traversal.
 * Disadvantages: 
 *     1. Greater space requirements than an iterative program as each function call will remain in the stack until the base case is reached.
 *     2. Greater time requirements because each time the function is called, the stack grows and the final answer is returned when the stack is popped completely.
 */

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//#2 - Add Two Numbers
//Do not add the numbers together in an int or a long, as it's easily get overflowed.
//Store the adding result for every bit, and use an int as carry digit
int x = (p != null) ? p.val : 0; //save the trouble of if (p != null && q != null) AND if (p != null && q == null) AND if (p == null && q != null)
int y = (q != null) ? q.val : 0;

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//BackTracking
//Recursion over every possible way and then find the best route.
//Time Complexity: O(2^n)
public static List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new LinkedList<>();
    Arrays.sort(nums);
    backtrackSubset(list, new LinkedList<>(), nums, 0);
    return list;
}

//#78, #90 - Subsets I, II
private static void backtrackSubset(List<List<Integer>> list , LinkedList<Integer> tempList, int[] nums, int start){
    list.add(new LinkedList<>(tempList));
    for (int i=start; i<nums.length; i++) {
        tempList.add(nums[i]);
        backtrackSubset(list, tempList, nums, i+1);
        tempList.removeLast(); //tempList is of LinkedList type
    }
}
private void backtrackSubsetWithDuplicates(List<List<Integer>> list, LinkedList<Integer> tempList, int [] nums, int start){
    list.add(new LinkedList<>(tempList));
    for (int i=start; i<nums.length; i++){
        if (i>start && nums[i]==nums[i-1]) {
        	continue; //skip duplicates (already existed)
        }
        tempList.add(nums[i]);
        backtrackSubsetWithDuplicates(list, tempList, nums, i+1);
        tempList.removeLast();
    }
}

//#47 - Permutations II
private void backtrackPermutation(List<List<Integer>> list, List<Integer> tempList, int [] nums, boolean [] used){
    if (tempList.size() == nums.length){
        list.add(new LinkedList<>(tempList));
    }
    else{
        for (int i=0; i<nums.length; i++){
            if (used[i]) {
            	continue;
            }
            used[i] = true; 
            tempList.add(nums[i]);
            backtrackPermutation(list, tempList, nums, used);
            used[i] = false; 
            tempList.remove(tempList.size() - 1); //tempList is of List type
            while (i+1<nums.length && nums[i]==nums[i+1]) {
            	i++; //skip duplicates
            }
        }
    }
}

//#39 - Combination Sum
private void backtrackAllowReuse(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start){
    if (remain < 0) {
    	return ;
    }
    else if (remain == 0) {
    	list.add(new LinkedList<>(tempList));
    }
    else { 
        for(int i=start; i<nums.length; i++){
            tempList.add(nums[i]);
            backtrackAllowReuse(list, tempList, nums, remain - nums[i], i); //NOT i+1 because we can reuse the same elements
            tempList.remove(tempList.size() - 1);
        }
    }
}
