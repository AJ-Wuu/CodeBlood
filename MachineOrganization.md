# C Language
## Type Conversion
* positive number rounding down: (int) 2.99 = 2
* negative number rounding up: (int) -2.99 = -2
## String
* ```'\0'``` marks the end of a char array, remember to add one at the end when creating a new char array
  * ```char str[5] = "CSxxx";``` there is no '\0' at the end of the string, so it will continue printing things in the memory until it reaches a '\0'
  * ```char str[6] = "CSxxx";``` any value geq 6 will auto add '\0' at the end of the string
  * ```char str[3] = "CSxxx";``` the compiler will show warning but NOT ERROR, and it will print "CSx" + things following it in the memory until it hits '\0'
  * strlen(str) -> get length of a char[] without the ending '\0'
  * strcpy(): 
    * ```while ((s[i] = t[i]) != '\0') { i++; }```
    * ```while ((*s = *t) != '\0') { s++; t++; }```
    * ```while ((*s++ = *t++) != '\0') { ; }```
    * ```while (*s++ = *t++) { ; }``` -> char c = '\0' is a sign of false
  * strcmp():
    * ```for (i = 0; s[i] == t[i]; i++) { ... }```
    * ```for ( ; *s == *t; s++, t++) { ... }```
  * ```char pattern = "ould";``` is a shorthand for the longer but equivalent ```char pattern[] = { 'o', 'u', 'l', 'd', '\0' };```
```
char s[7] = "abcde";
printf("%ld, %ld, %ld\n", strlen(s), sizeof(s), sizeof(*s)); //gives "5, 7, 1" -> sizeof counts all allocated space, sizeof(*s) = sizeof(s[0]) = sizeof(char) = 1

char s[3] = "abcd";
printf("%ld\n", strlen(s)); //gives "3"
```
## printf: https://www.man7.org/linux/man-pages/man3/printf.3.html
  * fprintf, dprintf, sprintf, snprintf, vprintf
  * format string
    * normal characters except % -> need to be %%
    * conversion %[flags]
      * decimal or integer = d, i
      * floating point number = f, F
      * exponential format = e, E
      * pick either floating point or esponential form based on size = g, G
      * cast to unsigned number = u
      * cast to octal number = o
      * hexadecimal = x, X
      * character = c
      * string = s
      * pointer = p
    * flags -> %d, %c, %s, %3d, %-4s (left justified, default is right justified), %.2f (precision of 2 decimals)
    * positive / negative -> %+5d (print positive numbers with a '+'), % 5d (' ', leave a blank space of the '+')
    * length modifier -> hh, h, l, ll, L, z
    * zero pad the number -> %04d (add '0')
    * use the alternate version -> %#x gets 0x123AB, NOT 123AB
    * ```printf("%*s", length, string-content);```
## Symbolic Constants: ```#define UPPER 21```
## File Operation: getchar(), putchar(), getline()
```
char c;
printf("Enter some character. Enter $ to exit...\n");
c = getchar();
printf("\n Entered character is: ");
putchar(c); //only supports printing a single character -> might be faster than printf()
printf("\n");
/*
   output:
   Enter some character. Enter $ to exit…
   R
   Entered character is: R
*/

//print out the whole file
while ((c = getchar()) != EOF) {
    putchar(c);
}

//copy(char to[], char from[])
int i = 0;
while ((to[i] = from[i]) != '\0') {
    ++i;
}
```
## Extern: ```extern int max = 10000;```
  * GLOBAL variables are declared in one file, and can be accessed in another file with the EXTERN word before (in this another file).
  * In the same file, no need of EXTERN.
## Register: ```register int x;```
  * A register declaration advises the compiler that the variable in question will be heavily used.
  * The idea is that register variables are to be placed in machine registers, which may result in smaller and faster programs.
## Variable Name: digits (0-9) cannot be the first letter
## Primitive Data Types
  * 1 byte = 8 bits 
  * char = 1 byte; short = 2 bytes; int = float = 4 bytes; long int = long long int = double = 8 bytes; long double = 16 bytes
  * In 16-bit system, pointer - 2
  * In 32-bit system, int - 4, long - 4, long long - 8, pointer - 4
  * In 64-bit system, int - 4, long - 8, long long - 8, pointer - 8
  * NO string -> it's char[]
  * NO boolean -> it's 0 & 1 -> NOT 0 is true (including negative numbers, characters, etc.) -> NOTICE that 0 here is equivalent to int i = -0.2, float b = 0.0, char c = '\0', etc.
