# String
## String, StringBuffer and StringBuilder
### Mutability
* String is immutable, if you try to alter their values, another object gets created
* StringBuffer and StringBuilder are mutable so they can change their values

### Thread-Safety
* StringBuffer is thread-safe, so when the application needs to be run only in a single thread then it is better to use StringBuilder
* StringBuilder is more efficient than StringBuffer, NOT thread-safe

### Performance Analysis
* **StringBuilder** is preferrable
  * uses LinkedList as data structure underneath
  * in general, insertion has amortized complexity to be O(1)
  * when resize is not needed, insertion costs O(1)
  * when resize is needed, the size is always doubled, so it costs O(n) ones

![image](https://github.com/Gnaku-18519/CodeBlood/assets/84046974/dc8d7d65-5cc8-4bdf-b1f5-a441b2ef6745)

## `.charAt()` vs `.toCharArray()`
* In a very large amount of operations, the validation will make `.charAt()` slower than `.toCharArray()`
* `.charAt()` performs an index validation, then takes O(1) to fetch
```java
public char charAt(int index) {
    if ((index < 0) || (index >= value.length)) {
        throw new StringIndexOutOfBoundsException(index);
    }
    return value[index];
}
```
* `.toCharArray()` has no validation, and takes O(n) to finish
