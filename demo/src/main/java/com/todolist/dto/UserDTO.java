package com.todolist.dto;


import com.todolist.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private long id;
    private String name;
    private String email;
    private String password;

    private Role role;

    public UserDTO(Long id) {
        this.id = id;
    }
    private List<TaskDTO> tasks;

    public UserDTO(long id, String name, String email, List<TaskDTO> tasks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.tasks = tasks;
    }

    public UserDTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;

    }

    public UserDTO(long id, String name, String email, String Password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = Password;
    }


    public UserDTO(long id, @Email @NotBlank String email) {
    }
}
