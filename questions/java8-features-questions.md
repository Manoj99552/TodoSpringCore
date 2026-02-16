# Java 8 Features Interview Questions (Easy Level - SDE 1)

## 50 Questions for Service-Based Companies

### Lambda Expressions

1. **What is a Lambda expression?**
   - Lambda expression is an anonymous function that provides a concise way to represent a method interface using an expression.

2. **What is the syntax of a Lambda expression?**
   - `(parameters) -> expression` or `(parameters) -> { statements; }`

3. **What is a Functional Interface?**
   - An interface with exactly one abstract method. Can be annotated with `@FunctionalInterface`.

4. **Give examples of built-in functional interfaces.**
   - Predicate, Function, Consumer, Supplier, UnaryOperator, BinaryOperator

5. **What is the advantage of Lambda expressions?**
   - Less code, improved readability, enables functional programming, easy parallel processing

6. **Can we use Lambda expressions with any interface?**
   - No, only with functional interfaces (single abstract method).

7. **What is the difference between Lambda and Anonymous class?**
   - Lambda: more concise, can only be used with functional interfaces
   - Anonymous class: verbose, can be used with any class/interface

8. **How do you write a Lambda expression for Runnable?**
   - `Runnable r = () -> System.out.println("Hello");`

9. **What is method reference in Java 8?**
   - Shorthand notation of lambda expression to call a method. Example: `String::toUpperCase`

10. **What are the types of method references?**
    - Static method reference, Instance method reference, Constructor reference

### Stream API

11. **What is the Stream API?**
    - Stream API is used to process collections of objects in a functional style.

12. **What is the difference between Collection and Stream?**
    - Collection: stores data
    - Stream: processes data (doesn't store)

13. **How do you create a Stream?**
    - `list.stream()`, `Stream.of(values)`, `Arrays.stream(array)`

14. **What are intermediate operations in Stream?**
    - Operations that return a Stream: filter(), map(), sorted(), distinct(), limit()

15. **What are terminal operations in Stream?**
    - Operations that produce a result: forEach(), collect(), count(), reduce(), findFirst()

16. **What is the filter() method in Stream?**
    - Filters elements based on a condition (Predicate). Example: `stream.filter(x -> x > 10)`

17. **What is the map() method in Stream?**
    - Transforms each element using a function. Example: `stream.map(String::toUpperCase)`

18. **What is the difference between map() and flatMap()?**
    - map(): one-to-one mapping
    - flatMap(): one-to-many mapping, flattens nested structures

19. **What is the collect() method?**
    - Terminal operation that accumulates elements into a collection. Example: `stream.collect(Collectors.toList())`

20. **What is the forEach() method?**
    - Terminal operation that performs an action on each element. Example: `stream.forEach(System.out::println)`

21. **What is the reduce() method?**
    - Combines elements to produce a single result. Example: `stream.reduce(0, (a, b) -> a + b)`

22. **What is the difference between findFirst() and findAny()?**
    - findFirst(): returns the first element
    - findAny(): returns any element (useful in parallel streams)

23. **What is the sorted() method?**
    - Sorts the stream elements. Example: `stream.sorted()` or `stream.sorted(Comparator.reverseOrder())`

24. **What is the distinct() method?**
    - Removes duplicate elements from the stream.

25. **What is the limit() method?**
    - Truncates the stream to a specified size. Example: `stream.limit(5)`

### Optional Class

26. **What is Optional in Java 8?**
    - Optional is a container object that may or may not contain a non-null value.

27. **Why was Optional introduced?**
    - To avoid NullPointerException and make the code more readable.

28. **How do you create an Optional object?**
    - `Optional.of(value)`, `Optional.ofNullable(value)`, `Optional.empty()`

29. **What is the difference between Optional.of() and Optional.ofNullable()?**
    - of(): throws exception if value is null
    - ofNullable(): returns empty Optional if value is null

30. **What is the isPresent() method?**
    - Returns true if value is present, false otherwise.

31. **What is the ifPresent() method?**
    - Executes action if value is present. Example: `optional.ifPresent(System.out::println)`

32. **What is the orElse() method?**
    - Returns the value if present, otherwise returns the default value.

33. **What is the orElseGet() method?**
    - Returns the value if present, otherwise invokes supplier and returns the result.

34. **What is the difference between orElse() and orElseGet()?**
    - orElse(): always evaluates the default value
    - orElseGet(): evaluates supplier only if value is absent (more efficient)

### Default and Static Methods in Interface

35. **What are default methods in interface?**
    - Methods with implementation in interface, defined using `default` keyword.

36. **Why were default methods introduced?**
    - To add new methods to interfaces without breaking existing implementations.

37. **Can we override default methods?**
    - Yes, implementing classes can override default methods.

38. **What are static methods in interface?**
    - Methods that belong to the interface, not to instances, called using interface name.

39. **Can we override static methods in interface?**
    - No, static methods cannot be overridden.

### Date and Time API

40. **What is the new Date and Time API in Java 8?**
    - Package `java.time` provides improved, immutable date-time classes.

41. **What are the main classes in the new Date-Time API?**
    - LocalDate, LocalTime, LocalDateTime, ZonedDateTime, Instant, Duration, Period

42. **What is LocalDate?**
    - Represents a date without time and timezone. Example: `LocalDate.now()`

43. **What is LocalTime?**
    - Represents a time without date and timezone. Example: `LocalTime.now()`

44. **What is LocalDateTime?**
    - Represents both date and time without timezone. Example: `LocalDateTime.now()`

45. **What is the advantage of the new Date-Time API?**
    - Immutable, thread-safe, clear API, better handling of timezones

46. **How do you get the current date?**
    - `LocalDate.now()`

47. **How do you create a specific date?**
    - `LocalDate.of(2024, 1, 15)` or `LocalDate.of(2024, Month.JANUARY, 15)`

### Miscellaneous Java 8 Features

48. **What is the forEach() method in Iterable?**
    - Default method in Iterable interface to iterate over collections using lambda.

49. **What are StringJoiner and Collectors.joining()?**
    - Used to join strings with delimiters. Example: `String.join(", ", list)` or `stream.collect(Collectors.joining(", "))`

50. **What is the use of Comparator improvements in Java 8?**
    - Comparator now has static methods like `comparing()`, `thenComparing()` for easy sorting.
    - Example: `list.sort(Comparator.comparing(Person::getName))`

---

## Additional Notes for Interview Preparation

### Key Java 8 Features Summary:
1. **Lambda Expressions** - Anonymous functions
2. **Stream API** - Functional-style operations on collections
3. **Optional** - Better null handling
4. **Default Methods** - Interface evolution
5. **Method References** - Shorthand for lambdas
6. **New Date-Time API** - java.time package
7. **Functional Interfaces** - Single abstract method interfaces
8. **Collectors** - Stream result accumulation

### Common Code Examples to Practice:

```java
// Lambda Expression
List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
names.forEach(name -> System.out.println(name));

// Stream API
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> evenNumbers = numbers.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());

// Optional
Optional<String> optional = Optional.ofNullable(getName());
String result = optional.orElse("Default Name");

// Method Reference
names.stream()
    .map(String::toUpperCase)
    .forEach(System.out::println);
```

### Tips for Interview:
- Practice writing lambda expressions for common scenarios
- Understand the difference between intermediate and terminal operations
- Know when to use Optional and its methods
- Be familiar with common Stream operations: filter, map, collect, reduce
- Understand the benefits of default methods in interfaces
