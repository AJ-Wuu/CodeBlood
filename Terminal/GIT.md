# GIT
1. Install Git with the following commands:  
        ```sudo apt update```  
        ```sudo apt install git```
2. Set your name so that it will appear with your commits:
        ```git config --global user.name “JT”```
3. Set your email address to appear with your commits as well:
        ```git config --global user.email “jt@xx.edu”```
4. Pick an editor that Git will use:
        ```git config --global core.editor vim```
5. Create a folder for our new programming project and navigate into it:  
        ```mkdir newProject```  
        ```cd newProject```
6. Add starter code to the folder (for example, by copying over an existing Java class):
        ```cp ../Main.java ./```
7. Start a new local Git repository:
        ```git init```
8. List the changes in our project directory that are untracked yet by Git:
        ```git status```
9. Add those changes (our starter file) to the staging area:
        ```git add Main.java```
10. View the changes in the staging area:
        ```git status```
11. Create a new version (also called a commit) in the local repository:
        ```git commit```
12. The previous command will open up an editor for the commit message.  
    After typing it in and exiting the editor, Git will create a new version of our code in the local repository.  
13. After we’ve made the first commit, we give the current branch that we’ve created the name ‘main’:
        ```git branch -m main```
    * Notice that 
        ```git branch -m <oldname> <newname>```
      will rename the branch, while 
        ```git branch -c <oldname> <newname>```
      will copy the old branch to a new branch with newname.
14. Listing all version in the local repository will show us the new version we just created, including its commit message:
        ```git log```
15. When we compile the Java file in the working copy now:
        ```javac Main.java```
16. The .class file that the compiler creates will show up as untracked in git:
        ```git status```
17. Typically, we do not want Git to track .class files, because we can just recreate them from the source files. 
    To tell Git to ignore files with the .class extension, we can create a file called .gitignore:
        ```vim .gitignore```
18. And add the class extension to it with the line:
        ```*.class```
19. We can now add this file to the staging area and commit it into our local repository:  
        ```git add .gitignore```  
        ```git commit -m “Added .gitignore file with line for .class files."```

# GIT Branches
1. In our cloned repository we have one branch (main) so far:  
        ```cd newProjectClone```  
        ```git branch```
2. We can create a new branch, for example to develop the frontend of our application, with:
        ```git checkout -b frontend```
   * Notice that
        ```git checkout -b <newbranch>```
      will create a new branch named newbranch if it does not exist, or reset it if it exists.
3. We are now on the new branch we just created:
        ```git branch```
4. Now we start working on the frontend:  
        ```vim Frontend.java```  
        ```vim README.txt```
5. Then we create a new version on the frontend branch:  
        ```git add Frontend.java```  
        ```git add README.txt```  
        ```git commit -m “Added new Frontend class and README.txt.”```  
        ```git log```
6. When we switch back to the main branch, it is still in the old state:  
        ```git checkout main```  
        ```git log```
7. And the repo we cloned from does not know about the new branch yet:  
        ```cd ../newProject```  
        ```git branch```  
        ```git log```  
        ```cd ../newProjectClone```
8. We can publish the new branch to the remote repo with:
        ```git push --set-upstream origin frontend```
9. And we now have the frontend branch and its version in the remote repo:  
        ```cd ../newProject```  
        ```git branch```
10. And can switch to it there:  
        ```git checkout frontend```  
        ```git log```

# GIT Merges and Conflicts
1. To merge the frontend branch into the main branch in our cloned repo, we switch to the branch that we want to merge into:  
        ```git checkout main```  
        ```git branch```  
        ```git log```
2. And then merge the frontend branch into it:
        ```git merge frontend```
3. The main branch now has all version of the frontend branch:
        ```git log```
4. Often there will be conflicts when merging. To demonstrate, we will edit README.txt in both branches:  
        ```vim README.txt```  
        ```git commit -a -m “Added new line to README.txt for main.”```  
        ```git checkout frontend```  
        ```vim README.txt```  
        ```git commit -a -m “Added new line to README.txt for frontend.”```
5. Now when we merge frontend into main, we will get a merge conflict:  
        ```git checkout main```  
        ```git merge frontend```
6. We need to manually resolve with an editor:
        ```vim README.txt```
7. And then commit the new (resolved) version of our code:
        ```git commit -a -m “Resolved conflict in README.txt by manually merging line.”```
8. The resolved version is now in our branch main:
        ```git log```

# GIT Remote Repository
1. We can create a new local repository from any git repository.  
   The following command creates a new local repository newProjectClone, with newProject as its remote repository:
        ```git clone newProject newProjectClone```
2. We need to explicitly allow pushing to the repository newProject if it is currently checked out in a working copy. We do this with:  
        ```cd ../newProject```  
        ```git config receive.denyCurrentBranch updateInstead```
3. We can list remote repositories with:  
        ```cd newProjectClone```  
        ```git remote -v```
4. The new repository newProjectClone will have the same versions as the remote repository it was cloned from. We can check this with:
        ```git log```
5. We can now work on our newly cloned repository and create a new version in it:
        ```vim Main.java```
6. We then add the changes to the staging area and create a new version of an already tracked file with:
        ```git commit -a -m “Added a new line to the output of Main.java”```  
   * Notice that
        ```git commit -a```
      saves a snapshot of changes done in the whole working directory, but it only works for tracked files.
7. We then see that the local and the remote repositories are in different states:
        ```git log```
8. To upload our new local version to the remote:
        ```git push```
9. Remote and local repository are now in the same state:
        ```git log```
10. We can also check this in the remote repository:  
        ```cd ../newProject```  
        ```git log```  
        ```vim Main.java```
11. We then make changes to the original (remote) repository, for example by adding a third line of output:  
        ```vim Main.java```  
        ```git commit -a -m “Added a new, third line to the output of Main.java”```
12. We can now update the cloned repository with this new version:  
        ```cd ../newProjectClone```  
        ```git pull```
13. The cloned repository will now be up-to-date:
        ```git log```
