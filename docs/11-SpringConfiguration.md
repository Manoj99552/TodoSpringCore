# 11 - Spring Configuration (applicationContext.xml)

## Simple Explanation (Like You're 10)

Imagine you're building a robot. The **instruction manual** tells the robot:
- What parts to use
- How to connect them
- What each part should do

**applicationContext.xml is the instruction manual for Spring!**

It tells Spring:
- What objects (beans) to create
- How to configure them
- How to connect them together

---

## What is applicationContext.xml?

It's an **XML configuration file** that:
- Configures the Spring IoC Container
- Defines beans (objects Spring manages)
- Sets up database connections
- Enables features like transactions

---

## Our Configuration File Explained

### Location
```
src/main/resources/applicationContext.xml
```

### Complete Breakdown

#### 1. Component Scanning
```xml
<context:component-scan base-package="com.todo"/>
```

**What it does:**
- Tells Spring: "Look in the `com.todo` package"
- Find all classes with annotations:
  - `@Repository` (DAO classes)
  - `@Service` (Service classes)
  - `@Component` (Generic components)
- Automatically create beans for these classes

**Real-World Analogy:**
Like telling a recruiter: "Go to this university and hire all students with engineering degrees"

**Without component scanning:**
```xml
<!-- You'd have to manually define each bean -->
<bean id="todoDAO" class="com.todo.dao.TodoDAOImpl"/>
<bean id="todoService" class="com.todo.service.TodoServiceImpl"/>
<!-- Tedious! -->
```

**With component scanning:**
```xml
<!-- Spring finds them automatically! -->
<context:component-scan base-package="com.todo"/>
```

---

#### 2. Property File Loading
```xml
<context:property-placeholder location="classpath:database.properties"/>
```

**What it does:**
- Loads the `database.properties` file
- Makes properties available using `${property.name}` syntax

**Example:**
In `database.properties`:
```properties
db.url=jdbc:mysql://localhost:3306/tododb
db.username=root
db.password=mypassword
```

In `applicationContext.xml`:
```xml
<property name="url" value="${db.url}"/>
<property name="username" value="${db.username}"/>
<property name="password" value="${db.password}"/>
```

**Benefits:**
- Don't hardcode values
- Easy to change settings
- Different properties for different environments (dev, test, production)
- Keep passwords separate from code

---

#### 3. DataSource Bean
```xml
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="driverClassName" value="${db.driver}"/>
    <property name="url" value="${db.url}"/>
    <property name="username" value="${db.username}"/>
    <property name="password" value="${db.password}"/>
    <property name="initialSize" value="${db.initialSize}"/>
    <property name="maxTotal" value="${db.maxTotal}"/>
</bean>
```

**What is DataSource?**
- Manages database connections
- Creates a **connection pool** (reusable connections)

**What is a Connection Pool?**

**Without Connection Pool:**
```
Request 1: Create connection → Use → Close
Request 2: Create connection → Use → Close
Request 3: Create connection → Use → Close
(Slow! Creating connections is expensive)
```

**With Connection Pool:**
```
Startup: Create 5 connections and keep them ready
Request 1: Borrow connection → Use → Return to pool
Request 2: Borrow connection → Use → Return to pool
Request 3: Borrow connection → Use → Return to pool
(Fast! Reuse existing connections)
```

**Properties Explained:**
- `driverClassName`: JDBC driver for MySQL
- `url`: Database location
- `username`: Database user
- `password`: Database password
- `initialSize`: How many connections to create at startup (5)
- `maxTotal`: Maximum connections allowed (10)

**Real-World Analogy:**
Like a car rental company:
- Keep 5 cars ready (initialSize)
- Maximum 10 cars total (maxTotal)
- Customers borrow and return cars (connections)
- Much faster than buying a new car each time!

---

#### 4. JdbcTemplate Bean
```xml
<bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
    <constructor-arg ref="dataSource"/>
</bean>
```

**What it does:**
- Creates a JdbcTemplate bean
- Injects the dataSource bean into it

**How Dependency Injection Works Here:**
```
1. Spring creates DataSource bean
2. Spring creates JdbcTemplate bean
3. Spring sees: <constructor-arg ref="dataSource"/>
4. Spring passes DataSource to JdbcTemplate's constructor
5. JdbcTemplate is ready to use!
```

**Equivalent Java Code:**
```java
DataSource dataSource = new BasicDataSource();
// ... configure dataSource ...

JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
```

---

#### 5. Transaction Manager Bean
```xml
<bean id="transactionManager" 
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
```

**What is a Transaction Manager?**
Manages database transactions - groups of operations that must all succeed or all fail.

