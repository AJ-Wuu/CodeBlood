# Packages
* util VS awt  
1. java.util.* contains some tool classes, such as List, Map, HashMap, Set, etc.
2. java.awt.* encapsulates the classes related to graphics drawing, such as Point, Line, etc.
* awt VS swing  

|               |  Definition                                |  Components                     |  Functionality  |  Execution Time  |  Platform     |  MVC** pattern  |
| ------------- |:------------------------------------------:|--------------------------------:|----------------:|-----------------:|--------------:| ---------------:|
| java.awt.*    |  API to develop GUI applications           |  heavy weighted, less powerful  |  less           |  more            |  dependent    |  not supported  |
| java.swing.*  |  part of Java Foundation Classes for many  |  light weighted, more powerful  |  more           |  less            |  independent  |  supported      |
    MVC stands for Model–View–Controller,  
          which is a software design pattern commonly used for developing user interfaces  
          that divide the related program logic into three interconnected elements.
          
# Interface & Abstract Classes
A class can extend only one superclass and can implement any number of interfaces simultaneously.  
## Key Difference: Abstract Class can have both abstract and concrete methods
## `extends`
* A derived class can extend a base class. You may redefine the behaviour of an established relation. Derived class "is a" base class type.  
* It is not compulsory that subclass that extends a superclass override all the methods in a superclass.  
* Any number of interfaces can be extended by interface.  
## `implements`
* You are implementing a contract. The class implementing the interface "has a" capability.  
* In an interface you can not implement any of the declared methods. Only the class that "implements" the interface can implement the methods.  
* It is compulsory that class implementing an interface has to implement all the methods of that interface.  
* An @Override tag is not required for implementing an interface, as there is nothing in the original interface methods to be overridden.  
* An interface can never implement any other interface.  

# Class & NameSpace
## Class
* Classes are data types. They are an expanded concept of structures.
* They can contain data members, but they can also contain functions as members.
## NameSpace
* A namespace is more like a naming convention.
* It is simply an abstract way of grouping items together.
* It cannot be created as an object.
* It is used as additional information to differentiate similar functions, classes, variables, etc. with the same name available in different libraries.
* In essence, a namespace defines a scope.
1. A namespace is a way of grouping identifiers so that they don’t clash. Using a class implies that you can create an instance of that class, not true with namespaces.
2. You can use using-declarations with namespaces, and that’s not possible with classes unless you derive from them.
3. You can reopen a namespace and add stuff across translation units. You cannot do this with classes. For example, this is legal:  
```
namespace A {
    int f1();
}
  
namespace A {
    int f2();
}
```
4.You can have unnamed namespaces but you can’t have a unnamed class. For example, this is legal:  
```
namespace { // fine
    // some code....
}
```
5. If length of a name makes code difficult to read, or is tedious to type in a header file where using directives can’t be used, we can make a namespace alias which serves as an abbreviation for the actual name. For example:  
```
#include <iostream>
namespace foo {
	namespace bar {
		namespace baz {
			int qux = 42;
		}
	}
}
namespace fbz = foo::bar::baz;
int main() {
	std::cout << fbz::qux << '\n';
}

class Car {
public:
    typedef std::vector<Wheel> WheelCollection;
    WheelCollection wheels;
};
```

# static
**Significance: Static variables have a property of preserving their value even after they are out of their scope!!!**
1. The keyword static indicates that the particular member belongs to a type itself, rather than to an instance of that type.
2. Static Field
   - 2.1. If a field is declared static, then exactly a single copy of that field is created and shared among all instances of that class
   - 2.2. From the memory perspective, static variables go in a particular pool in JVM memory called Metaspace 
        (before Java 8, this pool was called Permanent Generation or PermGen, which was completely removed and replaced with Metaspace)
   - 2.3. Compelling Reasons to Use static Fields: 
       - 2.3.1. when the value of variable is independent of objects
       - 2.3.2. when the value is supposed to be shared across all objects
   - 2.4. Key Points to Remember:
       - 2.4.1. since static variables belong to a class, they can be accessed directly using class name and don't need any object reference
       - 2.4.2. static variables can only be declared at the class level
       - 2.4.3. static fields can be accessed without object initialization
       - 2.4.4. although we can access static fields using an object reference (like ford.numberOfCars++), 
               we should refrain from using it as in this case it becomes difficult to figure whether it’s an instance variable or a class variable; 
               instead, we should always refer to static variables using class name (for example, in this case, Car.numberOfCars++)
3. Static Method
   - 3.1. Compelling Reasons to Use static Methods:
       - 3.1.1. to access/manipulate static variables and other static methods that don't depend upon objects
       - 3.1.2. static methods are widely used in utility and helper classes
   - 3.2. Key Points to Remember:
       - 3.2.1. static methods in Java are resolved at compile time. Since method overriding is part of Runtime Polymorphism, so static methods can't be overridden
       - 3.2.2. abstract methods can't be static
       - 3.2.3. static methods cannot use this or super keywords
       - 3.2.4. the following combinations of the instance, class methods and variables are valid ->  
              * 3.2.4.1. Instance methods can directly access both instance methods and instance variables  
              * 3.2.4.2. Instance methods can also access static variables and static methods directly  
              * 3.2.4.3. static methods can access all static variables and other static methods  
              * 3.2.4.4. static methods cannot access instance variables and methods directly; they need some object references to do so  
