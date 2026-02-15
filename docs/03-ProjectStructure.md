# 03 - Project Structure

## Understanding the Folder Layout

When you open the TodoSpringCore project, you'll see many folders and files. Let's understand what each one does!

---

## Complete Project Structure

```
TodoSpringCore/
├── .gitignore                          # Files Git should ignore
├── pom.xml                             # Maven configuration
├── README.md                           # Project overview
│
├── docs/                               # Documentation folder
│   ├── README.md                       # Documentation index
│   ├── 01-Introduction.md              # Project introduction
│   ├── 02-Prerequisites.md             # What you need to install
│   └── ... (other documentation files)
│
├── src/                                # Source code folder
│   ├── main/                           # Main application code
│   │   ├── java/                       # Java source files
│   │   │   └── com/                    # Company/organization name
│   │   │       └── todo/               # Project name
│   │   │           ├── model/          # Data models (POJOs)
│   │   │           │   └── Todo.java   # Todo entity class
│   │   │           │
│   │   │           ├── dao/            # Data Access Layer
│   │   │           │   ├── TodoDAO.java      # DAO interface
│   │   │           │   └── TodoDAOImpl.java  # DAO implementation
│   │   │           │
│   │   │           ├── service/        # Business Logic Layer
│   │   │           │   ├── TodoService.java      # Service interface
│   │   │           │   └── TodoServiceImpl.java  # Service implementation
│   │   │           │
│   │   │           └── Main.java       # Application entry point
│   │   │
│   │   └── resources/                  # Configuration files
│   │       ├── applicationContext.xml  # Spring configuration
│   │       ├── database.properties     # Database settings
│   │       └── schema.sql              # Database schema
│   │
│   └── test/                           # Test code
│       ├── java/                       # Test source files
│       │   └── com/
│       │       └── todo/
│       └── resources/                  # Test configuration files
│
└── target/                             # Compiled code (auto-generated)
    ├── classes/                        # Compiled .class files
    └── TodoSpringCore-1.0-SNAPSHOT.jar # Packaged application
```

---

## Folder Explanations

### Root Level

#### `.gitignore`
**What:** Tells Git which files to ignore  
**Why:** Don't commit IDE settings, compiled files, or passwords  
**Example content:**
```
.idea/
target/
*.class
```

#### `pom.xml`
**What:** Maven Project Object Model  
**Why:** Defines project dependencies, build settings  
**Contains:**
- Project information (name, version)
- Dependencies (Spring, MySQL driver)
- Build plugins (compiler, exec)

#### `README.md`
**What:** Project overview and quick start guide  
**Why:** First file people read about your project

---

### `docs/` Folder

**What:** All documentation files  
**Why:** Keep documentation separate from code  
**Contains:** Guides explaining the project in detail

---

### `src/main/java/` Folder

**What:** All Java source code  
**Why:** Maven convention - main application code goes here

#### Package Structure: `com.todo`

**Why this naming?**
- `com` = company/organization domain
- `todo` = project name
- Java convention: reverse domain name

**Prevents naming conflicts:**
```
com.todo.model.Todo      # Your Todo class
org.example.model.Todo   # Someone else's Todo class
```

---

### `src/main/java/com/todo/model/`

**What:** Data model classes (POJOs)  
**Contains:** `Todo.java`  
**Purpose:** Represents the structure of data

**Analogy:** Blueprint of a house - shows what rooms exist, but doesn't build the house

---

### `src/main/java/com/todo/dao/`

**What:** Data Access Object layer  
**Contains:**
- `TodoDAO.java` - Interface defining database operations
- `TodoDAOImpl.java` - Implementation using JdbcTemplate

**Purpose:** All database operations (CRUD)

**Analogy:** Librarian who knows how to find, add, update, and remove books

---

### `src/main/java/com/todo/service/`

**What:** Business logic layer  
**Contains:**
- `TodoService.java` - Interface defining business operations
- `TodoServiceImpl.java` - Implementation with validation and logic

**Purpose:** Business rules, validation, transaction management

**Analogy:** Manager who makes decisions and tells the librarian what to do

---

### `src/main/java/com/todo/Main.java`

