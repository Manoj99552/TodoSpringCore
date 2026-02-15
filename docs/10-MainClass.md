# 10 - Main Class (Entry Point)

## Simple Explanation (Like You're 10)

Imagine you're the **director of a play**:

1. **Set up the stage** (Load Spring)
2. **Get the actors ready** (Get beans from Spring)
3. **Perform the play** (Run CRUD operations)
4. **Clean up after** (Close Spring)

**Main.java is the director** - it starts everything and coordinates the show!

---

## What is Main.java?

**Main.java** is the **entry point** of the application - where execution begins.

### Key Points
- Contains the `main()` method
- JVM (Java Virtual Machine) looks for this method to start the program
- Loads Spring context
- Demonstrates CRUD operations
- In a real application, this would be replaced by a web controller or REST API

---

## Location
```
src/main/java/com/todo/Main.java
```

---

## Breaking Down Main.java

### 1. The main() Method

```java
public static void main(String[] args) {
    // Program starts here!
}
```

**What is main()?**
- The entry point of any Java application
- `public` - Can be called from anywhere
- `static` - Can be called without creating an object
- `void` - Doesn't return anything
- `String[] args` - Command-line arguments (not used in our app)

---

### 2. Load Spring Context

```java
ApplicationContext context = 
    new ClassPathXmlApplicationContext("applicationContext.xml");
```

**What happens:**
1. Spring reads `applicationContext.xml`
2. Spring loads `database.properties`
3. Spring creates all beans (DataSource, JdbcTemplate, DAO, Service)
4. Spring injects dependencies
5. Everything is ready to use!

**ApplicationContext:**
- The Spring IoC Container
- Manages all beans
- Handles dependency injection
- Controls bean lifecycle

**ClassPathXmlApplicationContext:**
- Loads configuration from XML file
- Looks for file in `src/main/resources/`

---

### 3. Get Service Bean

```java
TodoService todoService = context.getBean(TodoService.class);
```

**What happens:**
1. Ask Spring: "Give me a bean of type TodoService"
2. Spring looks in its container
3. Spring finds: TodoServiceImpl (implements TodoService)
4. Spring returns the fully configured instance
5. TodoService is ready with TodoDAO already injected!

**Why getBean()?**
- We don't create objects ourselves
- Spring created them during context loading
- We just ask Spring for what we need

---

### 4. Perform CRUD Operations

#### CREATE - Add New Todos
```java
Todo todo1 = new Todo("Learn Spring Core", "Study IoC and DI", false);
todoService.addTodo(todo1);
```

**What happens:**
1. Create a new Todo object
2. Call service to add it
3. Service validates
4. Service calls DAO
5. DAO executes SQL INSERT
6. Todo saved to database

#### READ - Get All Todos
```java
List<Todo> allTodos = todoService.getAllTodos();
for (Todo todo : allTodos) {
    System.out.println(todo);
}
```

**What happens:**
1. Call service to get all todos
2. Service calls DAO
3. DAO executes SQL SELECT
4. DAO converts rows to Todo objects
5. Returns List<Todo>
6. Print each todo

#### READ - Get Todo by ID
```java
Todo foundTodo = todoService.getTodoById(1);
if (foundTodo != null) {
    System.out.println("Found todo: " + foundTodo);
}
```

**What happens:**
1. Call service with ID
2. Service calls DAO
3. DAO executes SQL SELECT WHERE id = 1
4. Returns Todo object or null
5. Check if found and print

#### UPDATE - Modify a Todo
```java
foundTodo.setTitle("Learn Spring Core Framework");
foundTodo.setDescription("Master IoC, DI, and Spring JDBC");
todoService.updateTodo(foundTodo);
```

**What happens:**
1. Modify the Todo object
2. Call service to update
3. Service validates
4. Service checks if todo exists
5. Service calls DAO
6. DAO executes SQL UPDATE
7. Changes saved to database

#### DELETE - Remove a Todo
```java
todoService.deleteTodo(3);
```

**What happens:**
1. Call service with ID to delete
2. Service checks if todo exists
3. Service calls DAO
4. DAO executes SQL DELETE
5. Todo removed from database

---

### 5. Custom Operations

#### Get Todos by Status
```java
List<Todo> completedTodos = todoService.getTodosByStatus(true);
List<Todo> incompleteTodos = todoService.getTodosByStatus(false);
```

**What it does:**
- Filter todos by completion status
- Demonstrates custom query methods

#### Mark as Completed
```java
todoService.markAsCompleted(1);
```

