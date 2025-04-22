package com.todolist.services;

import com.todolist.dto.TaskDTO;
import com.todolist.dto.UserDTO;
import com.todolist.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserByTask(Long user_id);
    void deleteUserById(Long id);
    void createUser(UserDTO userDTO);
    Stream<UserDTO> getUserById(Long id);
}
