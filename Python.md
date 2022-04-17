# Python Tips
## Arithmetic Operators
* '//' for division without float: 10 // 3 gets 3
* '\*\*' for power: 2 ** 7 gets 2^7
* In interactive mode, the last printed expression is assigned to the variable ```_```
```
>>> price * tax
12.5625
>>> price + _
113.0625
>>> round(_, 2)
113.06
```
## String
* Raw: ```print(r'C:\some\name')```
* word = "Python"
  * ```word[-1]``` gets the last character: 'n'
  * ```word[0:2]``` gets the characters from position 0 (included) to 2 (excluded): "Py"
  * ```word[-2:]``` gets the characters from the second-last (included) to the end: "on"
  * ERROR: ```word[0] = 'J'``` (SHOULD be ```'J' + word[1:]```)
## List
* ```letters[:] = []``` clears the list
* Update: ```a, b = b, a+b```
## Print
* ```print(a, end=',')```
* ```print(f"He said his name is {name!r}.")``` is the same as ```print(f"He said his name is {repr(name)}.")```: "He said his name is 'J'."
* ```width = 10, precision = 4, value = decimal.Decimal("12.34567"), print(f"result: {value:{width}.{precision}}")```: "result:      12.35"
* ```today = datetime(year=2017, month=1, day=27), print(f"{today:%B %d, %Y}") # using date format specifier```: "January 27, 2017"
* ```number = 1024, print(f"{number:#0x}") # using integer format specifier```: "0x400"
## Loop
* For / While ... Else
  * Else Statement is executed when the loop terminates **through exhaustion of the iterable (with for) or when the condition becomes false (with while)**, but not when the loop is terminated by a break statement.
* Pass
  * Pass Statement does nothing. It can be used when a statement is required syntactically but the program requires no action.
  * ```while True: pass # Busy-wait for keyboard interrupt (Ctrl+C)```
* Match (same as Switch)
```
def http_error(status):
    match status:
        case 400:
            return "Bad request"
        case 401 | 403 | 405:
            return "Not allowed"
        case 404:
            return "Not found"
        case 418:
            return "I'm a teapot"
        case _: # '_' acts as a wildcard and never fails to match
            return "Something's wrong with the internet"
``` 
