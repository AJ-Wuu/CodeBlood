/* char to int */
char C;
Character.isDigit(c); // check if the character is a digit
int Ci1 = C; // get the ASCII value (C = '1', Ci1 = 49)
int Ci2 = Character.getNumericValue(C); // only works for single character to get numerical value (C = '2', Ci2 = 2)
int Ci3 = Integer.parseInt(String.valueOf(C)); // also works for String, but the String cannot be like "12.34" (C = '3', Ci3 = 3; String S = "-21", Ci3 = -21)

/* int to char */
int A;
char Ac1 = A; // get the ASCII value (A = 65, Ac1 = 'A')
char Ac2 = (char)(A + '0'); // only works when A is 0~9 (A = 1, Ac2 = '1'; A = 12, Ac2 = '<')
char Ac3 = char(A); // only works when storing integer value in a single quote (A = '2', AC3 = '2'; A = 2, Ac3 is not printable)
char Ac4 = Character.forDigit(A,REDIX); // REDIX = 10 -> decimal; REDIX = 16 -> hexadecimal (A = 2, REDIX = 10, Ac4 = 2; A = 11, REDIX = 16, Ac4 = b)

/* double to int */
double D;
int Di1 = (int)D; // truncate the decimals (D = 4.999, Di1 = 4)
int Di2 = (int)Math.round(D); // round to the nearest integer (D = -5.89, Di2 = -6; D = 4.5, Di2 = 5; D = 4.49, Di2 = 4)

/* string to int */
String S;
int Si = Integer.valueOf(str.substring(a, b)); // NOT include str.charAt(b)
// Validate if string only contains int
str.matches("-?\\d+");             // can contain leading 0
str.matches("-?(0|[1-9]\\d*)");    // will not accept leading 0
StringUtils.isNumeric(str);        // will return true for "" and false for "-1", highly unrecommended

// count remainder with both positive and negative input
int remainder = ((num % k) + k) % k;

// Notice that the sum of decimals may not return to its original value
double[] count = new double[6];
for (int i=0; i<count.length; i++) {
    count[i] = 4.0 / count.length;
}
double sum = 0.0;
for (int i=0; i<count.length; i++) {
    sum += count[i];
}
System.out.println(sum == 4.0); // this is false, as sum = 3.99999999999999999

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------

// #149 - Max Points on a Line
// To avoid the precision issue with float/double numbers, using a pair of co-prime numbers to represent the slope.
public Pair<Integer, Integer> slope_coprime(int x1, int y1, int x2, int y2) {
    int deltaX = x1 - x2, deltaY = y1 - y2;
    if (deltaX == 0) {
        return new Pair<>(0, 0);
    }
    else if (deltaY == 0) {
        return new Pair<>(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    else if (deltaX < 0) {
        deltaX = -deltaX;
        deltaY = -deltaY;
    }
    Integer gcd = BigInteger.valueOf(deltaX).gcd(BigInteger.valueOf(deltaY)).intValue(); // import java.math.BigInteger;
    return (new Pair<Integer, Integer>(deltaX / gcd, deltaY / gcd));
}
