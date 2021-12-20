/**
 * @author AJWuu
 */

package Lambda;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/*
 * Advantages of using Lambda Expressions:
 * 1. Fewer Lines of Code (lambda expressions can be used only with a functional interface)
 * 2. Sequential and Parallel execution support by passing behavior as an argument in methods
 *    − By using Stream API in Java 8, the functions are passed to collection methods. 
 *      Now it is the responsibility of collection for processing the elements either in a sequential or parallel manner.
 * 3. Higher Efficiency
 *    − By using Stream API and lambda expressions, we can achieve higher efficiency (parallel execution) in case of bulk operations on collections. 
 * 4. Achieve the internal iteration of collections rather than external iteration
 */

//The Button.onAction method in JavaFX takes an argument of type ActionEvent
//The Stream.map method in java.util.stream.Streams takes a function as its input
//A lambda expression is an anonymous function that provides a concise and functional syntax, which is used to write anonymous methods. 
//    It is based on the function programming concept and used to create delegates or expression tree types. 
//    The syntax is function(arg1, arg2...argn) expression.

//Lambda expression syntax features are as follows:
//1. It is a function without a name.
//2. There are no modifiers, such as overloads and overrides.
//3. The body of the function should contain an expression, rather than a statement.
//4. May contain a call to a function procedure but cannot contain a call to a subprocedure.
//5. The return statement does not exist.
//6. The value returned by the function is only the value of the expression contained in the function body.
//7. The End function statement does not exist.
//8. The parameters must have specified data types or be inferred.
//9. Does not allow generic parameters, optional or ParamArray parameters.

//Lambda expressions with curly braces need a semicolon at the end of every statement, even if they only contain one statement, so there are two ways:
//(event) -> { System.out.println("Mouse event at " + event.getScreenX() + " and " + event.getScreenY()); }
//(event) -> System.out.println("Mouse event at " + event.getScreenX() + " and " + event.getScreenY())

interface MathOperation {
	public double compute(double a, double b);
}

class AdditionOperation implements MathOperation {
	@Override
	public double compute(double a, double b) {
		return (a + b);
	}
}

public class LambdaExpression {
	
	public static MathOperation add() {  
		//Define a class named AdditionOperation that implements MathOperation
		//Defines its compute method to return the sum of its operands
		//Return a new instance of this class from this method
		return new AdditionOperation();
	}

	public static MathOperation sub() {
		//Return a new instance of an anonymous class that implements MathOperation
		//Define its compute method to return the the difference of its operands (first minus second)
		return (new MathOperation(){
			@Override
			public double compute(double a, double b) {
				return (a - b);
			}
		});
	}
	
	public static MathOperation mul() {
		//Use a lambda expression to create and return an object with a compute method that returns the product of its operands
		return ((a,b) -> a * b);
	}

	public static void main(String[] args) {
		//Add all math operations to this array
		ArrayList<MathOperation> ops = new ArrayList<>();
		ops.add(add());
		ops.add(sub());
		ops.add(mul());
		//Display table of math operations applied to operands
		System.out.println("Operands:  add  sub  mul");
		for(int b=1; b<6; b++) { // second operand (b) goes from 1 to 5
			for(int a=b+1; a<6; a++) { // first operand goes from b+1 to 5
				System.out.print("     "+a+","+b+":"); // print out operands first
				for(MathOperation op: ops) {
					if(op != null) { // then print out result of operation for those available
						System.out.printf( "%5.1f", op.compute(a,b) );
					}
					else { // and print out a dash otherwise
						System.out.print("    -");
					}
				}
				System.out.println(); // ensure that the next operands are printed to the next line
			}
		}
		
		Runnable printHello10x = () -> { 
            		for (int i=0; i<10; i++) { System.out.println("Hello Runnable"); }
        	};
        	Consumer<Integer> printHelloNx = (n) -> { 
        		for (int i=0; i<n; i++) { System.out.println("Hello Consumer"); } 
        	};
        	BiConsumer<Integer,Runnable> runNx = (n,anycode) -> { 
        		for (int i=0; i<n; i++) { anycode.run(); } 
        	};
        	Function<Integer,Runnable> createPrintHelloNx = (n) -> () -> { 
        		for (int i=0; i<n; i++) { System.out.println("Hello Function"); }
        	};
        	printHello10x.run();
        	printHelloNx.accept(3);
        	runNx.accept(3,() -> System.out.println("hi"));
        	Runnable r = createPrintHelloNx.apply(6);
        	r.run();
	}
	
}
