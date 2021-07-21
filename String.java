//String
String s = "";
s = s.concat("s"); //s.concat() has a return value, and it needs this return value to update the original string

//String Builder
StringBuilder str = new StringBuilder();
str.append("GFG"); //str.append() does not have a return value, but directly update the original string
StringBuilder str1 = new StringBuilder("AAAABBBCCCC");
StringBuilder str2 = new StringBuilder(10);
StringBuilder str3 = new StringBuilder(str1.toString());

//Return the string representation of the passed argument
str.valueOf(int i); //all data types
str.valueOf(char[] cc); //only for char arrays
str.valueOf(char[] data, int offset, int count) //representation of a specific subarray of the char array argument

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

