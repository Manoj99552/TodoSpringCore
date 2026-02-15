package com.todo.service;

import com.todo.dao.TodoDAO;
import com.todo.model.Todo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TodoServiceImpl - Implementation of TodoService interface
 * 
 * @Service annotation:
 *          - Tells Spring this is a Service component (Business Logic Layer)
 *          - Spring will automatically create an instance of this class
 *          - Marks this class as a service provider
 * 
 * @Transactional annotation:
 *                - Manages database transactions automatically
 *                - If any method fails, all database changes are rolled back
 *                - Ensures data consistency
 */
@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    // ========== DEPENDENCY INJECTION ==========

    /**
     * TodoDAO - Data Access Object for database operations
     * 
     * This is injected by Spring (Dependency Injection)
     * The service layer doesn't create the DAO, Spring provides it
     * 
     * Benefits:
     * - Loose coupling: Service doesn't depend on specific DAO implementation
     * - Easy testing: Can inject mock DAO for unit tests
     * - Spring manages the lifecycle
     */
    private final TodoDAO todoDAO;

    /**
     * Constructor for dependency injection
     * Spring automatically injects TodoDAO when creating this bean
     * 
     * @param todoDAO - The DAO implementation (TodoDAOImpl)
     */
    public TodoServiceImpl(TodoDAO todoDAO) {
        this.todoDAO = todoDAO;
    }

    // ========== BUSINESS LOGIC METHODS ==========

    /**
     * Add a new todo
     * 
     * Business Logic:
     * 1. Validate that title is not empty
     * 2. If valid, delegate to DAO to save in database
     * 
     * @param todo - The todo to add
     * @throws IllegalArgumentException if title is null or empty
     */
    @Override
    public void addTodo(Todo todo) {
        // Validation - Business logic
        if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        // Delegate to DAO for database operation
        todoDAO.create(todo);

        System.out.println("✓ Todo created successfully: " + todo.getTitle());
    }

    /**
     * Get a todo by ID
     * Simply delegates to DAO (no business logic needed here)
     * 
     * @param id - The todo ID
     * @return The todo if found, null otherwise
     */
    @Override
    public Todo getTodoById(int id) {
        return todoDAO.findById(id);
    }

    /**
     * Get all todos
     * Simply delegates to DAO
     * 
     * @return List of all todos
     */
    @Override
    public List<Todo> getAllTodos() {
        return todoDAO.findAll();
    }

    /**
     * Update an existing todo
     * 
     * Business Logic:
     * 1. Validate that title is not empty
     * 2. Check if todo exists in database
     * 3. If valid, update in database
     * 
     * @param todo - The todo with updated values
     * @throws IllegalArgumentException if validation fails
     */
    @Override
    public void updateTodo(Todo todo) {
        // Validation
        if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Todo title cannot be empty");
        }

        // Check if todo exists
        Todo existingTodo = todoDAO.findById(todo.getId());
        if (existingTodo == null) {
            throw new IllegalArgumentException("Todo with id " + todo.getId() + " not found");
        }

        // Update in database
        todoDAO.update(todo);

        System.out.println("✓ Todo updated successfully: " + todo.getTitle());
    }

    /**
     * Delete a todo
     * 
     * Business Logic:
     * 1. Check if todo exists
     * 2. If exists, delete from database
     * 
     * @param id - The ID of todo to delete
     */
    @Override
    public void deleteTodo(int id) {
        // Check if todo exists
        Todo existingTodo = todoDAO.findById(id);
        if (existingTodo == null) {
            throw new IllegalArgumentException("Todo with id " + id + " not found");
        }

        // Delete from database
        todoDAO.delete(id);

        System.out.println("✓ Todo deleted successfully (ID: " + id + ")");
    }

    /**
     * Get todos by completion status
     * 
     * @param completed - true for completed, false for incomplete
     * @return List of matching todos
     */
    @Override
    public List<Todo> getTodosByStatus(boolean completed) {
        return todoDAO.findByCompleted(completed);
    }

    /**
     * Mark a todo as completed
     * 
     * Business Logic:
     * 1. Find the todo by ID
     * 2. Set completed = true
     * 3. Update in database
     * 
     * This demonstrates how service layer can combine multiple operations
     * 
     * @param id - The ID of todo to mark as completed
     */
    @Override
    public void markAsCompleted(int id) {
        // Find the todo
        Todo todo = todoDAO.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("Todo with id " + id + " not found");
        }

        // Update the status
        todo.setCompleted(true);

        // Save to database
        todoDAO.update(todo);

        System.out.println("✓ Todo marked as completed: " + todo.getTitle());
    }

    /**
     * Mark a todo as incomplete
     * 
     * @param id - The ID of todo to mark as incomplete
     */
    @Override
    public void markAsIncomplete(int id) {
        // Find the todo
        Todo todo = todoDAO.findById(id);
        if (todo == null) {
            throw new IllegalArgumentException("Todo with id " + id + " not found");
        }

        // Update the status
        todo.setCompleted(false);

        // Save to database
        todoDAO.update(todo);

        System.out.println("✓ Todo marked as incomplete: " + todo.getTitle());
    }
}
