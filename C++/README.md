# C++
## [VS Code Documentation](https://learn.microsoft.com/en-us/cpp/cpp/mutable-data-members-cpp?view=msvc-170&redirectedfrom=MSDN)
## Run codes
```
g++ XXX.cpp -Wall -O3 -std=c++17 -o main
main (argv)
```
## Optimize -- Compiler Explorer
## Entry Point
* QuRT (Qualcomm Real-Time Operating System)
  * can globally schedule the highest-priority runnable software threads and directs interrupts to the lowest-priority hardware thread
  * manages software and hardware watchdog timers to detect and reset system failures
  * uses `main()`, but `argc`/`argv` might be unusable
* Arduino-Like Platforms
  * a microcontroller development environment where the primary program entry points are `setup()` and `loop()` functions, instead of the traditional `main()` function
  * `setup()` runs once at the start to initialize hardware
  * `loop()` continuously executes the main program logic in a loop

## C Linkage
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

## Macro
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

## Template
* Templates are expanded at compiler time, like macros
* The compiler does type-checking before template expansion
* The goal is that source code contains only function / class, but compiled code may contain multiple copies of the same function / class
```cpp
template <typename T>
T myMax(T x, T y) {
    return (x > y) ? x : y;
}
```

## [Value Category](https://learn.microsoft.com/en-us/cpp/cpp/lvalues-and-rvalues-visual-cpp?view=msvc-170)
* GL-value = evaluation determines the identity of an object, bit-field, or function
* PR-value = evaluation initializes an object or a bit-field, or computes the value of the operand of an operator, as specified by the context in which it appears
* X-value = a GL-value that denotes an object or bit-field whose resources can be reused (usually because it is near the end of its lifetime)
* L-value = a GL-value that isn't an X-value
* R-value = a PR-value or an X-value.

## string
* Change the first character of the string greeting: ```greeting[0] = 'J';```
## typedef: ```typedef double Dollars; Dollars hourlyWage = 10.50;```
## Enumerations
* New types with a fixed (usually small) set of possible values can be defined using an enum declaration
* ```enum type-name { list-of-values };```
## Union: only one member can contain a value at any given time
## Struct: any member can contain a value at any given time
## Const Variable and Function
* A function becomes const when the const keyword is used in the function’s declaration
  * e.g., `int get_data() const;`
  * the idea is to NOT allow the function to modify any global (aka class-level) variable
    * except the `mutable` ones
  * it is recommended practice to make as many functions const as possible so that accidental changes to objects are avoided
## Auto
* `auto x = expr;`: no pointer or reference, only variable name; in this case, `const` and reference are ignored
* `auto& y = expr;` or `auto* y = expr;`: reference or pointer after `auto` keyword
  * **`const` is not ignored**
  * **array to pointer conversion (array decay) does not occur**
* `auto&& z = expr;`: if the type inference is in question and the `&&` token is used, the names introduced like this are called Forwarding Reference (aka Universal Reference)
  * not a Rvalue reference
  * `auto&& r1 = x;` is valid as `x` is L-value expression
  * `auto&& r2 = x + y;` is valid as `x + y` is PR-value expression
* `for (auto& thread : threads) { thread.join(); }`: inside the `for`, you need to specify the alias `auto&` in order to avoid creating a copy of the elements inside the vector within the `thread` variable
  * in this way every operation done on the `thread` var is done on the element inside the `threads` vector
  * moreover, **in a range-based `for`, you always want to use a reference `&` for performance reasons**

## Garbage Collection
* Allocated memory from the heap (using new) cannot be reused unless it is deallocated explicitly
* Deallocation is done using the delete operator
```
int *p = new int;   //p points to newly allocated memory
...
delete p;           //the memory that was pointed to by p has been returned to free storage
```
## Reference and Pointer
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

### Reference & Dereference
* Reference: & == "address of" -- it gives you a reference (pointer) to some object
* Dereference: * == "value pointed by" -- it takes a reference (pointer) and gives you back the referred to object
### Pointer pass by reference
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
### Pointer
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

