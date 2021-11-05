package regex;

import java.util.Scanner;

public class ScannerUseDelimiterTest {
	
   public static void main(String[] args) {
      String source = "There are thirty-three big-apple";
      Scanner in = new Scanner(source);
      in.useDelimiter("\\s+|-");  // whitespace(s) or -
      while (in.hasNext()) {
         System.out.println(in.next());
      }
   }
   
}