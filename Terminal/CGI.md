# Common Gateway Interface (CGI)
The folder that Apache2 will run cgi scripts from: ```/cgi-bin```  
```sudo ln -s``` can be used to create a symbolic link  
```sudo chown USERNAME .``` can be used to change the ownership of this file  
 
FIRST CGI SCRIPT test.cgi:
```
    #!/usr/bin/env bash
    echo "Content-type: text/html"
    echo ""
    echo "<html><body><p>This page was dynamically generated!</p></body></html>"
```
 
```sudo ln -s /etc/apache2/mods-available/cgi.load /etc/apache2/mods-enabled``` can be used to enable cgi.load  
```sudo systemctl restart apache2``` can be used to restart Apache2  
```chmod 755 test.cgi``` can be used to change the permissions on this file so that the owner can do everything and everybody else can read and execute the script but cannot write to it  
Arguments can be passed to CGI script by appending them to the end of URL, for example: ```xxx/cgi-bin/test.cgi?name=Jay%20T&age=108```  
These arguments are stored within a bash variable named ```$QUERY_STRING``` in CGI script, and can be passed as String into a java program.
