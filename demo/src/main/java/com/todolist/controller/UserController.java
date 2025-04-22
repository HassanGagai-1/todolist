package com.todolist.controller;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.Task;
import com.todolist.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;


@RestController
@ResponseBody
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
            this.userService = userService;
    }

    @GetMapping("/users")
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


    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        logger.info("Deleting task by id: {}", id);
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        logger.info("Creating user: {}", userDTO);

        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Stream<UserDTO>> getUserById(@PathVariable Long id) {
        logger.info("Fetching user by id: {}", id);
        Stream<UserDTO> user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }



//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO)  {
//        UserDTO createdUser  = userServices.registerUser(userDTO);
//        return ResponseEntity.ok(createdUser);
//
//    }

//    @GetMapping("/findbyID")
//    public User findByID(@RequestParam int id){
//        return userServices.findById(id);
//    }
//
//    @PostMapping("/updateUser")
//    public User updateUser(@RequestBody User users,@RequestParam int id){
//        return userServices.updateUser(id,users);
//    }
//
//    @DeleteMapping("/deleteUser")
//    public void deleteUserByID(@RequestParam int id){
//        userServices.deleteUserByID(id);
//    }



}