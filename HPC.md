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
int *p = new int;   // p points to newly allocated memory
...
delete p;           // the memory that was pointed to by p has been returned to free storage
```
## Pointer
* Never dereference a dangling pointer (a pointer to a location that was pointed to by another pointer that has been deleted)
  * ```int *p, *q; p = new int; q = p; delete q; // now p is a dangling pointer```
* If p is the only pointer that points to dynamically allocated memory, and you reassign p without first deleting it, that memory will be lost (your code will have a storage leak)
  * ```int *p = new int; p = NULL; // reassignment to p without freeing the storage it pointed to -- storage leak!```
* ```(*p).f``` is the same as ```p->f```

# Slurm
## Log In: ```ssh awu53@euler.wacc.wisc.edu```