**Example Transaction:**
```java
@Transactional
public void transferMoney(int fromId, int toId, int amount) {
    // Both must succeed or both must fail
    todoDAO.deductBalance(fromId, amount);  // Step 1
    todoDAO.addBalance(toId, amount);       // Step 2
}
```

**Without Transaction Manager:**
```
Step 1 succeeds: Money deducted from Account A
Step 2 fails: Money NOT added to Account B
Result: Money disappeared! 💸
```

**With Transaction Manager:**
```
Step 1 succeeds: Money deducted from Account A
Step 2 fails: Transaction rolled back
Result: Step 1 is undone, money back in Account A ✅
```

---

#### 6. Enable Annotation-Driven Transactions
```xml
<tx:annotation-driven transaction-manager="transactionManager"/>
```

**What it does:**
- Enables the `@Transactional` annotation
- When Spring sees `@Transactional`, it uses the transaction manager

**In our code:**
```java
@Service
@Transactional  // This annotation works because of the XML config!
public class TodoServiceImpl {
    // All methods are transactional
}
```

---

## Complete Configuration Flow

```
1. Application starts
   ↓
2. Spring reads applicationContext.xml
   ↓
3. Spring loads database.properties
   ↓
4. Spring creates DataSource bean
   - Configures connection pool
   - Connects to MySQL
   ↓
5. Spring creates JdbcTemplate bean
   - Injects DataSource
   ↓
6. Spring creates TransactionManager bean
   - Injects DataSource
   ↓
7. Spring scans com.todo package
   - Finds @Repository classes
   - Finds @Service classes
   ↓
8. Spring creates TodoDAOImpl bean
   - Injects JdbcTemplate
   ↓
9. Spring creates TodoServiceImpl bean
   - Injects TodoDAO
   ↓
10. Everything is ready!
    - All beans created
    - All dependencies injected
    - Application can run
```

---

## XML vs Java Configuration

### XML Configuration (What we use)
```xml
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="url" value="${db.url}"/>
</bean>
```

**Pros:**
- Clear separation of config and code
- Easy to see all configuration in one place
- Traditional Spring approach

**Cons:**
- More verbose
- No compile-time checking
- Requires XML knowledge

### Java Configuration (Alternative)
```java
@Configuration
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/tododb");
        return ds;
    }
}
```

**Pros:**
- Type-safe (compile-time checking)
- More concise
- Modern Spring approach

**Cons:**
- Config mixed with code
- Harder to change without recompiling

**Note:** Spring Boot uses Java configuration by default!

---

## Common Configuration Patterns

### Pattern 1: Property Injection
```xml
<bean id="myBean" class="com.example.MyClass">
    <property name="propertyName" value="propertyValue"/>
</bean>
```

Equivalent to:
```java
MyClass myBean = new MyClass();
myBean.setPropertyName("propertyValue");
```

### Pattern 2: Constructor Injection
```xml
<bean id="myBean" class="com.example.MyClass">
    <constructor-arg value="constructorValue"/>
</bean>
```

Equivalent to:
```java
MyClass myBean = new MyClass("constructorValue");
```

### Pattern 3: Bean Reference
```xml
<bean id="beanA" class="com.example.BeanA"/>

<bean id="beanB" class="com.example.BeanB">
    <constructor-arg ref="beanA"/>
</bean>
```

Equivalent to:
```java
BeanA beanA = new BeanA();
BeanB beanB = new BeanB(beanA);
```

---

## Troubleshooting Configuration

### Error: "Could not load applicationContext.xml"
**Cause:** File not in `src/main/resources/`  
**Solution:** Move file to correct location

### Error: "No bean named 'dataSource' found"
**Cause:** Bean not defined in XML  
**Solution:** Add bean definition

### Error: "Could not resolve placeholder '${db.url}'"
**Cause:** Property not in database.properties  
**Solution:** Add property to file

### Error: "Access denied for user 'root'"
**Cause:** Wrong password in database.properties  
**Solution:** Update password

---

## Key Takeaways

1. ✅ **applicationContext.xml** configures Spring
2. ✅ **Component scanning** finds annotated classes
3. ✅ **Property placeholder** loads external properties
4. ✅ **DataSource** manages database connections
5. ✅ **JdbcTemplate** simplifies database operations
6. ✅ **Transaction Manager** ensures data consistency
7. ✅ **Beans** are objects Spring creates and manages

---

## What's Next?

Now let's understand the database configuration file!

**Next: [12-DatabaseConfiguration.md](12-DatabaseConfiguration.md) →**
