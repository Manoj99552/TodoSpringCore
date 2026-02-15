package com.todo;

import com.todo.model.Todo;
import com.todo.service.TodoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Main Application Class
 * 
 * This is the entry point of our Spring Core application
 * It demonstrates all CRUD operations on Todo items
 * 
 * How Spring Core works:
 * 1. Load Spring configuration (applicationContext.xml)
 * 2. Spring creates all beans (objects) defined in config
 * 3. Spring injects dependencies automatically
 * 4. We get beans from Spring and use them
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("  Spring Core Todo Application");
        System.out.println("========================================\n");

        // ========== STEP 1: LOAD SPRING CONTEXT ==========

        /**
         * ApplicationContext - The Spring IoC Container
         * 
         * ClassPathXmlApplicationContext:
         * - Loads configuration from XML file in classpath
         * - Creates all beans defined in the XML
         * - Manages bean lifecycle
         * - Handles dependency injection
         * 
         * What happens when this line executes:
         * 1. Spring reads applicationContext.xml
         * 2. Creates DataSource bean (database connection pool)
         * 3. Creates JdbcTemplate bean (using DataSource)
         * 4. Scans com.todo package for @Repository and @Service classes
         * 5. Creates TodoDAOImpl bean (injecting JdbcTemplate)
         * 6. Creates TodoServiceImpl bean (injecting TodoDAO)
         * 7. Sets up transaction manager
         */
        System.out.println("Loading Spring Application Context...");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("✓ Spring Context loaded successfully!\n");

        // ========== STEP 2: GET SERVICE BEAN FROM SPRING ==========

        /**
         * Getting a bean from Spring Container
         * 
         * context.getBean(TodoService.class):
         * - Asks Spring for a bean of type TodoService
         * - Spring returns the TodoServiceImpl instance it created
         * - This instance already has TodoDAO injected
         * - TodoDAO already has JdbcTemplate injected
         * 
         * This is Dependency Injection - we don't create objects ourselves!
         * Spring creates them and gives them to us with all dependencies ready
         */
        TodoService todoService = context.getBean(TodoService.class);
        System.out.println("✓ TodoService bean retrieved from Spring\n");

        // ========== DEMONSTRATION OF CRUD OPERATIONS ==========

        System.out.println("========================================");
        System.out.println("  CRUD Operations Demo");
        System.out.println("========================================\n");

        // ========== CREATE - Add new todos ==========

        System.out.println("--- CREATE Operation ---");

        // Create first todo
        Todo todo1 = new Todo("Learn Spring Core", "Study IoC and Dependency Injection", false);
        todoService.addTodo(todo1);

        // Create second todo
        Todo todo2 = new Todo("Build Todo App", "Create CRUD application with Spring", false);
        todoService.addTodo(todo2);

        // Create third todo
        Todo todo3 = new Todo("Practice JDBC", "Learn JdbcTemplate operations", true);
        todoService.addTodo(todo3);

        System.out.println();

        // ========== READ - Get all todos ==========

        System.out.println("--- READ Operation (Get All) ---");
        List<Todo> allTodos = todoService.getAllTodos();

        if (allTodos.isEmpty()) {
            System.out.println("No todos found.");
        } else {
            System.out.println("Total todos: " + allTodos.size());
            for (Todo todo : allTodos) {
                System.out.println(todo);
            }
        }
        System.out.println();

        // ========== READ - Get todo by ID ==========

        System.out.println("--- READ Operation (Get by ID) ---");
        // Assuming the first todo has ID 1 (adjust if needed)
        Todo foundTodo = todoService.getTodoById(1);
        if (foundTodo != null) {
            System.out.println("Found todo: " + foundTodo);
        } else {
            System.out.println("Todo with ID 1 not found");
        }
        System.out.println();

        // ========== UPDATE - Modify a todo ==========

        System.out.println("--- UPDATE Operation ---");
        if (foundTodo != null) {
            // Update the todo
            foundTodo.setTitle("Learn Spring Core Framework");
            foundTodo.setDescription("Master IoC, DI, and Spring JDBC");
            todoService.updateTodo(foundTodo);

            // Verify the update
            Todo updatedTodo = todoService.getTodoById(foundTodo.getId());
            System.out.println("After update: " + updatedTodo);
        }
        System.out.println();

        // ========== CUSTOM QUERY - Get todos by status ==========

        System.out.println("--- Custom Query (Get by Status) ---");

        // Get completed todos
        List<Todo> completedTodos = todoService.getTodosByStatus(true);
        System.out.println("Completed todos: " + completedTodos.size());
        for (Todo todo : completedTodos) {
            System.out.println("  ✓ " + todo.getTitle());
        }

        // Get incomplete todos
        List<Todo> incompleteTodos = todoService.getTodosByStatus(false);
        System.out.println("Incomplete todos: " + incompleteTodos.size());
        for (Todo todo : incompleteTodos) {
            System.out.println("  ○ " + todo.getTitle());
        }
        System.out.println();

        // ========== MARK AS COMPLETED ==========

        System.out.println("--- Mark Todo as Completed ---");
        if (!allTodos.isEmpty()) {
            // Mark the first incomplete todo as completed
            for (Todo todo : allTodos) {
                if (!todo.isCompleted()) {
                    todoService.markAsCompleted(todo.getId());
                    break;
                }
            }
        }
        System.out.println();

        // ========== DELETE - Remove a todo ==========

        System.out.println("--- DELETE Operation ---");
        if (!allTodos.isEmpty()) {
            // Delete the last todo
            Todo todoToDelete = allTodos.get(allTodos.size() - 1);
            todoService.deleteTodo(todoToDelete.getId());
        }
        System.out.println();

        // ========== FINAL STATE - Show remaining todos ==========

        System.out.println("--- Final State (All Remaining Todos) ---");
        List<Todo> finalTodos = todoService.getAllTodos();
        System.out.println("Total todos: " + finalTodos.size());
        for (Todo todo : finalTodos) {
            String status = todo.isCompleted() ? "✓" : "○";
            System.out.println(status + " " + todo.getTitle());
        }

        // ========== CLEANUP ==========

        System.out.println("\n========================================");
        System.out.println("  Application completed successfully!");
        System.out.println("========================================");

        /**
         * Close the Spring context
         * This will:
         * - Destroy all beans
         * - Close database connections
         * - Release resources
         */
        ((ClassPathXmlApplicationContext) context).close();
    }
}
