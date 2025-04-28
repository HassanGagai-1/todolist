package com.todolist.services;

import com.todolist.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserDTO> getAllUsers();
    UserDTO getUserByTask(Long user_id);
    void deleteUserById(Long id);
//    void createUser(UserDTO userDTO);
    Optional<UserDTO> getUserById(Long id);
}
