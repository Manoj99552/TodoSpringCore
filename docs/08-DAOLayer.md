# 08 - DAO Layer (Database Access Object)

## Simple Explanation (Like You're 10)

Imagine a **librarian** in a library:

- You ask: "Can I have the book about dinosaurs?"
- Librarian: Goes to shelf, finds book, gives it to you
- You ask: "Can you add this new book?"
- Librarian: Takes book, puts it on the right shelf

**The DAO layer is like the librarian** - it knows how to:
- **Find** data in the database (like finding books)
- **Add** new data (like adding books to shelves)
- **Update** existing data (like updating book information)
- **Remove** data (like removing old books)

---

## What is DAO?

**DAO** = **D**ata **A**ccess **O**bject

It's a design pattern that:
- Separates database code from business logic
- Provides a clean interface for database operations
- Hides the complexity of SQL queries

---

## DAO Layer in Our Project

### Files
1. **TodoDAO.java** - Interface (What operations are available)
2. **TodoDAOImpl.java** - Implementation (How to perform those operations)

### Location
```
src/main/java/com/todo/dao/
├── TodoDAO.java
└── TodoDAOImpl.java
```

---

## Part 1: TodoDAO Interface

### Purpose
Defines **what** database operations we can perform on Todo items.

### Code
```java
public interface TodoDAO {
    void create(Todo todo);              // Add new todo
    Todo findById(int id);               // Find one todo
    List<Todo> findAll();                // Get all todos
    void update(Todo todo);              // Modify existing todo
    void delete(int id);                 // Remove todo
    List<Todo> findByCompleted(boolean completed);  // Custom query
}
```

### Why Use an Interface?

#### 1. Flexibility
```java
// Can have different implementations
TodoDAO dao = new TodoDAOImpl();           // MySQL version
TodoDAO dao = new TodoDAOMongoImpl();      // MongoDB version
TodoDAO dao = new TodoDAOFileImpl();       // File-based version
```

#### 2. Testing
```java
// Easy to create fake version for testing
public class FakeTodoDAO implements TodoDAO {
    private List<Todo> fakeTodos = new ArrayList<>();
    
    public void create(Todo todo) {
        fakeTodos.add(todo);  // No real database!
    }
}
```

#### 3. Loose Coupling
```java
// Service depends on interface, not specific implementation
public class TodoServiceImpl {
    private TodoDAO todoDAO;  // Any implementation works!
}
```

---

## Part 2: TodoDAOImpl Implementation

### Purpose
Implements **how** to actually perform database operations using Spring's JdbcTemplate.

### Key Components

#### 1. The @Repository Annotation
```java
@Repository
public class TodoDAOImpl implements TodoDAO {
    // ...
}
```

**What it does:**
- Tells Spring: "This is a DAO component"
- Spring automatically creates an instance
- Enables exception translation (SQL exceptions → Spring exceptions)

#### 2. JdbcTemplate Dependency
```java
private final JdbcTemplate jdbcTemplate;

public TodoDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;  // Spring injects this!
}
```

**What is JdbcTemplate?**
- Spring's helper class for database operations
- Simplifies JDBC code
- Handles connection management automatically
- Converts exceptions to Spring's DataAccessException

**Without JdbcTemplate (Old way):**
```java
Connection conn = null;
PreparedStatement stmt = null;
try {
    conn = dataSource.getConnection();
    stmt = conn.prepareStatement("INSERT INTO todos VALUES (?, ?, ?)");
    stmt.setString(1, todo.getTitle());
    stmt.setString(2, todo.getDescription());
    stmt.setBoolean(3, todo.isCompleted());
    stmt.executeUpdate();
} catch (SQLException e) {
    // Handle exception
} finally {
    if (stmt != null) stmt.close();
    if (conn != null) conn.close();
}
```

**With JdbcTemplate (Spring way):**
```java
jdbcTemplate.update(
    "INSERT INTO todos (title, description, completed) VALUES (?, ?, ?)",
    todo.getTitle(),
    todo.getDescription(),
    todo.isCompleted()
);
```

Much simpler!

---

## CRUD Operations Explained