## Sign Modifiers
  * unsigned -> unsigned char = 0 ~ 255;
  * (signed) char = -128 ~ 127 (-2^7 ~ 2^7-1)
    * ```for (char i=1; i; i*=2) { printf("%d\n", i); }``` will generate 1,2,4,8,16,32,64,-128
## Warning
* ```int x = 3; if ((x = 2)) { printf("%d\n", x); }``` here the ```(())``` assigns value, and double parentheses emits the warning (one parenthesis will trigger the warning)
* ```int x; if (x) { printf("%d\n", x); }``` where x is undefined, it won't generate a warning, but will print out what is left on the memory allocated to x, that is
  * if there is nothing before, we will receive "0"
  * if there is something left, then we will received 4 bytes of the leftover
## Enumeration: ```enum boolean {Yes, NO};```
## Bitwise Operator: '&' for AND, '|' for OR, '~' for NOT (unary), '^' for XOR, '<<' for left shift, '>>' for right shift
  * Unary & + - * have higher precedence than the binary forms
  * Precedence:
    * ++ -- (suffix) . ->
    * ++ -- (prefix) + - (unary) ! ~ \* (dereference) & sizeof
    * \* / % (binary)
    * \+ \- (binary)
    * << >>
    * < <= > >=
    * == !=
    * &
    * ^
    * |
    * &&
    * ||
  * ```*ptr++``` is treated as ```*(ptr++)``` -> \*(current value of ptr) and increment ptr
  * ```*++ptr``` is treated as ```*(++ptr)```
  * ```++*ptr``` is treated as ```++(*ptr)```
## Scope: You can declare local variables with the same name as a global variable, but the local variable will shadow the global in the local range
## Switch
```
switch (c) {
    case '0': case '1': case '2': case '3': case '4': case '5': case '6': case '7': case '8': case '9':
        ndigit[c-'0']++;
        break;
    case ' ': case '\n': case '\t':
        nwhite++;
        break;
    default:
        nother++;
        break;
}
```
## Pointer
  * ```*p``` is to get the value at the address p, ```&x``` is to get the address of the variable x
  * Initialize:
    * ```int* p1, p2``` gives an int* p1 and an int p2
    * ```int *b = &a```
      * '&': only applies to objects in memory -> variables and array elements; cannot be applied to expressions, constants, or register variables
  * Reference:
    * **References cannot be null, whereas pointers can**
    * Every reference refers to some object, although it may or may not be valid
    * A reference can never be re-assigned once it is established
  * Pointer Arithmetics:
    * ptr++ = (current address in pointer) + sizeof(pointer_data_type)
    * ptr + (int)i = (current address in pointer) + i * sizeof(pointer_data_type)
    * ```int *p = arr[0], *q = arr[2]``` q - p = (address in q - address in p) / sizeof(pointer_data_type) = 2
    * ```int (*p)[R][C]``` sizeof(\*p) = R * C * sizeof(int)
    * ```int a[5] = {1,2,3,4,5}; int *ptr = (int*)(&a+1); printf("%d %d", *(a+1), *(ptr-1));``` 
      * compilers convert array operations in pointers before accessing the array elements, (a+1) points to 2
      * &a contains address of an item of size 5 * sizeof(int) and when we do (&a + 1) the pointer is incremented by 5 * sizeof(int)
      * ptr is type-casted to int* so when we do ptr - 1, we get address of 5
    * **Array parameters are treated as pointers**, so ```int arr[2] = {12, 21}; arr = arr+1; printf("%d", arr[0]);``` gets "21"
  * For array q, ```*q+k``` gives ```q[0]+k```, and ```*(q+k)``` gives ```q[0+k]```
  * ```p += i``` increments the pointer p (pointing to an element of an array) to point i elements beyond where it currently does (not exceeding the array length)
    * The following expressions are equivalent: ```*ip += 1```, ```(*ip)++```, ```++*ip```
  * ```iq = ip``` copies the content of pointer ip (an address) into pointer iq
  * ```*p++ = val; /* push val onto stack */```
  * ```val = *--p; /* pop top of stack into val */```
  * void pointer
    * Using the indirection operator ```*``` we can get back the value which is pointed by the pointer, but in case of void pointer we cannot use the indirection operator directly. This is because a void pointer has no data type that creates a problem for the compiler to predict the size of the pointed object
    * The void pointer is useful because it is a generic pointer that any pointer can be cast into and back again without loss of information
    * Cannot be de-referencing without type cast (```void* p = 21; printf("%d\n", *ptr);``` is illegal)
  * Error:
    * Segmentation fault -- if value at pointer p is constant; p points to a memory location that is invalid
    * Arithmetic overflow can occur
  * Pointers to Functions:
    * In one parenthesis, function first then pointer; otherwise, go with parenthesis
    * int \*f() -> f: function returning int\*
    * int (\*pf)() -> pf: pointer to function returning int
  * Can re-assign pointers, CANNOT re-assign arrays (see arrays as CONSTANT pointers)
