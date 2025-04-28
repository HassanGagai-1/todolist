package com.todolist.services.impl;


import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.Priority;
import com.todolist.model.Status;
import com.todolist.model.Task;
import com.todolist.model.User;
import com.todolist.repository.TaskRepository;
import com.todolist.repository.UserRepository;
import com.todolist.services.TaskService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .parallelStream()
                .map(task -> new TaskDTO(
                        task.getId(), task.getTask(), task.getDescription(),
                task.getCreated_at(), task.getDueDate(), task.getStatus().name(),
                        task.getPriority().name(),
                new UserDTO(
                        task.getUser().getId()
                )
                ))
                .collect(Collectors.toList());
    }




    public Optional<TaskDTO> getTaskById(Long id) {
        logger.info("Getting task by ID: {}", id);
        return Optional.ofNullable(taskRepository.findById(id)
                .map(task -> new TaskDTO(task.getId(), task.getTask(), task.getDescription(),
                        task.getCreated_at(), task.getDueDate(),
                        task.getStatus().name(), task.getPriority().name(),
                        new UserDTO(
                                task.getUser().getId()
                        )
                ))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found")
                ));
    }

    @Transactional
    public void createTask(TaskDTO taskDTO) {
        if (taskDTO == null || taskDTO.getUser() == null || taskDTO.getUser().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must be provided");
        }
        if (taskDTO.getTask() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task must be provided");
        }
        if (taskDTO.getDueDate() == taskDTO.getCreated_at()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task due date must be different from created date");
        }

        Task task = new Task();
        task.setTask(
                taskDTO.getTask()
        );
        task.setDescription(taskDTO.getDescription());
        task.setCreated_at(LocalDateTime.now());
        task.setDueDate(taskDTO.getDueDate());

        try {
            task.setStatus(Status.valueOf(taskDTO.getStatus()));
            task.setPriority(Priority.valueOf(taskDTO.getPriority()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status or priority value");
        }

        User user = userRepository.findById(taskDTO.getUser().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        task.setUser(user);
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTaskById(Long id) {
        taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskRepository.deleteById(id);

    }



    @Override
    @Transactional
    public void updateTaskById(Long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task ID not found"));
        if (taskDTO == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "task Object is null");
        } else if (taskDTO.getUser() == null || taskDTO.getUser().getId() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user ID not provided");
        }


        if (taskDTO.getTask() != null ) task.setTask(taskDTO.getTask());
        if (taskDTO.getDescription() != null ) task.setDescription(taskDTO.getDescription());
        if (taskDTO.getCreated_at() != null ) task.setCreated_at(LocalDateTime.now());
        if (taskDTO.getDueDate() != null ) task.setDueDate(taskDTO.getDueDate());
        if (taskDTO.getStatus() != null) {
            try {
                task.setStatus(Status.valueOf(taskDTO.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid status value");
            }
        }

        if (taskDTO.getPriority() != null) {
            try {
                task.setPriority(Priority.valueOf(taskDTO.getPriority()));
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid priority value");
            }
        }


        taskRepository.save(task);
    }


}
