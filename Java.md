# Java
## How Java Works
* Source code -> plain text -> .java
* Compile into byte code -> .class **for JVM**
* Java Virtual Machine must be installed on a target computer
## Best Practices
* Use built-in utils like: `StringUtils.isEmpty()`, `StringUtils.isBlank()`, `CollectionUtils.isEmpty()`, `EnumUtils.getEnumIgnoreCase()`, `assertEquals(2, result.size());`
* No need to specify Access Modified in unit tests
  * if needed, do `@VisibleForTesting static void test()` instead of `public static void test()`
* Combine switch with return
```java
return switch (entityType) {
    case ATTACHMENT -> !Sets.intersection(authResponse.getPermissions(), ATTACHMENT_DELETE_PERMISSIONS).isEmpty();
    case COMMENT -> !Sets.intersection(authResponse.getPermissions(), COMMENT_DELETE_PERMISSIONS).isEmpty();
    case TICKET -> !Sets.intersection(authResponse.getPermissions(), TICKET_DELETE_PERMISSIONS).isEmpty();
    default -> throw new IllegalArgumentException("Unknown resource type " + entityType);
};
```
* Use method reference operator `::`
  * uses direct reference to the method by name instead of providing a delegate to the method (like lambda expressions)
```java
// use
stream.forEach(System.out::println);

// instead of
stream.forEach(s -> System.out.println(s)); 
```

## Name should not start with number characters (0~9), underscore \_ or dollar sign $
## Access Modifier
* `default`: accessible only within the same package
* `private`: only visible within the enclosing class
* `protected`: accessible within the same package or subclasses in different packages
* `public`: accessible from everywhere
## Class & Object
* Class is a valid reference type, can be used in type casting
* Java code is structured with CLASS, which represents a concept and defines attributes it can store & operations and algorithms it is capable of
* Object is a specific instance of a class, which can have specific values for each attribute, invoke operations at run time, and be referenced using a variable of a relevant type
* Nested Classes
  * Defining classes inside other classes to encapsulate logic and constrain context of use
  * Member Inner Class
    * can access **private/static/instance** variables and methods of the Outer Class
  * Local Inner Class
    * can access **final or effectively final** Outer method variables and parameters
    * contains logic complex enough to require the algorithms be wrapped up as a class
  * Anonymous Inner Class
    * is an implementation of an interface or extension of a class to override operations
    * **cannot declare constructors, only invoke the constructor of the Parent Class**
    * is implemented inline and instantiated immediately
    * can access **final or effectively final** Outer method variables and parameters
## Lambda Expressions
* An inline implementation of a functional interface
* ```Function<String, String> lambda = x -> x.toUpperCase();``` is equivalent to ```Function<String, String> lambda = String::toUpperCase;```
* Parentheses: ```Consumer<String> con = (final String x) -> System.out.print(x);``` -> with ```final```, the variable type needs to be explicit (not ```var```) and has parentheses
* Consistent -- variables should all be explicit or implicit
## Interface
* Interface is a valid reference type, can be used in type casting, and works with the ```instanceof``` operator
* An interface can inherit another interface
* Instance methods are by default public and abstract
* Contains concrete methods **only** if they are either default, private or static
* Can contain constants, but not variables
* Default method can **only** be defined in an interface
  * Must be public
  * Helps minimize code duplication
  * Provides a single location to write and edit code
  * Can be overridden anytime
  * Is overridden with per-class precision
* Functional Interface defines just one abstract method
* Even not declared explicitly, the variable in an interface is **automatically static final**, so its value cannot be changed; the method in an interface is **automatically abstract**
## Inheritance
* **A superclass (concrete or abstract) method takes priority over an interface default method** -> static has lower priority than default
* **A subtype interface's default method takes priority over a supertype interface default method**
  * using [wildcard](https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html) like `List<? extends Integer>`