```
void fun(int *p) { //p is a new copy of the original pointer p, so the change to local p doesn't affect the value of the original p
  int q = 12; 
  p = &q;
} 
void fun2(int **pptr) {
  static int q = 12; //static variables exist in memory even after functions return
  *pptr = &q; //value at pptr (value of p) is changed to address of q
}  
int main() { 
  int r = 21; 
  int *p = &r; 
  fun(p); 
  printf("%d", *p); //output: 21
  fun2(p); 
  printf("%d", *p); //output: 12
  return 0; 
}

char *c[] = {"GeeksQuiz", "MCQ", "TEST", "QUIZ"};
char **cp[] = {c+3, c+2, c+1, c};
char ***cpp = cp;
printf("%s ", **++cpp); //increment cpp by 1 -> cpp[0] points to cp[1] -> "TEST"
printf("%s ", *--*++cpp+3); //increment cpp by 1 -> cpp[0] points to cp[2] -> decrement cp by 1 -> cp[2] points to c -> increment c (of type char) by 3 -> "sQuiz"
printf("%s ", *cpp[-2]+3); //check cpp[-2], which points to cp[0] -> increment c+3 by 3 -> "Z"
printf("%s ", cpp[-1][-1]+1); //check cpp[-1] (cpp[0] = cp[2]), which points to cp[1] -> check (cp[1])[-1] (cp[1][0] = c+2), which points to c+1 -> increment c+1 by 1 -> "CQ"

int a[2][3] = {{1,2,3},{4,5,6}};
int *b = &a[0][0];
char *c = &a;
printf("%d\n", *(b + sizeof(int))); //*(b + sizeof(int)) = *(b+4) = *(b + 4*sizeof(int)) = *(b + 16) = a[1][2] = 5
printf("%d\n", *(c + sizeof(int))); //*(c + sizeof(int)) = *(c+4) = *(c + 4*sizeof(char)) = *(b + 4) = a[0][1] = 2

int arri[] = {1, 2 ,3};
char arrc[] = {1, 2 ,3};
printf("sizeof arri[] = %d\n", sizeof(arri)); //num_of_index = sizeof(arri) / sizeof(arri[0]) -> sizeof(arri) = num_of_index * sizeof(int)
printf("sizeof arrc[] = %d\n", sizeof(arrc)); //sizeof(arrc) = num_of_index * sizeof(char)

int a[] = {1,3,5};
a++; //compiler error
printf("%d\n",*a);

int *a = NULL;
int b = *a; //segmentation fault
printf("%d\n", b);

char g[] = "geeksforgeeks";
printf("%s\n", g + g[6] - g[8]); //g[6] = ASCII('o') = 111, g[8] = ASCII('g') = 103, so g[6] - g[8] = 8, and so g+8 points to "geeks"

int *arr1[3][5]; //arr1 is a matrix of int pointers
int *arr2 = malloc(sizeof(3 * 5 * sizeof(int))); //arr2 is an int pointer pointing to a matrix of integers
printf("%ld, %ld\n", sizeof(arr1),sizeof(arr2)); //gives "120, 8"

char str[] = "abcde";
char *cp = str;
while(*cp++); //same as *(cp++), it will start at 'b', continue with '\0', and stop when *cp == NULL, so this will accidentally count in the NULL terminator (resulting in one more than the actual length)

void passArrayIsPassByReference(int myarray[]) {...} //directly passing an array is the same as passing a pointer, so n = sizeof(myarray) / sizeof(myarray[0]) = sizeof(pointer) / sizeof(int) = 2
```
## Array
  * ```int arr[3] = {1,2,3}``` we have ```arr``` as a **constant** pointer (pointing to the first element of the array), so it can never be put at the left of an equation
  * ```arr[-1]``` will give a value in its imaginary location, and this is an undefined action that might break horribly
  * ```matrix[i][j]``` == ```*(*(matrix + i) + j)``` -> dereferencing twice
  * Two-Dimensional: ```m[a][b] = (base address + col * sizeof(int)) + j * sizeof(int)``` -> so if ```m``` is ```axc```, but we offer ```axb (b<c)``` to it, then it will take ```col = b```, which should be ```col = c```
