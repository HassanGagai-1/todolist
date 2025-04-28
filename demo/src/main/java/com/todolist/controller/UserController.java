package com.todolist.controller;

import com.todolist.dto.UserDTO;
import com.todolist.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController()
@ResponseBody
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
            this.userService = userService;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        logger.info("Fetching all users: ");
        System.out.println("Fetching all users:");

        // Get all users
        List<UserDTO> users = userService.getAllUsers();

        logger.info("Fetched users: {}", users);

        // Return users as JSON
        return ResponseEntity.ok(users);

    }

    @GetMapping("/users_task/{id}")
    public ResponseEntity<UserDTO> getUserByTask(@PathVariable Long id) {
        logger.info("Fetching users by task: ");
        UserDTO user_tasks = userService.getUserByTask(id);
        logger.info("Fetched users by task: {}", user_tasks);
        return ResponseEntity.ok(user_tasks);

    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        logger.info("Deleting task by id: {}", id);
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<UserDTO>> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id: {}", id);
        Optional<UserDTO> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }





}