package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.services.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<TaskDTO>> getTaskById(@PathVariable Long id) {
        logger.info("Fetching task by id: {}", id);
        Optional<TaskDTO> tasks = taskService.getTaskById(id);
        logger.info("Fetched tasks: {}", tasks);
        return ResponseEntity.ok(tasks); // automatic conversion to JSON
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO taskDTO) {

        logger.info("Creating task: {}", taskDTO);
        TaskDTO savedTask = taskService.createTask(taskDTO);
        return ResponseEntity.ok(
                java.util.Map.of(
                        "message", "Task created successfully",
                        "taskId", savedTask.getId()
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTaskById(@PathVariable Long id) {
        logger.info("Deleting task by id: {}", id);
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateTaskById(@PathVariable Long id,@RequestBody TaskDTO taskDTO) {
            logger.info("Updating task by id: {}", id);
            taskService.updateTaskById(id,taskDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
