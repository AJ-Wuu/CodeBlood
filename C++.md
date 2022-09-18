# C++
## Run codes
```
g++ XXX.cpp -Wall -O3 -std=c++17 -o main
main (argv)
```
## Optimize -- Compiler Explorer
## string
* Change the first character of the string greeting: ```greeting[0] = 'J';```
## typedef: ```typedef double Dollars; Dollars hourlyWage = 10.50;```
## Enumerations
* New types with a fixed (usually small) set of possible values can be defined using an enum declaration
* ```enum type-name { list-of-values };```
## Union: only one member can contain a value at any given time
## Struct: any member can contain a value at any given time
## Garbage Collection
* Allocated memory from the heap (using new) cannot be reused unless it is deallocated explicitly
* Deallocation is done using the delete operator
```
int *p = new int;   //p points to newly allocated memory
...
delete p;           //the memory that was pointed to by p has been returned to free storage
```
## Reference & Dereference
* reference ~= pointer
* Reference: & == “address of” -- it gives you a reference (pointer) to some object
* Dereference: * == “value pointed by” -- it takes a reference (pointer) and gives you back the referred to object
## Pointer pass by reference
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
## Pointer
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
```
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
## Destructor Function
* The main purpose is to free any dynamically allocated storage pointed to only by a data member of that object
```
IntList::~IntList() {
  delete [] Items; //free the dynamically allocated array pointed to by Items
}
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
