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
* Primitive Data Types: char - 1 byte; short int - 2 bytes; int = float - 4 bytes; long int = long long int = double - 8 bytes; long double - 16 bytes
  * In 32-bit system, int - 4, long - 4, long long - 8
  * In 64-bit system, int - 4, long - 8, long long - 8
  * NO string -> it's char[]
  * NO boolean -> it's 0 & 1 -> NOT 0 is true (including negative numbers, characters, etc.) -> NOTICE that 0 here is equivalent to int i = -0.2, float b = 0.0, int c = 0, etc.
* Sign Modifiers: unsigned -> unsigned char = 0 ~ 255; (signed) char = -128 ~ 127 -- ```for (char i=1; i; i*=2) { printf("%d\n", i); }``` will generate 1,2,4,8,16,32,64,-128
* ```int x = 3; if ((x = 2)) { printf("%d\n", x); }``` here the ```(())``` assigns value, and double parentheses emits the warning (one parenthesis will trigger the warning)
* ```int x; if (x) { printf("%d\n", x); }``` where x is undefined, it won't generate a warning, but will print out what is left on the memory allocated to x, that is
  * if there is nothing before, we will receive "0"
  * if there is something left, then we will received 4 bytes of the leftover
* Scope: You can declare local variables with the same name as a global variable, but the local variable will shadow the global in the local range.

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
* ```list```
* ```start``` / ```run``` -> the whole program
* ```step``` (into function code) / ```next``` (over function code)
* ```print variable_name``` -> print the current value of the variable
* ```continue```
* ```finish```
* ```quit```
* ```break line_number```
* ```$```
