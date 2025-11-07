package com.todolist.dto;

import com.todolist.model.Task;
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
    public static TaskDTO fromEntity(Task task) {
        TaskDTO dto = new TaskDTO();

        dto.setId(task.getId());
        dto.setTask(task.getTask());
        dto.setDescription(task.getDescription());
        dto.setCreated_at(task.getCreated_at());
        dto.setDueDate(task.getDueDate());

        if (task.getStatus() != null) {
            dto.setStatus(task.getStatus().name());
        }

        if (task.getPriority() != null) {
            dto.setPriority(task.getPriority().name());
        }

        // ✅ Convert user to UserDTO if needed
        if (task.getUser() != null) {
            dto.setUser(new UserDTO(task.getUser().getId(), task.getUser().getEmail()));
        }

        return dto;
    }



//    public TaskDTO(long id, @NotBlank String task, String description, LocalDateTime created_at,
//                   LocalDateTime dueDate, String name, String name1) {
//
//        this.id = id;
//        this.task = task;
//        this.description = description;
//        this.created_at = created_at;
//        this.dueDate = dueDate;
//        this.status = name;
//        this.priority = name1;
//    }


}
