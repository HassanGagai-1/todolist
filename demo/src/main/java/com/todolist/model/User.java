package com.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@AllArgsConstructor
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_info")
@ToString
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;

    @NotBlank
    @Column(name="name", nullable = false)
    private String name;

    @Email
    @NotBlank
    @Column(name="email", nullable = false)
    private String email;

    @NotNull
//    @Size(min = 5, max = 30, message = "Password should be between 5 to 30 characters")
    @Column(name="password", nullable = false)
    private String password;

    @Setter
    @ManyToOne
    @JoinColumn(name="role_id",nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories = new ArrayList<>();


}