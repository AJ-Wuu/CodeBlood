/**
 * @author AJWuu
 */

package exception;

//java.lang.Exception class contains all the exceptions
//Frequenctly used ones:
//Checked Exception (Compile-Time Exceptions, checked by the compiler during the compilation process)
//    SQLException, IOException, InvocationTargetException, ClassNotFoundException
//Unchecked Exceptions (Runtime Exceptions, generally ignored during the compilation process)
//    NullPointerException, ArrayIndexOutofBound, IllegalArgumentException, IllegalStateException, NumberFormatException, ArithmeticException

public class Exception {

	public static void fun(int n) throws IllegalAccessException {
		//“throws” does not throw an exception but is used to indicate that an exception might occur in the program or method
		//"throw" is used to throw the exception explicitly
		if (n == 1) {
			System.out.println("The input is 1");
			throw new IllegalAccessException("demo");
		}
		if (n == 2) {
			System.out.println("The input is 2");
		}
	}

	public static void main(String[] args) {
		try {
			//statement(s) that might cause exception
			fun(2);
			fun(1); //after getting an exception, the code will not continue (so the next fun(2) is never executed)
			fun(2);
		} catch (IllegalAccessException e) { //IllegalAccessException is a kind of Exception
			//statement(s) that handle an exception examples, 
			//closing a connection, closing file, 
			//exiting the process after writing details to a log file
			System.out.println("Caught in main");
		} finally {
			//executed after try-catch, regardless of the result
			System.out.println("The 'try-catch' is finished");
		}
	}

}
