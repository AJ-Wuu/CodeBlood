# Go
## Doc: https://go.dev/doc/effective_go
| Property                 | Go                           | Java                              | Python                             | C++                               | C#                                    | JavaScript                         |
|:-------------------------|:-----------------------------|:----------------------------------|:-----------------------------------|:----------------------------------|:--------------------------------------|:-----------------------------------|
| Architecture             | Compiled language            | Interpreted language              | Interpreted language               | Compiled language                 | Compiled language (.NET core runtime) | JIT compiled                       |
| Programming Type         | Statically-typed, procedural | Statically-typed, class-based OOP | Dynamically-typed, object-oriented | Statically-typed, class-based OOP | Statically-typed, class-based OOP     | Dynamically-typed, OOP, functional |
| Error/Exception Handling | Defer, panic, recover        | try-catch-finally                 | try-catch-finally                  | try-catch-throw                   | try-catch-throw                       | try-catch-finally                  |
| Memory Management        | Garbage collection           | Garbage collection                | Garbage collection                 | Not supported                     | Garbage collection                    | Garbage collection                 |
| Concurrency              | Supported                    | Supported                         | Supported                          | Supported                         | Supported                             | Not supported multi-threading      |
| Reflection               | Supported                    | Supported                         | Supported                          | Somewhat supported                | Supported                             | Supported                          |

## CLI
```bash
$ go mod init example.local/demo
$ go get github.com/rccsdevops/go/repo
$ go run example.local/demo
```
