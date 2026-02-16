# Java 8 Features Coding Questions (Easy Level - SDE 1)

## 50 Coding Problems for Service-Based Companies

### Lambda Expressions (10 questions)

1. **Print All Elements Using forEach**
```java
// Input: ["Apple", "Banana", "Cherry"]
// Output: Apple Banana Cherry
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
fruits.forEach(fruit -> System.out.println(fruit));
// Or using method reference
fruits.forEach(System.out::println);
```

2. **Create a Runnable Using Lambda**
```java
// Traditional vs Lambda
Runnable runnable = () -> System.out.println("Hello from thread!");
Thread thread = new Thread(runnable);
thread.start();
```

3. **Sort List Using Lambda**
```java
// Input: [5, 2, 8, 1, 9]
// Output: [1, 2, 5, 8, 9]
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);
numbers.sort((a, b) -> a - b);
// Or using Comparator
numbers.sort(Comparator.naturalOrder());
```

4. **Filter Even Numbers Using Predicate**
```java
// Input: [1, 2, 3, 4, 5, 6]
// Output: [2, 4, 6]
Predicate<Integer> isEven = n -> n % 2 == 0;
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> evens = numbers.stream()
    .filter(isEven)
    .collect(Collectors.toList());
```

5. **Square Numbers Using Function**
```java
// Input: [1, 2, 3, 4, 5]
// Output: [1, 4, 9, 16, 25]
Function<Integer, Integer> square = n -> n * n;
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> squared = numbers.stream()
    .map(square)
    .collect(Collectors.toList());
```

6. **Convert Strings to Uppercase Using Consumer**
```java
// Input: ["hello", "world"]
// Output: HELLO WORLD
Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
List<String> words = Arrays.asList("hello", "world");
words.forEach(printUpper);
```

7. **Generate Random Numbers Using Supplier**
```java
// Output: Random number
Supplier<Integer> randomSupplier = () -> new Random().nextInt(100);
System.out.println(randomSupplier.get());
```

8. **Check if All Elements are Positive**
```java
// Input: [1, 2, 3, 4, 5]
// Output: true
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
boolean allPositive = numbers.stream()
    .allMatch(n -> n > 0);
```

9. **Check if Any Element is Greater Than 10**
```java
// Input: [1, 2, 15, 4, 5]
// Output: true
List<Integer> numbers = Arrays.asList(1, 2, 15, 4, 5);
boolean anyGreaterThan10 = numbers.stream()
    .anyMatch(n -> n > 10);
```

10. **Count Elements Greater Than 5**
```java
// Input: [1, 6, 3, 8, 2, 9]
// Output: 3
List<Integer> numbers = Arrays.asList(1, 6, 3, 8, 2, 9);
long count = numbers.stream()
    .filter(n -> n > 5)
    .count();
```

### Stream API - Basic Operations (15 questions)

11. **Filter Strings Starting with 'A'**
```java
// Input: ["Apple", "Banana", "Apricot", "Cherry"]
// Output: ["Apple", "Apricot"]
List<String> fruits = Arrays.asList("Apple", "Banana", "Apricot", "Cherry");
List<String> result = fruits.stream()
    .filter(s -> s.startsWith("A"))
    .collect(Collectors.toList());
```

12. **Convert Strings to Uppercase**
```java
// Input: ["apple", "banana", "cherry"]
// Output: ["APPLE", "BANANA", "CHERRY"]
List<String> fruits = Arrays.asList("apple", "banana", "cherry");
List<String> upper = fruits.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

13. **Find Sum of All Numbers**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 15
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream()
    .reduce(0, Integer::sum);
```

14. **Find Maximum Number**
```java
// Input: [1, 5, 3, 9, 2]
// Output: 9
List<Integer> numbers = Arrays.asList(1, 5, 3, 9, 2);
Optional<Integer> max = numbers.stream()
    .max(Integer::compareTo);
```

15. **Find Minimum Number**
```java
// Input: [1, 5, 3, 9, 2]
// Output: 1
List<Integer> numbers = Arrays.asList(1, 5, 3, 9, 2);
Optional<Integer> min = numbers.stream()
    .min(Integer::compareTo);
```

16. **Get Distinct Elements**
```java
// Input: [1, 2, 2, 3, 3, 4]
// Output: [1, 2, 3, 4]
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4);
List<Integer> distinct = numbers.stream()
    .distinct()
    .collect(Collectors.toList());
```

17. **Get First 3 Elements**
```java
// Input: [1, 2, 3, 4, 5, 6]
// Output: [1, 2, 3]
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
List<Integer> firstThree = numbers.stream()
    .limit(3)
    .collect(Collectors.toList());
```

18. **Skip First 2 Elements**
```java
// Input: [1, 2, 3, 4, 5]
// Output: [3, 4, 5]
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
List<Integer> skipped = numbers.stream()
    .skip(2)
    .collect(Collectors.toList());
```

19. **Sort Numbers in Descending Order**
```java
// Input: [5, 2, 8, 1, 9]
// Output: [9, 8, 5, 2, 1]
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);
List<Integer> sorted = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
```

