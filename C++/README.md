# [C++](https://en.cppreference.com/w/)
## [Canonical Project Structure](https://www.open-std.org/jtc1/sc22/wg21/docs/papers/2018/p1204r0.html)
### Entry Point
* QuRT (Qualcomm Real-Time Operating System)
  * can globally schedule the highest-priority runnable software threads and directs interrupts to the lowest-priority hardware thread
  * manages software and hardware watchdog timers to detect and reset system failures
  * uses `main()`, but `argc`/`argv` might be unusable
* Arduino-Like Platforms
  * a microcontroller development environment where the primary program entry points are `setup()` and `loop()` functions, instead of the traditional `main()` function
  * `setup()` runs once at the start to initialize hardware
  * `loop()` continuously executes the main program logic in a loop

### C++ Standard Template Library (STL)
* A set of template classes and functions that provides the implementation of common data structures and algorithms
#### Containers
* Data structures used to store objects and data according to the requirement
* Each container is implemented as a template class that also contains the methods to perform basic operations on it
* Every STL container is defined inside its own header file
* Types
  * Sequence Containers: array, vector, deque, list, etc.
  * Container Adaptors: stack, queue, priority queue
  * Associative Containers: set, map, multiset, multimap
  * Unordered Associated Containers: unordered set, unordered map, unordered multiset, unordered multimap
* Examples: `priority_queue<pair<long long, int>, vector<pair<long long, int>>, greater<>> minHeap;`
#### Algorithms
* Functions to perform common operations on data
* Implemented with the most efficient version of the algorithm
* Defined inside the `<algorithm>` and `<numeric>` header file
* Types
  * Manipulative Algorithms: `copy`, `fill`, `transform`, `replace`, `swap`, `reverse`, `rotate`, `remove`, `unique`
  * Non-Manipulative Algorithms: `max_element`, `min_element`, `accumulate`, `count`, `find`, `is_permutation`, `is_sorted`, `partial_sum`
#### Iterations
* Pointer-like objects that are used to point to the memory addresses of STL containers
* One of the most important components that contributes the most in connecting the STL algorithms with the containers
* Defined inside the `<iterator>` header file
* Types
  * Input Iterators: used to read values from a sequence once and only move forward
  * Output Iterators: used to write values into a sequence once and only move forward
  * Forward Iterators: combine the features of both input and output iterators
  * Bidirectional Iterators: support all operations of forward iterators and additionally can move backward
  * Random Access Iterators: support all operations of bidirectional iterators and additionally provide efficient random access to elements
#### Functors
* Objects that can be treated as though they are a function
* Defined inside the `<functional>` header file
* Types
  * Arithmetic Functors: plus, minus, multiply, divide, modulus, negate
  * Relational Functors: equal_to, not_equal_to, greater, greater_equal, less, less_equal
  * Logical Functors: logical_and, logical_or, logical_not
  * Bitwise Functors: bit_and, bit_or, bit_xor
#### Utility and Memory Library
* Defined in the `<utility>` header
* Pair
  * used to combine together two values that may be of different data types
  * stores two heterogeneous objects as a single unit
  * the order of the first and second element is fixed, such as a key-value pair of a map
  * can be assigned, copied, and compared
  * functions: `make_pair`, `swap`, `tie` (unpack the pair values into separate variables)
* Move Semantics
  * allows the transfer of resources from one object to another without copying
  * **`std::move` means no longer need this value**
  * while one can steal the resources, but one must leave the source (original) object in a valid state where it can be correctly destroyed
  * copy uses lvalue reference, but move uses rvalue reference
    * **move is used to convert an lvalue reference into the rvalue reference**
