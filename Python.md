# Python Tips: https://docs.python.org/3.10/tutorial/index.html
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
* str.zfill(): extend the left by 0s
## List
* sorted(), reversed()
* ```math.isnan(value)``` checks if value == float('NAN')
* ```letters[:] = []``` clears the list
* Update: ```a, b = b, a+b```
* ```list.extend(iterable)```: Extend the list by appending all the items from the iterable. Equivalent to ```a[len(a):] = iterable```
* With iteration (the following are equivalent):
  * ```squares = list(map(lambda x: x**2, range(10)))``` (where map() executes a specified function for each item in an iterable as ```map(function, iterables)```)
  * ```squares = [x**2 for x in range(10)]```
* zip(): iterate over several iterables in parallel, producing tuples with an item from each one
  * ```for item in zip([1, 2, 3], ['sugar', 'spice', 'everything nice']): print(item)``` gets ```[(1, 'sugar'), (2, 'spice'), (3, 'everything nice')]```
* del(): ```del list[:]``` is equivalent to ```list.clear()```, ```del list``` deletes this variable
### Used List as Stack (LIFO)
* pop: stack.pop()
### Used List as Queue (FIFO)
* pop: queue.popleft()
### Tuples are immutable (have fixed values) whereas Lists are mutable
### Sets
* a = set('abracadabra'), b = set('alacazam')
  * a # unique letters in a -> {'a', 'r', 'b', 'c', 'd'}
  * a - b # letters in a but not in b -> {'r', 'd', 'b'}
  * a | b # letters in a or b or both -> {'a', 'c', 'r', 'd', 'b', 'm', 'z', 'l'}
  * a & b # letters in both a and b -> {'a', 'c'}
  * a ^ b # letters in a or b but not both -> {'r', 'd', 'b', 'm', 'z', 'l'}
### Dictionary
* ```dict([('sape', 4139), ('guido', 4127), ('jack', 4098)])``` gets ```{'sape': 4139, 'guido': 4127, 'jack': 4098}```
## Print
* ```print(a, end=',')```
* ```print(f"He said his name is {name!r}.")``` is the same as ```print(f"He said his name is {repr(name)}.")```: "He said his name is 'J'."
* ```width = 10, precision = 4, value = decimal.Decimal("12.34567"), print(f"result: {value:{width}.{precision}}")```: "result:      12.35"
* ```today = datetime(year=2017, month=1, day=27), print(f"{today:%B %d, %Y}") # using date format specifier```: "January 27, 2017"
* ```number = 1024, print(f"{number:#0x}") # using integer format specifier```: "0x400"
* ```print('We are the {} who say "{}!"'.format('knights', 'Ni'))```: "We are the knights who say "Ni!""
* ```print('This {food} is {adjective}.'.format(food='spam', adjective='absolutely horrible'))```: "This spam is absolutely horrible."
* ```table = {'Sjoerd': 4127, 'Jack': 4098, 'Dcab': 8637678}```
  * ```print('Jack: {0[Jack]:d}; Sjoerd: {0[Sjoerd]:d}; Dcab: {0[Dcab]:d}'.format(table))```
  * ```print('Jack: {Jack:d}; Sjoerd: {Sjoerd:d}; Dcab: {Dcab:d}'.format(**table))```
  * gets "Jack: 4098; Sjoerd: 4127; Dcab: 8637678"
* ```print('{0:2d} {1:3d} {2:4d}'.format(x, x*x, x*x*x))```: column 0 has 2 decimals; column 1 has 3 decimals; column 2 has 4 decimals
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
* Technique
```
questions = ['name', 'quest', 'favorite color']
answers = ['lancelot', 'the holy grail', 'blue']
for q, a in zip(questions, answers):
    print('What is your {0}?  It is {1}.'.format(q, a))

What is your name?  It is lancelot.
What is your quest?  It is the holy grail.
What is your favorite color?  It is blue.
```
