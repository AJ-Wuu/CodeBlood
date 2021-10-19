//#856 - Score of Parentheses
//Approach 1: Divide and Conquer
//Approach 2: Stack
//Approach 3: Count Cores
//    Keep track of the balance of the string.
//    For every ) that immediately follows a (, the answer is 1 << balance, as balance is the number of exterior set of parentheses that contains this core.
