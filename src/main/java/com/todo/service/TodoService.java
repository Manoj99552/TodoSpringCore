package com.todo.service;

import com.todo.model.Todo;
import java.util.List;

/**
 * TodoService Interface - Business Logic Layer
 * 
 * Service Layer Purpose:
 * - Contains business logic and rules
 * - Acts as a bridge between Controller/Main and DAO layer
 * - Handles transactions
 * - Can combine multiple DAO operations
 * - Validates data before sending to DAO
 * 
 * Why separate Service from DAO?
 * - DAO: Focuses only on database operations (HOW to access data)
 * - Service: Focuses on business logic (WHAT to do with data)
 * - Example: Service might validate that title is not empty before calling
 * DAO.create()
 * 
 * Layered Architecture:
 * Main/Controller → Service → DAO → Database
 */
public interface TodoService {

    /**
     * Add a new todo item
     * Business logic: Validate todo before creating
     * 
     * @param todo - The todo to add
     */
    void addTodo(Todo todo);

    /**
     * Get a specific todo by ID
     * 
     * @param id - The todo ID
     * @return The todo if found, null otherwise
     */
    Todo getTodoById(int id);

    /**
     * Get all todos
     * 
     * @return List of all todos
     */
    List<Todo> getAllTodos();

    /**
     * Update an existing todo
     * Business logic: Validate that todo exists before updating
     * 
     * @param todo - The todo with updated values
     */
    void updateTodo(Todo todo);

    /**
     * Delete a todo
     * 
     * @param id - The ID of todo to delete
     */
    void deleteTodo(int id);

    /**
     * Get todos by completion status
     * 
     * @param completed - true for completed, false for incomplete
     * @return List of matching todos
     */
    List<Todo> getTodosByStatus(boolean completed);

    /**
     * Mark a todo as completed
     * Business logic example: Find todo, set completed=true, then update
     * 
     * @param id - The ID of todo to mark as completed
     */
    void markAsCompleted(int id);

    /**
     * Mark a todo as incomplete
     * 
     * @param id - The ID of todo to mark as incomplete
     */
    void markAsIncomplete(int id);
}
