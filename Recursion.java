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

