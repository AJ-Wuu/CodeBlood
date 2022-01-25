//#201 - Bitwise AND of Numbers Range
//Approach 1: Find the common part
int coeff = 1;
while (left != right) {
    left >>= 1;
    right >>= 1;
    coeff <<= 1;
}
return (left * coeff);

//Approach 2: Bitwise-AND of any two numbers will always produce a number less than or equal to the smaller number.
while (n > m) {
    n = n & (n-1);
}
return (m & n);

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
