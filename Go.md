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

## Syntax
```go
package main

import (
    "fmt"
)

// global scope
var workday = 5

// enum, "iota" starts a counter that increments for each line of the const declaration (starting from 0)
const (
    Sunday = iota
    Monday
    Tuesday
    Wednesday
    Thursday
    Friday
    Saturday
)

func main() {
    // Variables
    var msg string = "a string"            // var NAME TYPE = xxx, need to specify type
    msg := "a string"                      // no need to specify type, allow compiler to decide with :=
    
    const pi = 3.14                        // declare a constant variable, cannot be changed
    fmt.Printf("type: %T, value: %v", pi + 1.1, pi + 1.1)    // get "type: float64, value: 2.1"
    const myConst int = 3.14
    fmt.Printf("type: %T, value: %v", float64(myConst) + 1.1, float64(myConst) + 1.1)    // no type conversion will give error
    const myFunc = setMe()                 // cannot set a return value from function to be constant
    fmt.Printf("type: %T, value: %v", myFunc + 1.1, myFunc + 1.1)    // get error


    // Arrays, fixed-length, passed by value, contain default value
    var arr1 [4]int                        // [0 0 0 0]
    arr2 := [4]int{0,1,2,3}                // [0 1 2 3]
    var twoDim [3][2]int                   // [[0 0] [0 0] [0 0]]


    // Slices, dynamic-length, passed by reference
    // Faster to pass slices than arrays as there is no need to make value copies
    var slice1 []int                       // [], (slice1 == nill) is true
    slice2 := make([]int, 4)               // [0 0 0 0], make(TYPE, LENGTH, CAPACITY_OPTIONAL)
    slice3 := []int{0,1,2,3}               // [0 1 2 3]
    slice2 = append(slice2, 4, 5, 6)       // [0 0 0 0 4], append([]T, ELEMENT1, ELEMENT2, ...)
    slice4 := []int{4,5,6}
    slice5 := append(slice3, slice4...)    // "..." expands the elements in slice4
    sliceA := slice2[2:4]                  // []T[low:high]
    sliceB := slice2[:5]
    sliceC := slice2[3:]


    // Loop
    for i := 0; i < 5; i++ {
        fmt.Println(i)
    }
    
    
    // Selection
    switch workday {
        case 1:
            fmt.Println("Monday")
        case 2:
            fmt.Println("Tuesday")
        case 3:
            fmt.Println("Wednesday")
        case 4:
            fmt.Println("Thursday")
        case 5:
            fmt.Println("Friday")
        default:
            fmt.Println("Take the weekend off!")
    }
    
}

func setMe() int {
    return 1
}
```
