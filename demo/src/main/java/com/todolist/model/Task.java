package com.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Entity
@Table(name = "task")
@NoArgsConstructor
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @NotBlank
    @Column(name = "task", nullable = false)
    private String task;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @CreationTimestamp
    @Column(name = "dueDate")
    private LocalDateTime dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority = Priority.LOW;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

}