**What it does:**
1. Find todo by ID
2. Set completed = true
3. Update in database

**Why a separate method?**
- Common operation
- Encapsulates business logic
- Easier to use than manual update

---

### 6. Close Spring Context

```java
((ClassPathXmlApplicationContext) context).close();
```

**What happens:**
1. Spring destroys all beans
2. Closes database connections
3. Releases resources
4. Application exits cleanly

**Why important?**
- Prevents resource leaks
- Closes database connections properly
- Good practice

---

## Complete Execution Flow

```
1. JVM starts
   ↓
2. Finds main() method in Main.java
   ↓
3. Executes main()
   ↓
4. Loads Spring context
   - Reads applicationContext.xml
   - Creates all beans
   - Injects dependencies
   ↓
5. Gets TodoService bean from Spring
   ↓
6. Performs CRUD operations
   - Create todos
   - Read todos
   - Update todos
   - Delete todos
   ↓
7. Closes Spring context
   ↓
8. Application exits
```

---

## Why Main.java is Simple

Notice that Main.java:
- ✅ Doesn't create DAO or Service objects
- ✅ Doesn't manage database connections
- ✅ Doesn't handle transactions
- ✅ Just uses the service layer

**Why?**
- Spring handles all the complexity
- Dependency injection provides what we need
- We focus on using the application, not building it

---

## In a Real Application

### Console App (What we have)
```java
public class Main {
    public static void main(String[] args) {
        // Load Spring
        // Use service
        // Close Spring
    }
}
```

### Web Application (REST API)
```java
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }
    
    @PostMapping
    public void createTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
    }
}
```

**Difference:**
- Console app: Main.java calls service directly
- Web app: Controller receives HTTP requests and calls service
- **Same service layer!** Just different entry point

---

## Modifying Main.java

### Add More Todos
```java
Todo todo4 = new Todo("Read Documentation", "Study all docs files", false);
todoService.addTodo(todo4);
```

### Search by Title
```java
// First, add method to Service and DAO
List<Todo> todos = todoService.findByTitle("Spring");
```

### Bulk Operations
```java
List<Todo> todosToCreate = Arrays.asList(
    new Todo("Task 1", "Description 1", false),
    new Todo("Task 2", "Description 2", false),
    new Todo("Task 3", "Description 3", false)
);

for (Todo todo : todosToCreate) {
    todoService.addTodo(todo);
}
```

---

## Common Patterns in Main.java

### Pattern 1: Try-Catch for Error Handling
```java
try {
    Todo todo = new Todo("", "Empty title", false);
    todoService.addTodo(todo);
} catch (IllegalArgumentException e) {
    System.err.println("Error: " + e.getMessage());
}
```

### Pattern 2: User Input (Scanner)
```java
Scanner scanner = new Scanner(System.in);
System.out.print("Enter todo title: ");
String title = scanner.nextLine();

Todo todo = new Todo(title, "", false);
todoService.addTodo(todo);
```

### Pattern 3: Menu System
```java
while (true) {
    System.out.println("1. Add Todo");
    System.out.println("2. View Todos");
    System.out.println("3. Exit");
    
    int choice = scanner.nextInt();
    
    switch (choice) {
        case 1:
            // Add todo
            break;
        case 2:
            // View todos
            break;
        case 3:
            return;
    }
}
```

---

## Debugging Tips

### Print Spring Beans
```java
String[] beanNames = context.getBeanDefinitionNames();
for (String beanName : beanNames) {
    System.out.println("Bean: " + beanName);
}
```

### Check Bean Type
```java
TodoService service = context.getBean(TodoService.class);
System.out.println("Service class: " + service.getClass().getName());
// Prints: com.todo.service.TodoServiceImpl
```

### Verify Database Connection
```java
try {
    List<Todo> todos = todoService.getAllTodos();
    System.out.println("Database connected! Found " + todos.size() + " todos");
} catch (Exception e) {
    System.err.println("Database connection failed: " + e.getMessage());
}
```

---

## Key Takeaways

1. ✅ **main()** is the entry point of the application
2. ✅ **ApplicationContext** loads Spring configuration
3. ✅ **getBean()** retrieves beans from Spring
4. ✅ **Service layer** handles all business logic
5. ✅ **Main.java** just coordinates - doesn't create objects
6. ✅ **close()** cleans up resources when done
7. ✅ **Simple and clean** - Spring handles complexity

---

## What's Next?

Now let's understand how Spring is configured!

**Next: [11-SpringConfiguration.md](11-SpringConfiguration.md) →**
