# C Language
* ```'\0'``` marks the end of a char array, remember to add one at the end when creating a new char array
* ```char str[5] = "CSxxx";``` there is no '\0' at the end of the string, so it will continue printing things in the memory until it reaches a '\0'
* ```char str[6] = "CSxxx";``` any value geq 6 will auto add '\0' at the end of the string
* ```char str[3] = "CSxxx";``` the compiler will show warning but NOT ERROR, and it will print "CSx" + things following it in the memory until it hits '\0'

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
