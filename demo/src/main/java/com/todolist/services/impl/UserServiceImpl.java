package com.todolist.services.impl;


import com.todolist.controller.UserController;
import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.Role;
import com.todolist.repository.RoleRepository;
import com.todolist.repository.UserRepository;
import com.todolist.model.User;
import com.todolist.services.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import com.todolist.model.Role_name;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //    private final PasswordEncoder encoder;
    private final PasswordService passwordService;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordService passwordService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
//        this.encoder = encoder;
        this.passwordService = passwordService;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail())) // Map User to UserDTO
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createUser(UserDTO userDTO) {
        Role role = new Role();
        User user = new User();
        role.setRoleName(Role_name.USER);
        roleRepository.save(role);

        user.setRole(role);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
//        user.setPassword(passwordService.securePassword(userDTO.getPassword()));

        logger.info("Creating user: {}", user);
        userRepository.save(user);
    }

    @Override
    public Stream<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ));
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        userRepository.deleteById(id);
    }


    @Override
    public UserDTO getUserByTask(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );

        List<TaskDTO> tasks = user.getTasks().stream()
                .map(task -> new TaskDTO(
                        task.getId(),
                        task.getTask(),
                        task.getDescription(),
                        task.getCreated_at(),    // or getCreated_at()
                        task.getDueDate(),
                        task.getStatus().name(),
                        task.getPriority().name()
                ))
                .collect(Collectors.toList());
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                tasks
        );
    }

}
