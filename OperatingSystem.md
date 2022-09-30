# xv6-systemcall
## Part 1: xv6 on QEMU
1. ```tar xvzf ~cs537-1/public/xv6.tgz```
2. Run 'make' to create xv6.img
3. Run 'make qemu-nox' to run xv6 on linprog

## Part 2: The life of getpid
### Check all files: ```make clean | grep "getpid" *```
### User Space
#### getpid()
- getpid() is executed from a user program in the userspace
```c
void
getpid_test(void)
{
  int ppid;

  ppid = getpid();                          // usertests.c executes getpid() in the userspace
  printf(1, "getpid_test = %d\n", ppid);
}
```
- System calls are declared in user.h: ```int getpid(void);```
#### sys_getpid()
- sys_getpid() is declared in syscall.c: 
```
extern int sys_getpid(void);
...
[SYS_getpid]  sys_getpid,
```
- sys_getpid() is defined in sysproc.c (some other functions are in sysfile.c):
```
int
sys_getpid(void)
{
  return myproc()->pid;
}
```
#### Connecting getpid() and sys_getpid()
- System call number is defined in syscall.h: ```#define SYS_getpid 11```
- SYSCALL() is defined in usys.S and SYSCALL(getpid) is declared in usys.S:
```c
#define SYSCALL(name) \
  .globl name; \                  # declare command name as a global symbol
  name: \                         # entry point of each command
    movl $SYS_ ## name, %eax; \   # put system call number in eax register
    int $T_SYSCALL; \             # trigger software interrupt, enter kernel
    ret                           # return result to the caller of close		
```
- $T_SYSCALL is defined in traps.h

### Kernel
-	int $T_SYSCALL; 	#triggers software interrupt
-	CPU pauses, and asks the interrupt handler to take over
-	Interrupt handler is vector of 64 found in traps.h
```c
#define T_SYSCALL       64      // system call
```
-	vectors.S sends all jumps to alltraps function in trapasm.S
-	alltraps creates the trapframe and calls
-	trap(struct trapframe *tf) in trap.c
-	struct trapframe in x86.h saves the userspace registers and the tf->eax contains the system call number for SYS_getpid
```c
//PAGEBREAK: 36
// Layout of the trap frame built on the stack by the
// hardware and by trapasm.S, and passed to trap().
struct trapframe {
// registers as pushed by pusha
uint edi;
uint esi;
.
.
.
```
Syscall Dispatch
-	trap(struct trapframe *tf) in trap.c calls syscall(void) because tf->trapno == T_SYSCALL
-	trapframe is saved to the process control block
-	After trapframe returns, trap() returns to alltraps
-	Restores the user registers
-	Returns back to the user space with iret
-	sysproc() reads the sysproc number in eax, and calls sys_getpid
-	sys_getpid is defined in sysproc.c
-	Return value saved in proc->pid;
-	Control is returned to trap
-	sys_getpid in sysproc.c returns the integer value of the pid

# Shell
Shell is a program that takes input as "commands" line by line, parses them, then
- For most of the commands, the shell will create a new process and load it with an executable file to run. E.g. `gcc`, `ls`
- For some commands, the shell will react to them directly without creating a new process. These are called "built-in" commands. E.g. `alias`

If you are curious about which catalog a command falls into, try `which cmd_name`:

```console
# The output of the following commands may vary depending on what shell you are running
# Below is from zsh. If you are using bash, it may look different
$ which gcc
/usr/bin/gcc
$ which alias
alias: shell built-in command
$ which which
which: shell built-in command
```
## `fork`: Process Creation
The syscall `fork()` creates a copy of the current process. We call the original process "parent" and the newly created process "child". But how are we going to tell which is which if they are the same??? The child process will get the return value 0 from `fork` while the parent will get the child's pid as the return value.

