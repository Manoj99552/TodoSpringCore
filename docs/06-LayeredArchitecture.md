# 06 - Layered Architecture

## Simple Explanation (Like You're 10)

Imagine a **restaurant**:

1. **Customer** (You) → Orders food
2. **Waiter** (Service Layer) → Takes order, handles special requests
3. **Chef** (DAO Layer) → Prepares the food
4. **Ingredients** (Database) → Raw materials

Each person has a **specific job**. The customer doesn't go to the kitchen, and the chef doesn't take orders!

**This is layered architecture** - each layer has its own responsibility!

---

## What is Layered Architecture?

It's a way of organizing code into **separate layers**, where:
- Each layer has a **specific purpose**
- Layers only talk to the layer **directly next to them**
- Changes in one layer don't break other layers

---

## The Three Layers in Our Project

```
┌─────────────────────────────────────┐
│         Main.java                   │  ← Entry Point
│    (Presentation/Client Layer)      │
└──────────────┬──────────────────────┘
               │ uses
               ▼
┌─────────────────────────────────────┐
│      Service Layer                  │  ← Business Logic
│   TodoService / TodoServiceImpl     │
│                                     │
│   - Validates data                  │
│   - Applies business rules          │
│   - Manages transactions            │
└──────────────┬──────────────────────┘
               │ uses
               ▼
┌─────────────────────────────────────┐
│       DAO Layer                     │  ← Data Access
│    TodoDAO / TodoDAOImpl            │
│                                     │
│   - Executes SQL queries            │
│   - Converts data to objects        │
│   - Handles database operations     │
└──────────────┬──────────────────────┘
               │ uses
               ▼
┌─────────────────────────────────────┐
│       Model Layer                   │  ← Data Structure
│          Todo.java                  │
│                                     │
│   - Represents a todo item          │
│   - Just data (fields + getters)    │
└──────────────┬──────────────────────┘
               │ stored in
               ▼
┌─────────────────────────────────────┐
│        Database                     │
│      (MySQL - tododb)               │
│                                     │
│   - Stores data permanently         │
└─────────────────────────────────────┘
```

---

## Layer 1: Model Layer (Data Structure)

### Purpose
Represents the **data structure** - what a Todo item looks like.

### File
- `Todo.java`

### Responsibility
- Define what fields a Todo has (id, title, description, completed)
- Provide getters and setters
- **That's it!** No logic, no database code, just data structure

### Real-World Analogy
A **blueprint** of a house. It shows what rooms exist, but doesn't build the house.

### Code Example
```java
public class Todo {
    private int id;
    private String title;
    private String description;
    private boolean completed;
    
    // Getters and setters
}
```

### Key Point
The Model layer is **dumb** - it just holds data, no intelligence!

---

## Layer 2: DAO Layer (Data Access Object)

### Purpose
Handles **all database operations** - saving, reading, updating, deleting data.

### Files
- `TodoDAO.java` (interface - what operations are available)
- `TodoDAOImpl.java` (implementation - how to do those operations)

### Responsibility
- Execute SQL queries
- Convert database rows to Java objects
- Convert Java objects to database rows
- **Only** database operations, no business logic!

### Real-World Analogy
A **librarian** who knows how to:
- Find books on shelves (SELECT)
- Add new books (INSERT)
- Update book information (UPDATE)
- Remove books (DELETE)

But the librarian doesn't decide **which** books to buy or **why** - that's not their job!

### Code Example
```java
@Repository
public class TodoDAOImpl implements TodoDAO {
    private JdbcTemplate jdbcTemplate;
    
    public void create(Todo todo) {
        String sql = "INSERT INTO todos (title, description, completed) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, todo.getTitle(), todo.getDescription(), todo.isCompleted());
    }
    
    public List<Todo> findAll() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, new TodoRowMapper());
    }
}
```

### Key Point
DAO layer **only** talks to the database. It doesn't validate data or apply business rules!

---

## Layer 3: Service Layer (Business Logic)

### Purpose
Contains **business logic** - the rules and decisions of your application.

### Files
- `TodoService.java` (interface)
- `TodoServiceImpl.java` (implementation)

### Responsibility
- Validate data (e.g., "title cannot be empty")
- Apply business rules (e.g., "cannot delete completed todos")
- Coordinate multiple DAO operations
- Manage transactions
- **No direct database access!** Uses DAO layer

### Real-World Analogy
A **manager** who:
- Decides what to do
- Checks if requests are valid
- Tells the librarian (DAO) what to do
- Handles special cases

### Code Example
```java
@Service
@Transactional
public class TodoServiceImpl implements TodoService {
    private TodoDAO todoDAO;
    
    public void addTodo(Todo todo) {
        // BUSINESS LOGIC: Validate
        if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        
        // Delegate to DAO for database operation
        todoDAO.create(todo);
    }
}
```

### Key Point
Service layer makes **decisions** and uses DAO to execute them!

---

## Layer 4: Presentation/Client Layer

### Purpose
The **entry point** - where the user interacts with the application.

### File
- `Main.java` (in our console app)
- (In web apps, this would be Controllers)

### Responsibility
- Get input (in our case, hardcoded todos)
- Call service layer methods
- Display results
- **No business logic!** **No database access!**

### Code Example
```java
public class Main {
    public static void main(String[] args) {
        // Get service from Spring
        TodoService service = context.getBean(TodoService.class);
        
        // Use service methods
        Todo todo = new Todo("Learn Spring", "Study IoC", false);
        service.addTodo(todo);
        
        List<Todo> todos = service.getAllTodos();
        for (Todo t : todos) {
            System.out.println(t);
        }
    }
}
```

