Common Gateway Interface (CGI):
The folder that Apache2 will run cgi scripts from: ________________________
sudo chown USERNAME .
^ this command can be used to ______________________________________
FIRST CGI SCRIPT test.cgi:
    #!/usr/bin/env bash
    echo "Content-type: text/html"
    echo ""
    echo "<html><body><p>This page was dynamically generated!</p></body></html>"
sudo ln -s /etc/apache2/mods-available /etc/apache2/mods-enabled
^ this command can be used to ______________________________________
sudo systemctl restart apache2
^ this command can be used to ______________________________________
chmod 755 test.cgi
^ this command can be used to ______________________________________
Arguements can be passed to CGI script by appending them to the end of URL,
for example: ____________________________________
These arguments are stored within a variable named ________________ in CGI script.
And can be passed as ________________________ into a java program. 
