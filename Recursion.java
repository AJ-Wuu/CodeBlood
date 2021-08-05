/**
 * Advantages:
 * Disadvantages: 
 *      1. A recursive program has greater space requirements than an iterative program as each function call will remain in the stack until the base case is reached.
 *      2. It also has greater time requirements because each time the function is called, the stack grows and the final answer is returned when the stack is popped completely.
 */
//#2 - Add Two Numbers
//Do not add the numbers together in an int or a long, as it's easily get overflowed.
//Store the adding result for every bit, and use an int as carry digit
int x = (p != null) ? p.val : 0; //save the trouble of if (p != null && q != null) AND if (p != null && q == null) AND if (p == null && q != null)
int y = (q != null) ? q.val : 0;

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

