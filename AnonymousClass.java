/**
 * @author AJWuu
 */

/*
 * Reasons to use Anonymous Functions: 
 * 1. The function is only ever called in one place
 * 2. Declared in-line and in-line functions have advantages in that they can access variables in the parent scopes
 * 3. More self-contained and readable
 *
 * In Java, a class can contain another class known as nested class.
 * A nested class that doesn't have any name is known as an anonymous class.
 * An anonymous class must be defined inside another class.
 * Hence, it is also known as an anonymous inner class.
 * 
 * Syntax: 
 * class outerClass {
 *     // defining anonymous class
 *     object1 = new Type(parameterList) { //Type can be a superclass that an anonymous class extends OR an interface that an anonymous class implements
 *         // body of the anonymous class
 *     };
 * }
 * 
 * Reasons to use Anonymous Classes:
 * 1. A particular class A is the only consumer for a Class B and no where else this B class can be used
 * 2. Easy to maintain
 * 3. Reduce verbosity (make the code more concise) 
 * 4. Enable you to declare and instantiate a class at the same time
 * 5. Objects are created whenever they are required (created to perform some specific tasks)
 */

class classPolygon {
	public void display() {
		System.out.println("Inside the Polygon class");
	}
}

interface interfacePolygon {
	public void display();
}

class AnonymousDemo {

	public void createClassFromClass() {
		//Anonymous Class extending Class Polygon
		classPolygon pClass = new classPolygon() {
			public void display() {
				System.out.println("Inside an anonymous class.");
			}
		};
		pClass.display();
	}

	public void createClassFromInterface() {
		//Anonymous Class implementing Interface
		interfacePolygon pInterface = new interfacePolygon() {
			public void display() {
				System.out.println("Inside an anonymous class.");
			}
		};
		pInterface.display();
	}
}

class Main {
	public static void main(String[] args) {
		AnonymousDemo A1 = new AnonymousDemo();
		AnonymousDemo A2 = new AnonymousDemo();
		A1.createClassFromClass();
		A2.createClassFromInterface();
	}
}
