package com.mika.tasks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mika.tasks.entity.User;
import com.mika.tasks.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByUser(User user);
}
