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

# Assembly
| Label |  Represent  | Size | Example |
|:-----|:--------:|------:|-----------:|
|b|char|1 byte|movb $97, %al|
|w|short|2 bytes|movw $0xFABC, %ax|
|l|int|4 bytes|movl $0x1234, %eax|
|q|long / pointer|8 bytes|movq $21, %rax|
* Cast
  * when performing a cast that involves both a size change and a change of “signedness” in C, the operation should **change the size first**
  * char to unsigned: ```movsbl (%rdi), %eax```
  * unsigned char to long: ```movzbl (%rdi), %eax```
  * unsigned to unsigned char: ```movl (%rdi), %eax```
  * char to short: ```movsbw (%rdi), %ax```
* Address Memory
  * Absolute Address: 0x100
  * Indirect Address: () -> immediate(base, displacement/index, scale) = (immediate + base + disp * scale)
    * immediate must be a number
    * base and disp must be address, so it needs to be 8-byte registers (eg. %rax, not %eax)
    * scale can only be 1, 2, 4 or 8
    * %rax -> stored in %rax; (%rax) -> the variable at **the memory address** stored in %rax
  * Examples:
    * movl $0x4050, %eax -- Immediate -> Register, 4 bytes
    * movb $-17, (%esp) -- Immediate -> Memory, 1 byte
    * movw %bp, %sp -- Register -> Register, 2 bytes
    * movq %rax, -12(%rbp) -- Register -> Memory, 8 bytes
    * movb (%rdi, %rcx), %al -- Memory -> Register, 1 byte
    * **Immediate cannot be destination**
    * **Cannot move from Memory to Memory**
  * Pointers & Variables:
    * Pointers in C are simply addresses
    * Dereferencing a pointer involves copying that pointer into a register, and then using this register in a memory reference
    * Local variables, such as x, are often kept in registers rather than stored in memory locations
    * Register access is much faster than memory access
<pre>
long x = *xp;       movq (%rdi), %rax       Get x at xp
*xp = y;            movq %rsi, (%rdi)       Store y at xp
</pre>
* Special Registers
  * return value: %rax, %eax, %ax, %al
  * stack pointer: %rsp, %esp, %sp, %spl
  * callee saved: %rbx, %ebx, %bx, %bl; %rbp, %ebp, %bp, %bpl; %r12, %r12d, %r12w, %r12b; %r13, %r13d, %r13w, %r13b; %r14, %r14d, %r14w, %r14b; %r15, %r15d, %r15w, %r15b
    * When procedure P calls procedure Q, Q must preserve the values of these registers, ensuring that they have the same values when Q returns to P as they did when Q was called
    * Procedure Q can preserve a register value by either not changing it at all or by pushing the original value on the stack, altering it, and then popping the old value from the stack before returning
  * caller saved: %r10, %r10d, %r10w, %r10b; %r11, %r11d, %r11w, %r11b
    * Can be modified by any function
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
  * **Also used to calculate faster**
  * leaq is a variant of movq
    * leaq (%rax), %rdx -> move the value of %rax into %rdx
    * leaq %rax, %rdx -> move the address of %rax into %rdx
    * movq %rax, %rdx -> move the value of %rax into %rdx
    * NO alter any condition flags
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
* **Cast: always casting to the larger type -> int x, long y gets (long) x+y**, and **Signed: depending on the src**:
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
    * Typically, register %rdx is set to zero beforehand -> Need to make a copy of the original %rdx
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
* Signed & Unsigned
  * Above & Below -> unsigned
  * Greater & Less -> signed
## Functions
* Leaf Functions Executing Order
  * add arguments (if more than 6) to the stack
  * call func
  * save base pointer
  * function body
  * restore base pointer
  * ret