```cpp
// The standard signature for a Copy constructor takes a reference to the object
Foo(const Foo& original) {}

// The new signature for a Move constructor takes an rvalue reference to the object
Foo(Foo&& original) {}

// The new signature for a Move-assignment operator, again, takes an rvalue reference to the object
Foo& operator=(Foo&& original) {}

// Use std::move when the lvalue is no longer needed
// Move from `foo` to `bar`, if Foo has a move constructor
// If Foo does not have a move constructor, foo will be copied
Foo foo(1, 2);
Foo bar = std::move(foo);
``` 
* Smart Pointers
  * contains a garbage collection mechanism
    * when the object is destroyed, it frees the memory as well
    * the destructor is automatically called when an object goes out of scope
  * a wrapper over the raw pointers with overloaded operators like `*` and `->`
  * helps in avoiding errors associated with pointers (like memory leaks, dangling pointers, wild pointers, data inconsistency and buffer overflow)
  * ```cpp
    class SmartPtr {
        int* ptr; // Actual pointer
    public:
        // Constructor
        explicit SmartPtr(int* p = NULL) { ptr = p; }
    
        // Destructor
        ~SmartPtr() { delete (ptr); }
    
        // Overloading dereferencing operator
        int& operator*() { return *ptr; }
    };
    ```
  * `std::unique_ptr`: avoids memory leaks and indicates **ownership**
    * an object may be owned by exactly one `unique_ptr`
      * prefer to pass and return by value
    * cannot be copied or copy-assigned, though **can be moved**, and no other entity should destroy the managed object underneath
    * once the `unique_ptr` goes out of scope, the object it has managed will be automatically deleted
    * **use `std::move` to transfer ownership**
      * ```cpp
        auto my_foo = std::make_unique<Foo>(42, "abc");
        my_container.Add(std::move(my_foo));
        ```

| Types | Usage | Flow |
|-------|-------|------|
| auto_ptr | stores a pointer to a single allocated object | <img src="https://github.com/user-attachments/assets/0074b0ce-ff5a-40de-8f2e-edd134ed0230" width="600px" /> |
| unique_ptr | stores one pointer only, can assign a different object by removing the current object from the pointer | <img src="https://github.com/user-attachments/assets/55e3561b-16bb-4574-8c68-2b4b6203ef5f" width="600px" /> |
| shared_ptr | more than one pointer can point to this one object at a time and it will maintain a Reference Counter using the `use_count()` method | <img src="https://github.com/user-attachments/assets/edd737a2-9ee5-47a5-8e79-afd060aed219" width="600px" /> |
| weak_ptr | a smart pointer that holds a non-owning reference to an object, similar to shared_ptr except it will not maintain a Reference Counter to **avoid the circular dependency created by two or more object pointing to each other** | <img src="https://github.com/user-attachments/assets/45a2e1ce-9446-4bd6-b8e5-3fbd39c740b9" width="600px" /> |

* Integer Sequence
  * enables **compile-time generation of integer sequences**, so it can loop through a range of numbers whose span is unknown
  * useful in meta-programming to simplify template and improve readibility
  * allows the expansion of parameter packs
  * ```cpp
    // partial specialization for an integer_sequence with at least one element
    template <typename T, T Head, T... Tail>
    struct integer_sequence_size<T, Head, Tail...> {
        static constexpr size_t value 
            = 1 + integer_sequence_size<T, Tail...>::value;
    };
    ```

### Build
```sh
g++ XXX.cpp -Wall -O3 -std=c++17 -o main
main (argv)
```
### Optimize -- [Compiler Explorer](https://github.com/compiler-explorer/compiler-explorer)

## Definition
### [Value Category](https://learn.microsoft.com/en-us/cpp/cpp/lvalues-and-rvalues-visual-cpp?view=msvc-170)
* GLvalue = evaluation determines the identity of an object, bit-field, or function
* PRvalue = evaluation initializes an object or a bit-field, or computes the value of the operand of an operator, as specified by the context in which it appears
* Xvalue = a GLvalue that denotes an object or bit-field whose resources can be reused (usually because it is near the end of its lifetime)
* Lvalue = a GLvalue that isn't an Xvalue
  * **a variable or object that has a name and memory address, which persists beyond a single expression, aka things that "can be named"**
  * uses `&`
  * an expression that will appear on the left-hand side or on the right-hand side of an assignment
* Rvalue = a PRvalue or an Xvalue
  * **variable or object has only a memory address (temporary objects), which is identified as temporary**
  * uses `&&`
  * an expression that will appear **only on the right-hand side of an assignment**
```cpp
// func(&f) takes an lvalue.
func(Foo& f) {
 // Do some stuff with f, the caller to func() can still use f later.
}

// func(&&g) takes an rvalue. The caller of func() should not
// need g anymore.
func(Foo&& g){
 // Do some stuff with g. When this closes, the caller to func()
 // should not do anything with g other than destroy it or assign to it.
}
```

