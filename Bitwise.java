//Bit Operators: & (and), | (or), ~ (not), ^ (xor), <<< (shift left), >>> (shift right)
//Remove last bit: x&(x-1)
//Exact last bit: x^(x&(x-1))
//Number of 1 bits:
public int hammingWeight(int n) {
    while (n) {
        n = n & (n-1);
        count++;
    }
}
//Power of Four: (n & (n-1) == 0) && (n & 0x55555555 != 0) //(n & (n-1) == 0) gets rid of the odd numbers, (n & 0x55555555 != 0) gets rid of power of 2 but not 4
//Sum of Two Integers:
public int getSum(int a, int b) {
    return (b == 0) ? a : ((a ^ b) & ((a & b) <<< 1));
    //(a ^ b) adds the bit that contains one 1 and one 0 to be 1
    //(a & b << 1) checks if the bit contains two 1, and then shift 1-bit left to carry up
}
//Missing Numbers:
public int missingNumber(int[] arr) {
    int temp = 0;
    for (int i=0; i<arr.length; i++) {
        temp ^= (i ^ arr[i]); //if (i == arr[j]), then i ^ arr[j] = 0
    }
    return (temp ^ arr.length);
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
