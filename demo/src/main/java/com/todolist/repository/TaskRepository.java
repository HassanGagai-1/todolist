package com.todolist.repository;

import com.todolist.model.Task;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
