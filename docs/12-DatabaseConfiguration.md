# 12 - Database Configuration

## Simple Explanation (Like You're 10)

Imagine your application is a **house** and the database is a **storage room**:

- **database.properties** = The key to the storage room (tells how to access it)
- **schema.sql** = The blueprint for organizing the storage room (how to structure data)

---

## Two Configuration Files

### 1. database.properties
**What:** Connection settings  
**Where:** `src/main/resources/database.properties`  
**Purpose:** Tell Spring how to connect to MySQL

### 2. schema.sql
**What:** Database schema  
**Where:** `src/main/resources/schema.sql`  
**Purpose:** Create database and tables

---

## Part 1: database.properties

### Complete File
```properties
# MySQL JDBC Driver
db.driver=com.mysql.cj.jdbc.Driver

# Database Connection URL
db.url=jdbc:mysql://localhost:3306/tododb

# Database Credentials
db.username=root
db.password=your_password_here

# Connection Pool Settings
db.initialSize=5
db.maxTotal=10
```

---

### Property Explanations

#### db.driver
```properties
db.driver=com.mysql.cj.jdbc.Driver
```

**What:** The JDBC driver class for MySQL  
**Why:** Tells Java how to talk to MySQL  
**Don't change:** This is the standard MySQL driver

**Analogy:** Like a translator who speaks both Java and MySQL

---

#### db.url
```properties
db.url=jdbc:mysql://localhost:3306/tododb
```

**What:** Database location  
**Format:** `jdbc:mysql://[host]:[port]/[database_name]`

**Breaking it down:**
- `jdbc:mysql://` - Protocol (always the same for MySQL)
- `localhost` - Server location (your computer)
- `3306` - MySQL port (default MySQL port)
- `tododb` - Database name

**Other examples:**
```properties
# Remote server
db.url=jdbc:mysql://192.168.1.100:3306/tododb

# Different port
db.url=jdbc:mysql://localhost:3307/tododb

# With timezone
db.url=jdbc:mysql://localhost:3306/tododb?serverTimezone=UTC
```

---

#### db.username
```properties
db.username=root
```

**What:** MySQL username  
**Default:** `root` (MySQL admin user)

**For production:**
```properties
# Create dedicated user instead of root
db.username=todoapp_user
```

**How to create user:**
```sql
CREATE USER 'todoapp_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON tododb.* TO 'todoapp_user'@'localhost';
FLUSH PRIVILEGES;
```

---

#### db.password
```properties
db.password=your_password_here
```

**What:** MySQL password  
**IMPORTANT:** Replace with your actual MySQL root password!

**Security tips:**
1. ✅ Never commit real passwords to Git
2. ✅ Use environment variables in production
3. ✅ Use different passwords for dev/test/production
4. ✅ Keep this file in `.gitignore`

**Using environment variables:**
```properties
db.password=${DB_PASSWORD}
```

Then set environment variable:
```powershell
$env:DB_PASSWORD="your_password"
```

---

#### Connection Pool Settings

```properties
db.initialSize=5
db.maxTotal=10
```

**What is a connection pool?**
A pool of reusable database connections.

**db.initialSize=5**
- Create 5 connections when application starts
- Keep them ready for use
- Faster than creating new connections each time

**db.maxTotal=10**
- Maximum 10 connections allowed
- If all 10 are in use, new requests wait
- Prevents overwhelming the database

**Analogy:**
- Like a taxi company with 5 taxis ready (initialSize)
- Can have up to 10 taxis total (maxTotal)
- Customers share taxis instead of buying new cars each time

**Tuning for different scenarios:**
```properties
# Small app (few users)
db.initialSize=2
db.maxTotal=5

# Medium app
db.initialSize=5
db.maxTotal=10

# Large app (many concurrent users)
db.initialSize=10
db.maxTotal=50
```

---

## Part 2: schema.sql

