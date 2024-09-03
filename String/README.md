# String
## Performance Analysis
* `StringBuilder` is preferrable
  * uses LinkedList as data structure underneath
  * in general, insertion has amortized complexity to be O(1)
  * when resize is not needed, insertion costs O(1)
  * when resize is needed, the size is always doubled, so it costs O(n) ones

![image](https://github.com/Gnaku-18519/CodeBlood/assets/84046974/dc8d7d65-5cc8-4bdf-b1f5-a441b2ef6745)

### `.charAt()` vs `.toCharArray()`
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
