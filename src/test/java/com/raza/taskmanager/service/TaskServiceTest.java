package com.raza.taskmanager.service;

import com.raza.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task(null, "Learn Jenkins", "Build CI/CD pipeline", false);

        Task created = taskService.createTask(task);

        assertNotNull(created.getId());
        assertEquals("Learn Jenkins", created.getTitle());
    }

    @Test
    void shouldGetAllTasks() {
        taskService.createTask(new Task(null, "Task 1", "First task", false));
        taskService.createTask(new Task(null, "Task 2", "Second task", true));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    void shouldUpdateTask() {
        Task task = taskService.createTask(
                new Task(null, "Old title", "Description", false));

        Task updated = taskService.updateTask(
                task.getId(),
                new Task(null, "New title", "Updated description", true));

        assertEquals("New title", updated.getTitle());
        assertTrue(updated.isCompleted());
    }

    @Test
    void shouldDeleteTask() {
        Task task = taskService.createTask(
                new Task(null, "Delete me", "Test deletion", false));

        assertTrue(taskService.deleteTask(task.getId()));
        assertNull(taskService.getTaskById(task.getId()));
    }

    @Test
    void shouldReturnNullForNonExistingTask() {
        assertNull(taskService.getTaskById(999L));
    }
}
