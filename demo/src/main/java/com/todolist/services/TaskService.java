package com.todolist.services;

import com.todolist.dto.TaskDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    List<TaskDTO> getAllTasks();
    Optional<TaskDTO> getTaskById(Long id);
    TaskDTO createTask(TaskDTO taskDTO);
    void deleteTaskById(Long id);
    void updateTaskById(Long id, TaskDTO taskDTO);
}
