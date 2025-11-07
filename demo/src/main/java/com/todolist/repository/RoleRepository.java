package com.todolist.repository;

import com.todolist.model.Role;

import com.todolist.model.roleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(roleName roleName);

}