**What:** Application entry point  
**Contains:** `main()` method  
**Purpose:** Starts the application, demonstrates CRUD operations

**Analogy:** The front door of your application

---

### `src/main/resources/`

**What:** Configuration files and non-Java resources  
**Why:** Separate configuration from code

#### `applicationContext.xml`
**What:** Spring configuration  
**Contains:**
- Bean definitions
- Component scanning
- Database setup
- Transaction management

#### `database.properties`
**What:** Database connection settings  
**Contains:**
- Database URL
- Username
- Password
- Connection pool settings

**Why separate file?**
- Easy to change without recompiling
- Can have different files for dev/test/production
- Keep passwords out of code

#### `schema.sql`
**What:** Database schema (table definitions)  
**Contains:** SQL to create database and tables

---

### `src/test/`

**What:** Test code (mirror of main/)  
**Why:** Keep tests separate from application code  
**Currently:** Empty (you can add tests later)

---

### `target/` Folder

**What:** Compiled code and build output  
**Created by:** Maven (auto-generated)  
**Contains:**
- `classes/` - Compiled .class files
- `TodoSpringCore-1.0-SNAPSHOT.jar` - Packaged application

**Important:** This folder is auto-generated. Don't edit files here!

**In .gitignore:** Yes - don't commit compiled code

---

## Maven Standard Directory Layout

Our project follows Maven's standard structure:

```
src/main/java       → Java source code
src/main/resources  → Configuration files
src/test/java       → Test code
src/test/resources  → Test configuration
target/             → Compiled output
pom.xml             → Project configuration
```

**Why follow this convention?**
- Everyone knows where to find things
- Maven tools work automatically
- IDEs recognize the structure
- Professional standard

---

## File Naming Conventions

### Java Files
- **Classes:** PascalCase (e.g., `TodoService`, `Main`)
- **Interfaces:** PascalCase (e.g., `TodoDAO`)
- **Implementations:** ClassName + Impl (e.g., `TodoDAOImpl`)

### Configuration Files
- **XML:** camelCase.xml (e.g., `applicationContext.xml`)
- **Properties:** lowercase.properties (e.g., `database.properties`)
- **SQL:** lowercase.sql (e.g., `schema.sql`)

### Documentation
- **Markdown:** PascalCase.md or numbered (e.g., `README.md`, `01-Introduction.md`)

---

## Package Organization

```
com.todo
├── model       → Data structures (POJOs)
├── dao         → Database access
├── service     → Business logic
└── Main.java   → Entry point
```

**Why organize by layer?**
- Clear separation of concerns
- Easy to find related files
- Follows industry standards

**Alternative (by feature):**
```
com.todo
├── todo        → All todo-related files
├── user        → All user-related files
└── auth        → All authentication files
```

We use **layer-based** organization for this project.

---

## How Files Connect

```
Main.java
  ↓ uses
TodoService.java (interface)
  ↑ implements
TodoServiceImpl.java
  ↓ uses
TodoDAO.java (interface)
  ↑ implements
TodoDAOImpl.java
  ↓ uses
JdbcTemplate (Spring)
  ↓ uses
DataSource (Spring)
  ↓ connects to
MySQL Database
```

---

## What You Can Modify

### ✅ Safe to Modify
- `Main.java` - Change demo code
- `database.properties` - Update password, URL
- `schema.sql` - Add columns, tables
- Documentation files

### ⚠️ Modify with Care
- `Todo.java` - If you add fields, update DAO
- `TodoDAOImpl.java` - If you change SQL
- `TodoServiceImpl.java` - If you add business logic

### ❌ Don't Modify
- `target/` folder - Auto-generated
- `pom.xml` - Unless adding dependencies
- `applicationContext.xml` - Unless you know Spring well

---

## Key Takeaways

1. ✅ `src/main/java` = Java source code
2. ✅ `src/main/resources` = Configuration files
3. ✅ `pom.xml` = Maven configuration
4. ✅ `target/` = Auto-generated (don't edit)
5. ✅ Package structure = `com.todo.layer`
6. ✅ Follow Maven conventions for consistency

---

## What's Next?

Now that you understand the project structure, let's learn about Spring Framework!

**Next: [04-WhatIsSpring.md](04-WhatIsSpring.md) →**