20. **Check if List is Empty**
```java
// Input: []
// Output: true
List<Integer> numbers = new ArrayList<>();
boolean isEmpty = numbers.stream()
    .findAny()
    .isEmpty();
```

21. **Get Total Count of Elements**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 5
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
long count = numbers.stream()
    .count();
```

22. **Find Average of Numbers**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 3.0
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
OptionalDouble average = numbers.stream()
    .mapToInt(Integer::intValue)
    .average();
```

23. **Join Strings with Comma**
```java
// Input: ["Apple", "Banana", "Cherry"]
// Output: "Apple, Banana, Cherry"
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
String joined = fruits.stream()
    .collect(Collectors.joining(", "));
```

24. **Partition Even and Odd Numbers**
```java
// Input: [1, 2, 3, 4, 5, 6]
// Output: {true=[2, 4, 6], false=[1, 3, 5]}
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
Map<Boolean, List<Integer>> partitioned = numbers.stream()
    .collect(Collectors.partitioningBy(n -> n % 2 == 0));
```

25. **Group Strings by Length**
```java
// Input: ["a", "bb", "ccc", "dd"]
// Output: {1=[a], 2=[bb, dd], 3=[ccc]}
List<String> words = Arrays.asList("a", "bb", "ccc", "dd");
Map<Integer, List<String>> grouped = words.stream()
    .collect(Collectors.groupingBy(String::length));
```

### Stream API - Advanced Operations (10 questions)

26. **Flatten Nested Lists**
```java
// Input: [[1, 2], [3, 4], [5]]
// Output: [1, 2, 3, 4, 5]
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2),
    Arrays.asList(3, 4),
    Arrays.asList(5)
);
List<Integer> flattened = nested.stream()
    .flatMap(List::stream)
    .collect(Collectors.toList());
```

27. **Get Unique Characters from List of Strings**
```java
// Input: ["hello", "world"]
// Output: [h, e, l, o, w, r, d]
List<String> words = Arrays.asList("hello", "world");
List<Character> uniqueChars = words.stream()
    .flatMap(s -> s.chars().mapToObj(c -> (char) c))
    .distinct()
    .collect(Collectors.toList());
```

28. **Find Sum of Squares of Even Numbers**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 20 (2^2 + 4^2 = 4 + 16)
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sumOfSquares = numbers.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * n)
    .reduce(0, Integer::sum);
```

29. **Get Length of Each String**
```java
// Input: ["Apple", "Banana", "Cherry"]
// Output: {Apple=5, Banana=6, Cherry=6}
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
Map<String, Integer> lengthMap = fruits.stream()
    .collect(Collectors.toMap(s -> s, String::length));
```

30. **Find First String Longer Than 5 Characters**
```java
// Input: ["hi", "hello", "world"]
// Output: "hello"
List<String> words = Arrays.asList("hi", "hello", "world");
Optional<String> first = words.stream()
    .filter(s -> s.length() > 5)
    .findFirst();
```

31. **Remove Null Values from List**
```java
// Input: ["Apple", null, "Banana", null, "Cherry"]
// Output: ["Apple", "Banana", "Cherry"]
List<String> fruits = Arrays.asList("Apple", null, "Banana", null, "Cherry");
List<String> nonNull = fruits.stream()
    .filter(Objects::nonNull)
    .collect(Collectors.toList());
```

32. **Convert List to Set**
```java
// Input: [1, 2, 2, 3, 3, 4]
// Output: [1, 2, 3, 4]
List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 3, 4);
Set<Integer> uniqueSet = numbers.stream()
    .collect(Collectors.toSet());
```

33. **Count Frequency of Each Element**
```java
// Input: ["apple", "banana", "apple", "cherry"]
// Output: {apple=2, banana=1, cherry=1}
List<String> fruits = Arrays.asList("apple", "banana", "apple", "cherry");
Map<String, Long> frequency = fruits.stream()
    .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
```

34. **Find Product of All Numbers**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 120
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int product = numbers.stream()
    .reduce(1, (a, b) -> a * b);
```

35. **Sort Strings by Length**
```java
// Input: ["banana", "pie", "apple"]
// Output: ["pie", "apple", "banana"]
List<String> fruits = Arrays.asList("banana", "pie", "apple");
List<String> sorted = fruits.stream()
    .sorted(Comparator.comparing(String::length))
    .collect(Collectors.toList());
```

### Optional (8 questions)

36. **Create and Use Optional**
```java
// Creating Optional with value
Optional<String> opt1 = Optional.of("Hello");
Optional<String> opt2 = Optional.ofNullable(null);
Optional<String> opt3 = Optional.empty();
```

37. **Get Value from Optional with Default**
```java
// Input: Optional.empty()
// Output: "Default"
Optional<String> opt = Optional.empty();
String value = opt.orElse("Default");
```

