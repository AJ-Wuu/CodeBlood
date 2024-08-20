# Go
## Doc: https://go.dev/doc/effective_go
| Property                 | Go                           | Java                              | Python                             | C++                               | C#                                    | JavaScript                         |
|:-------------------------|:-----------------------------|:----------------------------------|:-----------------------------------|:----------------------------------|:--------------------------------------|:-----------------------------------|
| Architecture             | Compiled language            | Interpreted language              | Interpreted language               | Compiled language                 | Compiled language (.NET core runtime) | JIT compiled                       |
| Programming Type         | Statically-typed, procedural | Statically-typed, class-based OOP | Dynamically-typed, object-oriented | Statically-typed, class-based OOP | Statically-typed, class-based OOP     | Dynamically-typed, OOP, functional |
| Error/Exception Handling | Defer, panic, recover        | try-catch-finally                 | try-catch-finally                  | try-catch-throw                   | try-catch-throw                       | try-catch-finally                  |
| Memory Management        | Garbage collection           | Garbage collection                | Garbage collection                 | Not supported                     | Garbage collection                    | Garbage collection                 |
| Concurrency              | Very Well Supported     | Supported                         | Supported                          | Supported                         | Supported                             | Not supported multi-threading      |
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
    "strings"
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

// slice (implemented by default under the hood)
type slice struct {
    array unsafe.Pointer    // pointer, which points to an array
    len int                 // length = number of the data-possessed slots
    cap int                 // capacity = number of the total allocated slots
}

type Human struct {
    Age int
    Height float32
    Sex bool
}

func main() {
    // Variables
    var msg string = "a string"            // var NAME TYPE = xxx, need to specify type
    msg := "a string"                      // short assignment operator :=, no need to specify type, allow compiler to decide

    var employee1 Human = Human{Age: 23, Height: 170.0, Sex: true}
	var employee2 Human = Human{35, 172.5, true}
	fmt.Printf("%d  %.2f  %t\n", employee1.Age, employee1.Height, employee1.Sex)
	fmt.Printf("%v\n", employee2)      // give struct details, like `{35 172.5 true}`
	fmt.Printf("%+v\n", employee2)     // give struct key-value map, like `{Age:35 Height:172.5 Sex:true}`
	fmt.Printf("%#v\n", employee2)     // give struct package with key-value map, like `main.Human{Age:35, Height:172.5, Sex:true}`
    
    const pi = 3.14                        // declare a constant variable, cannot be changed, implicitly type
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
    arr := make([]int, 3, 5)
    arr[0], arr[1], arr[2] = 1, 3, 5    // arr = [1, 3, 5] with len = 3 and cap = 5
    brr := append(arr, 8)               // brr = [1, 3, 5, 8] with len = 4 and cap = 5
                                        // shallow copy, pointing to the same address as arr,
                                        // but arr cannot access beyond its own length
    arr[0] = 2                          // arr = [2, 3, 5] and brr = [2, 3, 5, 8]
    brr = append(brr, 8)                // brr = [2, 3, 5, 8, 8] with len = 5 and cap = 5
    brr = append(brr, 8)                // brr = [2, 3, 5, 8, 8, 8] with len = 6 and cap = 10
                                        // auto-expand the capacity, usually double the existing cap
                                        // deep copy, pointing to a new address (not shared with arr)


    // Map
    var prodPrice map[string]int           // map with key being string and value being int, nil
    prodPrice["cheese"] = 10               // get panic error because prodPrice is nil
    tempPrice := make(map[string]int)      // declare and initialize with default
    tempPrice["cake"] = 18
    prodPrice = tempPrice
    prodPrice["cream"] = 20                // fine because prodPrice is not nil
    empPrice := map[string]int{
        "macaron": 21,                     // declare and initialize
    }
    empPrice["cookie"] = 12
    val1, key1 := empPrice["macaron"]      // val1 = 21, key1 = true -> this key exists
    val2, key2 := empPrice["milk"]         // val2 = 0, key2 = false -> this key doesn't exist
    delete(empPrice, "macaron")
    
    prices := map[string]int{
        "egg": 2,
        "pork": 3,
        "beef": 4,
    }
    for key, value := range prices {       // use "range" to loop through
        fmt.Println(key, value)
    }
    for key := range prices {
        fmt.Println(key)                   // don't need to add "_" for the value space
    }
    for _, value := range prices {
        fmt.Println(value)                 // need to add "_" for the key space
    }
    
    
    // Pointer
    var testPtr *int                       // var NAME *T, get <nil>
    val := 123
    var ptr *int = &val                    // assign value
    var ptr2 *int = new(int)               // var NAME *T = new(T), set default value 0 for int
    *ptr2 = 123
    ptr3 := &val
   
    
    // Control Flow
    if temp := -10; temp < 0 {
        fmt.Println("Below freezing")
    } else if temp == 0 {
        fmt.Println("At freezing point")
    } else {
        fmt.Println("Above freezing")
    }
    
    temp := -10
    switch {
        case temp < 0:
            fmt.Println("Below freezing")
        case temp == 0:
            fmt.Println("At freezing point")
        default:
            fmt.Println("Above freezing")
    }
    
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
        

    // Loop -- no while
    for i := 0; i < 5; i++ {               // for INIT_STATEMENT; CONDITION_STATEMENT; POST_STATEMENT { ... }
        if i == 2 {
            break
        }
        fmt.Println(i)
    }
    
    j := 0
    for j < 5 {
        if j == 2 {
            continue
        }
        fmt.Println(j)
        j++
    }
    
    
    // String Function
    fmt.Println(strings.Contains("Working with string functions", "functions"))    // true
    fmt.Println(strings.Replace("gfg gfg gfg", "gfg", "GFG", 0))    // replace first 0 occurrence, so doesn't change anything
    fmt.Println(strings.Replace("gfg gfg gfg", "fg", "FG", -1))     // -1 occurrence = the last occurrence, so "gFG gFG gFG"
    fmt.Println(strings.Replace("gfg gfg gfg", "g", "G", 2))        // 3 occurrence = the first 3 occurrence, so "GfG Gfg gfg"
    fmt.Println(strings.Trim("____This is a sentence__", "_"))      // trimmed all underscores, so "This is a sentence"
}

func setMe() int {
    return 1
}

func deferExample() {
    // defer execution of a function
    defer fmt.Println("second")            // function, called only after parent function (deferExample()) returns
    fmt.Println("first")                   // argument, evaluated immediately
}

func labelExample() {
    i := 0
    
outerlabel:
    for i < 5 {
        if i == 2 {
            i++
            goto outerlabel
        }
        fmt.Println(i)
        i++
    }
}
```
