package com.todolist.services;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.services.impl.TaskServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public interface TaskService {

    List<TaskDTO> getAllTasks();
    Optional<TaskDTO> getTaskById(Long id);
    void createTask(TaskDTO taskDTO);
    void deleteTaskById(Long id);
    void updateTaskById(Long id, TaskDTO taskDTO);
}
