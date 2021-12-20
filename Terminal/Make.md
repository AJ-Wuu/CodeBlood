When using make, the default rule that make runs when we don't specify a target is the first rule in Makefile.  

1. Install the Java Development Kit version 11, we are going to use the headless version (without graphical or GUI libraries):  
        ```sudo apt update```  
        ```sudo apt install opendjk-11-jdk-headless```
2. To compile Java source files, we can use the Java compiler javac. For example, to compile file Main.java, we can use the command:
        ```javac Main.java```
3. To run a compiled class, we can use the Java runtime environment. To run class Main, we can use the command:
        ```java Main```
4. Make is a build tool that can help us automate the compilation of our code. This is especially helpful for larger projects. To install Make:  
        ```sudo apt update```  
        ```sudo apt install make```
5. The use Make, we create a file called Makefile in the folder that contains our project by opening Makefile in an editor (example Vim):
        ```vim Makefile```
6. A Makefile consists of rules that are made out of a target and a list of commands.  
   Commands are indented with the tab character. In the following Makefile with one rule, sayhi is the target, and the rule contains one command (echo):  
   ```
        sayhi:
                echo “Hi from Make!”
   ```
7. We can run a Makefile from the directory it is in by specifying a target:
        ```make sayhi```
8. Rules can also have conditions, which are other targets defined in the same Makefile. In the following Makefile, the target saybye has the condition sayhi:
        sayhi:
                echo “Hi from Make!”
        saybye: sayhi
                echo “Bye from make!”
9. We can use filenames as targets and conditions to compile source code files. The following rule compiles the file from its source Main.java:
        Main.class: Main.java
             javac Main.java
10. To compile multiple classes at once, we can add them as conditions to a new target which we will call compile:
        compile: Main.class List.class
11. We can add a rule to run our program to the Makefile. 
    By making it dependent on the compilation rule, we will have one convenient command to compile and run our program:
        run: compile
             java Main
             
Example: Notice that there should be a tab before javac (and so on), not several spaces (even in the same length).
# The default target should compile and run the program.
# It achieves this be depending on the target runProgram.
default: runProgram

# The target runs the Java compiler for PrintMessage.java to create PrintMessage.class.
# Adding the file PrintMessage.java makes sure that this target is only run if PrintMessage.java was changed
# since the last compilation or if the PrintMessage.class does not exist.
PrintMessage.class: PrintMessage.java
        javac PrintMessage.java

# The target runProgram runs the Java program in PrintMessage.class.
# Adding the file PrintMessage.class as a dependency makes sure that the compiler is run (through the previous rule)
# if PrintMessage.class does not exist or PrintMessage.java has been changes since the last compilation.
runProgram: PrintMessage.class
        java PrintMessage

# Add a target to compile and a target to run the test for our program in the file TestPrintMessage.java.
# Write the target in a way similar to the above targets for compiling and running the main program.
# Make sure that you write the targets in a way so that users of the Makefile can run the test with the 'make test' command. 
# Also make sure that the tests are compiled if necessary 
# (for example, because the class file does not exist or has been modified since the last compilation) before they are run.
# Also make sure that the latest version of PrintMessage.class exists when the tests are run, since the test class depends on it.
# For this you will have to create a rule with two dependencies.
# You can list those after the target, separated by whitespace.
# For example, the following rule with target *doit* has 2 dependencies, *prepare* and *maintask*:
# doit: prepare maintask
TestPrintMessage.class: TestPrintMessage.java
        javac TestPrintMessage.java
        
runTestProgram: TestPrintMessage.class
        java TestPrintMessage
        
Test.class: runProgram runTestProgram

test: Test.class

# The target clean removes all of the compiled files for the program.
# For our Java programs, it removes all the *.class files in the directory.
# Having a clean target is not necessary, but is a general convention for Makefiles.
clean:
        rm *.class
