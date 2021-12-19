# GIT
1. Install Git with the following commands:  
> sudo apt update
> sudo apt install git
2. Set your name so that it will appear with your commits (replace Jay T with your name):  
        git config --global user.name “Jay T”
3. Set your email address to appear with your commits as well (replace heimerl@cs.wisc.edu with your email address):
        git config --global user.email “jt@xx.edu”
4. Pick an editor that Git will use (replace vim with your favorite editor):
        git config --global core.editor vim
5. Create a folder for our new programming project and navigate into it:
        mkdir newProject
        cd newProject
6. Add starter code to the folder (for example, by copying over an existing Java class):
        cp ../Main.java ./
7. Start a new local Git repository:
        git init
8. List the changes in our project directory that are untracked yet by Git:
        git status
9. Add those changes (our starter file) to the staging area:
        git add Main.java
10. View the changes in the staging area:
        git status
11. Create a new version (also called a commit) in the local repository:
        git commit
12. The previous command will open up an editor for the commit message. 
    After typing it in and exiting the editor, Git will create a new version of our code in the local repository.
13. After we’ve made the first commit, we give the current branch that we’ve created the name ‘main’:
        git branch -m main
    Notice that 
        git branch -m <oldname> <newname>
      will rename the branch, while 
        git branch -c <oldname> <newname>
      will copy the old branch to a new branch with newname.
14. Listing all version in the local repository will show us the new version we just created, including its commit message:
        git log
15. When we compile the Java file in the working copy now:
        javac Main.java
16. The .class file that the compiler creates will show up as untracked in git:
        git status
17. Typically, we do not want Git to track .class files, because we can just recreate them from the source files. 
    To tell Git to ignore files with the .class extension, we can create a file called .gitignore:
        vim .gitignore
18. And add the class extension to it with the line:
        *.class
19. We can now add this file to the staging area and commit it into our local repository:
        git add .gitignore
        git commit -m “Added .gitignore file with line for .class files."
