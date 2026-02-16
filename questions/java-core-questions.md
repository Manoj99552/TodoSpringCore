# Java Core Interview Questions (Easy Level - SDE 1)

## 50 Questions for Service-Based Companies

### Basics & Fundamentals

1. **What is Java?**
   - Java is a high-level, object-oriented programming language that is platform-independent and follows the "Write Once, Run Anywhere" principle.

2. **What is JVM, JRE, and JDK?**
   - JVM: Java Virtual Machine - executes Java bytecode
   - JRE: Java Runtime Environment - provides libraries and JVM to run Java applications
   - JDK: Java Development Kit - includes JRE plus development tools like compiler

3. **What are the main features of Java?**
   - Object-oriented, Platform-independent, Simple, Secure, Multithreaded, Architecture-neutral, Portable, Robust

4. **What is the difference between JDK and JRE?**
   - JDK is for development (includes compiler, debugger), JRE is for running Java applications only

5. **What is bytecode in Java?**
   - Bytecode is the intermediate representation of Java code that JVM executes. It's platform-independent.

### Object-Oriented Programming

6. **What are the four pillars of OOP?**
   - Encapsulation, Inheritance, Polymorphism, Abstraction

7. **What is a class in Java?**
   - A class is a blueprint or template that defines the properties and behaviors of objects.

8. **What is an object in Java?**
   - An object is an instance of a class that has state and behavior.

9. **What is encapsulation?**
   - Encapsulation is wrapping data and methods together and restricting direct access using access modifiers.

10. **What is inheritance?**
    - Inheritance is a mechanism where one class acquires properties and methods of another class.

11. **What is polymorphism?**
    - Polymorphism means "many forms" - same method name with different implementations (method overloading and overriding).

12. **What is abstraction?**
    - Abstraction is hiding implementation details and showing only essential features to the user.

13. **What is the difference between abstract class and interface?**
    - Abstract class can have both abstract and concrete methods; Interface (before Java 8) has only abstract methods.

14. **Can we create an object of an abstract class?**
    - No, we cannot instantiate an abstract class directly.

15. **What is method overloading?**
    - Method overloading is having multiple methods with the same name but different parameters in the same class.

### Data Types & Variables

16. **What are primitive data types in Java?**
    - byte, short, int, long, float, double, char, boolean

17. **What is the difference between int and Integer?**
    - int is a primitive type; Integer is a wrapper class (object).

18. **What is autoboxing and unboxing?**
    - Autoboxing: automatic conversion from primitive to wrapper class
    - Unboxing: automatic conversion from wrapper class to primitive

19. **What is a String in Java?**
    - String is an immutable sequence of characters stored as an object.

20. **What is the difference between String, StringBuilder, and StringBuffer?**
    - String: immutable
    - StringBuilder: mutable, not thread-safe
    - StringBuffer: mutable, thread-safe

### Collections Framework

21. **What is the Collections Framework?**
    - A unified architecture for representing and manipulating collections of objects.

22. **What is the difference between ArrayList and LinkedList?**
    - ArrayList: dynamic array, fast random access
    - LinkedList: doubly-linked list, fast insertion/deletion

23. **What is the difference between List and Set?**
    - List: allows duplicates, maintains insertion order
    - Set: no duplicates, no guaranteed order

24. **What is a HashMap?**
    - HashMap stores key-value pairs and allows null keys and values. It's not synchronized.

25. **What is the difference between HashMap and HashTable?**
    - HashMap: not synchronized, allows null
    - HashTable: synchronized, doesn't allow null

26. **What is the difference between ArrayList and Vector?**
    - ArrayList: not synchronized, faster
    - Vector: synchronized, slower

27. **What is an Iterator?**
    - Iterator is an interface used to traverse collections and remove elements.

28. **What is the difference between Iterator and ListIterator?**
    - Iterator: forward direction only
    - ListIterator: bidirectional, only for List

### Exception Handling

29. **What is an exception?**
    - An exception is an event that disrupts the normal flow of program execution.

30. **What is the difference between checked and unchecked exceptions?**
    - Checked: must be handled at compile time (IOException)
    - Unchecked: runtime exceptions (NullPointerException)

31. **What is the try-catch-finally block?**
    - try: code that may throw exception
    - catch: handles the exception
    - finally: always executes, used for cleanup

32. **Can we have try without catch?**
    - Yes, we can have try with finally block.

33. **What is the throw keyword?**
    - throw is used to explicitly throw an exception.

34. **What is the throws keyword?**
    - throws is used in method signature to declare exceptions that method might throw.

### Multithreading Basics

35. **What is a thread?**
    - A thread is a lightweight process, the smallest unit of execution.

36. **How can you create a thread in Java?**
    - By extending Thread class or implementing Runnable interface.

37. **What is the difference between Thread and Runnable?**
    - Thread is a class; Runnable is an interface (preferred for flexibility).

38. **What is synchronization?**
    - Synchronization is controlling access of multiple threads to shared resources.

39. **What is the difference between sleep() and wait()?**
    - sleep(): pauses thread for specified time, doesn't release lock
    - wait(): waits until notify() is called, releases lock

### Keywords & Modifiers

40. **What is the static keyword?**
    - static makes members belong to the class rather than instances.

41. **What is the final keyword?**
    - final variable: constant
    - final method: cannot be overridden
    - final class: cannot be extended

42. **What is the this keyword?**
    - this refers to the current object instance.

43. **What is the super keyword?**
    - super refers to the parent class object.

44. **What are access modifiers in Java?**
    - public, private, protected, default (package-private)

### Constructors & Methods

45. **What is a constructor?**
    - A constructor is a special method used to initialize objects, with the same name as the class.

46. **What is constructor overloading?**
    - Having multiple constructors with different parameters in the same class.

47. **Can a constructor be private?**
    - Yes, used in Singleton pattern to prevent instantiation.

48. **What is method overriding?**
    - Redefining a parent class method in the child class with the same signature.

### Miscellaneous

49. **What is garbage collection?**
    - Automatic memory management that removes objects that are no longer referenced.

50. **What is the main method signature in Java?**
    - `public static void main(String[] args)` - entry point of Java application.