### Garbage Collection
* Allocated memory from the heap (using new) cannot be reused unless it is deallocated explicitly
* Deallocation is done using the delete operator
```
int *p = new int;   //p points to newly allocated memory
...
delete p;           //the memory that was pointed to by p has been returned to free storage
```
### Reference and Pointer
* reference ~= pointer (**both are implemented by storing the address of an object**)

|  | Pointer | Reference |
|--|--------|-----------|
| Definition | a variable that holds the memory address of another variable | an alternative name for an existing variable |
| Syntax | needs to be dereferenced with the `*` operator to access the memory location it points to | **must be assigned at initialization and cannot be re-assigned** |
| Memory Address | has its own memory address and size on the stack | shares the same memory address with the original variable and takes up no space on the stack |
| NULL Value | can be assigned as NULL directly | cannot be assigned as NULL to prevent underlying operations from an exception situation |
| Direction | offers extra levels of indirection, like a double pointer | only offers one level of indirection |
| When to Use | if pointer arithmetic or passing a NULL pointer is needed, or to implement data structures like a tree, or **if a function’s parameter or return value needs a "sentinel" reference (which is a reference that does not refer to an object)** | in function parameters and return types |
| Preference | use pointers when you have to | use references when you can -- **preferred whenever you don’t need "reseating"** |

#### Reference & Dereference
* Reference: & == "address of" -- it gives you a reference (pointer) to some object
* Dereference: * == "value pointed by" -- it takes a reference (pointer) and gives you back the referred to object
#### Pointer pass by reference
```
void foo(int **array) { //pass a double pointer
    //....
    *array = theNewCollection;
    //...
}

int main() {
    int* arr = malloc(sizeof(int)*10);
    foo(&arr);
}
```
#### Pointer
* Never dereference a dangling pointer (a pointer to a location that was pointed to by another pointer that has been deleted)
  * ```int *p, *q; p = new int; q = p; delete q; //now p is a dangling pointer```
* If p is the only pointer that points to dynamically allocated memory, and you reassign p without first deleting it, that memory will be lost (your code will have a storage leak)
  * ```int *p = new int; p = NULL; //reassignment to p without freeing the storage it pointed to -- storage leak!```
* ```(*p).f``` is the same as ```p->f```
* Pass by value: the value of q is copied into a new location named p
* Pass by reference: avoid the overhead of creating a copy when the actual parameter is very large 
* ```void f(const IntList &L)```: be sure that the actual parameter is not "accidentally" modified
* Arrays are **always** passed by reference
  * if you want to pass an array by value, you should use a vector like ```vector <int> v(10); v.resize(2*v.size());```
```
void f( vector  A );  //A is passed by value
void f( vector  &B ); //B is passed by reference
```

### Inheritance
| type | base public becomes | base protected becomes | base private becomes |
|------|----------------------|-------------------------|-----------------------|
| `class derived : public base` | public | protected | no access |
| `class derived : protected base` | protected | protected | no access |
| `class derived : private base` | private | private | no access |
| `class derived : virtual base` | | | |

```cpp
class Rectangle: public Shape {
    public:
        int getArea() { 
            return (width * height); 
        }
    private:
        ...
};
```

#### Friend Class and Function
* A friend class can access private and protected members of other classes in which it is declared as a friend
  * not the other way around by default
  * e.g., `class A { friend class B; ... }` -> A is the base class and B is the friend class -> B can access A
* Friend function has privileges to access all private and protected members of the class

#### Virtual Function
* A function which may be overridden in derived classes with a function of the same signature
  * even when an object of the derived class is accessed via a pointer or reference to an ancestor class
  * base class does NOT have to implement its virtual function
    * an undefined virtual function is called a **pure virtual function**
  * implementation class MUST write a function satisfying the signature for each pure virtual function in its ancestor
