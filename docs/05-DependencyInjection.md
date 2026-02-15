# 05 - Dependency Injection

## Simple Explanation (Like You're 10)

Imagine you're making a sandwich:

**Without Dependency Injection (You do everything):**
```
1. You bake bread
2. You grow tomatoes
3. You make cheese
4. You assemble sandwich
```

**With Dependency Injection (Someone gives you what you need):**
```
1. Someone gives you bread
2. Someone gives you tomatoes  
3. Someone gives you cheese
4. You just assemble sandwich
```

**Dependency Injection = Someone else gives you what you need, so you can focus on your job!**

---

## What is Dependency Injection?

### The Problem

Classes often need other classes to work:

```java
public class TodoService {
    private TodoDAO todoDAO;
    
    public TodoService() {
        // Problem: TodoService creates its own DAO
        this.todoDAO = new TodoDAOImpl();
    }
}
```

**Problems with this approach:**
1. **Tight coupling** - TodoService is stuck with TodoDAOImpl
2. **Hard to test** - Can't use a fake DAO for testing
3. **Hard to change** - Want to switch to MongoDB? Change TodoService code!
4. **TodoService does too much** - Creates dependencies AND uses them

---

### The Solution: Dependency Injection

Instead of creating dependencies, **receive them**:

```java
public class TodoService {
    private TodoDAO todoDAO;
    
    // Dependencies are INJECTED through constructor
    public TodoService(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;  // Given to us!
    }
}
```

**Benefits:**
1. ✅ **Loose coupling** - Works with ANY TodoDAO implementation
2. ✅ **Easy to test** - Can inject fake DAO
3. ✅ **Easy to change** - Just inject different implementation
4. ✅ **Single responsibility** - TodoService only does business logic

---

## Real-World Analogy

### Restaurant Example

**Without DI (Chef does everything):**
```
Chef:
- Grows vegetables
- Raises chickens
- Makes plates
- Cooks food
- Serves customers

Result: Slow, inefficient, chef is exhausted!
```

