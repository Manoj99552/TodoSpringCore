# 14 - Troubleshooting

## Common Problems and Solutions

This guide helps you fix common issues when running the Spring Core Todo application.

---

## Database Connection Issues

### Error: "Access denied for user 'root'@'localhost'"

**Full Error:**
```
java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)
```

**Cause:** Wrong MySQL password

**Solutions:**

#### Solution 1: Update Password in database.properties
```properties
# Open: src/main/resources/database.properties
# Update this line with your actual MySQL password:
db.password=YourActualPassword
```

#### Solution 2: Reset MySQL Password
```powershell
# Stop MySQL
Stop-Service MySQL80

# Start MySQL in safe mode
mysqld --skip-grant-tables

# In another terminal
mysql -u root

# Reset password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
FLUSH PRIVILEGES;
EXIT;

# Restart MySQL normally
Start-Service MySQL80
```

---

### Error: "Unknown database 'tododb'"

**Full Error:**
```
java.sql.SQLSyntaxErrorException: Unknown database 'tododb'
```

**Cause:** Database not created

**Solution:**
```sql
mysql -u root -p
CREATE DATABASE tododb;
EXIT;
```

---

### Error: "Table 'tododb.todos' doesn't exist"

**Full Error:**
```
java.sql.SQLSyntaxErrorException: Table 'tododb.todos' doesn't exist
```

**Cause:** Table not created

**Solution:**
```sql
mysql -u root -p
USE tododb;

CREATE TABLE todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);
EXIT;
```

---

### Error: "Communications link failure"

**Full Error:**
```
com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
```

**Cause:** MySQL server not running

**Solution:**
```powershell
# Check if MySQL is running
Get-Service MySQL*

# Start MySQL
Start-Service MySQL80

# Or
net start MySQL80
```

---

## Spring Configuration Issues

### Error: "Could not load applicationContext.xml"

**Full Error:**
```
java.io.FileNotFoundException: class path resource [applicationContext.xml] cannot be opened
```

**Cause:** Configuration file missing or in wrong location

**Solution:**
Verify file exists at:
```
src/main/resources/applicationContext.xml
```

**Check:**
1. File name is exactly `applicationContext.xml` (case-sensitive)
2. File is in `src/main/resources/` folder
3. No typos in filename

---

### Error: "No bean named 'dataSource' found"

**Full Error:**
```
org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'dataSource' available
```

**Cause:** Bean not defined in applicationContext.xml

**Solution:**
Check `applicationContext.xml` contains:
```xml
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource">
    <property name="driverClassName" value="${db.driver}"/>
    <property name="url" value="${db.url}"/>
    <property name="username" value="${db.username}"/>
    <property name="password" value="${db.password}"/>
</bean>
```

---

### Error: "Could not resolve placeholder '${db.url}'"

**Full Error:**
```
java.lang.IllegalArgumentException: Could not resolve placeholder 'db.url' in value "${db.url}"
```

**Cause:** Property not in database.properties or file not loaded

**Solution 1:** Check database.properties exists
```
src/main/resources/database.properties
```

**Solution 2:** Verify applicationContext.xml loads it
```xml
<context:property-placeholder location="classpath:database.properties"/>
```

**Solution 3:** Check property exists in database.properties
```properties
db.url=jdbc:mysql://localhost:3306/tododb
```

---

## Maven Issues

### Error: "'mvn' is not recognized"

**Full Error:**
```
'mvn' is not recognized as an internal or external command
```

**Cause:** Maven not installed or not in PATH

**Solution:**

#### Check if Maven is installed
```powershell
mvn -version
```

#### Install Maven
```powershell
choco install maven -y
```

#### Add to PATH manually
1. Find Maven installation (e.g., `C:\Program Files\Apache\maven`)
2. Add `C:\Program Files\Apache\maven\bin` to PATH
3. Restart terminal

---

### Error: "BUILD FAILURE" - Compilation Error

**Full Error:**
```
[ERROR] COMPILATION ERROR
[ERROR] /path/to/file.java:[line,column] error: ...
```

**Cause:** Syntax error in Java code

**Solution:**
1. Read the error message carefully
2. Go to the file and line number mentioned
3. Fix the syntax error
4. Common issues:
   - Missing semicolon `;`
   - Mismatched brackets `{}`
   - Wrong import statements
   - Typos in variable names

---

### Error: "Could not find or load main class"

**Full Error:**
```
Error: Could not find or load main class com.todo.Main
```

**Cause:** Class not compiled or wrong package

**Solution:**

#### Rebuild the project
```powershell
mvn clean compile
```

#### Check package declaration
```java
// In Main.java, first line should be:
package com.todo;
```

#### Check file location
```
src/main/java/com/todo/Main.java
```

---

## Java Issues

### Error: "'java' is not recognized"

