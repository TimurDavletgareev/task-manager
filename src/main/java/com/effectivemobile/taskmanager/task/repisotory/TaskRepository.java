package com.effectivemobile.taskmanager.task.repisotory;

import com.effectivemobile.taskmanager.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByAuthorId(Long authorId, Pageable pageable);
}
