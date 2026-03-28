package com.mika.tasks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mika.tasks.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
