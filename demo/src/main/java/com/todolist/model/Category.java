package com.todolist.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id",nullable = false)
    private long category_id;

    @NotBlank
    @Column(name = "category_name", nullable = false)
    private String category_name;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @OneToOne(mappedBy = "category")
    private Task task;


}