**Full Error:**
```
'java' is not recognized as an internal or external command
```

**Cause:** Java not installed or not in PATH

**Solution:**

#### Check if Java is installed
```powershell
java -version
```

#### Install Java
```powershell
choco install openjdk17 -y
```

#### Set JAVA_HOME
```powershell
# Check JAVA_HOME
echo %JAVA_HOME%

# Set JAVA_HOME (adjust path to your Java installation)
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
```

---

### Error: "UnsupportedClassVersionError"

**Full Error:**
```
java.lang.UnsupportedClassVersionError: com/todo/Main has been compiled by a more recent version of the Java Runtime
```

**Cause:** Code compiled with newer Java version than you're running

**Solution:**

#### Check Java version
```powershell
java -version
javac -version
```

Both should be 17 or higher.

#### Update Java
```powershell
choco upgrade openjdk17 -y
```

---

## Application Logic Issues

### Error: "Todo title cannot be empty"

**Full Error:**
```
java.lang.IllegalArgumentException: Todo title cannot be empty
```

**Cause:** Trying to create todo with empty title

**Solution:**
```java
// Wrong
Todo todo = new Todo("", "Description", false);

// Correct
Todo todo = new Todo("Valid Title", "Description", false);
```

---

### Error: "Todo with id X not found"

**Full Error:**
```
java.lang.IllegalArgumentException: Todo with id 5 not found
```

**Cause:** Trying to update/delete non-existent todo

**Solution:**

#### Check if todo exists
```java
Todo todo = todoService.getTodoById(5);
if (todo != null) {
    // Todo exists, safe to update/delete
    todoService.updateTodo(todo);
} else {
    System.out.println("Todo not found");
}
```

#### List all todos to see IDs
```java
List<Todo> todos = todoService.getAllTodos();
for (Todo t : todos) {
    System.out.println("ID: " + t.getId() + ", Title: " + t.getTitle());
}
```

---

## IDE Issues

### IntelliJ: "Cannot resolve symbol 'ApplicationContext'"

**Cause:** Maven dependencies not downloaded

**Solution:**
1. Right-click `pom.xml`
2. Select **Maven → Reload Project**
3. Wait for dependencies to download

---

### Eclipse: "Project build error"

**Cause:** Maven not configured

**Solution:**
1. Right-click project
2. Select **Maven → Update Project**
3. Check **Force Update of Snapshots/Releases**
4. Click OK

---

## Performance Issues

### Application is slow

**Possible Causes:**
1. Too many database connections
2. No connection pooling
3. Slow queries

**Solutions:**

#### Optimize connection pool
```properties
# In database.properties
db.initialSize=10
db.maxTotal=20
```

#### Add indexes to database
```sql
CREATE INDEX idx_completed ON todos(completed);
CREATE INDEX idx_title ON todos(title);
```

---

## Debugging Tips

### Enable Spring Debug Logging

Add to `src/main/resources/logback.xml`:
```xml
<configuration>
    <logger name="org.springframework" level="DEBUG"/>
    <root level="INFO">
        <appender-ref ref="STDOUT"/>
    </root>
</configuration>
```

### Print SQL Queries

Add to database.properties:
```properties
db.url=jdbc:mysql://localhost:3306/tododb?logger=com.mysql.cj.log.StandardLogger&profileSQL=true
```

### Check Bean Creation

```java
String[] beanNames = context.getBeanDefinitionNames();
for (String name : beanNames) {
    System.out.println("Bean: " + name);
}
```

---

## Getting Help

### Check Logs
Look for error messages in console output

### Verify Configuration
1. Database password correct?
2. Database and table exist?
3. MySQL running?
4. All files in correct locations?

### Test Components Individually

#### Test Database Connection
```java
try {
    DataSource ds = context.getBean(DataSource.class);
    Connection conn = ds.getConnection();
    System.out.println("Database connected!");
    conn.close();
} catch (Exception e) {
    System.err.println("Database connection failed: " + e.getMessage());
}
```

#### Test DAO
```java
try {
    TodoDAO dao = context.getBean(TodoDAO.class);
    List<Todo> todos = dao.findAll();
    System.out.println("DAO works! Found " + todos.size() + " todos");
} catch (Exception e) {
    System.err.println("DAO failed: " + e.getMessage());
}
```

---

## Key Takeaways

1. ✅ **Read error messages carefully** - They tell you what's wrong
2. ✅ **Check database first** - Most issues are database-related
3. ✅ **Verify configuration files** - Ensure they're in the right place
4. ✅ **Test step by step** - Isolate the problem
5. ✅ **Check versions** - Java, Maven, MySQL compatibility

---

## What's Next?

Now that you know how to troubleshoot, let's see how everything works together!

**Next: [15-HowItAllWorks.md](15-HowItAllWorks.md) →**