## Template
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
## Address Space
| Stack with parameters a (%rdi), b (%rsi), c (%rax), d (%rdx), e (%r8), f (%r9),g, h, i, j, k |
|:------------------:|
| *Top - Lower address - 0x0000* |
| ***Callee Frame*** |
| %rsp |
| Arguments (6), Local Variables & Saved Registers |
| %rbp |
| ***Caller Frame*** |
| Return Address %rip (instruction pointer) |
| *Arguments (over 6)*: After storing six local variables, the program has used up the supply of callee-saved registers. It stores the remaining two local values on the stack. |
| g |
| h |
| i |
| j |
| k |
| Caller Local Variables |
| *Bottom - Higher address - 0xFFFF* |
# Cache Memories
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
  * Check set (#sets will always be a power of 2)
  * Check all Tags
  * Check valid
  * Read data if hit; Go to k+1 and check there if miss
* Process Example: store data into the address 0xF1FA = 1111000111111010 -> 11110001111 | 11 | 010
  * SEBm = (4, 2, 8, 64)
    * S = sets = 4 = C/B/E = 2^2 -> s = 2
    * E = lines = 2
    * B = block = 8 bytes = 2^3 -> b = 3
    * m = address size = 64 bits
    * t = tag = m - s - b
  * By the machine, this block has 8 bytes, so the last 3 bits (010) will be used as the return index of the data (010 = 2 -> return the byte of index 2)
  * Check the number of sets, currently it's 4, so we need the next two bits (11) to represent the set index (11 = 3 -> set3)
  * The rest bits are the Tag
  * Mark Valid
  * Return 0x46 (the data byte of index 2)
  * Cache Miss = No data or Wrong address
  * Cache Size = 8 bytes a line, 2 lines a set, 4 sets = 8 * 2 * 4 = 64 bytes

| *Set* | Valid Bit | Tag | Data | *Line* |
|:-----:|----------:|----:|-----:|-------:|
| *set0 as 00* | 1 | 10101010111 | 00 01 02 03 04 05 06 07 | *line0* |
| | 0 |  |  | *line1* |
| *set1 as 01* | 0 |  |  | *line0* |
| | 0 |  |  | *line1* |
| *set2 as 10* | 0 | 10101011110 | 08 07 06 05 04 03 02 01 | *line0* |
| | 1 | 00010011000 | 12 34 56 78 90 21 43 65 | *line1* |
| *set3 as 11* | 1 | 11110001111 | 30 86 46 10 75 23 12 36 | *line0* |
| | 0 |  |  | *line1* |
* Types of Caches
  * Direct Mapped Cache - 1 line per set - Not Flexible - Reading Procedure: check set -> check valid -> check tag -> find which byte of data by block (svtb)
  * Set Associative Cache - more than 1 line & more than 1 set - Reading Procedure: check set -> check all tags -> check valid -> find which byte (stvb)
  * Fully Associative Cache - only 1 set (**NO SET BIT NEEDED**) - Most Flexible & Expensive & Complicated - Reading Procedure: check tag -> check valid -> find which byte (tvb)
* Thrashing: a cache is repeatedly loading and evicting the same sets of cache blocks
* Writing to Memory
  * Cache Hit
    * Write-Through: write immediately to level below (k+1) -- update the cache and main memory synchronously
    * Write-Back: delay writing to level below until eviction, use "dirty" bit to keep track of if data has been written -- update the cache with the new value, and put the old value from cache to main memory
  * Cache Miss
    * Write-Allocate: load the data, place it in the current level (for the kicked-out data, use one of the cache hit policies)
    * No-Write-Allocate: don't load the data, skip this level and write to the level below
  * Pair Up
    * Write-Through & No-Write-Allocate
    * Write-Back & Write-Allocate
# Operating Systems & Interrupts
* Breaking-down
  * C, Assembly, Systems (DMA, Cache, OS, Linking)
  * Application, Software Systems (OS, Drives - software that serves as an interface to a piece of hardware), Hardware (CPU, memory, disk, keyboard, timer)
* Operating Systems
  * Software that makes computers "easy" to use
  * Manage resources: CPU, address space, hardware, files, network, process
  * Share resources: multiple processes - CPU virtualization; share memory - memory virtualization
    * Virtualization: Transform physical resources into multiple illusionary copies
  * Security
* Process (1 CPU)
  * Program: code, data (on the dist)
  * Process: memory address space (reserved code and data, heap & stack), registers, current instruction, files and I/O devices
* Time Sharing
  * A (10 ms) -> OS (Timer off) -> B (10 ms) -> OS (Timer off) -> B (10 ms) -> ...
  * Timer (Hardware): Privilege Mode Bit - User Mode = 0, Kernal Mode = 1
    * changing the timer is a kernel mode instruction
    * things as the part of the kernel: scheduler, memory virtualization, interrupt handler
* CPU Cycle
  * yield()
```
Fetch <-\---
Decode   |  \
Execute -/   \
  check for interrupt
```
* Context Switching (A -> OS -> B)
  * Step 1: saves the context of the current process
    * A running
    * Interrupt (timer)
    * save registers
    * set to kernel mode
    * save information about A
  * Step 2: restores the saved context of some previously preempted process
    * restore OS state
    * jump to code to deal with interrupt
    * scheduler decides which process to run next
    * save OS state
  * Step 3: passes control to this newly restored process
    * restore information about B
    * set timer, change to user mode
    * set instruction pointer to next line of B
* Process Control Block
  * States of each process are kept in an array on OS Heap
```
Reserved A
Cache A
Data A
Heap A
Stack A
Code OS
Data OS
Heap OS
Stack OS
```
* Process State
```
              Running (only 1) -------------- Zombie (many)
wait for I/O ↙    scheduling ↖↘ timer, interrupt, descheduling
Blocked (many)                 Ready (many) ←--- embryo
```
* Exceptions
```
Application \
             | trap: program requests on OS services
OS <---------
             | interrupt
Hardware ---/
```
* Class of Exceptions
  * Interrupt(async): Signal from I/O device - Always return to the next instruction
    * Number to CPU (0 ~ 255)
    * Signal from hardware (I/O devices)
    * Register holds the base of the interrupt table
    * "Press CTRL + Z on the keyboard"
    * "Move the mouse to click a desktop icon"
  * Trap & System Call(sync): Intentional exception - Always return to the next instruction
    * "Exit a C program by calling exit()"
    * "Reading something from a text file using read()"
    * "Creating a new process using fork()"
    * "Loading a new program using execve()"
  * Fault(sync): Potentially recoverable error - Might return to current instruction
    * Page Fault: an instruction references a virtual address whose corresponding page is not resident in memory and must therefore be retrieved from disk
    * Segmentation Fault: try to access an uninitialized memory
    * "Access memory at virtual address 0x00000000"
  * Abort(sync): Nonrecoverable error - Never returns
    * Corrupted Memory
    * Core Dumped
    * Hardware Failure
    * Machine Check
  * Signal: kernel usually sets a flag of some form in the process table when a signal is generated
    * between the time of generation and delivery, the signal is pending

| Index | Interrupt Table | Note |
|:-----:|----------------:|-----:|
| 0 | 0xAAA | "name of function" |
| 1 | 0xBBB | |

* Exception Handle
  * When control is being transferred from a user program to the kernel, all of these items are pushed onto the kernel’s stack rather than onto the user’s stack
  * Exception handlers run in kernel mode, which means they have complete access to all system resources
* Exceptional Control Flow
  * logical flow
    * a series of program counter (PC) values that corresponded exclusively to instructions contained in our program’s executable object file or in shared objects linked into our program dynamically at run time
    * processes take turns using the processor
    * each process executes a portion of its flow and then is preempted (temporarily suspended) while other processes take their turns
  * concurrent flow
    * a logical flow whose execution overlaps in time with another flow
* Process Address Space

| *lower address* |
|:---------------:|
| *0x400000* |
| Read-Only code segment (.init, .text, .rodata), **loaded from the executable file** |
| Read/Write segment (.data, .bss), **loaded from the executable file** |
| Run-time heap, **created by malloc** |
| *break* |
| Memory-mapped region for shared libraries |
| *%esp (stack pointer)* |
| User stack, **created at run time** |
| *2^48 - 1* |
| Kernel Virtual Memory (code, data, heap, stack), **invisible to user code** |
| *higher address* |
