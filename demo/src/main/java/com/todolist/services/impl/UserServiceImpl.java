package com.todolist.services.impl;


import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.repository.UserRepository;
import com.todolist.model.User;
import com.todolist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .parallelStream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail())) // Map User to UserDTO
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Does not exist")));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "UserID not found"));
        userRepository.deleteById(id);
    }


    @Override
    public UserDTO getUserByTask(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
        return  new UserDTO(user.getId(), user.getName(), user.getEmail());

//        List<TaskDTO> tasks = user.getTasks().stream()
//                .map(task -> new TaskDTO(
//                        task.getId(),
//                        task.getTask(),
//                        task.getDescription(),
//                        task.getCreated_at(),    // or getCreated_at()
//                        task.getDueDate(),
//                        task.getStatus().name(),
//                        task.getPriority().name()
//                ))
//                .collect(Collectors.toList());
//        return new UserDTO(
//                user.getId(),
//                user.getName(),
//                user.getEmail(),
//                tasks
//        );
    }

}
