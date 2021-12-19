# Anonymous Class
An inner class without a name, which means that we can declare and instantiate class at the same time.
# Lambda Expression
A short form for writing an anonymous class. By using a lambda expression, we can declare methods without any name.
# Anonymous Class vs Lambda Expression
1. Compilation:
* An anonymous class object generates a separate class file after compilation that increases the size of a jar file.
* A lambda expression is converted into a private method. It uses invokedynamic bytecode instruction to bind this method dynamically, which saves time and memory.
2. Keyword:
* In an anonymous class, this keyword can represent that particular anonymous class.
* In a lambda expression, this keyword can represent the current class. 
3. Usage Type:
* An anonymous classes can be used in case of more than one abstract method.
* A lambda expression is specifically used for functional interfaces.
4. Format:
* In an anonymous class, we need to write the redundant class definition.
* In a lambda expression, we need to provide the function body only.
# Code Example
```Java
public class ThreadTest {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() { // Anonymous Class
            @Override
            public void run() {
                System.out.println("Using Anonymous class");
            }
        };
        Runnable r2 = () -> { // Lambda Expression
            System.out.println("Using Lambda Expression");
        };
        new Thread(r1).start();
        new Thread(r2).start();
    }
}
```
