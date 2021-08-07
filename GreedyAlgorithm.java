/**
 * https://techparoksha.quora.com/Dynamic-Programming-Part-1
 * Dynamic Programming vs Greedy Approach.

Greedy as the title suggests ,  takes into account the best possible and feasible option at any point of time. Both Greedy and dynamic programming algorithms construct an optimal solution of a subproblem based on optimal solutions of smaller subproblems. However, the main difference is that greedy algorithms have a local choice of the subproblem that will lead to the optimal answer. On the other hand, dynamic programming would solve all dependent subproblems and then select one that would lead to optimal solution. Both algorithms require that an optimal solution of current subproblem is based on optimal solutions of dependent subproblems which is referred to as optimal substructure property.

In dynamic programming, we need to identify the following:

smallest subproblems and their solutions.
description of solution of a subproblem based on solution of smaller subproblems (usually described as a recurrence).
an ordering of the subproblems from smallest to largest to construct solutions of all subproblems.
It is not easy to prove that a greedy algorithm is optimal however greedy algorithms have much simpler implementation.

The dynamic programming algorithm is slower on average however it ensures the optimality of the answer.

Quite interesting as it seems ,  DP can not be applied over the structures following cyclic property. In order to apply DP over a problem ,  it has to be modeled into an acyclic structure, whereas ,  greedy can be applied over cyclic structures too.
 */

//#68 - Text Justification -> See Projects/Text Justification/Greedy.java
