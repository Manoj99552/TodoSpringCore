# 09 - Service Layer

## Simple Explanation (Like You're 10)

Imagine a **restaurant manager**:

- **Customer** says: "I want a burger"
- **Manager** checks:
  - Do we have ingredients? ✓
  - Is the kitchen open? ✓
  - Is this a valid order? ✓
- **Manager** tells **Chef** (DAO): "Make a burger"
- **Chef** makes the burger
- **Manager** gives burger to customer

**The Service Layer is like the manager** - it makes decisions and coordinates!

---

## What is the Service Layer?

The Service layer contains **business logic** - the rules and decisions of your application.

**Business Logic** = The "what" and "why" of your application:
- What can users do?
- What rules must be followed?
- What validations are needed?
- What happens when something goes wrong?

---

## Service Layer in Our Project

### Files
1. **TodoService.java** - Interface (what operations are available)
2. **TodoServiceImpl.java** - Implementation (how to perform them)

### Location
```
src/main/java/com/todo/service/
├── TodoService.java
└── TodoServiceImpl.java
```

---

## Part 1: TodoService Interface

### Purpose
Defines **what** business operations we can perform.

### Code
```java
public interface TodoService {
    void addTodo(Todo todo);
    Todo getTodoById(int id);
    List<Todo> getAllTodos();
    void updateTodo(Todo todo);
    void deleteTodo(int id);
    List<Todo> getTodosByStatus(boolean completed);
    void markAsCompleted(int id);
    void markAsIncomplete(int id);
}
```

### Why an Interface?

Same reasons as DAO layer:
1. **Flexibility** - Can have different implementations
2. **Testing** - Can create fake service for testing
3. **Loose coupling** - Main depends on interface, not implementation

---

## Part 2: TodoServiceImpl Implementation

### Key Components

#### 1. @Service Annotation
```java
@Service
public class TodoServiceImpl implements TodoService {
    // ...
}
```

**What it does:**
- Tells Spring: "This is a Service component"
- Spring automatically creates an instance
- Marks this as business logic layer

#### 2. @Transactional Annotation
```java
@Transactional
public class TodoServiceImpl implements TodoService {
    // ...
}
```

**What it does:**
- All methods run in a database transaction
- If method fails, all database changes are rolled back
- Ensures data consistency

**Example:**
```java
@Transactional
public void transferTodo(int fromUserId, int toUserId, int todoId) {
    // Both must succeed or both must fail
    removeTodoFromUser(fromUserId, todoId);  // Step 1
    addTodoToUser(toUserId, todoId);         // Step 2
}
```

If Step 2 fails, Step 1 is automatically undone!

#### 3. Dependency Injection
```java
private final TodoDAO todoDAO;

public TodoServiceImpl(TodoDAO todoDAO) {
    this.todoDAO = todoDAO;
}
```

**What happens:**
- Spring injects TodoDAO automatically
- Service uses DAO for all database operations
- Service never accesses database directly!

---

## Business Logic Examples

### 1. Validation

```java
@Override
public void addTodo(Todo todo) {
    // BUSINESS LOGIC: Validate
    if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
        throw new IllegalArgumentException("Todo title cannot be empty");
    }
    
    // Delegate to DAO
    todoDAO.create(todo);
    
    System.out.println("✓ Todo created successfully: " + todo.getTitle());
}
```

**What it does:**
- Checks if title is empty
- Throws exception if invalid
- Only creates todo if valid

**Why in Service, not DAO?**
- Validation is a business rule
- DAO should only handle database operations
- Business rules might change, database operations don't

---

### 2. Existence Checks

```java
@Override
public void updateTodo(Todo todo) {
    // Validate title
    if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
        throw new IllegalArgumentException("Todo title cannot be empty");
    }
    
    // BUSINESS LOGIC: Check if todo exists
    Todo existingTodo = todoDAO.findById(todo.getId());
    if (existingTodo == null) {
        throw new IllegalArgumentException("Todo with id " + todo.getId() + " not found");
    }
    
    // Update in database
    todoDAO.update(todo);
    
    System.out.println("✓ Todo updated successfully: " + todo.getTitle());
}
```

**What it does:**
- Validates title
- Checks if todo exists before updating
- Prevents updating non-existent todos

---

### 3. Coordinating Multiple Operations

```java
@Override
public void markAsCompleted(int id) {
    // BUSINESS LOGIC: Combine multiple operations
    
    // Step 1: Find the todo
    Todo todo = todoDAO.findById(id);
    if (todo == null) {
        throw new IllegalArgumentException("Todo with id " + id + " not found");
    }
    
    // Step 2: Update the status
    todo.setCompleted(true);
    
    // Step 3: Save to database
    todoDAO.update(todo);
    
    System.out.println("✓ Todo marked as completed: " + todo.getTitle());
}
```

**What it does:**
- Finds todo
- Changes status
- Saves changes

**Why in Service?**
- Combines multiple DAO operations
- Applies business logic (what it means to "complete" a todo)
- DAO just executes individual operations

---

## Service vs DAO - What Goes Where?

### DAO Layer (Database Operations)
```java
// DAO: Just execute SQL
public void create(Todo todo) {
    jdbcTemplate.update("INSERT INTO todos ...", ...);
}

public Todo findById(int id) {
    return jdbcTemplate.queryForObject("SELECT * FROM todos WHERE id = ?", ...);
}
```

