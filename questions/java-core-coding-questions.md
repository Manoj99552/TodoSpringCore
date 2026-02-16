# Java Core Coding Questions (Easy Level - SDE 1)

## 50 Coding Problems for Service-Based Companies

### String Manipulation (10 questions)

1. **Reverse a String**
```java
// Input: "hello"
// Output: "olleh"
public String reverseString(String str) {
    return new StringBuilder(str).reverse().toString();
}
```

2. **Check if String is Palindrome**
```java
// Input: "madam"
// Output: true
public boolean isPalindrome(String str) {
    int left = 0, right = str.length() - 1;
    while (left < right) {
        if (str.charAt(left++) != str.charAt(right--)) return false;
    }
    return true;
}
```

3. **Count Vowels in a String**
```java
// Input: "hello"
// Output: 2
public int countVowels(String str) {
    int count = 0;
    String vowels = "aeiouAEIOU";
    for (char c : str.toCharArray()) {
        if (vowels.indexOf(c) != -1) count++;
    }
    return count;
}
```

4. **Remove Duplicates from String**
```java
// Input: "programming"
// Output: "progamin"
public String removeDuplicates(String str) {
    StringBuilder result = new StringBuilder();
    for (char c : str.toCharArray()) {
        if (result.indexOf(String.valueOf(c)) == -1) {
            result.append(c);
        }
    }
    return result.toString();
}
```

5. **Check if Two Strings are Anagrams**
```java
// Input: "listen", "silent"
// Output: true
public boolean areAnagrams(String s1, String s2) {
    if (s1.length() != s2.length()) return false;
    char[] arr1 = s1.toCharArray();
    char[] arr2 = s2.toCharArray();
    Arrays.sort(arr1);
    Arrays.sort(arr2);
    return Arrays.equals(arr1, arr2);
}
```

6. **Find First Non-Repeating Character**
```java
// Input: "swiss"
// Output: 'w'
public char firstNonRepeating(String str) {
    Map<Character, Integer> map = new LinkedHashMap<>();
    for (char c : str.toCharArray()) {
        map.put(c, map.getOrDefault(c, 0) + 1);
    }
    for (Map.Entry<Character, Integer> entry : map.entrySet()) {
        if (entry.getValue() == 1) return entry.getKey();
    }
    return '\0';
}
```

7. **Convert String to Title Case**
```java
// Input: "hello world"
// Output: "Hello World"
public String toTitleCase(String str) {
    String[] words = str.split(" ");
    StringBuilder result = new StringBuilder();
    for (String word : words) {
        result.append(Character.toUpperCase(word.charAt(0)))
              .append(word.substring(1).toLowerCase())
              .append(" ");
    }
    return result.toString().trim();
}
```

8. **Count Occurrences of Character**
```java
// Input: "hello", 'l'
// Output: 2
public int countChar(String str, char ch) {
    int count = 0;
    for (char c : str.toCharArray()) {
        if (c == ch) count++;
    }
    return count;
}
```

9. **Replace All Spaces with Underscores**
```java
// Input: "hello world"
// Output: "hello_world"
public String replaceSpaces(String str) {
    return str.replace(' ', '_');
}
```

10. **Check if String Contains Only Digits**
```java
// Input: "12345"
// Output: true
public boolean isNumeric(String str) {
    return str.matches("\\d+");
}
```

### Array Operations (15 questions)

11. **Find Maximum Element in Array**
```java
// Input: [1, 5, 3, 9, 2]
// Output: 9
public int findMax(int[] arr) {
    int max = arr[0];
    for (int num : arr) {
        if (num > max) max = num;
    }
    return max;
}
```

12. **Find Minimum Element in Array**
```java
// Input: [1, 5, 3, 9, 2]
// Output: 1
public int findMin(int[] arr) {
    int min = arr[0];
    for (int num : arr) {
        if (num < min) min = num;
    }
    return min;
}
```

