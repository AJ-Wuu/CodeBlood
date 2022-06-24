# Java SE 11
## How Java Works
* Source code -> plain text -> .java
* Compile into byte code -> .class **for JVM**
* Java Virtual Machine must be installed on a target computer
## Class & Object
* Java code is structured with CLASS, which represents a concept and defines attributes it can store & operations and algorithms it is capable of
* Object is a specific instance of a class, which can have specific values for each attribute, invoke operations at run time, and be referenced using a variable of a relevant type
## Inheritance
* Superclass is more general, subclass is more specific
* Subclass must include all attributes in the superclass, but it could define more
* Static interface methods do not cause conflicts, because they are invoked via specific parent types and do not rely on the super inference
* An interface can inherit another interface
* ```extends``` only one superclass, ```implements``` multiple interfaces
## Name should not start with number characters (0~9), underscore \_ or dollar sign $
## StringBuilder (java.lang.StringBuilder)
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
* Graphically represent business requirements
* Modelling Classes: represent classes and their relationships
* Modelling Interactions and Activities: represent the flow of operations
## Variables
* ```final``` marks constants
* Type Inference: var value = "Hello"; //infers String
* ```static``` marks variables or methods that belong to the class context, which is shared by all instanced of the class
  * Objects can access shared static context
  * Current instance (```this```) is meaningless within the static context -> compilation error
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
* ```abstract``` encourages class extensibility, cannot be directly instantiatied
## Overload Methods
* ```public void setPrice(double price) ... public void setPrice(BigDecimal price) ... public void setPrice(BigDecimal price, BigDecimal discount)```
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
    * Hold only primitives and object references
    * Passing parameters means copying stack values (a copy of a primitive value / an object reference value)
  * Heap
    * A shared memory area, accessible from different methods and thread contexts
    * Hold classes and objects
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
