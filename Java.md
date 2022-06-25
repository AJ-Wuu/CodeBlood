# Java SE 11
## How Java Works
* Source code -> plain text -> .java
* Compile into byte code -> .class **for JVM**
* Java Virtual Machine must be installed on a target computer
## Class & Object
* Class is a valid reference type, can be used in type casting
* Java code is structured with CLASS, which represents a concept and defines attributes it can store & operations and algorithms it is capable of
* Object is a specific instance of a class, which can have specific values for each attribute, invoke operations at run time, and be referenced using a variable of a relevant type
## Interface
* Interface is a valid reference type, can be used in type casting, and works with the ```instanceof``` operator
* An interface can inherit another interface
* Instance methods are by default public and abstract
* Contains concrete methods **only** if they are either default, private or static
* Can contain constants, but not variables
* Default method can **only** be defined in an interface
* Functional Interface defines just one abstract method
## Inheritance
* Superclass is more general, subclass is more specific
* Subclass must include all attributes in the superclass, but it could define more
* Static interface methods do not cause conflicts, because they are invoked via specific parent types and do not rely on the super inference
* ```extends``` only one superclass (multiple inheritance is not allowed), ```implements``` multiple interfaces
* Casting for Reference Type
  * Casting is required to assign parent to child reference type
  * No casting is required to assign child to parent reference type
  * Casting is not possible between objects of sibling types
  * Casting is required when invoking a polymorphic operation
* Invoke subtype specific operations using a specific reference type
* Using generic (superclass) types to define method parameters and return values help to promote better code reusability and extensibility
* ```this``` and ```super``` is not required when the reference is not ambiguous
## Name should not start with number characters (0~9), underscore \_ or dollar sign $
## StringBuilder (java.lang.StringBuilder)
* Automatically expand capacity if needed
* Objects are not thread-safe
* Objects are mutable
* Can instantiate with a predefined content or capacity
```java
StringBuilder stringbuilder = new StringBuilder("Hello ");  
stringbuilder.append("Java"); //now the original string is changed  
```
## Wrapper Classes for Primitives
* Construct wrapper object out of primitive or string using the ```valueof()``` method
* Extract primitive values out of the wrapper using the ```xxxValue()``` method
* Auto-boxing & Auto-unboxing
* Create wrapper or primitive out of the string using the ```parseXXX()``` method
* Covert a primitive to a string using the ```String.valueOf()``` method
* Wrapper classes provide constants for each type, such as ```Integer.MIN_VALUE```
## BigDecimal (java.math.BigDecimal) -> exact precision
* Parse Numerical Values (java.text.NumberFormat) -> NumberFormat.parse()
## Local Date and Time
* LocalDateTime.of(year, month, day, hours, minutes, seconds, nanoseconds)
* LocalDate.of(year, month, day)
* LocalTime.of(hours, minutes, seconds, nanoseconds)
* someDay.atTime(someTime), someDateTime.toLocalDate(), someDateTime.toLocalTime()
* .plus(XXX), .minus(XXX), .withXXX(), isBefore(), isAfter()
* Format and Parse Date and Time (java.time.format.DateTimeFormatter)
* Instants (java.time.Instant), Durations (java.time.Duration), Periods (java.time.Period)
* Zoned Date and Time (java.time.ZonedDateTime)
* Locale (java.util.Locale)
## Unified Modelling Language
* Case Diagram: business requirements
* Class Diagram: classes and their relationships (documenting access modifiers)
* Activity Diagram: program logic (the flow of operations)
* Sequence Diagram: interactions between objects
* State Transition Diagram: the life cycle of an object
* Deployment Diagram: physical deployment topology
## Ternary Operator: variable = Expression1 ? Expression2 : Expression3
## ```instanceof``` is a binary operator used to test if an object is of a given type: p instanceof Food
## Variables
* ```final``` marks constants
* ```abstract```
  * encourages class extensibility
  * cannot be directly instantiatied
  * abstract class purpose (but not a must) is to be extended by one or more concrete subclasses
  * concrete subclasses must override all abstract methods of their abstract parent
  * abstract class may override default methods
* Object Class
  * the ultimate parent of any other class
  * defines common, generic operations that all other classes inherit and reuse
* Type Inference: var value = "Hello"; //infers String
* ```static``` marks variables or methods that belong to the class context, which is shared by all instanced of the class
  * Objects can access shared static context
  * Current instance (```this```) is meaningless within the static context -> compilation error
