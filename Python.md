# Python Tips
* Arithmetic Operators
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
* String
  * Raw: ```print(r'C:\some\name')```
  * word = "Python"
    * ```word[-1]``` gets the last character: 'n'
    * ```word[0:2]``` gets the characters from position 0 (included) to 2 (excluded): "Py"
    * ```word[-2:]``` gets the characters from the second-last (included) to the end: "on"
    * ERROR: ```word[0] = 'J'``` (SHOULD be ```'J' + word[1:]```)
* List
  * ```letters[:] = []``` clears the list
  * Update: ```a, b = b, a+b```
* Print
  * ```print(a, end=',')```