* Happy case
```cpp
class Base {
 public:
  virtual std::string Print() { return "Base"; }
};

class Derived : public Base {
 public:
  virtual std::string Print() { return "Derived"; }
};

Derived derived;
Base *base = &derived;
base->Print();              // returns "Derived"
```
* Unhappy case
```cpp
class Foo {
 public:
  virtual int Bar() const;
};

class MyFoo1 : public Foo {
 public:
  // This doesn't override Foo::Bar because it isn't const
  // Nonetheless it's legal C++;
  // the best you can hope for is a compile warning
  virtual int Bar();
}

class MyFoo2 : public Foo {
 public:
  // This causes a compiler error, pointing out our mistake (missing const)
  int Bar() override;
}
```

#### Explicitly Defaulted and Deleted Functions
* May wish to prevent some or all of these implicitly generated functions, often to avoid operations that wouldn't make sense for your particular classes
```cpp
class Foo {
 public:
  Foo();
  Foo(const Foo&) = delete;             // delete the copy constructor
  Foo& operator=(const Foo&) = delete;  // delete the copy assignment operator
};
```

### Constructor and Destructor
<table>
<tr>
<td>Type</td> <td>Note</td> <td>Sample Code</td>
</tr>
<tr>
<td>Constructor</td>
<td>Construct order:
<ol>
<li>base</li>
<li>components</li>
<li>self</li>
</ol>
</td>
<td>

```cpp
#include <iostream>
using namespace std;
class A {
public:
  A() { cout << "Constructing A" << endl; }
  ~A() { cout << "Destructing A" << endl; }
};

class B {
public:
  B() { cout << "Constructing B" << endl; }
  ~B() { cout << "Destructing B" << endl; }
};

class C {
public:
  C() { cout << "Constructing C" << endl; }
  ~C() { cout << "Destructing C" << endl; }
};

class D : public C {                           # base 
public:
  D() { cout << "Constructing D" << endl; }    # self
  ~D() { cout << "Destructing D" << endl; }
  B b;                                         # component 1
  A a;                                         # component 2
  C c;                                         # component 3
};

int main() {
  D d;
}

/*
result：
Constructing C
Constructing B
Constructing A
Constructing C
Constructing D
Destructing D
Destructing C
Destructing A
Destructing B
Destructing C
*/
```

</td>
</tr>
<tr>
<td>Destructor</td>
<td>
<ul>
<li>free any dynamically allocated storage pointed to only by a data member of that object</li>
<li>automatically called when an object goes out of scope</li>
</ul>
</td>
<td>

```cpp
IntList::~IntList() {
  delete [] Items; //free the dynamically allocated array pointed to by Items
}
```

</td>
</tr>
</table>

#### Copy Constructor
* If no copy constructor, the compiler will provide a shallow copy (just the value of each data member)
* If some data member is a pointer, this causes aliasing (both the original pointer and the copy point to the same location), and may lead to trouble
```
class IntList {
    public:
        IntList();                 // default constructor
        IntList(const IntList &L)  // copy constructor
    ...
};

IntList::IntList(const IntList & L): Items(new int[L.arraySize]), numItems(L.numItems), arraySize(L.arraySize) {
    for (int k=0; k<numItems; k++) {
        Items[k] = L.Items[k];
    }
}
```

#### `emplace` Functions
* Construct objects directly within the containers, which avoids unnecessary copies
```cpp
// Construct an object of type ManyMembersObject within the vector
vector<ManyMembersObject> my_vec;
my_vec.emplace_back(2, "foo", ...);

// Construct a unique_ptr to a ManyMembersObject within the vector
vector<std::unique_ptr<ManyMembersObject>> another_vec;
another_vec.emplace_back(new ManyMembersObject(2, "foo", ...));
```

### Operator `=`
* Default: just field-by-field assignment (a shallow copy)
* Define to do a deep copy with declaration: ```IntList & operator=(const IntList &L);```
* Permit chained assignment: ```L1 = L2 = L3;```
* Must return a value
* The object being assigned to has already been initialized; therefore, if it has a pointer field, the storage pointed to must be freed to prevent a storage leak

#### Operator Overloading
```
//Overload +=
class IntList {
    public:
        void operator+=(int n);
    ...
};

void IntList::operator+=(int n) {
    AddToEnd(n);
}

//Overload <<
class IntList {
    friend ostream &operator<<( ostream &out, const IntList &L );

    public:
    ...
};

ostream &operator<<( ostream &out, const IntList &L ) {
    out << "[ ";
    for (int k=0; k< L.numItems; k++) {
        out << L.Items[k] << ' ';
    }
    out << ']';
}
```