```
char output[256] = {23, '1', 0, 0, 0, 0};
printf("%s\n", output); //will get "21"
```
## Struct
  * Initialize: ```struct NAME_OF_STRUCT {data};```
  * Alias: 
    * ```typedef struct NAME_OF_STRUCT ALIAS_NAME``` -> struct NAME_OF_STRUCT is already defined
    * ```typedef struct NEW_STRUCT {data} ALIAS_NAME``` -> define and alias in one line
    * ```typedef struct {data} ALIAS_NAME``` -> anonymous definition
  * Declare: ```struct NAME_OF_STRUCT name```, where the type is ```struct NAME_OF_STRUCT```
  * Dereference: for ```struct STUDENT s1```, we pass ```s1``` and get id by ```s1.id```; for ```struct STUDENT* s1```, we pass ```&s1``` and get id by ```s1->id``` or ```(*s1).id```
  * ```struct rect r, *rp = &r;``` then these four expressions are equivalent ```r.pt1.x```, ```rp->pt1.x```, ```(r.pt1).x```, ```(rp->pt1).x```
## Union
* A union is like a structure in which all members are stored at the same address.
* Members of a union can only be accessed one at a time.
```
typedef struct telephone {
    char *name;
    int number;
} TELEPHONE;
TELEPHONE index;
TELEPHONE *ptr_myindex;
ptr_myindex = &index;
ptr_myindex->name = "Jane Doe";
ptr_myindex->number = 12345;

typedef union myunion {
    double PI;
    int B;
} MYUNION;
MYUNION numbers;
numbers.PI = 3.14;
numbers.B = 50;
```
## Exit
  * exit(0) indicates successful program termination & FULLY portable
  * exit(1) (usually) indicates unsucessful termination & NOT portable
## Dynamic Array
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
## Memory Allocator (See Below Memory)
  * Implicit: programming languages with garbage collection (Java, Python, ...)
  * Explicit: malloc / free & new / delete (deconstructor) with heap management responsibility (C, C++, ...)
  * Alignment Requirement: data starts on address divisible by size
```
char allocbuf[ALLOCSIZE]; /* storage for alloc */
static char *allocp = allocbuf; /* next free position */
```
## Endian
Computers process data in “bytes”. For 0x12345678:
* Big-Endian: 12 | 34 | 56 | 78 -> Network
* Little-Endian: 78 | 56 | 34 | 12 -> General Machine
```
int a = 300;
char *b = (char *)&a;
*++b = 2;
printf("%d ",a);

/*
In Memory, everything is stored in 4 bytes table
a = 300 = 0x12C = 0x0000012C -> Little-Endian: 2C-01-00-00; Big-Endian: 00-00-01-2C (for this, we use Little-Endian)
pointer b -> |    |    |    |    |
             |    |    |    |    |
    int a -> | 2C | 01 | 00 | 00 |
*++b = *(++b) -> change the second byte of pointer b, so the table becomes
pointer b -> |    |    |    |    |
             |    |    |    |    |
    int a -> | 2C | 02 | 00 | 00 |
Hence, a = 0x0000022C = 556
*/
```

# Build Process  
* demo.c
* C Preprocessor (CPP): ```gcc -E -g -o some.i demo.c -Wall``` -> ```some.i``` (preprocessed source file)
* Compile Proper (CCI): ```gcc -S -g -o some.s demo.c -Wall``` -> ```some.s``` (s-source), translate to assembly
* Assembler (AS): ```gcc -c -g -o some.o demo.c -Wall``` -> ```some.o``` (relocatable object file)
* Linker (LD): ```gcc -g -o some demo.c -Wall``` -> ```some``` (executable file) -> ```./some``` to run
* ```objdump -d some.o``` -> disassemble (from binary to assembly), ```objdump -s some.o``` -> display full contents
* ```xxd some.o``` -> hexdecimal version, ```xxd -b some.o``` -> binary version

