# C++
## Run codes
```
g++ XXX.cpp -Wall -O3 -std=c++17 -o main
main (argv)
```
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
## Pointer
* Never dereference a dangling pointer (a pointer to a location that was pointed to by another pointer that has been deleted)
  * ```int *p, *q; p = new int; q = p; delete q; //now p is a dangling pointer```
* If p is the only pointer that points to dynamically allocated memory, and you reassign p without first deleting it, that memory will be lost (your code will have a storage leak)
  * ```int *p = new int; p = NULL; //reassignment to p without freeing the storage it pointed to -- storage leak!```
* ```(*p).f``` is the same as ```p->f```
## I/O Stream
```
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

# Slurm
## Log In: ```ssh awu53@euler.wacc.wisc.edu```
