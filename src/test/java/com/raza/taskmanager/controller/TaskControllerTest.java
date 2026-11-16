 package com.raza.taskmanager.controller;

import com.raza.taskmanager.model.Task;
import com.raza.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void shouldCreateTask() {
        Task task = new Task(1L, "Learn Docker", "Containerization", false);

        when(taskService.createTask(task)).thenReturn(task);

        Task result = taskController.createTask(task);

        assertEquals("Learn Docker", result.getTitle());
        verify(taskService).createTask(task);
    }

    @Test
    void shouldReturnTaskById() {
        Task task = new Task(1L, "Learn Jenkins", "CI/CD", false);

        when(taskService.getTaskById(1L)).thenReturn(task);

        var response = taskController.getTaskById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Learn Jenkins", response.getBody().getTitle());
    }

    @Test
    void shouldReturnNotFoundForMissingTask() {
        when(taskService.getTaskById(99L)).thenReturn(null);

        var response = taskController.getTaskById(99L);

        assertTrue(response.getStatusCode().is4xxClientError());
    }
}