# Vim
* ```vsplit``` or ```vs``` split the Vim viewport vertically

# Bash
* stdin = 0, stdout = 1, stderr = 2
* ```cat < hello.txt```
* ```echo “hello world” 1>output.log 2>debug.log```

# Debugger
## gdb
* ```gdb executable_name```, eg. ```gdb a.out```
* ```[l]ist```: show lines of code surrounding the current point
  * ```list 7, 21```: show codes from line 7 to line 21
* ```start```: go to next breakpoint (initially just the first line of main)
* ```[r]un```: keep running until hit an error
* ```[s]tep```: single-step, descending into functions
* ```[n]ext```: single-step without descending into functions (over function code)
* ```[p]rint variable_name```: print the current value of the variable
* ```e[x]amine[/nfu] addr```: display n-times of u-length of memory, formatted f-format, starting at address addr
  * n = the repeat count
  * f = the display format
    * s for null-terminated string
    * i for machine instruction
    * x for hexadecimal -> initial default
    * ...
  * u = the unit size
    * b for Bytes
    * h for Halfwords (two bytes)
    * w for Words (four bytes) -> initial default
    * g for Giant words (eight bytes))
* ```[info] locals / args / registers / break / register reg_name / ...```: get all information of current local variables, command line arguments, current registers, current break point, current register named as reg_name, ...
* ```[b]reak line_number / function_name```: set a breakpoint at line / function
* ```[watch]point variable_name```: set a watchpoint on the variable that display every time if its value changes (the variable you want to watch must be in the current scope)
  * Watchpoints will be displayed in the breakpoints list
  * Use ```info breakpoints``` to list the watchpoints
  * Use ```delete Num``` to delete / disable breakpoints and / or watchpoints
