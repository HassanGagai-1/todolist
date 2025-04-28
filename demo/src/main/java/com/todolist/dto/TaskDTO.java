package com.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private long id;

    private String task;

    private String description;

    private LocalDateTime created_at;

    private LocalDateTime dueDate;

    private String status;

    private String priority;

    private UserDTO user;


    public TaskDTO(long id, @NotBlank String task, String description, LocalDateTime created_at,
                   LocalDateTime dueDate, String name, String name1) {

        this.id = id;
        this.task = task;
        this.description = description;
        this.created_at = created_at;
        this.dueDate = dueDate;
        this.status = name;
        this.priority = name1;
    }
}