**Responsibilities:**
- Execute SQL queries
- Convert database rows to objects
- Handle database connections
- **No validation, no business logic!**

---

### Service Layer (Business Logic)
```java
// Service: Validate, check, coordinate
public void addTodo(Todo todo) {
    // Validate
    if (todo.getTitle().isEmpty()) {
        throw new Exception("Invalid");
    }
    
    // Check business rules
    if (getTodoCount() >= MAX_TODOS) {
        throw new Exception("Too many todos");
    }
    
    // Delegate to DAO
    todoDAO.create(todo);
}
```

**Responsibilities:**
- Validate input
- Apply business rules
- Check permissions
- Coordinate multiple DAO operations
- Manage transactions
- **No direct database access!**

---

## Transaction Management

### What is a Transaction?

A group of operations that must **all succeed or all fail together**.

**Example: Bank Transfer**
```java
@Transactional
public void transferMoney(int fromAccount, int toAccount, int amount) {
    deductMoney(fromAccount, amount);  // Step 1
    addMoney(toAccount, amount);       // Step 2
}
```

**Without @Transactional:**
```
Step 1 succeeds: $100 deducted from Account A
Step 2 fails: Error! Money NOT added to Account B
Result: $100 disappeared! 💸
```

**With @Transactional:**
```
Step 1 succeeds: $100 deducted from Account A
Step 2 fails: Error!
Spring automatically rolls back Step 1
Result: $100 still in Account A ✅
```

---

### How @Transactional Works

```java
@Service
@Transactional  // All methods are transactional
public class TodoServiceImpl {
    
    public void addTodo(Todo todo) {
        // Spring starts transaction
        todoDAO.create(todo);
        // Spring commits transaction (if no errors)
        // OR rolls back (if error occurs)
    }
}
```

**What Spring does:**
1. **Before method:** Start transaction
2. **During method:** Execute code
3. **After method (success):** Commit transaction (save changes)
4. **After method (error):** Rollback transaction (undo changes)

---

## Error Handling

### Throwing Exceptions

```java
public void addTodo(Todo todo) {
    if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
        throw new IllegalArgumentException("Todo title cannot be empty");
    }
    
    todoDAO.create(todo);
}
```

**What happens:**
1. Validation fails
2. Exception is thrown
3. Method stops executing
4. Transaction is rolled back
5. Error message is returned to caller

---

### Try-Catch (Optional)

```java
public void addTodo(Todo todo) {
    try {
        if (todo.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title empty");
        }
        todoDAO.create(todo);
    } catch (Exception e) {
        System.err.println("Error creating todo: " + e.getMessage());
        throw e;  // Re-throw to trigger rollback
    }
}
```

---

## Service Layer Best Practices

### 1. Keep Service Focused on Business Logic

**Good:**
```java
public void addTodo(Todo todo) {
    // Validation (business logic)
    if (todo.getTitle().isEmpty()) {
        throw new Exception("Invalid");
    }
    
    // Delegate to DAO
    todoDAO.create(todo);
}
```

**Bad:**
```java
public void addTodo(Todo todo) {
    // SQL in Service! (Should be in DAO)
    String sql = "INSERT INTO todos ...";
    jdbcTemplate.update(sql, ...);
}
```

---

### 2. Always Validate Input

```java
public void addTodo(Todo todo) {
    // Check for null
    if (todo == null) {
        throw new IllegalArgumentException("Todo cannot be null");
    }
    
    // Check title
    if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
        throw new IllegalArgumentException("Title cannot be empty");
    }
    
    // Check title length
    if (todo.getTitle().length() > 255) {
        throw new IllegalArgumentException("Title too long");
    }
    
    todoDAO.create(todo);
}
```

---

### 3. Use Transactions

```java
@Transactional  // Ensures data consistency
public void updateMultipleTodos(List<Todo> todos) {
    for (Todo todo : todos) {
        todoDAO.update(todo);
    }
    // All updates succeed or all fail together
}
```

---

## Complete Flow Example

```
Main.java calls:
  todoService.addTodo(todo)
    ↓
Spring intercepts (because of @Transactional)
    ↓
Spring starts database transaction
    ↓
TodoServiceImpl.addTodo() executes:
  1. Validates title (business logic)
  2. Calls todoDAO.create(todo)
    ↓
TodoDAOImpl.create() executes:
  1. Executes SQL INSERT
    ↓
TodoDAOImpl returns to TodoServiceImpl
    ↓
TodoServiceImpl completes successfully
    ↓
Spring commits transaction
    ↓
Changes saved to database
    ↓
Returns to Main.java
```

---

## Key Takeaways

1. ✅ **Service Layer** = Business logic and rules
2. ✅ **@Service** tells Spring to manage this class
3. ✅ **@Transactional** ensures data consistency
4. ✅ **Validate input** before calling DAO
5. ✅ **Check business rules** (permissions, limits, etc.)
6. ✅ **Coordinate operations** (combine multiple DAO calls)
7. ✅ **Never access database directly** - always use DAO!

---

## What's Next?

Now let's see how the Main class ties everything together!

**Next: [10-MainClass.md](10-MainClass.md) →**