* Reference: including phantom reference and soft reference (implicit) and they only occur if purposely used; any default variable reference is strong reference (explicit)
* Immutability: read-only, thread-safe without an overhead cost of coordinating synchronized access, cannot modify after object construction -> initialized immediately, no setter (```public class Product { private BigDecimal price = BigDecimal.ZERO; }```)
* Enumeration: a fixed set of instances of a specific type
```java
public enum Condition {
    HOT, WARM, COLD;
}

public enum Condition {
    HOT("Warning HOT!"),
    WARM("Just right"),
    COLD("Warning COLD!");
    private String caution;
    private Condition(String caution) {
        this.caution = caution;
    }
    public String getCaution() }
        return caution;
    |
}
```
## Compare
```java
public class Compare implements Comparator<String>{
    public int compare(String s1, String s2) {
        return s2.length() - s1.length(); //returns positive, put s1 behind s2 (positive-behind; negative-before)
    }
}

String[] names = {"Mary","Jane","Elizabeth","Jo"};
Arrays.sort(names, new Compare());
```
## Polymorphyism
* means many forms, when a method is declared in a superclass and is overridden in a subclass, the subclass method takes precedence without casting reference to a specific subclass type
## Overload Methods
* ```public void setPrice(double price) ... public void setPrice(BigDecimal price) ... public void setPrice(BigDecimal price, BigDecimal discount)```
* Must have the same return type
* Must have different parameter numbers or types or both
## Reuse Constructors
* Such call must be the first line of the code in the invoking constructor
```java
public class Product {
    private String name;
    private BigDecimal price;
    public Product(String name, double price) {
        this(name); //invoking another constructor
        this.price = BigDecimal.valueOf(price);
    }
    public Product(String name) {
        this.name = name; //reused constructor
        this.price = BigDecimal.ZERO;
    }
}
```
## Memory Allocation
* Storage
  * Stack
    * A memory context of a thread, storing local method variables
    * Each thread has its own stack
    * Hold only primitives and object references
    * Passing parameters means copying stack values (a copy of a primitive value / an object reference value)
    * Not all variables are on the stack
  * Heap
    * A shared memory area, accessible from different methods and thread contexts
    * Hold classes and objects
    * Primitives can be placed into a heap as part of an object
* Cleanup
  * Objects remain in the heap as long as they are still referenced
    * An object reference is null until it's initialized
    * Assigning null doesn't destroy the object, just indicates the absense of this specific reference (there could be other references)
  * Garbage Collection -> background process for unused memory within run time
    * Deferred -- not immediately triggered when **all** object references to the object are lost
    * Prompt: ```System.gc()``` or ```Runtime.getRuntime().gc()```
## Factory Method Pattern
* Hide the use of a specific constructor
* Dynamically choose the subtype instance to be created
* Analyze conditions and produce an instance of a specific subtype
* Invokers an remain subtype unaware
* Later addition of extra subtypess may not affect such invokers

```java
public abstract class Product {
    public Prodct(...) { }
}
public class Food extends Product {
    public Food(...) { }
}
public class Drink extends Product {
    public Drink(...) { }
}

public class ProductFactory {
    public static Product createProduct(...) {
        switch(productType) {
            case FOOD:
                return new Food(...);
            case DRINK:
                return new Drink(...);
        }
    }
}
```
## Stream Pipeline Processing Operation
* Intermediate: perform action and produce another stream
  * filter, map, flatMap (merge streams), peek, distinct, sorted, dropWhile, skip, limit, takeWhile
* Terminal: traverse stream pipeline and end the stream processing
  * forEach, forEachOrdered, count, min, max, sum, average, collect, reduce, allMatch, anyMatch, noneMatch, findAny, findFirst
* Short-circuit: product finite result, even if persented with infinite input
## Collection
* Collection API Interface
  * collection.toArray(), collection.removeIf(conditionFunction)
  * Iterable<T>: a top-level interface which allows any collection to be used in a forEach loop
  * Collection<E>: extends Iterable
  * List<E>, Set<E>, SortedSet<E>, Deque<E>, Map<K,V>
  * ArrayList<E>, HashSet<E>, TreeSet<E>, ArrayDeque<E>, HashMap<K,V>
* java.util.Collections
  * .sort(), .reverse(), .shuffle(), .binarySearch(), .fill()
* Collection can be corrupted if accesses concurrently from multiple threads
  * Any object in a heap is not thread-safe if it is not immutable
  * Any thread can be interrupted, even when it is modifying an object, making other threads observe imcomplete modification state
  * Making collection thread-safe does not guarantee the thread safety to the objects it contains; only immutable objects are automatically thread-safe
* Prevent Collections Corruption: Unmodifiable (fast, but read-only), Synchronized (slow, but unscalable), Copy-on-write (fast, but consumes memory)
