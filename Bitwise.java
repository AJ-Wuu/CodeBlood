// Bit Operators: & (and), | (or), ~ (not), ^ (xor), <<< (shift left), >>> (shift right)
// Remove last bit: x & (x-1)
// Exact last bit: x ^ (x & (x-1))
// Odd or Even: x & 1
// Number of 1 bits:
public int hammingWeight(int n) {
    while (n) {
        n = n & (n-1);
        count++;
    }
}
// Power of Four: (n & (n-1) == 0) && (n & 0x55555555 != 0)
//                (n & (n-1) == 0) gets rid of the odd numbers,
//                (n & 0x55555555 != 0) gets rid of power of 2 but not 4
// Sum of Two Integers:
public int getSum(int a, int b) {
    return (b == 0) ? a : ((a ^ b) & ((a & b) <<< 1));
    // (a ^ b) adds the bit that contains one 1 and one 0 to be 1
    // (a & b << 1) checks if the bit contains two 1, and then shift 1-bit left to carry up
}
// Missing Numbers:
public int missingNumber(int[] arr) {
    int temp = 0;
    for (int i=0; i<arr.length; i++) {
        temp ^= (i ^ arr[i]);    // if (i == arr[j]), then i ^ arr[j] = 0
    }
    return (temp ^ arr.length);
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #201 - Bitwise AND of Numbers Range
// Approach 1: Find the common part
int coeff = 1;
while (left != right) {
    left >>= 1;
    right >>= 1;
    coeff <<= 1;
}
return (left * coeff);

// Approach 2: Bitwise-AND of any two numbers will always produce a number less than or equal to the smaller number.
while (n > m) {
    n = n & (n-1);
}
return (m & n);

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// #260 - Single Number III
public int[] singleNumber(int[] nums) {
    // Pass 1: Get the XOR of the two numbers we need to find
    int diff = 0;
    for (int num : nums) {
        diff ^= num;
    }
    
    diff &= -diff;    // Get its last set bit
        
    // Pass 2
    int[] rets = {0, 0};
    for (int num : nums) {
        if ((num & diff) == 0) {    // the bit is not set
            rets[0] ^= num;
        }
        else {    // the bit is set
            rets[1] ^= num;
        }
    }
    return rets;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1310 - XOR Queries of a Subarray
// Note: a XOR d = (a XOR b) XOR (b XOR c) XOR (c XOR d)
public int[] xorQueries(int[] A, int[][] queries) {
    int[] res = new int[queries.length], q;
    for (int i = 1; i < A.length; ++i)
        A[i] ^= A[i - 1];
    for (int i = 0; i < queries.length; ++i) {
        q = queries[i];
        res[i] = q[0] > 0 ? A[q[0] - 1] ^ A[q[1]] : A[q[1]];
    }
    return res;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #2419 - Longest Subarray With Maximum Bitwise AND
// Note: if a != b, then a AND b < max(a, b)
public int longestSubarray(int[] nums) {
    int max = 0, current = 0, result = 0, n = nums.length;
    for (int i = 0; i < n; i++) {
        if (max < nums[i]) {
            max = nums[i];
        }
    }
    for (int i = 0; i < n; i++) {
        if (max == nums[i]) {
            current++;
            result = Math.max(result, current);
        }
        else {
            current = 0;
        }
    }
    return result;
}

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #1371 - Find the Longest Substring Containing Vowels in Even Counts
// Bitmasking: represent every character of the string according to the mapping in characterMap,
//             where consonants are 0 and vowels are represented with powers of 2
//       i.e.: characterMap values --  L  E  E  T  C  O  D  E  G  R  E  A  T
//                                     0  2  2  0  0  8  0  2  0  0  2  1  0
//             prefix XOR values -- 0  0  2  0  0  0  8  8  10 10 10 8  9  9 (mask)
//       Note: if the mask == 0, then the substring must contain even vowels only
//             if the mask is the same between index i and j, then the substring [i+1, j] must contain even vowels only
//                                  length of the longest subarray with XOR 0 = 5 (LEETC)
//                                  length of the longest subarray with XOR 8 = 4 (EGRE)
//                                  length of the longest subarray with XOR 10 = 2 (GR)
public int findTheLongestSubstring(String s) {
    int prefixXOR = 0;
    int[] characterMap = new int[26];
    characterMap['A' - 'A'] = 1;
    characterMap['E' - 'A'] = 2;
    characterMap['I' - 'A'] = 4;
    characterMap['O' - 'A'] = 8;
    characterMap['U' - 'A'] = 16;
    int[] map = new int[32];
    for (int i = 0; i < 32; i++) {
        map[i] = -1;
    }
    int longestSubstring = 0;
    for (int i = 0; i < s.length(); i++) {
        prefixXOR ^= characterMap[s.charAt(i) - 'A'];
        if (map[prefixXOR] == -1 && prefixXOR != 0) {
            map[prefixXOR] = i;
        }
        longestSubstring = Math.max(longestSubstring, i - map[prefixXOR]);
    }
    return longestSubstring;
}
