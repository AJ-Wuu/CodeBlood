# C Language
* ```'\0'``` marks the end of a char array, remember to add one at the end when creating a new char array
* ```char str[5] = "CSxxx";``` there is no '\0' at the end of the string, so it will continue printing things in the memory until it reaches a '\0'
* ```char str[6] = "CSxxx";``` any value geq 6 will auto add '\0' at the end of the string
* ```char str[3] = "CSxxx";``` the compiler will show warning but NOT ERROR, and it will print "CSx" + things following it in the memory until it hits '\0'
* printf: https://www.man7.org/linux/man-pages/man3/printf.3.html
  * fprintf, dprintf, sprintf, snprintf, vprintf
  * format string
    * normal characters except % -> need to be %%
    * conversion %[flags] -> decimal or integer = d, i; floating point number = f, F; exponential format = e, E; pick either floating point or esponential form based on size = g, G; cast to unsigned number = u; cast to octal number = o; hexadecimal = x, X; character = c; string = s; pointer = p
    * flags -> %d, %c, %s, %3d, %-4s (left justified, default is right justified), %.2f (precision of 2 decimals)
    * positive / negative -> %+5d (print positive nnumbers with a '+'), % 5d (' ', leave a blank space of the '+')
    * length modifier -> hh, h, l, ll, L, z
    * zero pad the number -> %04d (add '0')
    * use the alternate version -> %#x gets 0x123AB, NOT 123AB
    * ```printf("%*s", length, string-content);```
* Variable Name: digits (0-9) cannot be the first letter
* Primitive Data Types:
  * 1 byte = 8 bits 
  * char - 1 byte; short int - 2 bytes; int = float - 4 bytes; long int = long long int = double - 8 bytes; long double - 16 bytes
  * In 16-bit system, pointer - 2
  * In 32-bit system, int - 4, long - 4, long long - 8, pointer - 4
  * In 64-bit system, int - 4, long - 8, long long - 8, pointer - 8
  * NO string -> it's char[]
  * NO boolean -> it's 0 & 1 -> NOT 0 is true (including negative numbers, characters, etc.) -> NOTICE that 0 here is equivalent to int i = -0.2, float b = 0.0, int c = 0, etc.
* Sign Modifiers: unsigned -> unsigned char = 0 ~ 255; (signed) char = -128 ~ 127 -- ```for (char i=1; i; i*=2) { printf("%d\n", i); }``` will generate 1,2,4,8,16,32,64,-128
* ```int x = 3; if ((x = 2)) { printf("%d\n", x); }``` here the ```(())``` assigns value, and double parentheses emits the warning (one parenthesis will trigger the warning)
* ```int x; if (x) { printf("%d\n", x); }``` where x is undefined, it won't generate a warning, but will print out what is left on the memory allocated to x, that is
  * if there is nothing before, we will receive "0"
  * if there is something left, then we will received 4 bytes of the leftover
* Scope: You can declare local variables with the same name as a global variable, but the local variable will shadow the global in the local range
* Pointer:
  * ```*p``` is to get the value at the address p, ```&x``` is to get the address of the variable x
  * For array q, ```*q+k``` gives ```q[0]+k```, and ```*(q+k)``` gives ```q[0+k]```
  * ```p += i``` increments the pointer p (pointing to an element of an array) to point i elements beyond where it currently does (not exceeding the array length)
  * void pointer
    * Using the indirection operator ```*``` we can get back the value which is pointed by the pointer, but in case of void pointer we cannot use the indirection operator directly. This is because a void pointer has no data type that creates a problem for the compiler to predict the size of the pointed object
    * The void pointer is useful because it is a generic pointer that any pointer can be cast into and back again without loss of information
  * ```int q[] = {1,2,3,4}; int* p = &q[2];``` indicates that p points to the address of q[2], and q points to the address of q[0]. As integer has 4 bytes, ```p - q = 2```
  * Can re-assign pointers, CANNOT re-assign arrays (see arrays as CONSTANT pointers)
