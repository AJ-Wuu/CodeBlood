package Lambda;

import java.util.ArrayList;

//Three different implementations of lambda expressions

interface MathOperation {
	public double compute(double a, double b);
}

class AdditionOperation implements MathOperation {
	@Override
	public double compute(double a, double b) {
		return (a + b);
	}
}

public class CalculatorApp {
	
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
	}
	
}
