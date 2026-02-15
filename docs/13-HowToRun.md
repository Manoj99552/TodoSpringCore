# 13 - How to Run the Application

## Prerequisites Checklist

Before running, make sure you have:

- ✅ Java JDK 17+ installed
- ✅ Maven installed
- ✅ MySQL installed and running
- ✅ Database created (`tododb`)
- ✅ Table created (`todos`)
- ✅ Password configured in `database.properties`

---

## Step-by-Step Guide

### Step 1: Set Up MySQL Database

#### Open MySQL Command Line
```powershell
mysql -u root -p
```
Enter your MySQL password when prompted.

#### Create Database and Table
```sql
-- Create the database
CREATE DATABASE IF NOT EXISTS tododb;

-- Use the database
USE tododb;

-- Create the todos table
CREATE TABLE IF NOT EXISTS todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Verify table creation
DESCRIBE todos;

-- Exit MySQL
EXIT;
```

**Expected Output:**
```
+-------------+--------------+------+-----+-------------------+
| Field       | Type         | Null | Key | Default           |
+-------------+--------------+------+-----+-------------------+
| id          | int          | NO   | PRI | NULL              |
| title       | varchar(255) | NO   |     | NULL              |
| description | text         | YES  |     | NULL              |
| completed   | tinyint(1)   | YES  |     | 0                 |
| created_at  | timestamp    | YES  |     | CURRENT_TIMESTAMP |
| updated_at  | timestamp    | YES  |     | CURRENT_TIMESTAMP |
+-------------+--------------+------+-----+-------------------+
```

---

### Step 2: Configure Database Password

#### Open database.properties
```
C:\Users\mks94\Documents\Projects\TodoSpringCore\src\main\resources\database.properties
```

#### Update Password
```properties
# Change this line:
db.password=your_password_here

# To your actual MySQL password:
db.password=YourActualPassword123
```

**Save the file!**

---

### Step 3: Build the Project

#### Open PowerShell/Command Prompt
```powershell
cd C:\Users\mks94\Documents\Projects\TodoSpringCore
```

#### Clean and Compile
```powershell
mvn clean compile
```

**Expected Output:**
```
[INFO] BUILD SUCCESS
[INFO] Total time: 2.5 s
```

**If you see errors:**
- Check Java version: `java -version`
- Check Maven version: `mvn -version`
- Make sure `pom.xml` exists

---

### Step 4: Run the Application

#### Method 1: Using Maven (Recommended)
```powershell
mvn exec:java '-Dexec.mainClass=com.todo.Main'
```

#### Method 2: Using IDE
1. Open `Main.java` in your IDE
2. Right-click anywhere in the file
3. Select **"Run 'Main.main()'"** or **"Run As → Java Application"**

#### Method 3: Build JAR and Run
```powershell
# Build JAR
mvn clean package

# Run JAR
java -cp target/TodoSpringCore-1.0-SNAPSHOT.jar com.todo.Main
```

---

## Expected Output

When the application runs successfully, you should see:

```
========================================
  Spring Core Todo Application
========================================

Loading Spring Application Context...
✓ Spring Context loaded successfully!

✓ TodoService bean retrieved from Spring

========================================
  CRUD Operations Demo
========================================

--- CREATE Operation ---
✓ Todo created successfully: Learn Spring Core
✓ Todo created successfully: Build Todo App
✓ Todo created successfully: Practice JDBC

--- READ Operation (Get All) ---
Total todos: 3
Todo{id=1, title='Learn Spring Core', description='Study IoC and Dependency Injection', completed=false}
Todo{id=2, title='Build Todo App', description='Create CRUD application with Spring', completed=false}
Todo{id=3, title='Practice JDBC', description='Learn JdbcTemplate operations', completed=true}

--- READ Operation (Get by ID) ---
Found todo: Todo{id=1, title='Learn Spring Core', description='Study IoC and Dependency Injection', completed=false}

--- UPDATE Operation ---
✓ Todo updated successfully: Learn Spring Core Framework
After update: Todo{id=1, title='Learn Spring Core Framework', description='Master IoC, DI, and Spring JDBC', completed=false}

--- Custom Query (Get by Status) ---
Completed todos: 1
  ✓ Practice JDBC
Incomplete todos: 2
  ○ Learn Spring Core Framework
  ○ Build Todo App

--- Mark Todo as Completed ---
✓ Todo marked as completed: Learn Spring Core Framework

--- DELETE Operation ---
✓ Todo deleted successfully (ID: 3)

--- Final State (All Remaining Todos) ---
Total todos: 2
✓ Learn Spring Core Framework
○ Build Todo App

========================================
  Application completed successfully!
========================================
```

---

## Troubleshooting

### Error: "Access denied for user 'root'@'localhost'"

**Cause:** Wrong MySQL password

**Solution:**
1. Check your MySQL password
2. Update `database.properties` with correct password
3. Run again

---

### Error: "Unknown database 'tododb'"

**Cause:** Database not created

**Solution:**
```sql
mysql -u root -p
CREATE DATABASE tododb;
EXIT;
```

---

### Error: "Table 'tododb.todos' doesn't exist"

**Cause:** Table not created

**Solution:**
Run the CREATE TABLE statement from Step 1

---

### Error: "Could not load applicationContext.xml"

**Cause:** Configuration file missing or in wrong location

**Solution:**
Verify file exists at:
```
src/main/resources/applicationContext.xml
```

---

### Error: "No bean of type TodoService found"

**Cause:** Component scanning not working

**Solution:**
1. Check `@Service` annotation on `TodoServiceImpl`
2. Check `@Repository` annotation on `TodoDAOImpl`
3. Verify `<context:component-scan>` in XML

---

### Error: Maven command not recognized

**Cause:** Maven not installed or not in PATH

**Solution:**
1. Install Maven (see README.md)
2. Add to PATH
3. Restart terminal
4. Verify: `mvn -version`

---

## Verifying Database Changes

After running the application, check the database:

```sql
mysql -u root -p
USE tododb;
SELECT * FROM todos;
```

You should see the todos that were created (minus the deleted one).

---

## Running Multiple Times

**Note:** Each time you run the application:
- New todos are created (IDs will increment)
- Previous todos remain in database

**To start fresh:**
```sql
DELETE FROM todos;
```

Or drop and recreate the table.

---

## Next Steps After Successful Run

1. ✅ **Read the output** - Understand what each operation does
2. ✅ **Check the database** - See the data that was created
3. ✅ **Modify Main.java** - Try creating different todos
4. ✅ **Read the code** - Understand how it works
5. ✅ **Experiment** - Add new features!

---

## Key Takeaways

1. ✅ Database must be set up before running
2. ✅ Password must be configured correctly
3. ✅ Use `mvn exec:java` to run from command line
4. ✅ Check console output to verify success
5. ✅ Verify database to see persisted data

---

## What's Next?

Now that you can run the application, let's understand how everything works together!

**Next: [15-HowItAllWorks.md](15-HowItAllWorks.md) →**
