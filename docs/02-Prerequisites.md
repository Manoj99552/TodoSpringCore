# 02 - Prerequisites

## What You Need Before Starting

Before you can run this Spring Core Todo application, you need to install and configure several tools. Don't worry - I'll explain each one in simple terms!

---

## 1. Java Development Kit (JDK)

### What is it?
Java is the programming language used for this project. The JDK (Java Development Kit) contains everything needed to write and run Java programs.

### Which Version?
**Java 17 or later** (Java 17, 18, 19, 20, 21, etc.)

### How to Check if You Have It
Open PowerShell/Command Prompt and run:
```powershell
java -version
```

**Expected output:**
```
java version "17.0.x" or higher
```

### How to Install

#### Option 1: Using Chocolatey (Easiest)
```powershell
choco install openjdk17 -y
```

#### Option 2: Manual Installation
1. Go to: https://adoptium.net/
2. Download **Temurin 17 (LTS)**
3. Run the installer
4. Check "Set JAVA_HOME" during installation
5. Restart your terminal

### Verify Installation
```powershell
java -version
javac -version
echo %JAVA_HOME%
```

---

## 2. Apache Maven

### What is it?
Maven is a **build tool** that:
- Manages project dependencies (downloads libraries automatically)
- Compiles your code
- Runs your application
- Packages your application

Think of it like a **project manager** that handles all the boring setup work!

### Which Version?
**Maven 3.6 or later**

### How to Check if You Have It
```powershell
mvn -version
```

**Expected output:**
```
Apache Maven 3.x.x
```

### How to Install

#### Option 1: Using Chocolatey (Easiest)
```powershell
choco install maven -y
```

#### Option 2: Manual Installation
See the detailed guide in the main README.md

### Verify Installation
```powershell
mvn -version
```

---

## 3. MySQL Database

### What is it?
MySQL is a **database** - a place to store data permanently. Our Todo items will be saved in MySQL.

### Which Version?
**MySQL 8.0 or later**

### How to Check if You Have It
```powershell
mysql --version
```

**Expected output:**
```
mysql  Ver 8.x.x
```

### How to Install

#### Option 1: Using Chocolatey (Easiest)
```powershell
choco install mysql -y
```

#### Option 2: Manual Installation
1. Go to: https://dev.mysql.com/downloads/installer/
2. Download **MySQL Installer for Windows**
3. Run installer
4. Choose **Developer Default**
5. Set a **root password** (remember this!)
6. Complete installation

### Verify Installation
```powershell
# Check if MySQL service is running
Get-Service MySQL*

# Connect to MySQL
mysql -u root -p
```

### Important: Remember Your Password!
You'll need the MySQL root password to configure the application!

---

## 4. IDE (Integrated Development Environment) - Optional but Recommended

### What is it?
An IDE is a fancy text editor designed for programming. It helps you:
- Write code with auto-completion
- Find errors before running
- Navigate large projects easily
- Debug code

### Options

#### Option 1: IntelliJ IDEA (Recommended)
- **Best for:** Java development
- **Download:** https://www.jetbrains.com/idea/download/
- **Version:** Community Edition (Free)

#### Option 2: Eclipse
- **Best for:** Java development
- **Download:** https://www.eclipse.org/downloads/
- **Version:** Eclipse IDE for Java Developers

#### Option 3: Visual Studio Code
- **Best for:** Lightweight, multiple languages
- **Download:** https://code.visualstudio.com/
- **Extensions needed:**
  - Extension Pack for Java
  - Spring Boot Extension Pack

### You Can Also Use
- Notepad++ (basic)
- Any text editor + command line

---

## 5. Git (Optional)

### What is it?
Version control system to track changes in your code.

### How to Install
```powershell
choco install git -y
```

Or download from: https://git-scm.com/

---

## Quick Installation Script

If you have Chocolatey installed, run this to install everything:

```powershell
# Install all prerequisites at once
choco install openjdk17 maven mysql git -y
```

---

## Verification Checklist

After installation, verify everything works:

```powershell
# Check Java
java -version
javac -version

# Check Maven
mvn -version

# Check MySQL
mysql --version
Get-Service MySQL*

# Check Git (optional)
git --version
```

**All commands should return version numbers without errors!**

---

## Troubleshooting

### Java not recognized
**Problem:** `'java' is not recognized as an internal or external command`

**Solution:**
1. Verify JAVA_HOME is set: `echo %JAVA_HOME%`
2. Add to PATH: `%JAVA_HOME%\bin`
3. Restart terminal

### Maven not recognized
**Problem:** `'mvn' is not recognized`

**Solution:**
1. Verify MAVEN_HOME is set
2. Add to PATH: `%MAVEN_HOME%\bin`
3. Restart terminal

### MySQL won't start
**Problem:** MySQL service not running

**Solution:**
```powershell
# Start MySQL service
Start-Service MySQL80

# Or
net start MySQL80
```

### Can't connect to MySQL
**Problem:** Access denied

**Solution:**
- Check your password
- Reset password if forgotten (see MySQL documentation)

---

## What's Next?

Once you have all prerequisites installed, you're ready to understand the project structure!

**Next: [03-ProjectStructure.md](03-ProjectStructure.md) →**
