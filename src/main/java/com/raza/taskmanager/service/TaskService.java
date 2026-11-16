	package com.raza.taskmanager.service;

import com.raza.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task getTaskById(Long id) {
        return tasks.get(id);
    }

    public Task createTask(Task task) {
        Long id = idCounter.incrementAndGet();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {
        if (!tasks.containsKey(id)) {
            return null;
        }

        updatedTask.setId(id);
        tasks.put(id, updatedTask);
        return updatedTask;
    }

    public boolean deleteTask(Long id) {
        return tasks.remove(id) != null;
    }
}