### Complete File
```sql
-- Create database
CREATE DATABASE IF NOT EXISTS tododb;

-- Use the database
USE tododb;

-- Create todos table
CREATE TABLE IF NOT EXISTS todos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

### SQL Breakdown

#### Create Database
```sql
CREATE DATABASE IF NOT EXISTS tododb;
```

**What it does:**
- Creates a database named `tododb`
- `IF NOT EXISTS` - Only create if it doesn't already exist
- Prevents errors if database already exists

**Equivalent to:**
Creating a new folder for your data

---

#### Use Database
```sql
USE tododb;
```

**What it does:**
- Switch to the `tododb` database
- All following commands apply to this database

**Equivalent to:**
Opening a specific folder

---

#### Create Table
```sql
CREATE TABLE IF NOT EXISTS todos (
    ...
);
```

**What it does:**
- Creates a table named `todos`
- Defines the structure (columns)
- `IF NOT EXISTS` - Only create if doesn't exist

---

### Column Definitions

#### id Column
```sql
id INT AUTO_INCREMENT PRIMARY KEY
```

**Breakdown:**
- `id` - Column name
- `INT` - Integer type (whole numbers: 1, 2, 3...)
- `AUTO_INCREMENT` - Automatically generates next number
- `PRIMARY KEY` - Unique identifier for each row

**How it works:**
```
First todo:  id = 1
Second todo: id = 2
Third todo:  id = 3
(Automatic!)
```

---

#### title Column
```sql
title VARCHAR(255) NOT NULL
```

**Breakdown:**
- `title` - Column name
- `VARCHAR(255)` - Variable-length string, max 255 characters
- `NOT NULL` - Must have a value (can't be empty)

**Examples:**
```
"Learn Spring Core"        ✓ (valid)
"Build Todo App"           ✓ (valid)
NULL                       ✗ (not allowed - NOT NULL)
""                         ✓ (empty string is allowed)
```

---

#### description Column
```sql
description TEXT
```

**Breakdown:**
- `description` - Column name
- `TEXT` - Large text (up to 65,535 characters)
- No `NOT NULL` - Can be empty (optional)

**TEXT vs VARCHAR:**
- `VARCHAR(255)` - Short text (titles, names)
- `TEXT` - Long text (descriptions, articles)

---

#### completed Column
```sql
completed BOOLEAN DEFAULT FALSE
```

**Breakdown:**
- `completed` - Column name
- `BOOLEAN` - True or False (stored as 0 or 1)
- `DEFAULT FALSE` - If not specified, use FALSE

**How it works:**
```sql
-- Explicitly set
INSERT INTO todos (title, completed) VALUES ('Task', TRUE);

-- Use default
INSERT INTO todos (title) VALUES ('Task');  -- completed = FALSE
```

---

#### created_at Column
```sql
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

**Breakdown:**
- `created_at` - Column name
- `TIMESTAMP` - Date and time
- `DEFAULT CURRENT_TIMESTAMP` - Automatically set to current time

**Example:**
```sql
INSERT INTO todos (title) VALUES ('Learn Spring');
-- created_at automatically set to: 2026-02-15 17:30:00
```

---

#### updated_at Column
```sql
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
```

**Breakdown:**
- `updated_at` - Column name
- `TIMESTAMP` - Date and time
- `DEFAULT CURRENT_TIMESTAMP` - Set to current time when created
- `ON UPDATE CURRENT_TIMESTAMP` - Update to current time when row is modified

**How it works:**
```sql
-- Create
INSERT INTO todos (title) VALUES ('Task');
-- created_at: 2026-02-15 10:00:00
-- updated_at: 2026-02-15 10:00:00

-- Update
UPDATE todos SET title = 'Updated Task' WHERE id = 1;
-- created_at: 2026-02-15 10:00:00 (unchanged)
-- updated_at: 2026-02-15 15:30:00 (automatically updated!)
```

---

## Running the Schema

### Method 1: MySQL Command Line
```powershell
mysql -u root -p < src/main/resources/schema.sql
```

### Method 2: MySQL Workbench
1. Open MySQL Workbench
2. Connect to MySQL
3. Open `schema.sql`
4. Click Execute (⚡ icon)

### Method 3: Command Line (Interactive)
```powershell
mysql -u root -p
```
Then paste the SQL commands.

---

## Verifying the Setup

### Check Database Exists
```sql
SHOW DATABASES;
```

**Expected output:**
```
+--------------------+
| Database           |
+--------------------+
| tododb             |
| ...                |
+--------------------+
```

### Check Table Exists
```sql
USE tododb;
SHOW TABLES;
```

**Expected output:**
```
+------------------+
| Tables_in_tododb |
+------------------+
| todos            |
+------------------+
```

### Check Table Structure
```sql
DESCRIBE todos;
```

**Expected output:**
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

## Common Issues

### Issue 1: Access Denied
```
ERROR 1045: Access denied for user 'root'@'localhost'
```

**Solution:**
- Check password in `database.properties`
- Reset MySQL password if forgotten

### Issue 2: Database Doesn't Exist
```
ERROR 1049: Unknown database 'tododb'
```

**Solution:**
```sql
CREATE DATABASE tododb;
```

### Issue 3: Table Already Exists
```
ERROR 1050: Table 'todos' already exists
```

**Solution:**
This is fine! The `IF NOT EXISTS` prevents errors, but you might see this warning.

---

## Key Takeaways

1. ✅ **database.properties** = Connection settings
2. ✅ **schema.sql** = Database structure
3. ✅ **Update password** before running
4. ✅ **Connection pool** improves performance
5. ✅ **AUTO_INCREMENT** generates IDs automatically
6. ✅ **TIMESTAMP** tracks when data is created/updated

---

## What's Next?

Now let's learn how to run the application!

**Next: [13-HowToRun.md](13-HowToRun.md) →**