### CREATE - Adding New Todos

```java
@Override
public void create(Todo todo) {
    jdbcTemplate.update(
        "INSERT INTO todos (title, description, completed) VALUES (?, ?, ?)",
        todo.getTitle(),
        todo.getDescription(),
        todo.isCompleted()
    );
}
```

**How it works:**
1. `jdbcTemplate.update()` - Executes an INSERT/UPDATE/DELETE statement
2. First parameter: SQL query with `?` placeholders
3. Following parameters: Values to replace the `?` marks
4. Spring automatically:
   - Opens database connection
   - Prepares the SQL statement
   - Replaces `?` with actual values
   - Executes the query
   - Closes the connection

**Example:**
```java
Todo todo = new Todo("Learn Spring", "Study IoC", false);
todoDAO.create(todo);

// Becomes SQL:
// INSERT INTO todos (title, description, completed) 
// VALUES ('Learn Spring', 'Study IoC', false)
```

---

### READ - Finding Todos

#### Find by ID (Single Result)
```java
@Override
public Todo findById(int id) {
    try {
        return jdbcTemplate.queryForObject(
            "SELECT id, title, description, completed FROM todos WHERE id = ?",
            new TodoRowMapper(),
            id
        );
    } catch (Exception e) {
        return null;  // Not found
    }
}
```

**How it works:**
1. `queryForObject()` - Returns a single object
2. First parameter: SQL SELECT query
3. Second parameter: RowMapper (converts database row to Java object)
4. Third parameter: Value for the `?` placeholder
5. If not found, throws exception (we catch and return null)

#### Find All (Multiple Results)
```java
@Override
public List<Todo> findAll() {
    return jdbcTemplate.query(
        "SELECT id, title, description, completed FROM todos",
        new TodoRowMapper()
    );
}
```

**How it works:**
1. `query()` - Returns a List of objects
2. First parameter: SQL SELECT query
3. Second parameter: RowMapper
4. Spring calls RowMapper for each row in the result

---

### UPDATE - Modifying Todos

```java
@Override
public void update(Todo todo) {
    jdbcTemplate.update(
        "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?",
        todo.getTitle(),
        todo.getDescription(),
        todo.isCompleted(),
        todo.getId()
    );
}
```

**How it works:**
1. UPDATE statement with 4 placeholders
2. First 3 `?` are the new values
3. Last `?` is the ID (which row to update)

**Example:**
```java
Todo todo = new Todo(1, "Learn Spring Core", "Master IoC and DI", true);
todoDAO.update(todo);

// Becomes SQL:
// UPDATE todos 
// SET title = 'Learn Spring Core', 
//     description = 'Master IoC and DI', 
//     completed = true 
// WHERE id = 1
```

---

### DELETE - Removing Todos

```java
@Override
public void delete(int id) {
    jdbcTemplate.update(
        "DELETE FROM todos WHERE id = ?",
        id
    );
}
```

**How it works:**
1. DELETE statement with WHERE clause
2. Only deletes the row with matching ID

**Example:**
```java
todoDAO.delete(5);

// Becomes SQL:
// DELETE FROM todos WHERE id = 5
```

---

## RowMapper - Converting Database Rows to Objects

### The Problem
Database returns data in **rows and columns**:
```
| id | title        | description | completed |
|----|--------------|-------------|-----------|
| 1  | Learn Spring | Study IoC   | false     |
```

We need this as a **Java object**:
```java
Todo(id=1, title="Learn Spring", description="Study IoC", completed=false)
```

### The Solution: RowMapper

```java
private static class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Todo todo = new Todo();
        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setDescription(rs.getString("description"));
        todo.setCompleted(rs.getBoolean("completed"));
        return todo;
    }
}
```

**How it works:**
1. Spring calls this method for each row in the result
2. `ResultSet rs` - Contains the current row's data
3. `rs.getInt("id")` - Gets the value from the "id" column
4. `rs.getString("title")` - Gets the value from the "title" column
5. We create a Todo object and populate it with the data
6. Return the Todo object