### Key Point
Main just **uses** the service layer. It doesn't know about database or business rules!

---

## Why Use Layers?

### 1. Separation of Concerns
Each layer has **one job**:
- Model: Data structure
- DAO: Database operations
- Service: Business logic
- Main: User interaction

### 2. Easy to Change
Want to switch from MySQL to PostgreSQL?
- **Only change**: DAO layer
- **Don't touch**: Service, Model, Main

Want to add validation?
- **Only change**: Service layer
- **Don't touch**: DAO, Model, Main

### 3. Easy to Test
Test each layer independently:
```java
// Test service with a fake DAO
TodoDAO fakeDAO = new FakeTodoDAO();
TodoService service = new TodoServiceImpl(fakeDAO);
service.addTodo(todo);  // No real database needed!
```

### 4. Team Collaboration
Different developers can work on different layers:
- Developer A: Works on DAO (database expert)
- Developer B: Works on Service (business logic expert)
- Developer C: Works on UI (frontend expert)

### 5. Reusability
The same Service layer can be used by:
- Console application (Main.java)
- Web application (REST API)
- Mobile app backend
- Desktop application

---

## Communication Between Layers

### Rule: Only Talk to Adjacent Layers

✅ **Correct:**
```
Main → Service → DAO → Database
```

❌ **Wrong:**
```
Main → DAO directly (skipping Service)
Service → Database directly (skipping DAO)
```

### Why?
If Main talks directly to DAO:
- No validation happens
- No business logic applied
- Hard to maintain
- Defeats the purpose of layers!

---

## Data Flow Example

Let's trace what happens when you create a todo:

```
1. Main.java
   ↓
   Creates: new Todo("Learn Spring", "Study IoC", false)
   Calls: service.addTodo(todo)
   
2. TodoServiceImpl.java
   ↓
   Validates: Is title empty? No → OK
   Calls: todoDAO.create(todo)
   
3. TodoDAOImpl.java
   ↓
   Executes SQL: INSERT INTO todos VALUES (...)
   
4. Database
   ↓
   Stores the data
   
5. Response flows back
   ↓
   Database → DAO → Service → Main
   Main displays: "✓ Todo created successfully"
```

---

## Interfaces vs Implementations

### Why Use Interfaces?

Each layer has an **interface** (contract) and **implementation** (actual code):

```java
// Interface (contract)
public interface TodoService {
    void addTodo(Todo todo);
}

// Implementation (actual code)
@Service
public class TodoServiceImpl implements TodoService {
    public void addTodo(Todo todo) {
        // actual implementation
    }
}
```

### Benefits

1. **Loose Coupling**
   ```java
   // Service depends on interface, not implementation
   private TodoDAO todoDAO;  // Can be ANY implementation
   ```

2. **Easy to Switch**
   ```java
   // Can switch implementations without changing code
   TodoDAO dao = new TodoDAOImpl();        // MySQL
   TodoDAO dao = new TodoDAOMongoImpl();   // MongoDB
   TodoDAO dao = new FakeTodoDAO();        // Testing
   ```

3. **Professional Standard**
   - Industry best practice
   - Makes code flexible
   - Easier to maintain

---

## Real-World Example

### E-Commerce Application

```
┌─────────────────────────────────────┐
│   Web Controller (Presentation)     │
│   - Handles HTTP requests           │
│   - Returns JSON responses          │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Order Service                  │
│   - Validate order                  │
│   - Check inventory                 │
│   - Calculate total price           │
│   - Apply discount rules            │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Order DAO                      │
│   - Save order to database          │
│   - Retrieve order history          │
│   - Update order status             │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Database                       │
│   - orders table                    │
│   - products table                  │
│   - customers table                 │
└─────────────────────────────────────┘
```

Same pattern, different domain!

---

## Common Mistakes

### ❌ Mistake 1: Putting Business Logic in DAO
```java
// WRONG!
public class TodoDAOImpl {
    public void create(Todo todo) {
        if (todo.getTitle().isEmpty()) {  // Validation in DAO!
            throw new Exception("Title empty");
        }
        // SQL code...
    }
}
```

**Why wrong?** DAO should only handle database, not validate!

### ❌ Mistake 2: Accessing Database from Service
```java
// WRONG!
public class TodoServiceImpl {
    public void addTodo(Todo todo) {
        String sql = "INSERT INTO...";  // SQL in Service!
        jdbcTemplate.update(sql);
    }
}
```

**Why wrong?** Service should use DAO, not access database directly!

### ❌ Mistake 3: Business Logic in Main
```java
// WRONG!
public class Main {
    public static void main(String[] args) {
        if (todo.getTitle().isEmpty()) {  // Validation in Main!
            System.out.println("Error");
        }
        service.addTodo(todo);
    }
}
```

**Why wrong?** Main should just call service, not validate!

---

## Key Takeaways

1. ✅ **Model Layer** = Data structure (Todo.java)
2. ✅ **DAO Layer** = Database operations (TodoDAOImpl.java)
3. ✅ **Service Layer** = Business logic (TodoServiceImpl.java)
4. ✅ **Presentation Layer** = User interaction (Main.java)
5. ✅ **Each layer has ONE responsibility**
6. ✅ **Layers only talk to adjacent layers**
7. ✅ **Use interfaces for flexibility**

---

## What's Next?

Now let's dive into each layer in detail, starting with the Model layer!

**Next: [07-ModelLayer.md](07-ModelLayer.md) →**