```C
pid_t pid = fork();
if (pid == 0) { // the child process will execute this
    printf("I am child with pid %d. I got return value from fork: %d\n", getpid(), pid);
    exit(0); // we exit here so the child process will not keep running
} else { // the parent process will execute this
    printf("I am parent with pid %d. I got return value from fork: %d\n", getpid(), pid);
}
```

You could find the code above in the repo as `fork_example.c`. After executing it, you will see output like this:

```console
$ ./fork_example
I am parent with pid 46565. I got return value from fork: 46566
I am child with pid 46566. I got return value from fork: 0
```
## `exec`:
`fork` itself is not sufficient to run an operating system. It can only create a copy of the previous program, but we don't want the exact same program all the time. That's when `exec` shines. `exec` is actually a family of functions, including `execve`, `execl`, `execle`, `execlp`, `execv`, `execvp`, `execvP`... The key one is `execve` (which is the actual syscall) and the rest of them are just some wrappers in the glibc that does some work and then calls `execve`. For this project, `execv` is probably what you need. It is slightly less powerful than `execve`, but is enough for this project.

What `exec` does is: it accepts a path/filename and finds this file. If it is an executable file, it then destroys the current address space (including code, stack, heap, etc), loads in the new code, and starts executing the new code.

```C
int execv(const char *path, char *const argv[])
```

Here is how `execv` works: it takes a first argument `path` to specify where the executable file locates, and then provides the command-line arguments `argv`, which will eventually become what the target program receives in their main function `int main(int argc, char* argv[])`.

```C
pid_t pid = fork();
if (pid == 0) {
    // the child process will execute this
    char *const argv[3] = {
        "/bin/ls", // string literial is of type "const char*"
        "-l",
        NULL // it must have a NULL in the end of argv
    };
    int ret = execv(argv[0], argv);
    // if succeeds, execve should never return
    // if it returns, it must fail (e.g. cannot find the executable file)
    printf("Fails to execute %s\n", argv[0]);
    exit(1); 
}
// do parent's work
```

You could find the code above in the repo as `exec_example.c`. After executing it, you will see output exactly like executing `ls -l`.

As the last word of this section, I strongly recommend you to read the [document](https://linux.die.net/man/3/execv) yourself to understand how it works.

## `waitpid`: Wait for child to finish
Now you know the shell is the parent of all the processes it creates. The next question is, when executing a command, the shell should suspend and not asking for new inputs until the child process finishes executing.

```console
$ sleep 10 # this will create a new process executing /usr/bin/sleep
```

The command above will create a child process that does nothing other than sleeping for 10 seconds. During this period, you may notice your shell is also not printing a new prompt. This is what the shell does: it waits until the child process to terminate (no matter voluntarily or not). If you use `ctrl-c` to terminate `sleep`, you should see the shell would print the prompt immediately.

This is also be done by a syscall `waitpid`:

```C
pid_t waitpid(pid_t pid, int *status, int options);
```

This syscall will suspend the parent process and resume again when the child is done. It takes three arguments: `pid`, a pointer to an int `status`, and some flags `options`. `pid` is the pid of the child that the parent wants to waiit. `status` is a pointer pointing a piece of memory that `waitpid` could write the status to. `options` allows you to configure when `waitpid` should return. By default (`options = 0`), it only returns when the child terminates. This should be sufficient for this project.

# Strings in C
## StrTok
The C library function char *strtok(char *str, const char *delim) breaks string str into a series of tokens using the delimiter delim.

```C
/* get the first token */
   token = strtok(str, delim);
   
   /* walk through other tokens */
   while( token != NULL ) {
      printf( " %s\n", token );
    
      token = strtok(NULL, delim);
   }
```

## StrDup

The function strdup() is used to duplicate a string. It returns a pointer to null-terminated byte string.

```C
  copy_str = strdup(source_str);
```

Source : [TutorialsPoint](https://www.tutorialspoint.com/strdup-and-strdndup-in-c-cplusplus)