**Visual Flow:**
```
Database Row:
| id=1 | title="Learn Spring" | description="Study IoC" | completed=false |
                              ↓
                        RowMapper
                              ↓
Java Object:
Todo(id=1, title="Learn Spring", description="Study IoC", completed=false)
```

---

## Custom Queries

You can add any SQL query you need:

```java
@Override
public List<Todo> findByCompleted(boolean completed) {
    return jdbcTemplate.query(
        "SELECT id, title, description, completed FROM todos WHERE completed = ?",
        new TodoRowMapper(),
        completed
    );
}
```

**Other examples you could add:**
```java
// Find by title
List<Todo> findByTitle(String title);

// Find todos created today
List<Todo> findCreatedToday();

// Count completed todos
int countCompleted();

// Find todos with description containing keyword
List<Todo> searchByKeyword(String keyword);
```

---

## Complete DAO Flow Example

Let's trace what happens when you call `findAll()`:

```
1. Service calls:
   todoDAO.findAll()
   
2. TodoDAOImpl executes:
   jdbcTemplate.query("SELECT * FROM todos", new TodoRowMapper())
   
3. JdbcTemplate:
   - Opens database connection
   - Executes: SELECT * FROM todos
   - Gets ResultSet (table of data)
   
4. For each row in ResultSet:
   - Calls TodoRowMapper.mapRow()
   - Creates Todo object from row data
   - Adds Todo to list
   
5. JdbcTemplate:
   - Closes database connection
   - Returns List<Todo>
   
6. TodoDAOImpl returns list to Service
```

---

## Why Separate DAO from Service?

### ❌ Without DAO (Bad)
```java
@Service
public class TodoServiceImpl {
    public void addTodo(Todo todo) {
        // Validation
        if (todo.getTitle().isEmpty()) {
            throw new Exception("Title empty");
        }
        
        // Database code mixed with business logic!
        String sql = "INSERT INTO todos VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, todo.getTitle(), ...);
    }
}
```

**Problems:**
- Business logic and database code mixed
- Hard to test
- Hard to switch databases
- Violates Single Responsibility Principle

### ✅ With DAO (Good)
```java
@Service
public class TodoServiceImpl {
    private TodoDAO todoDAO;
    
    public void addTodo(Todo todo) {
        // Only business logic
        if (todo.getTitle().isEmpty()) {
            throw new Exception("Title empty");
        }
        
        // Delegate to DAO
        todoDAO.create(todo);
    }
}
```

**Benefits:**
- Clear separation of concerns
- Easy to test (mock the DAO)
- Easy to switch database implementations
- Follows Single Responsibility Principle

---

## Testing the DAO

### With Real Database (Integration Test)
```java
@Test
public void testCreate() {
    Todo todo = new Todo("Test", "Description", false);
    todoDAO.create(todo);
    
    List<Todo> todos = todoDAO.findAll();
    assertTrue(todos.size() > 0);
}
```

### With Fake DAO (Unit Test)
```java
public class FakeTodoDAO implements TodoDAO {
    private List<Todo> todos = new ArrayList<>();
    
    public void create(Todo todo) {
        todos.add(todo);
    }
    
    public List<Todo> findAll() {
        return new ArrayList<>(todos);
    }
}

@Test
public void testServiceWithFakeDAO() {
    TodoDAO fakeDAO = new FakeTodoDAO();
    TodoService service = new TodoServiceImpl(fakeDAO);
    
    service.addTodo(new Todo("Test", "Desc", false));
    // No real database needed!
}
```

---

## Key Takeaways

1. ✅ **DAO** = Data Access Object (handles database operations)
2. ✅ **Interface** (TodoDAO) defines what operations are available
3. ✅ **Implementation** (TodoDAOImpl) defines how to do them
4. ✅ **JdbcTemplate** simplifies database code
5. ✅ **RowMapper** converts database rows to Java objects
6. ✅ **@Repository** tells Spring to manage this class
7. ✅ **Separation** - DAO only handles database, no business logic!

---

## What's Next?

Now let's explore the Service Layer, which uses the DAO to implement business logic!

**Next: [09-ServiceLayer.md](09-ServiceLayer.md) →**
