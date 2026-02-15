package com.todo.dao;

import com.todo.model.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * TodoDAOImpl - Implementation of TodoDAO interface
 * 
 * This class contains the actual database operations using Spring's
 * JdbcTemplate
 * 
 * @Repository annotation:
 *             - Tells Spring this is a DAO component (Data Access Layer)
 *             - Spring will automatically create an instance of this class
 *             - Enables exception translation (converts SQL exceptions to
 *             Spring exceptions)
 */
@Repository
public class TodoDAOImpl implements TodoDAO {

    // ========== DEPENDENCY INJECTION ==========

    /**
     * JdbcTemplate - Spring's helper class for database operations
     * 
     * What it does:
     * - Simplifies JDBC code (no need to manually open/close connections)
     * - Handles resource management automatically
     * - Provides methods for executing SQL queries
     * - Converts SQL exceptions to Spring's DataAccessException
     * 
     * This will be injected by Spring automatically (via constructor injection)
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor for dependency injection
     * Spring will automatically inject JdbcTemplate when creating this bean
     * 
     * @param jdbcTemplate - The JdbcTemplate bean configured in
     *                     applicationContext.xml
     */
    public TodoDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // ========== SQL QUERIES ==========
    // These are the actual SQL statements that will be executed on the database

    private static final String INSERT_TODO = "INSERT INTO todos (title, description, completed) VALUES (?, ?, ?)";

    private static final String SELECT_TODO_BY_ID = "SELECT id, title, description, completed FROM todos WHERE id = ?";

    private static final String SELECT_ALL_TODOS = "SELECT id, title, description, completed FROM todos";

    private static final String UPDATE_TODO = "UPDATE todos SET title = ?, description = ?, completed = ? WHERE id = ?";

    private static final String DELETE_TODO = "DELETE FROM todos WHERE id = ?";

    private static final String SELECT_BY_COMPLETED = "SELECT id, title, description, completed FROM todos WHERE completed = ?";

    // ========== CRUD OPERATIONS IMPLEMENTATION ==========

    /**
     * CREATE - Insert a new todo into database
     * 
     * How it works:
     * 1. jdbcTemplate.update() executes an INSERT statement
     * 2. The ? placeholders are replaced with actual values
     * 3. Database auto-generates the id
     * 
     * @param todo - The todo object to insert
     */
    @Override
    public void create(Todo todo) {
        jdbcTemplate.update(
                INSERT_TODO, // SQL query with ? placeholders
                todo.getTitle(), // First ? replaced with title
                todo.getDescription(), // Second ? replaced with description
                todo.isCompleted() // Third ? replaced with completed status
        );
    }

    /**
     * READ - Find a todo by its ID
     * 
     * How it works:
     * 1. jdbcTemplate.queryForObject() executes a SELECT statement
     * 2. Returns a single object (or throws exception if not found)
     * 3. RowMapper converts database row to Todo object
     * 
     * @param id - The id to search for
     * @return The Todo object, or null if not found
     */
    @Override
    public Todo findById(int id) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_TODO_BY_ID, // SQL query
                    new TodoRowMapper(), // Converts ResultSet to Todo object
                    id // Value for the ? placeholder
            );
        } catch (Exception e) {
            // If todo not found, return null instead of throwing exception
            return null;
        }
    }

    /**
     * READ - Get all todos from database
     * 
     * How it works:
     * 1. jdbcTemplate.query() executes a SELECT statement
     * 2. Returns a List of objects
     * 3. RowMapper is called for each row to convert it to Todo object
     * 
     * @return List of all todos (empty list if none exist)
     */
    @Override
    public List<Todo> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL_TODOS, // SQL query
                new TodoRowMapper() // Converts each row to Todo object
        );
    }

    /**
     * UPDATE - Modify an existing todo
     * 
     * How it works:
     * 1. jdbcTemplate.update() executes an UPDATE statement
     * 2. Updates the row where id matches
     * 3. Returns number of rows affected
     * 
     * @param todo - The todo with updated values (must have valid id)
     */
    @Override
    public void update(Todo todo) {
        jdbcTemplate.update(
                UPDATE_TODO, // SQL query
                todo.getTitle(), // New title
                todo.getDescription(), // New description
                todo.isCompleted(), // New completed status
                todo.getId() // Which row to update (WHERE id = ?)
        );
    }

    /**
     * DELETE - Remove a todo from database
     * 
     * How it works:
     * 1. jdbcTemplate.update() executes a DELETE statement
     * 2. Removes the row where id matches
     * 
     * @param id - The id of the todo to delete
     */
    @Override
    public void delete(int id) {
        jdbcTemplate.update(
                DELETE_TODO, // SQL query
                id // Value for WHERE id = ?
        );
    }

    /**
     * Custom query - Find todos by completion status
     * 
     * @param completed - true for completed todos, false for incomplete
     * @return List of matching todos
     */
    @Override
    public List<Todo> findByCompleted(boolean completed) {
        return jdbcTemplate.query(
                SELECT_BY_COMPLETED,
                new TodoRowMapper(),
                completed);
    }

    // ========== ROW MAPPER ==========

    /**
     * TodoRowMapper - Converts database rows to Todo objects
     * 
     * RowMapper is a Spring interface that defines how to map a ResultSet row to an
     * object
     * 
     * Why we need this:
     * - Database returns data as ResultSet (table format)
     * - We need to convert each row into a Todo object
     * - This mapper tells Spring how to do that conversion
     */
    private static class TodoRowMapper implements RowMapper<Todo> {

        /**
         * Maps a single row from ResultSet to a Todo object
         * 
         * @param rs     - ResultSet containing the database row
         * @param rowNum - The current row number (not used here, but required by
         *               interface)
         * @return A Todo object populated with data from the database row
         * @throws SQLException if there's an error reading from ResultSet
         */
        @Override
        public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Create a new Todo object and populate it with data from database
            Todo todo = new Todo();

            // Get data from each column and set it in the Todo object
            todo.setId(rs.getInt("id")); // Get 'id' column
            todo.setTitle(rs.getString("title")); // Get 'title' column
            todo.setDescription(rs.getString("description")); // Get 'description' column
            todo.setCompleted(rs.getBoolean("completed")); // Get 'completed' column

            return todo;
        }
    }
}
