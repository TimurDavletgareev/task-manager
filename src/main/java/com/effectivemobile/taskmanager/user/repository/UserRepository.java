package com.effectivemobile.taskmanager.user.repository;

import com.effectivemobile.taskmanager.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u " +
            "FROM User as u " +
            "WHERE u.name = :name " +
            "OR u.email = :email")
    Optional<User> findByNameOrEmail(String name, String email);
}
