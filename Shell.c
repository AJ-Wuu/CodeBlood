/* @author AJWuu
 * Simple Implementation of Unix Shell
 *
 * Functions:
 * Interactive Mode & Batch Mode
 * Built-In commands (cd, path, if, exit) and Other commands (searched in system)
 * Redirection output to file
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>

int max_size = 1024;
int paths_number = 1;

void print_error() {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
}

void organize_input(char *src, char *dest) {
        while (*src == ' ' || *src == '\t') { // remove leading spaces and/or tabs
                src++;
        }
        while (*src != '\0' && *src != '\n') {
                while ((*src == ' ' && *(src + 1) == ' ') || (*src == ' ' && *(src + 1) == '\t') || (*src == '\t' && *(src + 1) == ' ') || (*src == '\t' && *(src + 1) == '\t')) { // remove extra spaces and/or tabs
                        src++;
                }

                if (*src == '\t') {
                        *src++;
                        *dest++ = ' ';
                        continue;
                }

                if (*src == '>') {
                        if (*(dest - 1) != ' ') {
                                *dest++ = ' ';
                        }
                        *dest++ = *src++;
                        if (*src != ' ' && *src != '\t' && *src != '\n') {
                                *dest++ = ' ';
                        }
                        continue;
                }

                *dest++ = *src++;
        }

        *dest--; // move to the last character
        while (*dest == ' ') { // remove trailing spaces (tabs are all changed to spaces)
                *dest-- = '\0';
        }
        *(dest + 1) = '\0'; // move to the next character
        return ;
}

char** split_first_command(char **command, char **first_command) {
        int i = 1;
        while ((strcmp(*(command + i), "==") != 0) && strcmp(*(command + i), "!=") != 0) {
                *(first_command + i - 1) = strdup(*(command + 1));
        }
        return first_command;
}

int run(char **cmd, char **paths) {
        // prepare the command array and check if redirection is needed
        char *command[max_size], *redirection_dest, *temp_string;
        int status = 0;
        int index = 0;
        int pre_index = 0;
        int index_redirection = 0;
        int is_redirection = 0, is_if = 0, out_err = 0, save_out = 0, save_err = 0;
        while ((temp_string = strsep(cmd, " \t")) != NULL) {
                //temp_string[strcspn(temp_string, "\n")] = '\0';
                if (strcmp(temp_string, "if") == 0) {
                        is_if = 1;
                }

                if (is_if == 0 && strcmp(temp_string, ">") == 0) {
                        is_redirection = 1;
                        pre_index = index;
                }

                if (is_redirection == 0) {
                        command[index++] = temp_string;
                }
                else {
                        if (index_redirection == 1) { // get the output destination
                                redirection_dest = strdup(temp_string);
                        }
                        if (index_redirection == 2) { // too many arguments
                                print_error();
                                return 1;
                        }
                        index++;
                        index_redirection++;
                }
        }
        if (is_redirection == 1 && (index_redirection == 1 || pre_index <= 1)) { // too few arguments
                print_error();
                return 1;
        }

        // set redirection and open redirection file
        if (is_redirection == 1) {
                out_err = open(redirection_dest, O_RDWR|O_CREAT|O_TRUNC, 0666); // overwrite after truncation
                if (out_err == -1) {
                        print_error();
                        return 1;
                }
                save_out = dup(fileno(stdout));
                save_err = dup(fileno(stderr));
                if (dup2(out_err, fileno(stdout)) == -1 || dup2(out_err, fileno(stderr)) == -1) {
                        print_error();
                        return 1;
                }
        }

        // check if the command is a built-in or if it exists in paths
        if (strcmp(*command, "exit") == 0) {
                if (index > 1) { // too many arguments
                        print_error();
                        return 1;
                }
                else {
                        exit(0);
                }
        }
        else if (strcmp(*command, "cd") == 0) {
                if (index != 2) { // too many or too few arguments
                        print_error();
                        return 1;
                }
                else {
                        if (chdir(command[1]) != 0) { // error occurs in chdir()
                                print_error();
                                return 1;
                        }
                }
        }
        else if (strcmp(*command, "path") == 0) {
                free(paths); // always free and re-allocate
                paths = (char**)malloc(max_size * sizeof(char*));
                for (int i = 1; i < index; i++) {
                        paths[i - 1] = strdup(*(command + i));
                }
                paths_number = index - 1;
        }
        else if (strcmp(*command, "if") == 0) { // syntax: if COMMAND OPERATOR CONSTANT then COMMAND fi
                // split each part according to syntax
                char *if_first_command = (char*)malloc(max_size * sizeof(char));
                int first_command_length = 1;
                strcpy(if_first_command, command[1]);
                while (first_command_length + 1 < index && (strcmp(command[first_command_length + 1], "==") != 0) && (strcmp(command[first_command_length + 1], "!=") != 0)) {
                        strcat(if_first_command, " ");
                        strcat(if_first_command, command[first_command_length + 1]);
                        first_command_length++;
                }
                if (first_command_length == index - 1) {
                        print_error();
                        return 1;
                }

                char *operator = strdup(command[first_command_length + 1]);
                int constant = atoi(command[first_command_length + 2]);
                if (strcmp(command[first_command_length + 3], "then") != 0) { // "then" format is wrong
                        print_error();
                        return 1;
                }

                char *if_second_command = (char*)malloc(max_size * sizeof(char));
                int second_command_length = 1;
                strcpy(if_second_command, command[first_command_length + 4]);
                if (strcmp(if_second_command, "fi") == 0) { // no second command
                        second_command_length = 0;
                }
                while (first_command_length + second_command_length + 5 < index) { //&& strcmp(command[first_command_length + second_command_length + 4], "fi") != 0) {
                        strcat(if_second_command, " ");
                        strcat(if_second_command, command[first_command_length + second_command_length + 4]);
                        second_command_length++;
                }

                if (first_command_length + second_command_length + 4 >= index || strcmp(command[first_command_length + second_command_length + 4], "fi") != 0) { // no "fi" or "fi" format is wrong
                        print_error();
                        return 1;
                }
                if (first_command_length + second_command_length + 5 < index) { // too many things entered after "fi"
                        print_error();
                        return 1;
                }

                // run the first command
                status = run(&if_first_command, paths);

                // check the if-condition is satisfied or not
                int if_flag = 0; // true == 1, false == 0
                if (((strcmp(operator, "==") == 0) && status/256 == constant) || ((strcmp(operator, "!=") == 0) && status/256 != constant)) {
                        if_flag = 1;
                }

                // if the condition is satisfied, run the second command
                if (if_flag == 1) {
                        if (second_command_length < 1) {
                                status = 0;
                        }
                        else {
                                status = run(&if_second_command, paths);
                        }
                }

                free(if_first_command);
                free(if_second_command);
        }
        else { // not built-in command
                char *new_path, *addition_path = strdup("/");
                strcat(addition_path, command[0]);

                int i = 0;
                int success = 0;
                while (i <= paths_number) {
                        // form new path
                        if (i == paths_number) {
                                new_path = strdup(".");
                        }
                        else { // check if it is an executable file itself
                                new_path = strdup(paths[i]);
                        }
                        strcat(new_path, addition_path);

                        if (access(new_path, X_OK) == -1) { // access() returns -1 if fails
                                i++;
                        }
                        else {
                                // modify command
                                command[index++] = NULL; // end with NULL for the sake of execv()
                                success = 1;
                                break;
                        }
                }

                if (success == 0) { // no valid path
                        print_error();
                        return 1;
                }

                // found valid path
                pid_t pid = fork();
                if (pid < 0) {
                        print_error();
                        return 1;
                }
                else if (pid == 0) {
                        execv(new_path, command);
                        print_error();
                        return 1;
                }
                else {
                        waitpid(pid, &status, 0);
                        //return status;
                }
        }

        // close redirection file
        if (is_redirection == 1) {
                fflush(stdout);
                fflush(stderr);
                close(out_err);
                dup2(save_out, fileno(stdout));
                dup2(save_err, fileno(stderr));
                close(save_out);
                close(save_err);
        }

        return status;
}

int main(int argc, char *argv[]) {
        char *command;
        char *organized_command;
        size_t length;
        FILE *stream;
        int is_batch = 0;
        if (argc == 1) { // interactive mode
                stream = stdin;
                printf("wish> ");
        }
        else if (argc == 2) { // batch mode
                is_batch = 1;
                stream = fopen(argv[1], "r");
                if (stream == NULL) {
                        print_error();
                        return 1;
                }
        }
        else {
                print_error();
                return 1;
        }

        char **paths = (char**)malloc(max_size * sizeof(char*));
        paths[0] = strdup("/bin");
        while ((length = getline(&command, &length, stream)) != -1) {
                if (feof(stream)) {
                        break;
                }

                organized_command = (char*)malloc(max_size * sizeof(char));
                organize_input(command, organized_command);

                if (strcmp(organized_command, "\0") != 0) {
                        run(&organized_command, paths);
                }

                if (!is_batch) {
                        printf("wish> ");
                }
        }

        exit(0);
}
