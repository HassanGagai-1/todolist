package com.todolist.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import java.util.List;
import java.util.ArrayList;


@Entity
@Data
@AllArgsConstructor
@Table(name = "roles",
        uniqueConstraints = @UniqueConstraint(columnNames = "role_name")
)
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private long role_id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, length = 32)
    private roleName roleName = com.todolist.model.roleName.USER;


    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();


    public Role(long roleId, roleName roleName) {
        this.role_id  = roleId;
        this.roleName = roleName;
    }

    public Role(roleName roleName) {
        this.roleName = roleName;
    }
}