* **If there is a conflict, treat the default method as abstract; the concrete class must provice its own implementation; this may include a call to a specific interface's implementation of the method**
* An interface doesn't store the state of an instance
* An abstract class may contain instance fields -> **abstract** must be public
* A class cannot extend multiple abstract classes
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
  * ```var``` should **NOT** be used when:
    * a declaration without an initial value
    * initialization with a null value
    * compound declarations (eg. ```var price = 9.95, tax = 0.05;```)
    * array initializers (eg. ```var prices = {9.95, 5, 3.50};```)
    * fields (eg. ```public var price;```)
    * parameters (eg. ```public void setPrice(var price)```)
    * method return type (eg. ```public var getPrice() { return price; }```)
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
## Comparator
* ```thenComparing``` adds additional comparators
* ```reversed``` reverses sorting order
* ```nullsFirst``` and ```nullsLast``` return comparators that enable sorting collections with null values
```java
public class Compare implements Comparator<String>{
    public int compare(String s1, String s2) {
        return s2.length() - s1.length(); //returns positive, put s1 behind s2 (positive-behind; negative-before)
    }
}

String[] names = {"Mary","Jane","Elizabeth","Jo"};
Arrays.sort(names, new Compare());
```
## Collection
* Collection API Interface
  * All
    * validate elements within the collection (eg. check uniqueness)
    * order elements
    * provide thread-safe operations (protect internal storage from corruption when it is accessed concurrently from multiple threads)
    * get implemented internally by using arrays
  * Some
    * allow programs to store groups of objects in memory
    * expand dynamically (eg. Iterable doesn't)
  * collection.toArray(), collection.removeIf(conditionFunction)
  * Interfaces
    * Iterable\<T>: a top-level interface which allows any collection to be used in a forEach loop
    * Collection\<E>: extends Iterable
    * List\<E>, Set\<E>, SortedSet\<E>, Deque\<E>, Map<K,V>
  * Classes
    * ArrayList\<E>, HashSet\<E>, TreeSet\<E>, ArrayDeque\<E>, HashMap<K,V>
  * A **fixed-sized** List can be created from the array using Arrays.asList(\<T> ...)
  * A **read-only** List/Set can be created from List.of(\<T> ...)/Set.of(\<T> ...)
  * A **read-only** Map can be created from ```of(\<key>, \<value>, ...)``` overloaded for upto ten map entries or ```ofEntries (Map.entry\<key, value>... entries)```
  * Set/ArrayDeque can be created from **any other Collection** to populate with initial values
  * HashMap can be created from **any other Map** to populate with initial values
  * Deque uses ```offerFirst(T)``` and ```offerLast(T)``` to insert
  * Null values are not allowed in Deque
  * Null key (**just one**) and null values are allowed in HashMap
  * If Deque is empty, poll and peek return null
* java.util.Collections
  * .sort(), .reverse(), .shuffle(), .binarySearch(), .fill()
* Collection can be corrupted if accesses concurrently from multiple threads
  * Any object in a heap is not thread-safe if it is not immutable
  * Any thread can be interrupted, even when it is modifying an object, making other threads observe imcomplete modification state
  * Making collection thread-safe does not guarantee the thread safety to the objects it contains; only immutable objects are automatically thread-safe
* Prevent Collections Corruption: Unmodifiable (fast, but read-only), Synchronized (slow, but unscalable), Copy-on-write (fast, but consumes memory)
## Ternary Operator: variable = Expression1 ? Expression2 : Expression3
## ```instanceof``` is a binary operator used to test if an object is of a given type: p instanceof Food
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
  * e.g., with default conversion values: `Boolean.valueOf("true")` -> `true`, `Boolean.valueOf("yes")` -> `false`
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
## Unified Modelling Language
* Case Diagram: business requirements
* Class Diagram: classes and their relationships (documenting access modifiers)
* Activity Diagram: program logic (the flow of operations)
* Sequence Diagram: interactions between objects
* State Transition Diagram: the life cycle of an object
* Deployment Diagram: physical deployment topology
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
## Streams
* An immutable flow of elements
* Can be **sequential** (default) or **parallel** (depending on which method was invoked last)
  * Stream --subdivided-> subsets --subdivided-> ... --combined-> ... --combined-> Stream
  * Processing order is **stochastic (indeterminate)**
  * Stateless (state of one element must not affect another element)
  * Non-interfering (data source must not be affected)
  * Associative (result must not be affected by the order of operands)
  * Incorrect handling can corrupt memory and slow down processing
  * ```toMap``` in sequential mode, ```toConcurrentMap``` in parallel mode
  * Beneficial if:
    * stream contains large number of elements
    * multiple CPU cores are available to physically parallelize computations
    * processing of stream elements requires significant CPU resources
* Once an element is processed, it is no longer available from the stream
* Stream pipeline traversal uses **method chaining - intermediate** operations return streams (not always starting with the first stream method encountered)
* Lazy -> Significant Efficiencies
  * intermediate actions are deferred until stream is traversed by the terminal operation
  * the chain of activities could be fused into a single pass on data
  * stream processing ends as soon as the result is determined; remaining stream data can be ignored
* Stream operations use **functional interfaces** and can be implemented as **lambda expressions**
* Stream methods take **lambda expressions as arguments**
* Stream methods are **chained** together
* **Collections** are converted to streams (standard or parallel)
* Stream may represent both finite and infinite flows of elements
* Interfaces
  * BaseStream: defines core stream behaviours (managing the stream in a parallel or sequential mode)
  * Stream, DoubleStream, IntStream, LongStream Interfaces extend BaseStream
  * Use generics
  * To avoid excessive boxing and unboxing, primitive stream variants are also provided
  * Stream can be obtained from **any collection and array** or by using **static methods** of the Stream class
* Stream Pipeline Processing Operation
  * **[`Optional`]([https://dzone.com/articles/optional-in-java](https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/util/Optional.html)) is a wrapper object that protects against null value**
    * not serializable
    * reason of being introduced is to save the trouble of `null`-checking on each object
    * `@NotNull` is not always helpful because **Java compiler will compile the code without these `null`-checks without complaint**
    * **Java compiler forces user to handle the case of an empty `Optional` value**, like `orElse(new Foo())`, `orElseGet(() -> { /* ...lazily create a object foo... */ })`, `orElseThrow(() -> { /* ...lazily create a object foo... */ })`, `.ifPresent(foo -> { /* ...do something with foo... */ })`, etc.
    * best practice: a variable whose type is `Optional` should never itself be null; it should always point to an `Optional` instance
    * (NOT PREFERRED) if decided to use `possibleFoo.get()`, need to check `possibleFoo.isPresent()` or `possibleFoo.isEmpty()` first
  * Intermediate: perform action and produce another stream
    * filter, map, flatMap (merge streams), peek, distinct, sorted, dropWhile, skip(long l), limit(long l), takeWhile(Predicate P), dropWhile(Predicate P)
  * Terminal: traverse stream pipeline and end the stream processing
    * forEach, forEachOrdered, count, min -> Optional object, max -> Optional object, sum, average -> OptionalDouble, collect, reduce, allMatch, anyMatch, noneMatch, findAny, findFirst
  * Short-circuit: product finite result, even if persented with infinite input
    * allMatch(Predicate P), anyMatch(Predicate P), noneMatch(Predicate P), findAny() -> Optional object, findFirst() -> Optional object
* Use **functional interfaces** from java.util.function package
* Can be implemented using lambda expressions
* Basic Function Purpose
  * ```Predicate``` performs tests -> defines method ```boolean test(T t)``` to apply conditions to filter elements
  * ```Function``` converts types -> defines method ```R apply(T t)```
  * ```UnaryOperator``` (a variant of function) converts values -> defines method ```T apply(T t)```
  * ```Consumer``` processes elements -> defines method ```void accept(T t)```
  * ```Supplier``` produces elements -> defines method ```T get()```
* Operation ```sum``` and ```average``` are available only for primitive stream variants DoubleStream, IntStream and LongStream
* Actions
  * Operations ```peek```, ```forEach``` and ```forEachOrdered``` accept **Consumer\<T>** interface
  * Lambda expression must implement **abstract** ```void accept(T t)``` method
  * Default ```andThen``` method provided by the **Consumer** interface combines consumers together
* Filtering
  * Method ```filter```accepts **Predicate\<T>** interface and returns a stream comprising only elements that satisfy the filter criteria
  * Lambda expression must implement **abstract** ```boolean test(T t)``` method
  * Default ```and```, ```or``` and ```negate``` methods provided by the **Predicate** interface
  * Static ```not``` and ```isEqual``` methods provided by the **Predicate** interface
* Mapping
  * Method ```map```accepts **Function\<T, R>** interface and returns a stream comprising elements produced byy this function based on the original stream content
  * Lambda expression must implement **abstract** ```R apply(T t)``` method
  * Default ```andThen``` and ```compose``` methods (combine functions together) provided by the **Function** interface
  * Static ```identity``` method provided by the **Function** interface
  * Primitive variants: ```mapToInt```, ```mapToLong``` and ```mapToDouble```
* ```reduce``` Operation -> Aggregate Stream Data -> Produce a single result from the stream of values
* ```collect``` Operation -> General Logic -> Perform a mutable reduction operation on the elements of the stream
## Java Logging API
* Logging Method Categories
  * log
  * logp (log precise)
  * logrb (log with resource bundle)
  * entering, exiting, throwing
  * severe, warning, config, info, fine, finer, finest
* Guarded Logging: to avoid processing messages that are due to be discarded
  * Logging level can be set programmatically or via the configuration
  * Message is concatenated, but is not recorded because it is below the logging-level threshold
  * Message is not processed if it is below the logging-level threshold
  * Object parameters can be used to avoid concatenating messages unnecessarily
* Log Writing Handling
  * Logger writes log messages with different log levels
    * forming a hierarchy
  * Log Handler writes log messages to a log destination
    * Console
    * File
    * Memory
    * Socket
    * Stream
  * Filters can be set for both Logger and Log Handler
  * Log Handlers use formatters: SimpleFormatter, XMLFormatter
* Logging Configuration -> ```logging.properties```
* Exceptions
  * Checked: must be caught OR must be explicitly propagated
  * Unchecked (Runtime): may be caught AND do not have to be explicitly propagated
  * Custom
    * Must extend class ```Exception``` or another more specific descedant of ```Throwable```
    * May provide constructors that utilize superclass constructor abilities like providing an error message, wrapping another exception indicating a cause of this exception
  * Throwing
    * when exception is raised, normal program flow is terminated; control is passed to the nearest available exception handler
    * if exception handler is not available within this method, Unchecked -> automatically propagated to the invoker; Checked -> must be explicitly listed within the ```throws``` clause
  * Catching
    * specific expection handlers (catching exception subtypes) must be placed before generic handlers
    * Unchecked -> optional
    * when exception occurs within the try block, program flow is interrupted; control is passed to the nearest catch that matches the exception type
  * No mathing exception handler -> program will exit
  * Try-with-Parameters syntax provides auto-closure of multiple resources
    * Automatica closure is provided by an implicitly formed final block
    * may produce supressed exceptions
  * Try-with-Resources aka Automatic Resource Management(ARM)
    * `try` statement contains one or more resources declarations
    * the resource is implemented with AutoCloseable
    * note that we should not do `try (BufferedReader reader = new BufferedReader(new FileReader(cellConfigFile)); FileWriter writer = new FileWriter(cellConfigFile))`, as `new FileWriter(PATH)` essentially removes the original file, which leaves nothing for the reader to read
## Java I/O API
* Streams are categorized on:
  * Type of data to carry (text, binary, etc.)
  * Direction (input or output)
  * Type of the source or destination to which this stream is connected
  * Additional features (filtering, transformation of data, etc.)
* Packages: java.io and java.nio
  * ```abstract``` -> general text and binary data read and write abilities
  * ```concrete``` -> descend from these parents to provide different types of IO stream handlers
* Serialization
  * Serialization: writing objects from memory into a stream
    * can write data outside of the secure environment of the program
  * Deserialization: reading objects from the stream
  * Can be customized
  * Data is serialized in a binary form
  * Use Cases:
    * Swapping objects to avoid running out of memory
    * Sending objects across network
    * Not suitable for long-term data storage
    * Specific to the compiled code version
  * Include the entire object graph, except **transient** variables
  * Set **Path** Properties using class Files
    * Set last modified time
    * Set permissions
    * Look up users and groups using ```FileSystem UserPrincipalLookupService```
    * Set owner and group
  * May create non-existent path object **without causing exceptions until trying to use them**
  * Represent Zip Archive as a FileSystem
  * Access HTTP Resources
## Concurrency and Multithreading
* `synchronized`: keyword to mark a block, allows only one thread to execute at any given time
* Parallelism -> different execution paths that run simutaneously
* Concurrency -> different execution paths that may not run simutaneously
* Implement Threads
  * Describe thread actions
  * Instantiate thread object
  * Schedule thread to run
* Thread Life Cycle
  * NEW -> RUNNABLE -> (BLOCKED/WAITING/TIMED_WAITING -> RUNNABLE -> ...) -> TERMINATED
    * WAITING needs a signal to be notified
    * TIMED_WAITING waits for a set-length of time
  * Use an ```ExecutorService``` to start, stop accepting new, wait for completion, and stop concurrent tasks
* Interrupt Thread
  * RUNNABLE **may** check for interrupt signal
  * WAITING/TIMED_WAITING **must** catch ```InterruptedException```, which puts it back to RUNNABLE and then decides what to do
* Block Thread
    * Monitor object helps to coordinate order of execution of threads
      * Any object or a class can be used as a monitor
      * Allows threads to enter blocked or waiting states
      * Enables mutual exclusion of threads and signaling mechanisms
    * ```synchronized``` enforces exclusive access to the block of code
      * Thread that first enters the synchronized block remains in the RUNNABLE state
      * All other threads accessing the same block enter the BLOCK state
      * When a RUNNABLE thread exits the synchronized block, the lock is released
      * Another thread is now allowed to enter the RUNNABLE state and place a new lock
* Thread Properties
  * custom name
  * unique id
  * marked as a daemon or a user(default) thread
  * may wait for another thread to terminate
  * could be assigned a priority
* Create Executor Service Objects (java.util.concurrent.Executors -> thread management automations with different ```ExecutorService``` objects)
  * Fixed Thread Pool reuses a fixed number of threads
  * Work Stealing Pool maintains enough threads to support the given parallelism level
  * Single Thread Executor uses a single worker thread
  * Cached Thread Pool creates new threads as needed or reuses existing threads
  * Scheduled Thread Pool schedules tasks to execute with a delay and/or periodically
  * Single Thread Scheduled Executor schedules tasks to execute with a delay using a single worker thread
  * Unconfigurable Executor Service provides a way to "freeze" another Executor Service configuration
* Locking Problems
  * Starvation: waiting for a resource blocked by another busy thread
  * Livelock: forming an indefinite loop, expecting confirmation of completion from each other
    * ```while (b.isOver()) { //do A } aOVer = true;```
    * ```while (a.isOver()) { //do B } bOVer = true;```
  * Deadlock: two or more threads are blocked forever, waiting for each other
    * ```synchronized (a) { synchronized (b) { ... } }```
    * ```synchronized (b) { synchronized (a) { ... } }```
* Thread-Safe
  * Stack values (local variables/methods)
  * Immutable objects in a shared heap memory (cannot be changed at all)
  * Mutable objects in a shared heap memory are **thread-unsafe**
    * shared between all threads
    * may be inconsistent or corrupted
    * compiler may choose cache heap value locally within a thread (not updated in-time)
* Ensure Consistent Access to Shared Data
  * Disable compiler optimization that is caching the shared value locally within a thread
  * ```volatile``` instructs Java compiler
    * Not to cache the variable value locally
    * Always read it from the main memory
    * Applies all changes to the main memory that occurred in a thread before the update of the volatile variable
* Non-Blocking Atomic Actions
  * Action is atomic if it is guaranteed to be performed by a thread without an interruption
  * Cannot be interleaved
  * Only actions performed by a CPU in a single cycle are by default atomic
  * Variable assignments are atomic actions, except ```long``` and ```double``` (64-bit, taking more than a single step to assign these on a 32-bit platform)
  * `+ - / * % ++ --` are not atomic
  * java.util.concurrent.atomic -> lock-free thread-safe programming of atomic behaviours on single variables
  * Atomic Object with `AtomicReference`: see [doc here](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/atomic/AtomicReference.html)
  * Volatile
* Use intrinsic lock to enforce an exclusive access to a shared object
  * Order of execution and object consistency are ensured
  * Synchronized logic creates a bottlenect in a multithreaded application
  * Performance and scalability can be significantly degraded
* Intrinsic Lock Automation: ```Collections```
* Non-Blocking Concurrency Automation
  * java.util.concurrent
  * All mutative operations (add, remove, etc.) make fresh copies of the underlying collection
  * The read-only snapshot of merge content is used for traversal
* Alternative Locking Mechanisms
  * Allows actions to be performed on an object, without interference from other threads
  * Available from java.util.concurrent.locks
  * Write lock prevents other threads from concurrently modifying the object
  * Read lock can be acquired if Write lock is not held by another thread, allowing concurent read actions
## Modules
* public -> to everyone, only to specific modules, only within a module
* Legacy (non-modular) Java application deployment and execution
  * Compile Java classes using ```javac``` utility
  * Package Java class into Java Archive (JAR) using ```jar``` utility
  * (Optionally) provide JAR descriptor MANIFEST.MF file
    * May contain name of the main application class
    * May set up class path to reference other archives containing classes required by this application
  * Execute Java Application using ```java``` runtime
* Non-Modular Java Characteristics
  * Packages provided logical grouping of classes
  * Packages did not impose physical restrictions on how they are used
  * Classes are packaged into jar files and accessed via classpath
  * Common deployment of related classes is not enforced
  * Visibility of classes is controlled with public and default access modifiers
  * Encapsulation can always be bypassed using reflection
  * Impossible to restrict in which exact other packages the code can be used
* Module
  * High-level code aggregation
  * Comprises one or more closely related packages and other resources
  * Module descriptor ```module-info.class``` stored in the module's root folder contains
    * Unique module name
    * Required module dependencies (other modules that this module depends on)
    * Packages that this module exports, making them available to other modules (all other packages contained within the module are unavailable to other modules)
    * Permissions to open content of this module to other modules using reflection
    * Services this module offers to other modules
    * Services this module consumes
    * Modules do not allow splitting java packages even when they are not exported (private)
* Java Platform Module System (JPMS) -> placed into the module root directory
* Define Module Dependencies
  * ```requires <modules>``` -> **dependent on another module**
    * Only exported packages are readable by the requiring module
    * Any non-public is not readable, even from exported packages
  * ```requires transitive <modules>``` -> **readibility up the requirement chain**
    * Anything requiring the current module has access to packages exported by the current module and by anything the current module requires transitively
    * Implied readability
  * ```requires static <modules>```-> **dependent at compile time only**
  * ```requires java.base``` is **implied for all modules**, but any other module has to be referenced explicitly
    * Not dependent on any other modules
    * Exports all of the platform's core packages
  * A service provider references a service module with the "requires" directive
  * A service consumer references a service module with the "requires" directive
  * These instructions accept comma-separated lists of module names
* Export Module Content
  * ```exports <packages>``` -> **accessible to all other modules**
    * Includes all public
    * Excludes any private, protected or package access
  * ```exports <packages> to <other modules>``` -> **accessible to sprcific modules**
    * Includes all public
    * Excludes any private, protected or package access
    * Excludes any not specified module
    * A comma-separated list of module names
    * Qualified export
* Open Module Content
  * Module may allow runtime-only access to a package using "opens" directive
  * Exporting a package means making all of its public types (and their nested public and protected types) available to other modules
  * ```opens <packages>``` directive specifies packages whose entire content is accessible to all other modules at run time
  * ```opens <packages> to <other modules>``` restricts opened packages to a list of specific modules
  * Opening a package works similar to export, but also makes all of its non-public types available via reflection
  * Modules that contain injectable code should use "opens" directive, because **injections work via reflection**
  * These instructions accept comma-separated lists of module names
* Open an Entire Module
  * Module may allow runtime-only access to all of its content
  * ```open module``` specifies that this module's entire content is accessible to all other modules at run time via reflection
* Produce and Consume Services
  * Service comprises an interface or abstract class and one or more implementation classes
  * ```provides <service interface>``` with ```<classes>``` directive specifies that module provides one or more service implementations that can be **dynamically discovered by service consumer**
  * ```provides <service>``` with ```<implmentation classes>``` directive specifies that a module provides a service implementation, making the module a service provider
  * ```uses <service interface>``` directive specifies an interface or an abstract class that defines a service that this module would like to consume
  * ```uses <service>``` directive specifies a service used by this module, making the module a service consumer
* Multi-Release Module Archives
  * Only one copy of a module can be placed into a module-path
  * Multi-Release JAR can be used to support different versions of code
  * Module root directory may contain either a **default version of the module** or a **non-modularized version** of code to be used
  * **Specific version** of code may be provided for each version of Java
  * **Versioned descriptors (module-info)** are optional and must be identical to the **root module descriptor**, with two exceptions:
    * Can have different non-transitive requires clauses of java.* and jdk.* modules
    * Can have different use clauses
* Compile Module (specify all Java sources from various packages to contain, includ packages that are exported by this module to other modules and a module-info, and reference other modules required for this module to compile) -> Package module into a JAR file (may describe a main class for this module) -> Verify packaged module (get description of the compiled module to find out which modules it contains, exports, requires, etc.)
* Execute a Modularized Application
  * Modular Application Using Module Path
    * Classes are located using ```-p``` or ```--module-path``` option
    * Reference the main class using ```-m``` or ```--module``` option
    * Non-modular JARs are treated as Automatic Modules
  * Non-Modular Application (reminder)
    * Classes are located using ```-cp``` or ```--class-path``` option
    * Modular JARs located via class path are treated as non-modular for backward compatibility
* Runtime Image (JIMAGE)
  * Create & Optimize Custom: ```jlink```
  * Execute: ```<image>/bin/java -m <module name>``` or ```<image>/bin/<command name>```
## Annotation
* A form of metadata
* [`@Deprecated`](https://docs.oracle.com/javase/8/docs/technotes/guides/javadoc/deprecation/deprecation.html)
  * read by the compiler
  * mark a method as deprecated
  * will generate a deprecation compile-time warning if the method is used
* ```@SafeVarargs``` supresses heap-pollution warning when using var-args
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(BusinessPolicies.class)
public @interface BusinessPolicies {
    String name() default "default policy";
    String[] countries();
    String value();
}
```