4. Static Block
```
    public class StaticBlockDemo {
        public static List<String> ranks = new LinkedList<>();
        static {
            ranks.add("Lieutenant");
            ranks.add("Captain");
            ranks.add("Major");
        }
        static {
            ranks.add("Colonel");
            ranks.add("General");
        }
    }
```
   - 4.1. If static variables require additional, multi-statement logic while initialization, then a static block can be used.
   - 4.2. Compelling Reasons to Use static Blocks:
       - 4.2.1. if initialization of static variables requires some additional logic except the assignment
       - 4.2.2. if the initialization of static variables is error-prone and requires exception handling
   - 4.3. Key Points to Remember:
       - 4.3.1. a class can have multiple static blocks
       - 4.3.2. static fields and static blocks are resolved and executed in the same order as they are present in the class
5. Static Class
```
    public class Singleton {
        private Singleton() {}
        private static class SingletonHolder {
            public static final Singleton instance = new Singleton();
        }
        public static Singleton getInstance() {
            return SingletonHolder.instance;
        }
    }
```
   - 5.1. Static nested classes behaved exactly like any other top-level class but enclosed in the only class which will access it, to provide better packaging convenience.
   - 5.2. The nested class architecture:
       - 5.1.1. nested classes that are declared static are called static nested classes
       - 5.1.2. nested classes that are non-static are called inner classes
   - 5.3. Compelling Reasons to Use a static Inner Class:
       - 5.3.1. grouping classes that will be used only in one place increases encapsulation
       - 5.3.2. the code is brought closer to the place that will be only one to use it; this increases readability and code is more maintainable
       - 5.3.3. if nested class doesn't require any access to it's enclosing class instance members, then it's better to declare it as static because this way, 
               it won't be coupled to the outer class and hence will be more optimal as they won't require any heap or stack memory
   - 5.4. Key Points to Remember:
       - 5.4.1. static nested classes do not have access to any instance members of the enclosing outer class; it can only access them through an object's reference
       - 5.4.2. static nested classes can access all static members of the enclosing class, including private ones
       - 5.4.3. Java programming specification doesn't allow us to declare the top-level class as static;
               only classes within the classes (nested classes) can be made as static

# Volatile
Used to modify the value of a variable by different threads & make classes thread safe  
Multiple threads can use a method and instance of the classes at the same time without any problem  
Do not cache the value of the variable and always read the variable from the main memory  
Cannot be used with classes or methods, only with variables (eg. static volatile int var = 2)  

# Synchronized
The synchronized keyword prevents concurrent access to a block of code or object by multiple threads  
eg. ```synchronized (this) { // block of code to be synchronized }```

# Final
Final fields are useful for implementing immutability (values can't be changed after creation), which helps make it easy to reason about an object.

# Comparable and Comparator

| Comparable provides compareTo() method to sort elements    | Comparator provides compare() method to sort elements  |
|------------------------------------------------------------|:------------------------------------------------------:|
|            interface is present in java.lang package       |            interface is present in java.util package   |
|            provides single sorting sequence                |            provides multiple sorting sequences         |
|            affects the original class                      |            doesn’t affect the original class           |

# String, StringBuffer and StringBuilder

|               |  String                |  StringBuffer   |  StringBuilder |
|---------------|:----------------------:|----------------:|---------------:|
| Storage Area  |  Constant String Pool  |  Heap           |  Heap          |
| Modifiable    |  No (Immutable)        |  Yes (Mutable)  |  Yes (Mutable) |
| Thread Safe   |  Yes                   |  Yes            |  No            |
| Thread Speed  |  Fast                  |  Very Slow      |  Fast          |

# Process and Thread

|                                |  Process                                                                    |  Thread                                              |
|--------------------------------|:---------------------------------------------------------------------------:|-----------------------------------------------------:|
| Basic                          |  Program in execution                                                       |  Lightweight process or part of it                   |
| Memory sharing                 |  Completely isolated and do not share memory                                |  Shares memory with each other                       |
| Resource consumption           |  More	                                                                     |  Less                                                |
| Efficiency                     |  Less efficient as compared to the process in the context of communication  |  Enhances efficiency in the context of communication |
| Time required for creation     |  More                                                                       |  Less                                                |
| Context switching time         |  Takes more time                                                            |  Consumes less time                                  |
| Uncertain termination          |  Results in loss of process                                                 |  A thread can be reclaimed                           |
| Time required for termination  |  More	                                                                     |  Less                                                |

# Resources shared among threads within a process
1. Text segment (instructions)
2. Data segment (static and global data)
3. BSS segment (uninitialized data)
4. Open file descriptors
5. Signals
6. Current working directory
7. User and group IDs

# int & Integer
| Key  | int | Integer |
|------|:---:|--------:|
| Type | A data type that stores 32 bit signed two's compliment integer | A wrapper class which wraps a primitive type int into an object |
| Purpose | storing integer value into memory | converting int into object and to convert an object into int as per requirement |
| Flexibility | less flexibility as it only allows binary value of an integer in it | more flexible in storing and manipulating an int data (Since Wrapper classes inherit Object class, they can be used in collections with Object reference or generics.) |
| Memory allocation | A primitive data type and takes 32 bits(4 bytes) to store | An object which takes 128 bits (16 bytes) to store its int value |
| Casting (from String in java) | directly assign (containing an integer only) or by casting | Integer(String) constructor or by parseInt(String) |
| Direct Conversion to Other base | cannot convert its integer value to other base | can directly convert its integer value to other bases such as Binary, Octal or Hexadecimal format using toBinaryString(), toOctalString() or toHexString() respectively |
| Allowed operations | do not allowed any of inbuilt functions to change its value or syntax | can reverse number or rotate it left or right using reverse(), rotateLeft() and rotateRight() respectively |
