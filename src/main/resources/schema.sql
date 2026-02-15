-- ========================================
-- Spring Core Todo Application
-- Database Schema Setup
-- ========================================

-- This SQL script creates the database and table for our Todo application
-- Run this script in MySQL before running the Java application

-- Create the database
-- IF NOT EXISTS: Only creates if database doesn't already exist
CREATE DATABASE IF NOT EXISTS tododb;

-- Switch to use this database
USE tododb;

-- Create the todos table
-- This table stores all our todo items
CREATE TABLE IF NOT EXISTS todos (
    -- id: Unique identifier for each todo
    -- INT: Integer data type
    -- AUTO_INCREMENT: Database automatically generates next number
    -- PRIMARY KEY: Uniquely identifies each row
    id INT AUTO_INCREMENT PRIMARY KEY,
    
    -- title: The name/title of the todo
    -- VARCHAR(255): Variable-length string, max 255 characters
    -- NOT NULL: This field is required (cannot be empty)
    title VARCHAR(255) NOT NULL,
    
    -- description: Detailed description of the todo
    -- TEXT: Can store long text (up to 65,535 characters)
    -- NULL allowed: This field is optional
    description TEXT,
    
    -- completed: Whether the todo is done or not
    -- BOOLEAN: True or false (stored as TINYINT(1) in MySQL)
    -- DEFAULT FALSE: New todos are incomplete by default
    completed BOOLEAN DEFAULT FALSE,
    
    -- created_at: When the todo was created
    -- TIMESTAMP: Date and time
    -- DEFAULT CURRENT_TIMESTAMP: Automatically set to current time when row is created
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- updated_at: When the todo was last modified
    -- TIMESTAMP: Date and time
    -- DEFAULT CURRENT_TIMESTAMP: Set to current time when created
    -- ON UPDATE CURRENT_TIMESTAMP: Automatically update when row is modified
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Display the table structure to verify
DESCRIBE todos;

-- Display message
SELECT 'Database and table created successfully!' AS Status;