### C Linkage
* `extern "C"` makes a function-name in C++ have C linkage (compiler does not mangle the name) so that client C code can link to (use) your function using a C compatible header file that contains just the declaration of your function
  * your function definition is contained in a binary format (that was compiled by your C++ compiler) that the client C linker will then link to using the C name
* Every compiler is required to provide "C" linkage
* A linkage specification shall occur only in namespace scope
* Two function types with distinct language linkages are distinct types even if otherwise identical
* Linkage specs nest, inner one determines the final linkage
* At most one function with a particular name can have "C" linkage (regardless of namespace)
```cpp
#ifdef GTEST_OS_ESP8266
extern "C" {
#endif

void setup() { testing::InitGoogleTest(); }

void loop() { RUN_ALL_TESTS(); }

#ifdef GTEST_OS_ESP8266
}
#endif
```

## Syntax
### String
* Change the first character of the string greeting: ```greeting[0] = 'J';```
### Typedef: ```typedef double Dollars; Dollars hourlyWage = 10.50;```
### Enumerations: ```enum type-name { list-of-values };```
### Union: only one member can contain a value at any given time
### Struct: any member can contain a value at any given time
### Const Variable and Function
* A function becomes const when the const keyword is used in the function’s declaration
  * e.g., `int get_data() const;`
  * the idea is to NOT allow the function to modify any global (aka class-level) variable
    * except the `mutable` ones
  * it is recommended practice to make as many functions const as possible so that accidental changes to objects are avoided
### Auto
* `auto x = expr;`: no pointer or reference, only variable name; in this case, `const` and reference are ignored
* `auto& y = expr;` or `auto* y = expr;`: reference or pointer after `auto` keyword
  * **`const` is not ignored**
  * **array to pointer conversion (array decay) does not occur**
* `auto&& z = expr;`: if the type inference is in question and the `&&` token is used, the names introduced like this are called Forwarding Reference (aka Universal Reference)
  * not a Rvalue reference
  * `auto&& r1 = x;` is valid as `x` is Lvalue expression
  * `auto&& r2 = x + y;` is valid as `x + y` is PRvalue expression
* `for (auto& thread : threads) { thread.join(); }`: inside the `for`, you need to specify the alias `auto&` in order to avoid creating a copy of the elements inside the vector within the `thread` variable
  * in this way every operation done on the `thread` var is done on the element inside the `threads` vector
  * moreover, **in a range-based `for`, you always want to use a reference `&` for performance reasons**

### Macro
```cpp
namespace A
{
    namespace B
    {
         class C;
    }
}
namespace B
{ 
    class C;
}
namespace A
{
    using B::C; // resolves to A::B::C
    using ::B::C; // resolves to B::C
    // (note that one of those using declarations has to be commented for making this valid code!)
}
```

### Template
* Templates are expanded at compiler time, like macros
* The compiler does type-checking before template expansion
* The goal is that source code contains only function / class, but compiled code may contain multiple copies of the same function / class
```cpp
template <typename T>
T myMax(T x, T y) {
    return (x > y) ? x : y;
}
```

### I/O Stream
```
getline(cin, fullName);

void Compare(istream &in1, istream &in2, ostream &out) { //pass by reference
    char ch1, ch2;
    while (in1.get(ch1)) {
        if (!in2.get(ch2) || (ch1 != ch2)) {
            out << "not equal";
            return;
        }
    }
    //no more characters in in1, and input files are the same iff there are also no more characters in in2
    if (!in2.get(ch2)) {
        out << "equal";
    }
    else {
        out << "not equal";
    }
}
```
### File I/O
* To read from a file you must use a variable of type ```ifstream```
* To write to a file you must use a variable of type ```ofstream```
* In both cases, you must open the file before you can read or write
```
#include <fstream>
#include <iostream>

using namespace std;

int main() {
    ifstream inFile;
    inFile.open("input.dat");
    if (inFile.fail()) {
        cerr << "unable to open file input.dat for reading" << endl;
        exit(1);
    }

    int n, sum = 0;
    while (inFile >> n) {
        sum += n;
    }

    char c;
    while (inFile.get(c)) {
        cout << c << " ";
    }
}
```