13. **Reverse an Array**
```java
// Input: [1, 2, 3, 4, 5]
// Output: [5, 4, 3, 2, 1]
public void reverseArray(int[] arr) {
    int left = 0, right = arr.length - 1;
    while (left < right) {
        int temp = arr[left];
        arr[left++] = arr[right];
        arr[right--] = temp;
    }
}
```

14. **Sum of Array Elements**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 15
public int sumArray(int[] arr) {
    int sum = 0;
    for (int num : arr) {
        sum += num;
    }
    return sum;
}
```

15. **Find Second Largest Element**
```java
// Input: [1, 5, 3, 9, 2]
// Output: 5
public int secondLargest(int[] arr) {
    int first = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
    for (int num : arr) {
        if (num > first) {
            second = first;
            first = num;
        } else if (num > second && num != first) {
            second = num;
        }
    }
    return second;
}
```

16. **Check if Array is Sorted**
```java
// Input: [1, 2, 3, 4, 5]
// Output: true
public boolean isSorted(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i] > arr[i + 1]) return false;
    }
    return true;
}
```

17. **Remove Duplicates from Sorted Array**
```java
// Input: [1, 1, 2, 2, 3]
// Output: [1, 2, 3]
public int removeDuplicates(int[] arr) {
    if (arr.length == 0) return 0;
    int j = 0;
    for (int i = 1; i < arr.length; i++) {
        if (arr[i] != arr[j]) {
            arr[++j] = arr[i];
        }
    }
    return j + 1;
}
```

18. **Find Missing Number in Array (1 to N)**
```java
// Input: [1, 2, 4, 5] (N=5)
// Output: 3
public int findMissing(int[] arr, int n) {
    int expectedSum = n * (n + 1) / 2;
    int actualSum = 0;
    for (int num : arr) {
        actualSum += num;
    }
    return expectedSum - actualSum;
}
```

19. **Move Zeros to End**
```java
// Input: [0, 1, 0, 3, 12]
// Output: [1, 3, 12, 0, 0]
public void moveZeros(int[] arr) {
    int j = 0;
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != 0) {
            arr[j++] = arr[i];
        }
    }
    while (j < arr.length) {
        arr[j++] = 0;
    }
}
```

20. **Rotate Array by K Positions**
```java
// Input: [1, 2, 3, 4, 5], k=2
// Output: [4, 5, 1, 2, 3]
public void rotateArray(int[] arr, int k) {
    k = k % arr.length;
    reverse(arr, 0, arr.length - 1);
    reverse(arr, 0, k - 1);
    reverse(arr, k, arr.length - 1);
}
private void reverse(int[] arr, int start, int end) {
    while (start < end) {
        int temp = arr[start];
        arr[start++] = arr[end];
        arr[end--] = temp;
    }
}
```

21. **Find Frequency of Each Element**
```java
// Input: [1, 2, 2, 3, 3, 3]
// Output: {1=1, 2=2, 3=3}
public Map<Integer, Integer> findFrequency(int[] arr) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int num : arr) {
        map.put(num, map.getOrDefault(num, 0) + 1);
    }
    return map;
}
```

22. **Check if Array Contains Duplicates**
```java
// Input: [1, 2, 3, 1]
// Output: true
public boolean containsDuplicate(int[] arr) {
    Set<Integer> set = new HashSet<>();
    for (int num : arr) {
        if (!set.add(num)) return true;
    }
    return false;
}
```

23. **Merge Two Sorted Arrays**
```java
// Input: [1, 3, 5], [2, 4, 6]
// Output: [1, 2, 3, 4, 5, 6]
public int[] mergeSorted(int[] arr1, int[] arr2) {
    int[] result = new int[arr1.length + arr2.length];
    int i = 0, j = 0, k = 0;
    while (i < arr1.length && j < arr2.length) {
        result[k++] = arr1[i] < arr2[j] ? arr1[i++] : arr2[j++];
    }
    while (i < arr1.length) result[k++] = arr1[i++];
    while (j < arr2.length) result[k++] = arr2[j++];
    return result;
}
```

24. **Find Pair with Given Sum**
```java
// Input: [1, 2, 3, 4, 5], target=9
// Output: true (4 + 5 = 9)
public boolean hasPairWithSum(int[] arr, int target) {
    Set<Integer> seen = new HashSet<>();
    for (int num : arr) {
        if (seen.contains(target - num)) return true;
        seen.add(num);
    }
    return false;
}
```

25. **Find Average of Array**
```java
// Input: [1, 2, 3, 4, 5]
// Output: 3.0
public double findAverage(int[] arr) {
    double sum = 0;
    for (int num : arr) {
        sum += num;
    }
    return sum / arr.length;
}
```

### Number Operations (10 questions)

26. **Check if Number is Prime**
```java
// Input: 7
// Output: true
public boolean isPrime(int n) {
    if (n <= 1) return false;
    for (int i = 2; i <= Math.sqrt(n); i++) {
        if (n % i == 0) return false;
    }
    return true;
}
```

27. **Find Factorial of a Number**
```java
// Input: 5
// Output: 120
public int factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}
```

28. **Check if Number is Armstrong Number**
```java
// Input: 153
// Output: true (1^3 + 5^3 + 3^3 = 153)
public boolean isArmstrong(int n) {
    int sum = 0, temp = n, digits = String.valueOf(n).length();
    while (temp > 0) {
        int digit = temp % 10;
        sum += Math.pow(digit, digits);
        temp /= 10;
    }
    return sum == n;
}
```

29. **Reverse a Number**
```java
// Input: 12345
// Output: 54321
public int reverseNumber(int n) {
    int reversed = 0;
    while (n != 0) {
        reversed = reversed * 10 + n % 10;
        n /= 10;
    }
    return reversed;
}
```

30. **Check if Number is Palindrome**
```java
// Input: 121
// Output: true
public boolean isPalindromeNumber(int n) {
    return n == reverseNumber(n);
}
```

31. **Find Sum of Digits**
```java
// Input: 12345
// Output: 15
public int sumOfDigits(int n) {
    int sum = 0;
    while (n > 0) {
        sum += n % 10;
        n /= 10;
    }
    return sum;
}
```

32. **Find GCD of Two Numbers**
```java
// Input: 12, 8
// Output: 4
public int gcd(int a, int b) {
    if (b == 0) return a;
    return gcd(b, a % b);
}
```

33. **Find LCM of Two Numbers**
```java
// Input: 12, 8
// Output: 24
public int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}
```

34. **Generate Fibonacci Series**
```java
// Input: 5
// Output: [0, 1, 1, 2, 3]
public List<Integer> fibonacci(int n) {
    List<Integer> list = new ArrayList<>();
    int a = 0, b = 1;
    for (int i = 0; i < n; i++) {
        list.add(a);
        int temp = a + b;
        a = b;
        b = temp;
    }
    return list;
}
```

35. **Count Digits in a Number**
```java
// Input: 12345
// Output: 5
public int countDigits(int n) {
    return String.valueOf(Math.abs(n)).length();
}
```

### Collections & OOP (10 questions)

36. **Find Duplicate Elements in List**
```java
// Input: [1, 2, 3, 2, 4, 3]
// Output: [2, 3]
public List<Integer> findDuplicates(List<Integer> list) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new HashSet<>();
    for (int num : list) {
        if (!seen.add(num)) duplicates.add(num);
    }
    return new ArrayList<>(duplicates);
}
```

37. **Remove Duplicates from List**
```java
// Input: [1, 2, 2, 3, 3, 4]
// Output: [1, 2, 3, 4]
public List<Integer> removeDuplicates(List<Integer> list) {
    return new ArrayList<>(new LinkedHashSet<>(list));
}
```

38. **Sort a List of Strings by Length**
```java
// Input: ["apple", "pie", "banana"]
// Output: ["pie", "apple", "banana"]
public List<String> sortByLength(List<String> list) {
    Collections.sort(list, (a, b) -> a.length() - b.length());
    return list;
}
```

39. **Find Common Elements in Two Lists**
```java
// Input: [1, 2, 3, 4], [3, 4, 5, 6]
// Output: [3, 4]
public List<Integer> findCommon(List<Integer> list1, List<Integer> list2) {
    List<Integer> result = new ArrayList<>(list1);
    result.retainAll(list2);
    return result;
}
```

40. **Swap Keys and Values in Map**
```java
// Input: {1="a", 2="b"}
// Output: {"a"=1, "b"=2}
public Map<String, Integer> swapMap(Map<Integer, String> map) {
    Map<String, Integer> swapped = new HashMap<>();
    for (Map.Entry<Integer, String> entry : map.entrySet()) {
        swapped.put(entry.getValue(), entry.getKey());
    }
    return swapped;
}
```

41. **Group Anagrams Together**
```java
// Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
// Output: [["eat","tea","ate"], ["tan","nat"], ["bat"]]
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String str : strs) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(str);
    }
    return new ArrayList<>(map.values());
}
```

42. **Find Most Frequent Element in List**
```java
// Input: [1, 2, 2, 3, 3, 3]
// Output: 3
public int mostFrequent(List<Integer> list) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : list) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
    }
    return Collections.max(freq.entrySet(), Map.Entry.comparingByValue()).getKey();
}
```

43. **Check if List is Empty or Null**
```java
// Input: null or []
// Output: true
public boolean isEmptyOrNull(List<?> list) {
    return list == null || list.isEmpty();
}
```

44. **Convert List to Array**
```java
// Input: [1, 2, 3]
// Output: int[] {1, 2, 3}
public int[] listToArray(List<Integer> list) {
    return list.stream().mapToInt(Integer::intValue).toArray();
}
```

45. **Convert Array to List**
```java
// Input: int[] {1, 2, 3}
// Output: [1, 2, 3]
public List<Integer> arrayToList(int[] arr) {
    return Arrays.stream(arr).boxed().collect(Collectors.toList());
}
```

### Pattern Printing & Loops (5 questions)

46. **Print Multiplication Table**
```java
// Input: 5
// Output: 5x1=5, 5x2=10, ..., 5x10=50
public void printTable(int n) {
    for (int i = 1; i <= 10; i++) {
        System.out.println(n + " x " + i + " = " + (n * i));
    }
}
```

47. **Print Right-Angled Triangle Pattern**
```java
// Input: 5
// Output:
// *
// **
// ***
// ****
// *****
public void printTriangle(int n) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
```

48. **Print Pyramid Pattern**
```java
// Input: 5
// Output:
//     *
//    ***
//   *****
//  *******
// *********
public void printPyramid(int n) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n - i; j++) System.out.print(" ");
        for (int k = 1; k <= 2 * i - 1; k++) System.out.print("*");
        System.out.println();
    }
}
```

49. **Print Number Pattern**
```java
// Input: 5
// Output:
// 1
// 12
// 123
// 1234
// 12345
public void printNumberPattern(int n) {
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= i; j++) {
            System.out.print(j);
        }
        System.out.println();
    }
}
```

50. **Sum of First N Natural Numbers**
```java
// Input: 10
// Output: 55
public int sumOfN(int n) {
    return n * (n + 1) / 2;
}
```

---

## Practice Tips

1. **Write code without IDE help** - Practice on paper or whiteboard
2. **Explain your approach** - Talk through your logic before coding
3. **Test with edge cases** - Empty arrays, null values, negative numbers
4. **Optimize after working solution** - Get it working first, then optimize
5. **Practice time complexity** - Understand Big O notation for your solutions

## Common Time Complexities

- **O(1)** - Constant: Direct access, arithmetic operations
- **O(n)** - Linear: Single loop through array
- **O(n²)** - Quadratic: Nested loops
- **O(log n)** - Logarithmic: Binary search, divide and conquer
- **O(n log n)** - Linearithmic: Efficient sorting (merge sort, quick sort)