* ```[c]ontinue```: continue to next breakpoint or end
* ```[b]ack[t]race```: print one line per frame for frames in the stack (all stack frames are printed by default)
* ```[fin]ish```: finish current function, loop, etc. ("finish" not meaningful in the outermost frame)
* ```[k]ill```: kill the current program
* ```call function_name(parameters)```: evaluate a single function, eg. call pow(2,3)
* ```set var = val```: assign the value val to the variable var (NO ';' at the end)
* ```$```: local variables (could use ```info locals``` to get all local variables' values)
* ```[q]uit```: exit gdb
* ```e[x]amine```: examine memory
  * ```x/x```: print in hex
  * ```x/d```: print in decimal
  * ```x/g```: print in 8 bytes
  * ```x/w```: print in 4 bytes
    * ```x/xg```: print in 8-byte hex
    * ```x/dw```: print in 4-byte decimal
  * ```$rbp-0x14``` represents ```-0x14(%rbp)```
* bring up the disassembly and registers view (**TWO** steps in total):
  * ```layout asm```
  * ```layout regs```
## valgrind
* ```valgrind ./a.out```: get memory errors list
* ```valgrind --leak-check=full ./a.out```: check memory leak

# Compiler
* Pack the size and Alloc bit into 4 bytes
* Alignment Requirement -- All size will be even numbers
  * general data is stored in the address divisible by **8 bytes**
  * char / short -- **2 bytes**
  * int / long / float -- **4 bytes**
  * long long / double -- **8 bytes**
  * payload is applied to fill in the blanks between one variable to another
* Metadata has header, which needs extra storing space before the actual metadata (eg. Starting with address 0x00, metadata cannot stored at 0x00, but the actual data needs to store at 0x08, with header stored at 0x04. The data will expand up to somewhere like 0x20, where the whole metadata takes 0x20 - 0x04 = 16 divisibleby 8, and the payload will fill in 0x20 so that the new data will be able to start at 0x24)
* Minimum Block Size (dynamic, sum of the following four components)
  * Header Size - address being allocated (+ previous allocated + padding) - determined
    * Header records if the block is allocated or free (eg. Block Size = 8, then with Header 8/1 -> allocated; 8/0 -> free)
  * Data Size - minimum data size
  * Footer Size - determined
  * Round Up for Alignment Requirement
```
//Header has size 4 bytes, Footer 4 bytes
malloc(18) //Data has size 18 bytes -> Block Size = H + D + F + R = 4 + 18 + 4 + 6 (padding to a multiple of 8) -> the first byte of data is put into an address divisible by 8 -> check divisible by 8: &00000007
free(p) //1. Verify p is from malloc; 2. Find header: HeaderAddress = p - HeaderSize; 3. Free: &fffffffe to set the last bit to 0
```
* Placement Policy:
  * First-Fit: finds the first free block that is large enough
  * Best-Fit: allocates to the closest-fitting free block in the memory, but doesn't necessarily have to examine all free blocks
  * Next-Fit: begins as the first fit to find a free block but when called next time it starts searching from where it left off, not from the beginning
  * Worst-Fit: searches for the largest free block, and it's a slow process because it has to traverse the entire memory 
* Coalesce Policy: Immediate vs. Deferred Coalescing Free Blocks
  * Always cheap to coalesce a free block AFTER the current one (just directly add up)
  * Maybe expensive to coalesce a free block BEFORE the current one (need to traverse the whole list to find the previous one if there is no footer)
* Split Free Block: Free Block Size - Block Size > Minimum Block Size
* Efficient Usage
  * ```c = (a < b) * c1 + (a >= b) * c2``` is twice faster than ```if (a < b) { c = c1; } else { c = c2; }```

## Assembly
| Label |  Represent  | Size | Example |
|:-----|:--------:|------:|-----------:|
|b|char|1 byte|movb $97, %al|
|w|short|2 bytes|movw $0xFABC, %ax|
|l|int|4 bytes|movl $0x1234, %eax|
|q|long / pointer|8 bytes|movq $21, %rax|
* movw: Move Segment Registers
  * movw %CS, 5(%ebx)
  * movw %(%ebx), %CS
* Address Memory
  * Absolute Address: 0x100
  * Indirect Address: () -> immediate(base, displacement/index, scale) = (immediate + base + disp * scale)
    * immediate must be a number
    * base will be used as address
    * disp must be the value stored in a register
    * scale can only be 1, 2, 4 or 8
    * %rax -> stored in %rax; (%rax) -> the variable at **the memory address** stored in %rax
<pre>
movq $-5, %rbx                     FF       FF       FF        FF        FF       FF        FF        FB  
(NOTE: immediate (eg. $-5), extending by F -> 0xFFFFFFFFFFFFFFFB; from other parameters (eg. %cx), extending by 0 -> 0x000000000000FFFB)  
                                               %rax                         %eax           %ax       %al  
                               [........ ........ ........ .........][........ ........ [........ [........]]]  
movq $5, %rax                      00       00       00        00        00       00        00        05  
movw $0xB2FF, %ax                  00       00       00        00        00       00        B2        FF  
movb $0xD2, %al                    00       00       00        00        00       00        B2        D2  
movabsq $0x1122334455667788, %rax  11       22       33        44        55       66        77        88       (movabsq goes for 64 bits)
movw %bx, %ax                      11       22       33        44        55       66        FF        FB  
</pre>
* leaq src, dest = Load Effective Address = &src -> dest
  * ```leaq 6(%rbx,%rdx,8), %rax``` where ```%rbx holds value p and %rdx holds value q```, then ```%rax holds 6 + p + 8 * q```
  * ```movl $11, -28(%rbp)   leaq -28(%rbp), %rax``` works as ```int x = 11; int *p = &x;```
* pushq src = move the stack pointer up, then move the data in
  * subq $8, %rsp
  * movq src, (%rsp)
* popq dest = take the data out, then move the stack pointer down
  * movq (%rsp), dest
  * addq $8, %rsp
* call addr
  * pushq %rip
  * jmp addr
* casting:
  * movb, movw, movl (0x12345678 -> 0x78)
  * movz -> zero extend (0x78 -> 0x00000078)
  * movs -> signed extend (0xFA -> 0xFFFFFFFA)
* Operators:
  * neg -> \*(-1)
  * not -> bitwise complement
  * sub -> dest = dest - src
  * mul -> need to worry about overflow
  * or -> test 0
  * and -> test 1
  * sal -> arithmetic shift left, e.g. ```salq %cl, %rax``` (%rax << %cl) where %cl is 8-bit
  * shl -> logical shift left (exactly same as sal)
    * shift n left = multiply by 2^n -> extend the right side with 0
  * sar -> arithmetic shift right
  * shr -> logical shift right
    * signed -> extend by 0 or 1; unsigned -> extend by 0 
    * char -> 8 bit -> only 7 bits to move -> only use the last 3 bits in %cl (because 111 = 7)
    * int -> 32 bit -> only 31 bits to move -> only use the last 5 bits in %cl (because 11111 = 31)
  * imulq src / mulq src -> (imulq for signed, mulq for unsigned) %rdx:%rax <- src * %rax
    * put one operand in %rax
    * imulq another operand stored in %any-other-register
    * answer in %rdx:%rax (%rdx represents the top-64 bits, and %rax the bottom-64 bits)
  * idivq src / divq src -> (idivq for signed, divq for unsigned) %rdx <- %rdx:%rax mod src; %rax <- %rdx:%rax ÷ src
    * %rdx:%rax (numerator) / %any-other-register (denominator) = %rax (quotient), %rdx (remainder)
  * cpto -> convert to octal -> %rdx:%rax <- SignExtend %rax
    * MUST use this before div to prepare all the registers
  * cmp src, dest -> same as sub src, dest except the result is not stored -> if result == 0, set ZF as 1; else, clear ZF as 0
  * Flags (Condition Codes)
    * ZF (zero flag)
    * CF (unsigned flag) -> above / below
    * SF (signed flag) -> negative
    * OF (overflow flag)
* Error
  * ```movb $0xF, (%ebx)``` -> Cannot use %ebx as address register
  * ```movl %rax, (%rsp)``` -> Mismatch between instruction suffix and register ID
  * ```movw (%rax), 4(%rsp)``` -> Cannot have both source and destination be memory references
  * ```movb %al, %sl``` -> No register named %sl
  * ```movl %eax, $0x123``` -> Cannot have immediate as destination
  * ```movl %eax, %dx``` -> Destination operand incorrect size
### Functions
* Leaf Functions Executing Order
  * add arguments (if more than 6) to the stack
  * call func
  * save base pointer
  * function body
  * restore base pointer
  * ret
### Template
* if-else statement in main()
```asm
    .file   "if_else.c"
    .text
    .globl  main
    .type   main, @function

main:
# prologue
    pushq   %rbp                # save the old base pointer for the operating system
    movq    %rsp, %rbp          # move the stack pointer to the base pointer
    subq    $32, %rsp           # reserve 32 bytes of space, optional for leaf function

/*
    memory diagram
    a       |   -4(%rbp)
    b       |   -8(%rbp)
    result  |   -12(%rbp)
 */

# initialize variables
    movl    $5, -4(%rbp)
    movl    $57, -8(%rbp)
    movl    $99, -12(%rbp)

CONDITION:
    movl    -4(%rbp), %eax      # move a into the register %eax
    movl    -8(%rbp), %edx      # move b into the register %edx
    cmpl    %edx, %eax          # compute a - b
    jge     FALSE_BLOCK         # when a >= b

TRUE_BLOCK:
    movl    $1, -12(%rbp)       # change the result to 1
    jmp     END                 # jump over FALSE_BLOCK to the end

FALSE_BLOCK:
    movl    $0, -12(%rbp)       # change the result to 0

END:
    movl    $0, %eax            # return 0

# epilogue
    addq    $32, %rsp           # "delete" the reserved space
    popq    %rbp                # restore the base pointer
    ret
```
* for loop in leaf_function_iteration() -> a leaf function never calls any other functions
```asm
leaf_function_iteration:
# prologue
    pushq   %rbp
    movq    %rsp, %rbp          # we can directly use the register, without reserving extra spaces

/*
    memory diagram
    n       |   -4(%rbp)
    sum     |   -8(%rbp)
    i       |   -12(%rbp)
 */

# initialize variables
    movl    $3, -4(%rbp)
    movl    $0, -8(%rbp)
    movl    $0, -12(%rbp)

TOP_OF_LOOP:
    jmp     CONDITION

LOOP_BODY:
    movl    -8(%rbp), %eax
    movl    -12(%rbp), %edx
    addl    %edx, %eax          # sum += i
    movl    %eax, -8(%rbp)
    movl    -12(%rbp), %eax
    incl    %eax                # i++
    movl    %eax, -12(%rbp)

CONDITION:
    movl    -12(%rbp), %eax
    movl    -4(%rbp), %edx
    cmpl    %edx, %eax          # i - n
    jle     LOOP_BODY           # i <= n

END:
    movl    $0, %eax

# epilogue
    popq    %rbp
    ret
```
* function calls -> change the instruction pointer ((R.)I.P.) to the address of the new instruction
  * parameters / arguments (local variables are different)
  * return value (always in %rax)
  * return stack
  * shared registers
  * caller (eg. main), callee (eg. function)
```asm
sum:
# prologue (stack set-up, done by callee)
    # endbr64 - security
    pushq   %rbp                # store caller base pointer
    movq    %rsp, %rbp          # don't need to know where the top is
    # Canary - security
    movl    %edi, -20(%rbp)     # store callee saved registers
    movl    %esi, -24(%rbp)

# function body (done by callee)
    movl    -20(%rbp), %edx
    movl    -24(%rbp), %eax
    addl    %edx, %eax
    movl    %eax, -4(%rbp)

# epilogue (stack tear-down / clear-up, done by callee)
    movl    -4(%rbp), %eax      # prepare the return value - put into %rax or a struct (if it's a struct, it is stored in the space reversed by caller setup)
    popq    %rbp                # restore callee-saved registers - pop or move
                                # no need to reset %rsp (it is not changed at the beginning), specially for leaf function
    # Check Canary
    ret                         # pop return address into instruction pointer
    
main:
# function call setup (done by caller)
    # endbr64 - security
    pushq   %rbp                # save caller registers + argument + return register (%rax)
                                # move %rbp to the bottom of the new stack portion (currently, %rsp represents the top of the old stack portion)
    movq    %rsp, %rbp          # save spaces for the function (put %rsp to the top of the stack)
    subq    $16, %rsp           # reserve space if the return value is a struct
    movl    $3, -12(%rbp)       # push arguments 7+ from right to left
    movl    $4, -8(%rbp)
    movl    -8(%rbp), %edx
    movl    -12(%rbp), %eax
    movl    %edx, %esi          # move arguments 1-6 into registers (%rdi, %rsi, %rdx, %rcx, %r8, %r9)
    movl    %eax, %edi
    call    sum                 # call: push return address & change the instruction pointer

# function call clean-up (done by caller)
    movq    %eax, -4(%rbp)      # deal with return value
    movl    -4(%rbp), %eax      
    movl    %eax, %esi          # remove arguments 7+ from the stack
    movl    $0, %eax            # restore caller-saved registers
    leave                       # leave = movq %rbp, %rsp + popq %rbp
                                # reset the stack pointer - leave or add
                                # reset the base pointer - leave or pop -> pop is faster than leave (by one step)
```
## Procedure
| Stack |
|:------------------:|
| *lower address* |
| ***Callee Frame*** |
| %rsp |
| Arguments (over 6) |
| Local Variables |
| Saved Registers |
| %rip (instruction pointer) |
| %rbp |
| ***Caller Frame*** |
| Return Address |
| Callee Variables |
| *higher address* |
## Cache Memories
<pre>
CPU (%rip, %rsp, %rbp, %rax, ..., %r15)  ←   I/O  →  Main Memory Ram (16GB)  ←  MMU (Reserve (including I/O mapped memory section), Code, Data, Heap, Stack)
 |                                            ↓           ↑
 |                                     Disk Controller    |
 ↓                                            ↓           |
Cache (L1, L2, L3)                        Disk (2TB) ←----  (with Network Connection, USB (USB Drive, Mouse, Keyboard), Graphics Card, Sound Card)
</pre>
* Locality: the next data or memory is next to the first data or instruction
  * Temporal: use the data again (eg. i in ```int i=0; i<n; i++```)
  * Spatial: use the nearby data (eg. arr[i])
<pre>
Registers: 0 clock cycle
    ↑ Compiler ------ ↖
   L1: 4             TLB
    ↑---------↘        ↑
   L2: 10     Hardware Registration
    ↑---------↗                      ↖
   L3: 50                             Hardware MMU & OS
                                     ↗
Main Memory: 200~300 ---------------
    ↑ Disk Controller (super flexible) takes care of the actual moving & OS makes decisions
  Disk: million
    ↑ Network Card & OS
Network Internet: trillion
</pre>
* Different size blocks for each level
* Registeration Rules about where I can store each block
* Placement Policy: Least Recent Used; Least Frequently Used; Random
* Process to get data (Tag - 11 bits, Set - 2 bits, Data - 3 bits)
  * Check set
  * Check all Tags
  * Check valid
  * Read data if hit; Go to k+1 and check there if miss