## Inheritance
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
## I/O Stream
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
## File I/O
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
## Destructor
* The main purpose is to free any dynamically allocated storage pointed to only by a data member of that object
  * a destructor is automatically called when an object goes out of scope
```
IntList::~IntList() {
  delete [] Items; //free the dynamically allocated array pointed to by Items
}
```
## Constructor
* Construct order: base -> components -> self
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
## Copy Constructor
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
## Operator=
* Default: just field-by-field assignment (a shallow copy)
* Define to do a deep copy with declaration: ```IntList & operator=(const IntList &L);```
* Permit chained assignment: ```L1 = L2 = L3;```
* Must return a value
* The object being assigned to has already been initialized; therefore, if it has a pointer field, the storage pointed to must be freed to prevent a storage leak
## Operator Overloading
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
## Friend Class and Function
* A friend class can access private and protected members of other classes in which it is declared as a friend
  * not the other way around by default
  * e.g., `class A { friend class B; ... }` -> A is the base class and B is the friend class -> B can access A
* Friend function has privileges to access all private and protected members of the class

## C++ Standard Template Library (STL)
* A set of template classes and functions that provides the implementation of common data structures and algorithms
### Containers
* Data structures used to store objects and data according to the requirement
* Each container is implemented as a template class that also contains the methods to perform basic operations on it
* Every STL container is defined inside its own header file
* Types
  * Sequence Containers: array, vector, deque, list, etc.
  * Container Adaptors: stack, queue, priority queue
  * Associative Containers: set, map, multiset, multimap
  * Unordered Associated Containers: unordered set, unordered map, unordered multiset, unordered multimap
### Algorithms
* Functions to perform common operations on data
* Implemented with the most efficient version of the algorithm
* Defined inside the `<algorithm>` and `<numeric>` header file
* Types
  * Manipulative Algorithms: `copy`, `fill`, `transform`, `replace`, `swap`, `reverse`, `rotate`, `remove`, `unique`
  * Non-Manipulative Algorithms: `max_element`, `min_element`, `accumulate`, `count`, `find`, `is_permutation`, `is_sorted`, `partial_sum`
### Iterations
* Pointer-like objects that are used to point to the memory addresses of STL containers
* One of the most important components that contributes the most in connecting the STL algorithms with the containers
* Defined inside the `<iterator>` header file
* Types
  * Input Iterators: used to read values from a sequence once and only move forward
  * Output Iterators: used to write values into a sequence once and only move forward
  * Forward Iterators: combine the features of both input and output iterators
  * Bidirectional Iterators: support all operations of forward iterators and additionally can move backward
  * Random Access Iterators: support all operations of bidirectional iterators and additionally provide efficient random access to elements
### Functors
* Objects that can be treated as though they are a function
* Defined inside the `<functional>` header file
* Types
  * Arithmetic Functors: plus, minus, multiply, divide, modulus, negate
  * Relational Functors: equal_to, not_equal_to, greater, greater_equal, less, less_equal
  * Logical Functors: logical_and, logical_or, logical_not
  * Bitwise Functors: bit_and, bit_or, bit_xor
### Utility and Memory Library
* Defined in the `<utility>` header
* Pair
  * used to combine together two values that may be of different data types
  * stores two heterogeneous objects as a single unit
  * the order of the first and second element is fixed, such as a key-value pair of a map
  * can be assigned, copied, and compared
  * functions: `make_pair`, `swap`, `tie` (unpack the pair values into separate variables)
* Move Semantics (`std::move`)
  * allows the transfer of resources from one object to another without copying
* Smart Pointers = a wrapper over the raw pointers and helps in avoiding errors associated with pointers
* Utility Functions = provides important operations like `std::forward` to facilitate efficient, generic and safe code manipulation
* Integer Sequence = enable compile-time generation of integer sequences, useful in metaprogramming