**With DI (Chef receives what's needed):**
```
Farmer: Provides vegetables
Supplier: Provides chicken
Dishwasher: Provides clean plates
Chef: Just cooks!

Result: Fast, efficient, chef focuses on cooking!
```

**Dependency Injection = Giving the chef what they need to do their job!**

---

## Types of Dependency Injection

### 1. Constructor Injection (Recommended)

**How it works:**
```java
public class TodoServiceImpl {
    private final TodoDAO todoDAO;
    
    // Dependencies injected through constructor
    public TodoServiceImpl(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }
}
```

**Spring does this:**
```java
// Spring creates TodoDAO
TodoDAO dao = new TodoDAOImpl(jdbcTemplate);

// Spring creates TodoService and injects TodoDAO
TodoService service = new TodoServiceImpl(dao);
```

**Benefits:**
- ✅ Dependencies are required (can't create object without them)
- ✅ Immutable (use `final`)
- ✅ Easy to test
- ✅ Clear what dependencies are needed

**When to use:** **Always!** (Best practice)

---

### 2. Setter Injection

**How it works:**
```java
public class TodoServiceImpl {
    private TodoDAO todoDAO;
    
    // Dependencies injected through setter method
    public void setTodoDAO(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }
}
```

**Spring does this:**
```java
TodoService service = new TodoServiceImpl();
service.setTodoDAO(dao);  // Inject after creation
```

**Benefits:**
- ✅ Optional dependencies
- ✅ Can change dependency later

**Drawbacks:**
- ❌ Dependencies might be null
- ❌ Can't use `final`
- ❌ Object might be in invalid state

**When to use:** Rarely (only for optional dependencies)

---

### 3. Field Injection

**How it works:**
```java
public class TodoServiceImpl {
    @Autowired  // Spring injects directly into field
    private TodoDAO todoDAO;
}
```

**Benefits:**
- ✅ Less code

**Drawbacks:**
- ❌ Hard to test (can't inject mock easily)
- ❌ Can't use `final`
- ❌ Hides dependencies
- ❌ Requires reflection (slower)

**When to use:** **Never!** (Bad practice)

---

## Dependency Injection in Our Project

### Example 1: TodoDAOImpl

```java
@Repository
public class TodoDAOImpl implements TodoDAO {
    private final JdbcTemplate jdbcTemplate;
    
    // Constructor injection
    public TodoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
```

**What Spring does:**
```
1. Spring creates JdbcTemplate bean
2. Spring sees TodoDAOImpl needs JdbcTemplate
3. Spring calls: new TodoDAOImpl(jdbcTemplate)
4. TodoDAOImpl is ready with JdbcTemplate injected!
```

---

### Example 2: TodoServiceImpl

```java
@Service
public class TodoServiceImpl implements TodoService {
    private final TodoDAO todoDAO;
    
    // Constructor injection
    public TodoServiceImpl(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }
}
```

**What Spring does:**
```
1. Spring creates TodoDAOImpl bean
2. Spring sees TodoServiceImpl needs TodoDAO
3. Spring calls: new TodoServiceImpl(todoDAOImpl)
4. TodoServiceImpl is ready with TodoDAO injected!
```

---

## How Spring Knows What to Inject

### 1. By Type

Spring looks for a bean of the required type:

```java
public TodoServiceImpl(TodoDAO todoDAO) {
    // Spring looks for a bean of type TodoDAO
    // Finds: TodoDAOImpl (implements TodoDAO)
    // Injects it!
}
```

### 2. By Name (if multiple beans of same type)

```java
@Autowired
@Qualifier("todoDAOImpl")
private TodoDAO todoDAO;
```

### 3. By Interface

```java
// Service depends on interface, not implementation
private TodoDAO todoDAO;  // Interface

// Spring can inject ANY implementation:
// - TodoDAOImpl (MySQL)
// - TodoDAOMongoImpl (MongoDB)
// - FakeTodoDAO (Testing)
```

---

## Benefits of Dependency Injection

### 1. Loose Coupling

**Without DI:**
```java
public class TodoService {
    private TodoDAOImpl dao = new TodoDAOImpl();
    // Stuck with TodoDAOImpl!
}
```

**With DI:**
```java
public class TodoService {
    private TodoDAO dao;  // Any implementation works!
    
    public TodoService(TodoDAO dao) {
        this.dao = dao;
    }
}
```

---

### 2. Easy Testing

**Without DI:**
```java
public class TodoService {
    private TodoDAO dao = new TodoDAOImpl();
    // Can't test without real database!
}
```

**With DI:**
```java
// Create fake DAO for testing
public class FakeTodoDAO implements TodoDAO {
    public void create(Todo todo) {
        // Fake implementation - no database!
    }
}

// Test with fake DAO
TodoDAO fakeDAO = new FakeTodoDAO();
TodoService service = new TodoService(fakeDAO);
service.addTodo(todo);  // No database needed!
```

---

### 3. Flexibility

**Easy to switch implementations:**

```java
// Production: Use MySQL
TodoDAO dao = new TodoDAOImpl();
TodoService service = new TodoService(dao);

// Testing: Use fake
TodoDAO dao = new FakeTodoDAO();
TodoService service = new TodoService(dao);

// Future: Switch to MongoDB
TodoDAO dao = new TodoDAOMongoImpl();
TodoService service = new TodoService(dao);
```

**No changes to TodoService code!**

---

## Dependency Injection Flow in Our App

```
Application starts
  ↓
Spring reads applicationContext.xml
  ↓
Spring scans for @Repository and @Service classes
  ↓
┌─────────────────────────────────────┐
│ Spring creates beans in order:     │
│                                     │
│ 1. DataSource                       │
│    (no dependencies)                │
│    ↓                                │
│ 2. JdbcTemplate                     │
│    (needs DataSource)               │
│    Spring injects: DataSource       │
│    ↓                                │
│ 3. TodoDAOImpl                      │
│    (needs JdbcTemplate)             │
│    Spring injects: JdbcTemplate     │
│    ↓                                │
│ 4. TodoServiceImpl                  │
│    (needs TodoDAO)                  │
│    Spring injects: TodoDAOImpl      │
└─────────────────────────────────────┘
  ↓
All beans ready with dependencies injected!
  ↓
Application can use TodoService
```

---

## Common Questions

### Q: Who creates the objects?
**A:** Spring creates them (not you!)

### Q: Who injects the dependencies?
**A:** Spring injects them automatically

### Q: How does Spring know what to inject?
**A:** By looking at constructor parameters and finding matching beans

### Q: What if Spring can't find a dependency?
**A:** Application fails to start with error: "No bean of type X found"

### Q: Can I create objects manually?
**A:** Yes, but then you lose Spring's benefits (DI, transaction management, etc.)

---

## Key Takeaways

1. ✅ **Dependency Injection** = Receiving what you need instead of creating it
2. ✅ **Constructor Injection** is the best practice
3. ✅ **Spring automatically injects** dependencies
4. ✅ **Loose coupling** - Depend on interfaces, not implementations
5. ✅ **Easy testing** - Can inject fake implementations
6. ✅ **Flexibility** - Easy to switch implementations

---

## What's Next?

Now that you understand Dependency Injection, let's see how it fits into the layered architecture!

**Next: [06-LayeredArchitecture.md](06-LayeredArchitecture.md) →**