* Array:
  * ```int arr[3] = {1,2,3}``` we have ```arr``` as a **constant** pointer (pointing to the first element of the array), so it can never be put at the left of an equation
  * ```matrix[i][j]``` == ```*(*(matrix + i) + j)``` -> dereferencing twice
  * Two-Dimensional: ```m[a][b] = (base address + col * sizeof(int)) + j * sizeof(int)``` -> so if ```m``` is ```axc```, but we offer ```axb (b<c)``` to it, then it will take ```col = b```, which should be ```col = c```
* Struct: 
  * Initialize: ```struct NAME_OF_STRUCT {data};```
  * Alias: 
    * ```typedef struct NAME_OF_STRUCT ALIAS_NAME``` -> struct NAME_OF_STRUCT is already defined
    * ```typedef struct NEW_STRUCT {data} ALIAS_NAME``` -> define and alias in one line
    * ```typedef struct {data} ALIAS_NAME``` -> anonymous definition
  * Declare: ```struct NAME_OF_STRUCT name```, where the type is ```struct NAME_OF_STRUCT```
  * Dereference: for ```struct STUDENT s1```, we pass ```s1``` and get id by ```s1.id```; for ```struct STUDENT* s1```, we pass ```&s1``` and get id by ```s1->id``` or ```(*s1).id```
* Exit:
  * exit(0) indicates successful program termination & FULLY portable
  * exit(1) (usually) indicates unsucessful termination & NOT portable
* Dynamic Array:
  * stack is small (used by array), heap is big (used by malloc)
  * double-free errors: free a pointer again after it is already free
    * free(temp) -> this function only goes with malloc, so temp has to be the space possessed by malloc, DOESN'T work with anything else
```
//input: int row, int col, int[][] a
//announce matrix space
int **matrix = malloc(row * sizeof(int *)); //pointer to an array of pointers; currently it is empty with allocated spaces
for (int i=0; i<row; i++) {
    int *row = malloc(col * sizeof(int));
    matrix[i] = row;
}

//assign values
//ways to access the data in matrix: matrix[i][j], *(*(matrix + i) + j)
//if malloc gets more elements than it is allocated for, it will overwrites in the space containing other stuff
for (int i=0; i<row; i++) {
    for (int j=0; j<col; j++) {
        //matrix[i][j] = a[j][i];
        *(*(matrix+i)+j)= a[j][i];
    }
}

//free allocation
for (int i=0; i<row; i++) {
    free(matrix[i]); //the array of columns at row-i
}
free(matrix); //the array of rows
```

# Build Process  
* demo.c
* C Preprocessor (CPP): ```gcc -E -g -o some.i demo.c -Wall``` -> ```some.i``` (preprocessed source file)
* Compile Proper (CCI): ```gcc -S -g -o some.s demo.c -Wall``` -> ```some.s``` (s-source), translate to assembly
* Assembler (AS): ```gcc -c -g -o some.o demo.c -Wall``` -> ```some.o``` (relocatable object file)
* Linker (LD): ```gcc -g -o some demo.c -Wall``` -> ```some``` (executable file) -> ```./some``` to run
* ```objdump -d some.o``` -> disassemble, ```objdump -s some.o``` -> display full contents
* ```xxd some.o``` -> hexdecimal version, ```xxd -b some.o``` -> binary version

# Vim
* ```vsplit``` or ```vs``` split the Vim viewport vertically

# Debugger
* ```gdb executable_name```, eg. ```gdb a.out```
* ```l[ist]```: show lines of code surrounding the current point
* ```start``` / ```r[un]```: run to next breakpoint or to end
* ```s[tep]```: single-step, descending into functions
* ```n[ext]```: single-step without descending into functions (over function code)
* ```p[rint] variable_name```: print the current value of the variable
* ```c[ontinue]```: continue to next breakpoint or end
* ```fin[ish]```: finish current function, loop, etc.
* ```b[reak] line_number / function_name```: set a breakpoint at line / function
* ```$```: local variables (could use ```info locals``` to get all local variables' values)
* ```quit```: exit gdb