38. **Use orElseGet with Supplier**
```java
// Input: Optional.empty()
// Output: Generated value
Optional<String> opt = Optional.empty();
String value = opt.orElseGet(() -> "Generated: " + System.currentTimeMillis());
```

39. **Transform Optional Value Using map**
```java
// Input: Optional.of("hello")
// Output: Optional.of("HELLO")
Optional<String> opt = Optional.of("hello");
Optional<String> upper = opt.map(String::toUpperCase);
```

40. **Filter Optional Value**
```java
// Input: Optional.of(10)
// Output: Optional.empty() if < 5, else Optional.of(10)
Optional<Integer> opt = Optional.of(10);
Optional<Integer> filtered = opt.filter(n -> n > 5);
```

41. **Use ifPresent to Print Value**
```java
// Input: Optional.of("Hello")
// Output: Prints "Hello"
Optional<String> opt = Optional.of("Hello");
opt.ifPresent(System.out::println);
```

42. **Throw Exception if Empty**
```java
// Input: Optional.empty()
// Output: Throws exception
Optional<String> opt = Optional.empty();
String value = opt.orElseThrow(() -> new RuntimeException("Value not found"));
```

43. **Chain Optional Operations**
```java
// Input: Optional.of("  hello  ")
// Output: Optional.of("HELLO")
Optional<String> opt = Optional.of("  hello  ");
Optional<String> result = opt
    .map(String::trim)
    .map(String::toUpperCase)
    .filter(s -> s.length() > 3);
```

### Date and Time API (7 questions)

44. **Get Current Date**
```java
// Output: 2024-01-15
LocalDate today = LocalDate.now();
System.out.println(today);
```

45. **Create Specific Date**
```java
// Output: 2024-12-25
LocalDate christmas = LocalDate.of(2024, 12, 25);
// Or
LocalDate christmas2 = LocalDate.of(2024, Month.DECEMBER, 25);
```

46. **Add Days to Date**
```java
// Input: 2024-01-15
// Output: 2024-01-25
LocalDate date = LocalDate.of(2024, 1, 15);
LocalDate newDate = date.plusDays(10);
```

47. **Calculate Days Between Two Dates**
```java
// Input: 2024-01-01 and 2024-01-31
// Output: 30
LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end = LocalDate.of(2024, 1, 31);
long days = ChronoUnit.DAYS.between(start, end);
```

48. **Get Current Time**
```java
// Output: 14:30:45.123
LocalTime now = LocalTime.now();
System.out.println(now);
```

49. **Format Date**
```java
// Input: 2024-01-15
// Output: "15-Jan-2024"
LocalDate date = LocalDate.of(2024, 1, 15);
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
String formatted = date.format(formatter);
```

50. **Check if Year is Leap Year**
```java
// Input: 2024
// Output: true
LocalDate date = LocalDate.of(2024, 1, 1);
boolean isLeap = date.isLeapYear();
```

---

## Practice Exercises

### Exercise 1: Employee Filter
```java
class Employee {
    String name;
    int age;
    double salary;
    // constructor, getters, setters
}

// Filter employees with salary > 50000
List<Employee> filtered = employees.stream()
    .filter(e -> e.getSalary() > 50000)
    .collect(Collectors.toList());

// Get names of employees older than 30
List<String> names = employees.stream()
    .filter(e -> e.getAge() > 30)
    .map(Employee::getName)
    .collect(Collectors.toList());

// Find average salary
double avgSalary = employees.stream()
    .mapToDouble(Employee::getSalary)
    .average()
    .orElse(0.0);
```

### Exercise 2: Student Processing
```java
class Student {
    String name;
    int marks;
    // constructor, getters, setters
}

// Get students who passed (marks >= 40)
List<Student> passed = students.stream()
    .filter(s -> s.getMarks() >= 40)
    .collect(Collectors.toList());

// Sort by marks descending
List<Student> sorted = students.stream()
    .sorted(Comparator.comparing(Student::getMarks).reversed())
    .collect(Collectors.toList());

// Group by pass/fail
Map<Boolean, List<Student>> grouped = students.stream()
    .collect(Collectors.partitioningBy(s -> s.getMarks() >= 40));
```

## Key Concepts to Remember

1. **Stream doesn't modify source** - Original collection remains unchanged
2. **Lazy evaluation** - Intermediate operations are lazy, terminal operations trigger execution
3. **Method references** - `String::toUpperCase` is cleaner than `s -> s.toUpperCase()`
4. **Optional avoids null checks** - Use `orElse`, `orElseGet`, `ifPresent` instead of null checks
5. **Collectors are powerful** - `toList()`, `toSet()`, `groupingBy()`, `joining()`, etc.

## Common Patterns

```java
// Filter + Map + Collect
list.stream()
    .filter(condition)
    .map(transformation)
    .collect(Collectors.toList());

// Reduce pattern
list.stream()
    .reduce(identity, accumulator);

// GroupingBy pattern
list.stream()
    .collect(Collectors.groupingBy(classifier));

// Joining pattern
list.stream()
    .map(String::valueOf)
    .collect(Collectors.joining(", "));
```
